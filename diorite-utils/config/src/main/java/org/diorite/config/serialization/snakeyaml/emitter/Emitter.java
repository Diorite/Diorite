/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.diorite.config.serialization.snakeyaml.emitter;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.DumperOptions.Version;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.emitter.EmitterException;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.CollectionEndEvent;
import org.yaml.snakeyaml.events.CollectionStartEvent;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.ImplicitTuple;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.NodeEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.events.SequenceStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Constant;
import org.yaml.snakeyaml.util.ArrayStack;

import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.config.serialization.Serialization;
import org.diorite.config.serialization.snakeyaml.DumperOptions;

/**
 * <pre>
 * Emitter expects events obeying the following grammar:
 * stream ::= STREAM-START document* STREAM-END
 * document ::= DOCUMENT-START node DOCUMENT-END
 * node ::= SCALAR | sequence | mapping
 * sequence ::= SEQUENCE-START node* SEQUENCE-END
 * mapping ::= MAPPING-START (node node)* MAPPING-END
 * </pre>
 */
public final class Emitter implements Emitable
{
    private static final Map<Character, String> ESCAPE_REPLACEMENTS = new HashMap<>(15);
    public static final  int                    MIN_INDENT          = 1;
    public static final  int                    MAX_INDENT          = 10;

    private static final char[] SPACE        = {' '};
    public static final  int    BEST_WIDTH   = 80;
    public static final  int    SMALL_LENGTH = 128;
    public static final  int    HEX_RADIX    = 16;

    static
    {
        ESCAPE_REPLACEMENTS.put('\0', "0");
        ESCAPE_REPLACEMENTS.put('\u0007', "a");
        ESCAPE_REPLACEMENTS.put('\u0008', "b");
        ESCAPE_REPLACEMENTS.put('\u0009', "t");
        ESCAPE_REPLACEMENTS.put('\n', "n");
        ESCAPE_REPLACEMENTS.put('\u000B', "v");
        ESCAPE_REPLACEMENTS.put('\u000C', "f");
        ESCAPE_REPLACEMENTS.put('\r', "r");
        ESCAPE_REPLACEMENTS.put('\u001B', "e");
        ESCAPE_REPLACEMENTS.put('"', "\"");
        ESCAPE_REPLACEMENTS.put('\\', "\\");
        ESCAPE_REPLACEMENTS.put('\u0085', "N");
        ESCAPE_REPLACEMENTS.put('\u00A0', "_");
        ESCAPE_REPLACEMENTS.put('\u2028', "L");
        ESCAPE_REPLACEMENTS.put('\u2029', "P");
    }

    static final Map<String, String> DEFAULT_TAG_PREFIXES = new LinkedHashMap<>(2);

    static
    {
        DEFAULT_TAG_PREFIXES.put("!", "!");
        DEFAULT_TAG_PREFIXES.put(Tag.PREFIX, "!!");
    }

    private final Serialization serialization;

    // The stream should have the methods `write` and possibly `flush`.
    private final Writer stream;

    // Encoding is defined by Writer (cannot be overriden by STREAM-START.)
    // private Charset encoding;

    // Emitter is a state machine with a stack of states to handle nested
    // structures.
    final ArrayStack<EmitterState> states;
    EmitterState state;

    // Current event and the event queue.
    private final Queue<Event> events;
    @Nullable     Event        event;

    // The current indentation level and the stack of previous indents.
    final     ArrayStack<Integer> indents;
    @Nullable Integer             indent;

    // Flow level.
    int flowLevel;

    // Contexts.
    private boolean rootContext;
    private boolean mappingContext;
    private boolean simpleKeyContext;

    //
    // Characteristics of the last emitted character:
    // - current position.
    // - is it a whitespace?
    // - is it an indention character
    // (indentation space, '-', '?', or ':')?
    // private int line; this variable is not used
    int column;
    private boolean whitespace;
    private boolean indention;
    boolean openEnded;

    // Formatting details.
    Boolean canonical;
    // pretty print flow by adding extra line breaks
    Boolean prettyFlow;

    private boolean allowUnicode;
    private int     bestIndent;
    int indicatorIndent;
    int bestWidth;
    private char[] bestLineBreak;
    boolean splitLines;
    private int     longCommentThreshold;
    private String  longCommentBorder;
    private boolean longCommentBorderStartsWithComment;
    private String  longCommentRightBorder;

    // Tag prefixes.
    Map<String, String> tagPrefixes;

    // Prepared anchor and tag.
    @Nullable private String preparedAnchor;
    @Nullable private String preparedTag;

    // Scalar analysis and style.
    @Nullable private ScalarAnalysis analysis;
    @Nullable private Character      style;

    public Emitter(Serialization serialization, Writer stream, DumperOptions opts)
    {
        this.serialization = serialization;
        // The stream should have the methods `write` and possibly `flush`.
        this.stream = stream;
        // Emitter is a state machine with a stack of states to handle nested
        // structures.
        this.states = new ArrayStack<>(100);
        this.state = new ExpectStreamStart();
        // Current event and the event queue.
        this.events = new ArrayBlockingQueue<>(100);
        this.event = null;
        // The current indentation level and the stack of previous indents.
        this.indents = new ArrayStack<>(10);
        this.indent = null;
        // Flow level.
        this.flowLevel = 0;
        // Contexts.
        this.mappingContext = false;
        this.simpleKeyContext = false;

        //
        // Characteristics of the last emitted character:
        // - current position.
        // - is it a whitespace?
        // - is it an indention character
        // (indentation space, '-', '?', or ':')?
        this.column = 0;
        this.whitespace = true;
        this.indention = true;

        // Whether the document requires an explicit document indicator
        this.openEnded = false;

        // Formatting details.
        this.canonical = opts.isCanonical();
        this.prettyFlow = opts.isPrettyFlow();
        this.allowUnicode = opts.isAllowUnicode();
        this.bestIndent = 2;
        if ((opts.getIndent() > MIN_INDENT) && (opts.getIndent() < MAX_INDENT))
        {
            this.bestIndent = opts.getIndent();
        }
        this.indicatorIndent = opts.getIndicatorIndent();
        this.bestWidth = BEST_WIDTH;
        if (opts.getWidth() > (this.bestIndent * 2))
        {
            this.bestWidth = opts.getWidth();
        }
        this.bestLineBreak = opts.getLineBreak().getString().toCharArray();
        this.splitLines = opts.getSplitLines();
        this.longCommentThreshold = opts.getLongCommentThreshold();
        this.longCommentBorder = opts.getLongCommentBorder();
        this.longCommentBorderStartsWithComment = ! this.longCommentBorder.isEmpty() && (this.longCommentBorder.charAt(0) == '#');
        this.longCommentRightBorder = opts.getLongCommentRightBorder();

        // Tag prefixes.
        this.tagPrefixes = new LinkedHashMap<>(10);

        // Prepared anchor and tag.
        this.preparedAnchor = null;
        this.preparedTag = null;

        // Scalar analysis and style.
        this.analysis = null;
        this.style = null;
    }

    @Override
    public void emit(Event event) throws IOException
    {
        this.events.add(event);
        while (! this.needMoreEvents())
        {
            this.event = this.events.poll();
            this.state.expect(this);
            this.event = null;
        }
    }

    // In some cases, we wait for a few next events before emitting.

    private boolean needMoreEvents()
    {
        if (this.events.isEmpty())
        {
            return true;
        }
        Event event = this.events.peek();
        if (event instanceof DocumentStartEvent)
        {
            return this.needEvents(1);
        }
        else if (event instanceof SequenceStartEvent)
        {
            return this.needEvents(2);
        }
        else if (event instanceof MappingStartEvent)
        {
            return this.needEvents(3);
        }
        else
        {
            return false;
        }
    }

    private boolean needEvents(int count)
    {
        int level = 0;
        Iterator<Event> iter = this.events.iterator();
        iter.next();
        while (iter.hasNext())
        {
            Event event = iter.next();
            if ((event instanceof DocumentStartEvent) || (event instanceof CollectionStartEvent))
            {
                level++;
            }
            else if ((event instanceof DocumentEndEvent) || (event instanceof CollectionEndEvent))
            {
                level--;
            }
            else if (event instanceof StreamEndEvent)
            {
                level = - 1;
            }
            if (level < 0)
            {
                return false;
            }
        }
        return this.events.size() < (count + 1);
    }

    private void increaseIndent(boolean flow, boolean indentLess)
    {
        this.indents.push(this.indent);
        if (this.indent == null)
        {
            if (flow)
            {
                this.indent = this.bestIndent;
            }
            else
            {
                this.indent = 0;
            }
        }
        else if (! indentLess)
        {
            this.indent += this.bestIndent;
        }
    }

    void expectNode(boolean root, boolean mapping, boolean simpleKey) throws IOException
    {
        this.expectNode(root, mapping, simpleKey, this.indent);
    }

    void expectNode(boolean root, boolean mapping, boolean simpleKey, @Nullable Integer lastIndent) throws IOException
    {
        this.rootContext = root;
        this.mappingContext = mapping;
        this.simpleKeyContext = simpleKey;
        if (this.event instanceof AliasEvent)
        {
            this.expectAlias();
        }
        else if ((this.event instanceof ScalarEvent) || (this.event instanceof CollectionStartEvent))
        {
            this.processAnchor("&");
            this.processTag();
            if (this.event instanceof ScalarEvent)
            {
                this.expectScalar(lastIndent);
            }
            else if (this.event instanceof SequenceStartEvent)
            {
                if ((this.flowLevel != 0) || this.canonical || ((SequenceStartEvent) this.event).getFlowStyle()
                    || this.checkEmptySequence())
                {
                    this.expectFlowSequence();
                }
                else
                {
                    this.expectBlockSequence();
                }
            }
            else
            {// MappingStartEvent
                if ((this.flowLevel != 0) || this.canonical || ((MappingStartEvent) this.event).getFlowStyle() || this.checkEmptyMapping())
                {
                    this.expectFlowMapping();
                }
                else
                {
                    this.expectBlockMapping();
                }
            }
        }
        else
        {
            throw new EmitterException("expected NodeEvent, but got " + this.event);
        }
    }

    private void expectAlias() throws IOException
    {
        assert this.event != null;
        if (((NodeEvent) this.event).getAnchor() == null)
        {
            throw new EmitterException("anchor is not specified for alias");
        }
        this.processAnchor("*");
        this.state = this.states.pop();
    }

    private void expectScalar(@Nullable Integer lastIndent) throws IOException
    {
        assert this.event != null;
        ScalarEvent ev = (ScalarEvent) this.event;

        ImplicitTuple implicitTuple = ev.getImplicit();
        if (implicitTuple instanceof ImplicitTupleExtension)
        {
            this.writeCommentSafe(((ImplicitTupleExtension) implicitTuple).getComment(), 1, 1);
        }
        this.writeIndentForce(lastIndent);

        this.increaseIndent(true, false);
        this.processScalar();
        this.indent = this.indents.pop();
        this.state = this.states.pop();
    }

    // Flow sequence handlers.

    private void expectFlowSequence() throws IOException
    {
        this.writeIndicator("[", true, true, false);
        this.flowLevel++;
        this.increaseIndent(true, false);
        if (this.prettyFlow)
        {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowSequenceItem();
    }

    // Flow mapping handlers.

    private void expectFlowMapping() throws IOException
    {
        this.writeIndicator("{", true, true, false);
        this.flowLevel++;
        this.increaseIndent(true, false);
        if (this.prettyFlow)
        {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowMappingKey();
    }

    // Block sequence handlers.

    private void expectBlockSequence() throws IOException
    {
        boolean indentLess = this.mappingContext && ! this.indention;
        this.increaseIndent(false, indentLess);
        this.state = new ExpectFirstBlockSequenceItem();
    }

    // Block mapping handlers.
    private void expectBlockMapping() throws IOException
    {
        this.increaseIndent(false, false);
        this.state = new ExpectFirstBlockMappingKey();
    }

    // Checkers.

    private boolean checkEmptySequence()
    {
        return (this.event instanceof SequenceStartEvent) && ! this.events.isEmpty() && (this.events.peek() instanceof SequenceEndEvent);
    }

    private boolean checkEmptyMapping()
    {
        return (this.event instanceof MappingStartEvent) && ! this.events.isEmpty() && (this.events.peek() instanceof MappingEndEvent);
    }

    boolean checkEmptyDocument()
    {
        if (! (this.event instanceof DocumentStartEvent) || this.events.isEmpty())
        {
            return false;
        }
        Event event = this.events.peek();
        if (event instanceof ScalarEvent)
        {
            ScalarEvent e = (ScalarEvent) event;
            return (e.getAnchor() == null) && (e.getTag() == null) && (e.getImplicit() != null) && e.getValue().isEmpty();
        }
        return false;
    }

    boolean checkSimpleKey()
    {
        int length = 0;
        if ((this.event instanceof NodeEvent) && (((NodeEvent) this.event).getAnchor() != null))
        {
            if (this.preparedAnchor == null)
            {
                this.preparedAnchor = prepareAnchor(((NodeEvent) this.event).getAnchor());
            }
            length += this.preparedAnchor.length();
        }
        String tag = null;
        if (this.event instanceof ScalarEvent)
        {
            tag = ((ScalarEvent) this.event).getTag();
        }
        else if (this.event instanceof CollectionStartEvent)
        {
            tag = ((CollectionStartEvent) this.event).getTag();
        }
        if (tag != null)
        {
            if (this.preparedTag == null)
            {
                this.preparedTag = this.prepareTag(tag);
            }
            length += this.preparedTag.length();
        }
        if (this.event instanceof ScalarEvent)
        {
            if (this.analysis == null)
            {
                this.analysis = this.analyzeScalar(((ScalarEvent) this.event).getValue());
            }
            length += this.analysis.scalar.length();
        }
        return (length < SMALL_LENGTH) && ((this.event instanceof AliasEvent) ||
                                           ((this.event instanceof ScalarEvent) && ! ((this.analysis == null) || this.analysis.empty) &&
                                            ! this.analysis.multiline) || this.checkEmptySequence() || this.checkEmptyMapping());
    }

    // Anchor, Tag, and Scalar processors.

    private void processAnchor(String indicator) throws IOException
    {
        assert this.event != null;
        NodeEvent ev = (NodeEvent) this.event;
        if (ev.getAnchor() == null)
        {
            this.preparedAnchor = null;
            return;
        }
        if (this.preparedAnchor == null)
        {
            this.preparedAnchor = prepareAnchor(ev.getAnchor());
        }
        this.writeIndicator(indicator + this.preparedAnchor, true, false, false);
        this.preparedAnchor = null;
    }

    private void processTag() throws IOException
    {
        assert this.event != null;
        String tag;
        if (this.event instanceof ScalarEvent)
        {
            ScalarEvent ev = (ScalarEvent) this.event;
            tag = ev.getTag();
            if (this.style == null)
            {
                this.style = this.chooseScalarStyle();
            }
            if ((! this.canonical || (tag == null)) && (((this.style == null) && ev.getImplicit().canOmitTagInPlainScalar()) ||
                                                        ((this.style != null) && ev.getImplicit().canOmitTagInNonPlainScalar())))
            {
                this.preparedTag = null;
                return;
            }
            if (ev.getImplicit().canOmitTagInPlainScalar() && (tag == null))
            {
                tag = "!";
                this.preparedTag = null;
            }
        }
        else
        {
            CollectionStartEvent ev = (CollectionStartEvent) this.event;
            tag = ev.getTag();
            if ((! this.canonical || (tag == null)) && ev.getImplicit())
            {
                this.preparedTag = null;
                return;
            }
        }
        if (tag == null)
        {
            throw new EmitterException("tag is not specified");
        }
        if (this.preparedTag == null)
        {
            this.preparedTag = this.prepareTag(tag);
        }
        this.writeIndicator(this.preparedTag, true, false, false);
        this.preparedTag = null;
    }

    @Nullable
    private Character chooseScalarStyle()
    {
        assert this.event != null;
        ScalarEvent ev = (ScalarEvent) this.event;
        if (this.analysis == null)
        {
            this.analysis = this.analyzeScalar(ev.getValue());
        }
        if (((ev.getStyle() != null) && (ev.getStyle() == '"')) || this.canonical)
        {
            return '"';
        }
        if ((ev.getStyle() == null) && ev.getImplicit().canOmitTagInPlainScalar())
        {
            if (! (this.simpleKeyContext && (this.analysis.empty || this.analysis.multiline))
                && (((this.flowLevel != 0) && this.analysis.allowFlowPlain) || ((this.flowLevel == 0) && this.analysis.allowBlockPlain)))
            {
                return null;
            }
        }
        if ((ev.getStyle() != null) && ((ev.getStyle() == '|') || (ev.getStyle() == '>')))
        {
            if ((this.flowLevel == 0) && ! this.simpleKeyContext && this.analysis.allowBlock)
            {
                return ev.getStyle();
            }
        }
        if ((ev.getStyle() == null) || (ev.getStyle() == '\''))
        {
            if (this.analysis.allowSingleQuoted && ! (this.simpleKeyContext && this.analysis.multiline))
            {
                return '\'';
            }
        }
        return '"';
    }

    private void processScalar() throws IOException
    {
        assert this.event != null;
        ScalarEvent ev = (ScalarEvent) this.event;
        if (this.analysis == null)
        {
            this.analysis = this.analyzeScalar(ev.getValue());
        }
        if (this.style == null)
        {
            this.style = this.chooseScalarStyle();
        }
        boolean split = ! this.simpleKeyContext && this.splitLines;
        if (this.style == null)
        {
            this.writePlain(this.analysis.scalar, split);
        }
        else
        {
            switch (this.style)
            {
                case '"':
                    this.writeDoubleQuoted(this.analysis.scalar, split);
                    break;
                case '\'':
                    this.writeSingleQuoted(this.analysis.scalar, split);
                    break;
                case '>':
                    this.writeFolded(this.analysis.scalar, split);
                    break;
                case '|':
                    this.writeLiteral(this.analysis.scalar);
                    break;
                default:
                    throw new YAMLException("Unexpected style: " + this.style);
            }
        }
        this.analysis = null;
        this.style = null;
    }

    // Analyzers.

    String prepareVersion(Version version)
    {
        if (version.major() != 1)
        {
            throw new EmitterException("unsupported YAML version: " + version);
        }
        return version.getRepresentation();
    }

    private static final Pattern HANDLE_FORMAT = Pattern.compile("^![-_\\w]*!$");

    String prepareTagHandle(String handle)
    {
        if (handle.isEmpty())
        {
            throw new EmitterException("tag handle must not be empty");
        }
        if ((handle.charAt(0) != '!') || (handle.charAt(handle.length() - 1) != '!'))
        {
            throw new EmitterException("tag handle must start and end with '!': " + handle);
        }
        if (! "!".equals(handle) && ! HANDLE_FORMAT.matcher(handle).matches())
        {
            throw new EmitterException("invalid character in the tag handle: " + handle);
        }
        return handle;
    }

    String prepareTagPrefix(String prefix)
    {
        if (prefix.isEmpty())
        {
            throw new EmitterException("tag prefix must not be empty");
        }
        StringBuilder chunks = new StringBuilder();
        int start = 0;
        int end = 0;
        if (prefix.charAt(0) == '!')
        {
            end = 1;
        }
        while (end < prefix.length())
        {
            end++;
        }
        if (start < end)
        {
            chunks.append(prefix.substring(start, end));
        }
        return chunks.toString();
    }

    private String prepareTag(String tag)
    {
        if (tag.isEmpty())
        {
            throw new EmitterException("tag must not be empty");
        }
        if ("!".equals(tag))
        {
            return tag;
        }
        String handle = null;
        String suffix = tag;
        // shall the tag prefixes be sorted as in PyYAML?
        for (String prefix : this.tagPrefixes.keySet())
        {
            if (tag.startsWith(prefix) && ("!".equals(prefix) || (prefix.length() < tag.length())))
            {
                handle = prefix;
            }
        }
        if (handle != null)
        {
            suffix = tag.substring(handle.length());
            handle = this.tagPrefixes.get(handle);
        }

        int end = suffix.length();
        String suffixText = (end > 0) ? suffix.substring(0, end) : "";

        if (handle != null)
        {
            return handle + suffixText;
        }
        return "!<" + suffixText + ">";
    }

    private static final Pattern ANCHOR_FORMAT = Pattern.compile("^[-_\\w]*$");

    static String prepareAnchor(String anchor)
    {
        if (anchor.isEmpty())
        {
            throw new EmitterException("anchor must not be empty");
        }
        if (! ANCHOR_FORMAT.matcher(anchor).matches())
        {
            throw new EmitterException("invalid character in the anchor: " + anchor);
        }
        return anchor;
    }

    private ScalarAnalysis analyzeScalar(String scalar)
    {
        // Empty scalar is a special case.
        if (scalar.isEmpty())
        {
            return new ScalarAnalysis(scalar, true, false, false, true, true, false);
        }
        // Indicators and special characters.
        boolean blockIndicators = false;
        boolean flowIndicators = false;
        boolean lineBreaks = false;
        boolean specialCharacters = false;

        // Important whitespace combinations.
        boolean leadingSpace = false;
        boolean leadingBreak = false;
        boolean trailingSpace = false;
        boolean trailingBreak = false;
        boolean breakSpace = false;
        boolean spaceBreak = false;

        // Check document indicators.
        if (scalar.startsWith("---") || scalar.startsWith("..."))
        {
            blockIndicators = true;
            flowIndicators = true;
        }
        // First character or preceded by a whitespace.
        boolean precededByWhitespace = true;
        boolean followedByWhitespace = (scalar.length() == 1) || Constant.NULL_BL_T_LINEBR.has(scalar.charAt(1));
        // The previous character is a space.
        boolean previousSpace = false;

        // The previous character is a break.
        boolean previousBreak = false;

        int index = 0;

        while (index < scalar.length())
        {
            char ch = scalar.charAt(index);
            // Check for indicators.
            if (index == 0)
            {
                // Leading indicators are special characters.
                if ("#,[]{}&*!|>\'\"%@`".indexOf(ch) != - 1)
                {
                    flowIndicators = true;
                    blockIndicators = true;
                }
                if ((ch == '?') || (ch == ':'))
                {
                    flowIndicators = true;
                    if (followedByWhitespace)
                    {
                        blockIndicators = true;
                    }
                }
                if ((ch == '-') && followedByWhitespace)
                {
                    flowIndicators = true;
                    blockIndicators = true;
                }
            }
            else
            {
                // Some indicators cannot appear within a scalar as well.
                if (",?[]{}".indexOf(ch) != - 1)
                {
                    flowIndicators = true;
                }
                if (ch == ':')
                {
                    flowIndicators = true;
                    if (followedByWhitespace)
                    {
                        blockIndicators = true;
                    }
                }
                if ((ch == '#') && precededByWhitespace)
                {
                    flowIndicators = true;
                    blockIndicators = true;
                }
            }
            // Check for line breaks, special, and unicode characters.
            boolean isLineBreak = Constant.LINEBR.has(ch);
            if (isLineBreak)
            {
                lineBreaks = true;
            }
            if (! ((ch == '\n') || (('\u0020' <= ch) && (ch <= '\u007E'))))
            {
                if (((ch == '\u0085') || (('\u00A0' <= ch) && (ch <= '\uD7FF')) || (('\uE000' <= ch) && (ch <= '\uFFFD')))
                    && (ch != '\uFEFF'))
                {
                    // unicode is used
                    if (! this.allowUnicode)
                    {
                        specialCharacters = true;
                    }
                }
                else
                {
                    specialCharacters = true;
                }
            }
            // Detect important whitespace combinations.
            if (ch == ' ')
            {
                if (index == 0)
                {
                    leadingSpace = true;
                }
                if (index == (scalar.length() - 1))
                {
                    trailingSpace = true;
                }
                if (previousBreak)
                {
                    breakSpace = true;
                }
                previousSpace = true;
                previousBreak = false;
            }
            else if (isLineBreak)
            {
                if (index == 0)
                {
                    leadingBreak = true;
                }
                if (index == (scalar.length() - 1))
                {
                    trailingBreak = true;
                }
                if (previousSpace)
                {
                    spaceBreak = true;
                }
                previousSpace = false;
                previousBreak = true;
            }
            else
            {
                previousSpace = false;
                previousBreak = false;
            }

            // Prepare for the next character.
            index++;
            precededByWhitespace = Constant.NULL_BL_T.has(ch) || isLineBreak;
            followedByWhitespace = ((index + 1) >= scalar.length())
                                   || (Constant.NULL_BL_T.has(scalar.charAt(index + 1))) || isLineBreak;
        }
        // Let's decide what styles are allowed.
        boolean allowFlowPlain = true;
        boolean allowBlockPlain = true;
        boolean allowSingleQuoted = true;
        boolean allowBlock = true;
        // Leading and trailing whitespaces are bad for plain scalars.
        if (leadingSpace || leadingBreak || trailingSpace || trailingBreak)
        {
            allowFlowPlain = allowBlockPlain = false;
        }
        // We do not permit trailing spaces for block scalars.
        if (trailingSpace)
        {
            allowBlock = false;
        }
        // Spaces at the beginning of a new line are only acceptable for block
        // scalars.
        if (breakSpace)
        {
            allowFlowPlain = allowBlockPlain = allowSingleQuoted = false;
        }
        // Spaces followed by breaks, as well as special character are only
        // allowed for double quoted scalars.
        if (spaceBreak || specialCharacters)
        {
            allowFlowPlain = allowBlockPlain = allowSingleQuoted = allowBlock = false;
        }
        // Although the plain scalar writer supports breaks, we never emit
        // multiline plain scalars in the flow context.
        if (lineBreaks)
        {
            allowFlowPlain = false;
        }
        // Flow indicators are forbidden for flow plain scalars.
        if (flowIndicators)
        {
            allowFlowPlain = false;
        }
        // Block indicators are forbidden for block plain scalars.
        if (blockIndicators)
        {
            allowBlockPlain = false;
        }

        return new ScalarAnalysis(scalar, false, lineBreaks, allowFlowPlain, allowBlockPlain, allowSingleQuoted, allowBlock);
    }

    // Writers.

    void flushStream() throws IOException
    {
        this.stream.flush();
    }

    void writeStreamStart()
    {
        // BOM is written by Writer.
    }

    void writeStreamEnd() throws IOException
    {
        this.flushStream();
    }

    void writeIndicator(String indicator, boolean needWhitespace, boolean whitespace, boolean indentation) throws IOException
    {
        if (! this.whitespace && needWhitespace)
        {
            this.column++;
            this.stream.write(SPACE);
        }
        this.whitespace = whitespace;
        this.indention = this.indention && indentation;
        this.column += indicator.length();
        this.openEnded = false;
        this.stream.write(indicator);
    }

    void writeIndentMod(int mod) throws IOException
    {
        int indent;
        if (this.indent != null)
        {
            indent = this.indent;
        }
        else
        {
            indent = 0;
        }
        if ((indent > 0) && ((indent + mod) < 0))
        {
            this.writeIndent(0);
        }
        this.writeIndent(indent + mod);
    }

    void writeIndent() throws IOException
    {
        this.writeIndentMod(0);
    }

    void writeIndentForce(@Nullable Integer realIndent) throws IOException
    {
        int indent;
        if (realIndent != null)
        {
            indent = realIndent;
        }
        else
        {
            indent = 0;
        }
        this.writeWhitespace(indent - this.column);
    }

    void writeIndent(int indent) throws IOException
    {
        if (! this.indention || (this.column > indent) || ((this.column == indent) && ! this.whitespace))
        {
            this.writeLineBreak(null);
        }

        this.writeWhitespace(indent - this.column);
    }

    void writeWhitespace(int length) throws IOException
    {
        if (length <= 0)
        {
            return;
        }
        this.whitespace = true;
        char[] data = new char[length];
        for (int i = 0; i < data.length; i++)
        {
            data[i] = ' ';
        }
        this.column += length;
        this.stream.write(data);
    }

    private void writeLineBreak(@Nullable String data) throws IOException
    {
        this.whitespace = true;
        this.indention = true;
        this.column = 0;
        if (data == null)
        {
            this.stream.write(this.bestLineBreak);
        }
        else
        {
            this.stream.write(data);
        }
    }

    void writeVersionDirective(String versionText) throws IOException
    {
        this.stream.write("%YAML ");
        this.stream.write(versionText);
        this.writeLineBreak(null);
    }

    void writeTagDirective(String handleText, String prefixText) throws IOException
    {
        // XXX: not sure 4 invocations better then StringBuilders created by str
        // + str
        this.stream.write("%TAG ");
        this.stream.write(handleText);
        this.stream.write(SPACE);
        this.stream.write(prefixText);
        this.writeLineBreak(null);
    }

    // Scalar streams.
    private void writeSingleQuoted(String text, boolean split) throws IOException
    {
        this.writeIndicator("'", true, false, false);
        boolean spaces = false;
        boolean breaks = false;
        int start = 0, end = 0;
        char ch;
        while (end <= text.length())
        {
            ch = 0;
            if (end < text.length())
            {
                ch = text.charAt(end);
            }
            if (spaces)
            {
                if ((ch == 0) || (ch != ' '))
                {
                    if (((start + 1) == end) && (this.column > this.bestWidth) && split && (start != 0)
                        && (end != text.length()))
                    {
                        this.writeIndent();
                    }
                    else
                    {
                        int len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                    }
                    start = end;
                }
            }
            else if (breaks)
            {
                if ((ch == 0) || Constant.LINEBR.hasNo(ch))
                {
                    if (text.charAt(start) == '\n')
                    {
                        this.writeLineBreak(null);
                    }
                    String data = text.substring(start, end);
                    for (char br : data.toCharArray())
                    {
                        if (br == '\n')
                        {
                            this.writeLineBreak(null);
                        }
                        else
                        {
                            this.writeLineBreak(String.valueOf(br));
                        }
                    }
                    this.writeIndent();
                    start = end;
                }
            }
            else
            {
                if (Constant.LINEBR.has(ch, "\0 \'"))
                {
                    if (start < end)
                    {
                        int len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                        start = end;
                    }
                }
            }
            if (ch == '\'')
            {
                this.column += 2;
                this.stream.write("''");
                start = end + 1;
            }
            if (ch != 0)
            {
                spaces = ch == ' ';
                breaks = Constant.LINEBR.has(ch);
            }
            end++;
        }
        this.writeIndicator("'", false, false, false);
    }

    private void writeDoubleQuoted(String text, boolean split) throws IOException
    {
        this.writeIndicator("\"", true, false, false);
        int start = 0;
        int end = 0;
        while (end <= text.length())
        {
            Character ch = null;
            if (end < text.length())
            {
                ch = text.charAt(end);
            }
            if ((ch == null) || ("\"\\\u0085\u2028\u2029\uFEFF".indexOf(ch) != - 1)
                || ! (('\u0020' <= ch) && (ch <= '\u007E')))
            {
                if (start < end)
                {
                    int len = end - start;
                    this.column += len;
                    this.stream.write(text, start, len);
                    start = end;
                }
                if (ch != null)
                {
                    String data;
                    if (ESCAPE_REPLACEMENTS.containsKey(ch))
                    {
                        data = "\\" + ESCAPE_REPLACEMENTS.get(ch);
                    }
                    else if (! this.allowUnicode || ! StreamReader.isPrintable(ch))
                    {
                        // if !allowUnicode or the character is not printable,
                        // we must encode it
                        if (ch <= '\u00FF')
                        {
                            String s = "0" + Integer.toString(ch, HEX_RADIX);
                            data = "\\x" + s.substring(s.length() - 2);
                        }
                        else if ((ch >= '\uD800') && (ch <= '\uDBFF'))
                        {
                            if ((end + 1) < text.length())
                            {
                                Character ch2 = text.charAt(++ end);
                                String s = "000" + Long.toHexString(Character.toCodePoint(ch, ch2));
                                data = "\\U" + s.substring(s.length() - 8);
                            }
                            else
                            {
                                String s = "000" + Integer.toString(ch, HEX_RADIX);
                                data = "\\u" + s.substring(s.length() - 4);
                            }
                        }
                        else
                        {
                            String s = "000" + Integer.toString(ch, HEX_RADIX);
                            data = "\\u" + s.substring(s.length() - 4);
                        }
                    }
                    else
                    {
                        data = String.valueOf(ch);
                    }
                    this.column += data.length();
                    this.stream.write(data);
                    start = end + 1;
                }
            }
            if (((0 < end) && (end < (text.length() - 1))) && ((Objects.equals(ch, ' ')) || (start >= end)) &&
                ((this.column + (end - start)) > this.bestWidth) && split)
            {
                String data;
                if (start >= end)
                {
                    data = "\\";
                }
                else
                {
                    data = text.substring(start, end) + "\\";
                }
                if (start < end)
                {
                    start = end;
                }
                this.column += data.length();
                this.stream.write(data);
                this.writeIndent();
                this.whitespace = false;
                this.indention = false;
                if (text.charAt(start) == ' ')
                {
                    data = "\\";
                    this.column += data.length();
                    this.stream.write(data);
                }
            }
            end += 1;
        }
        this.writeIndicator("\"", false, false, false);
    }

    private String determineBlockHints(String text)
    {
        StringBuilder hints = new StringBuilder();
//        if (Constant.LINEBR.has(text.charAt(0), " ")) disabled, always add indent marker to prevent formatting problems.
        {
            hints.append(this.bestIndent);
        }
        char ch1 = text.charAt(text.length() - 1);
        if (Constant.LINEBR.hasNo(ch1))
        {
            hints.append("-");
        }
        else if ((text.length() == 1) || Constant.LINEBR.has(text.charAt(text.length() - 2)))
        {
            hints.append("+");
        }
        return hints.toString();
    }

    private String constructBorder(String longCommentBorder, int size)
    {
        char[] chars = longCommentBorder.toCharArray();
        StringBuilder sb = new StringBuilder(size);
        while (true)
        {
            for (char aChar : chars)
            {
                sb.append(aChar);
                if (size-- == 0)
                {
                    return sb.toString();
                }
            }
        }
    }

    private String createRightBorder(String comment, int longest, String commentRightBorder)
    {
        int commentLength = comment.length() + 2;
        String rightBorder;
        if (commentLength < longest)
        {
            rightBorder =
                    StringUtils.repeat(' ', DioriteMathUtils.ceil(((double) (longest - commentLength)) / commentRightBorder.length())) + '#';
        }
        else
        {
            rightBorder = commentRightBorder;
        }
        return "# " + comment + rightBorder;
    }

    private int getLongestCommentLength(String[] comments)
    {
        int longest = - 1;
        for (String comment : comments)
        {
            int commentLength = comment.length();
            if (commentLength > longest)
            {
                longest = commentLength;
            }
        }
        return longest;
    }

    private void printComments(String... comments) throws IOException
    {
        int length = comments.length;
        boolean longCommentBorderStartsWithComment = this.longCommentBorderStartsWithComment;
        String commentRightBorder = this.longCommentRightBorder;
        String border = null;
        int longest = - 1;
        if (length >= this.longCommentThreshold)
        {
            longest = this.getLongestCommentLength(comments) + (longCommentBorderStartsWithComment ? 3 : 4);
            border = this.constructBorder(this.longCommentBorder, longest);
            this.writeIndent();
            if (! longCommentBorderStartsWithComment)
            {
                this.stream.write('#');
            }
            this.stream.write(border);
        }
        boolean hasRightBorder = (border != null) && ! commentRightBorder.isEmpty();
        for (int i = 0; i < comments.length; i++)
        {
            String comment = comments[i];
            if (i != 0)
            {
                this.writeLineBreak(null);
            }
            this.writeIndentMod(0);
            if (hasRightBorder)
            {
                this.stream.write(this.createRightBorder(comment, longest, commentRightBorder));
            }
            else
            {
                this.stream.write("# " + comment);
            }
        }
        if (border != null)
        {
            this.writeLineBreak(null);
            this.writeIndent();
            if (! longCommentBorderStartsWithComment)
            {
                this.stream.write('#');
            }
            this.stream.write(border);
        }
    }

//    void writeComment(int firstNewLine, int lastNewLine, String... comment) throws IOException
//    {
//        while (firstNewLine-- > 0)
//        {
//            this.writeLineBreak(null);
//        }
//        List<String> comments = new ArrayList<>(comment.length * 2);
//        for (String s : comment)
//        {
//            Collections.addAll(comments, StringUtils.splitPreserveAllTokens(s, '\n'));
//        }
//        this.printComments(comments.toArray(new String[comments.size()]));
//        while (lastNewLine-- > 0)
//        {
//            this.writeLineBreak(null);
//        }
//    }

    void writeCommentSafe(@Nullable String comment, int firstNewLine, int lastNewLine) throws IOException
    {
        if ((comment == null) || comment.isEmpty())
        {
            return;
        }
        this.writeComment(comment, firstNewLine, lastNewLine);
    }

    void writeComment(String comment, int firstNewLine, int lastNewLine) throws IOException
    {
        while (firstNewLine-- > 0)
        {
            this.writeLineBreak(null);
        }
        this.printComments(StringUtils.splitPreserveAllTokens(comment, '\n'));
        while (lastNewLine-- > 0)
        {
            this.writeLineBreak(null);
        }
    }

    void writeFolded(String text, boolean split) throws IOException
    {
        String hints = this.determineBlockHints(text);
        this.writeIndicator(">" + hints, true, false, false);
        if (! hints.isEmpty() && (hints.charAt(hints.length() - 1) == '+'))
        {
            this.openEnded = true;
        }
        this.writeLineBreak(null);
        boolean leadingSpace = true;
        boolean spaces = false;
        boolean breaks = true;
        int start = 0, end = 0;
        while (end <= text.length())
        {
            char ch = 0;
            if (end < text.length())
            {
                ch = text.charAt(end);
            }
            if (breaks)
            {
                if ((ch == 0) || Constant.LINEBR.hasNo(ch))
                {
                    if (! leadingSpace && (ch != 0) && (ch != ' ') && (text.charAt(start) == '\n'))
                    {
                        this.writeLineBreak(null);
                    }
                    leadingSpace = ch == ' ';
                    String data = text.substring(start, end);
                    for (char br : data.toCharArray())
                    {
                        if (br == '\n')
                        {
                            this.writeLineBreak(null);
                        }
                        else
                        {
                            this.writeLineBreak(String.valueOf(br));
                        }
                    }
                    if (ch != 0)
                    {
                        this.writeIndent();
                    }
                    start = end;
                }
            }
            else if (spaces)
            {
                if (ch != ' ')
                {
                    if (((start + 1) == end) && (this.column > this.bestWidth) && split)
                    {
                        this.writeIndent();
                    }
                    else
                    {
                        int len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                    }
                    start = end;
                }
            }
            else
            {
                if (Constant.LINEBR.has(ch, "\0 "))
                {
                    int len = end - start;
                    this.column += len;
                    this.stream.write(text, start, len);
                    if (ch == 0)
                    {
                        this.writeLineBreak(null);
                    }
                    start = end;
                }
            }
            if (ch != 0)
            {
                breaks = Constant.LINEBR.has(ch);
                spaces = ch == ' ';
            }
            end++;
        }
    }

    void writeLiteral(String text) throws IOException
    {
        String hints = this.determineBlockHints(text);
        this.writeIndicator("|" + hints, true, false, false);
        if (! hints.isEmpty() && ((hints.charAt(hints.length() - 1)) == '+'))
        {
            this.openEnded = true;
        }
        this.writeLineBreak(null);
        boolean breaks = true;
        int start = 0, end = 0;
        while (end <= text.length())
        {
            char ch = 0;
            if (end < text.length())
            {
                ch = text.charAt(end);
            }
            if (breaks)
            {
                if ((ch == 0) || Constant.LINEBR.hasNo(ch))
                {
                    String data = text.substring(start, end);
                    for (char br : data.toCharArray())
                    {
                        if (br == '\n')
                        {
                            this.writeLineBreak(null);
                        }
                        else
                        {
                            this.writeLineBreak(String.valueOf(br));
                        }
                    }
                    if (ch != 0)
                    {
                        this.writeIndent();
                    }
                    start = end;
                }
            }
            else
            {
                if ((ch == 0) || Constant.LINEBR.has(ch))
                {
                    this.stream.write(text, start, end - start);
                    if (ch == 0)
                    {
                        this.writeLineBreak(null);
                    }
                    start = end;
                }
            }
            if (ch != 0)
            {
                breaks = Constant.LINEBR.has(ch);
            }
            end++;
        }
    }

    void writePlain(String text, boolean split) throws IOException
    {
        if (this.rootContext)
        {
            this.openEnded = true;
        }
        if (text.isEmpty())
        {
            return;
        }
        if (! this.whitespace)
        {
            this.column++;
            this.stream.write(SPACE);
        }
        this.whitespace = false;
        this.indention = false;
        boolean spaces = false;
        boolean breaks = false;
        int start = 0, end = 0;
        while (end <= text.length())
        {
            char ch = 0;
            if (end < text.length())
            {
                ch = text.charAt(end);
            }
            if (spaces)
            {
                if (ch != ' ')
                {
                    if (((start + 1) == end) && (this.column > this.bestWidth) && split)
                    {
                        this.writeIndent();
                        this.whitespace = false;
                        this.indention = false;
                    }
                    else
                    {
                        int len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                    }
                    start = end;
                }
            }
            else if (breaks)
            {
                if (Constant.LINEBR.hasNo(ch))
                {
                    if (text.charAt(start) == '\n')
                    {
                        this.writeLineBreak(null);
                    }
                    String data = text.substring(start, end);
                    for (char br : data.toCharArray())
                    {
                        if (br == '\n')
                        {
                            this.writeLineBreak(null);
                        }
                        else
                        {
                            this.writeLineBreak(String.valueOf(br));
                        }
                    }
                    this.writeIndent();
                    this.whitespace = false;
                    this.indention = false;
                    start = end;
                }
            }
            else
            {
                if ((ch == 0) || Constant.LINEBR.has(ch))
                {
                    int len = end - start;
                    this.column += len;
                    this.stream.write(text, start, len);
                    start = end;
                }
            }
            if (ch != 0)
            {
                spaces = ch == ' ';
                breaks = Constant.LINEBR.has(ch);
            }
            end++;
        }
    }
}
