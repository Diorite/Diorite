/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.chat;

import javax.annotation.Nullable;

import java.text.CharacterIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;

public class Parser
{
    private static final int NONE = - 1;

    private static final char ESCAPE = '\\';
    private static final char SPACE  = ' ';
    private static final char NULL   = '\0';
    private static final char END    = CharacterIterator.DONE;

    private final ParserSettings settings;
    private final ParserContext  context;
    private final ComponentElement             rootElement = new ComponentElement().setText("");
    private final Char2ObjectMap<FormatParser> parsers     = new Char2ObjectOpenHashMap<>(5);

    public Parser(String toParse, @Nullable ParserSettings settings)
    {
        this.context = new ParserContext(toParse);
        this.settings = (settings == null) ? new ParserSettings() : settings;

        this.initParsers();
    }

    private void initParsers()
    {
        if (this.settings.underlineEnabled)
        {
            this.addParser(new FormatParser('_', (p, e) -> e.setUnderlined(true), (p, e) -> e.isUnderlined()));
        }
        if (this.settings.italicEnabled)
        {
            this.addParser(new FormatParser('/', (p, e) -> e.setItalic(true), (p, e) -> e.isItalic()));
        }
        if (this.settings.boldEnabled)
        {
            this.addParser(new FormatParser('*', (p, e) -> e.setBold(true), (p, e) -> e.isBold()));
        }
        if (this.settings.strikethroughEnabled)
        {
            this.addParser(new FormatParser('~', (p, e) -> e.setStrikethrough(true), (p, e) -> e.isStrikethrough()));
        }
        if (this.settings.obfuscateEnabled)
        {
            this.addParser(new FormatParser('%', (p, e) -> e.setObfuscated(true), (p, e) -> e.isObfuscated()));
        }
        if (this.settings.alternateColorCharEnabled)
        {
            this.addParser(this.colorParser = new ColorParser('&'));
        }
        if (! this.settings.colorCharEnabled)
        {
            this.addParser(new SkipColorParser());
        }
    }

    private void addParser(FormatParser parser)
    {
        this.parsers.put(parser.key, parser);
    }

    private LinkedList<ComponentElement> levelQueue  = new LinkedList<>(List.of(this.rootElement));
    private LinkedList<ComponentElement> colorsQueue = new LinkedList<>();

    private @Nullable ColorParser colorParser;
    private boolean       escaped     = false;
    private StringBuilder sb          = new StringBuilder(128);
    private int           indexOfText = 0;
    private int level;

    public String parse()
    {
        ParserContext context = this.context;
        while (context.hasNext())
        {
            char c = context.next();
            FormatParser formatParser = this.parsers.get(c);
            if (formatParser != null)
            {
                formatParser.onKey(context, c);
            }
            else if (c == '\\')
            {
                if (this.escaped)
                {
                    this.sb.append(c);
                    this.escaped = false;
                }
                else
                {
                    this.escaped = true;
                }
            }
            else
            {
                this.escaped = false;
                this.sb.append(c);
            }
        }

        this.prepareElement();
        System.out.println("Parsed from: `" + this.context.getText() + "` To: " + this.rootElement);
        return this.rootElement.optimize().toString();
    }

    void resetStringBuilder()
    {
        this.sb = new StringBuilder(128);
    }

    void prepareElement()
    {
        if (this.sb.length() == 0)
        {
            return;
        }
        String text = this.sb.toString();
        ComponentElement element = new ComponentElement().setText(text);
        ComponentElement last = this.levelQueue.getLast();

        ChatColor chatColor = null;
        List<ComponentElement> extra = last.getExtra();
        if ((extra != null) && ! extra.isEmpty())
        {
            ComponentElement lastColor = this.colorsQueue.peekLast();
            ComponentElement lastExtra = extra.get(extra.size() - 1);
            if (lastColor == lastExtra)
            {
                chatColor = lastExtra.getColor();
            }
        }

        if ((last.getColor() != chatColor) && (chatColor != null))
        {
            this.decreaseLevel();
            if (this.colorParser != null)
            {
                this.colorParser.deactivate();
            }
            this.colorsQueue.removeLast();
            last = this.levelQueue.getLast();
        }
        last.addExtra(element);
    }

    void increaseLevel()
    {
        this.level++;
        ComponentElement element = new ComponentElement().setText("");
        this.levelQueue.getLast().addExtra(element);
        this.levelQueue.add(element);
        for (FormatParser parser : this.parsers.values())
        {
            if (parser.active && ! parser.check(element))
            {
                parser.apply(element);
            }
        }
    }

    void decreaseLevel()
    {
        this.level--;
        ComponentElement last = this.levelQueue.removeLast();
        ComponentElement peekLast = this.levelQueue.peekLast();
        if (peekLast == null)
        {
            return;
        }
        ComponentElement lastColor = this.colorsQueue.peekLast();
        ChatColor color = null;
        if (lastColor == last)
        {
            color = last.getColor();
        }
        if ((color != peekLast.getColor()) && (color != null))
        {
            if (this.colorParser != null)
            {
                this.colorParser.deactivate();
            }
            this.colorsQueue.removeLast();
            this.decreaseLevel();
        }
    }

    class SkipColorParser extends FormatParser
    {
        SkipColorParser()
        {
            super(ChatColor.COLOR_CHAR, null, null);
            this.active = true;
        }

        @Override
        void onKey(ParserContext context, char c)
        {
            if (c != this.key)
            {
                return;
            }
            char next = context.next();
            ChatColor byChar = ChatColor.getByChar(next);
            if (byChar == null)
            {
                context.previous();
                Parser.this.sb.append(c);
            }
        }

        @Override
        void apply(ComponentElement element)
        {
        }

        @Override
        boolean check(ComponentElement element)
        {
            return true;
        }
    }

    class ColorParser extends FormatParser
    {
        char color = NULL;

        ColorParser(char key)
        {
            super(key, null, null);
            this.active = true;
        }

        @Override
        void onKey(ParserContext context, char c)
        {
            if (c != this.key)
            {
                return;
            }
            if (Parser.this.escaped)
            {
                Parser.this.sb.append(c);
                Parser.this.escaped = false;
                return;
            }
            char next = context.next();
            ChatColor byChar = ChatColor.getByChar(next);
            if (byChar == null)
            {
                context.previous();
                Parser.this.sb.append(c);
                return;
            }
            if (Parser.this.sb.length() != 0)
            {
                Parser.this.prepareElement();
            }
            Parser.this.resetStringBuilder();
            this.color = next;
            Parser.this.increaseLevel();
        }

        @Override
        void apply(ComponentElement element)
        {
            if (this.color == NULL)
            {
                return;
            }
            ChatColor byChar = ChatColor.getByChar(this.color);
            if (byChar == null)
            {
                throw new IllegalStateException("Unknown color: " + this.color);
            }
            switch (byChar)
            {
                case BLACK:
                case DARK_BLUE:
                case DARK_GREEN:
                case DARK_AQUA:
                case DARK_RED:
                case DARK_PURPLE:
                case GOLD:
                case GRAY:
                case DARK_GRAY:
                case BLUE:
                case GREEN:
                case AQUA:
                case RED:
                case LIGHT_PURPLE:
                case YELLOW:
                case WHITE:
                case RESET:
                    element.setColor(byChar);
                    element.setBold(null);
                    element.setItalic(null);
                    element.setUnderlined(null);
                    element.setStrikethrough(null);
                    element.setObfuscated(null);
                    break;
                case OBFUSCATE:
                    element.setObfuscated(true);
                    break;
                case BOLD:
                    element.setBold(true);
                    break;
                case STRIKETHROUGH:
                    element.setStrikethrough(true);
                    break;
                case UNDERLINE:
                    element.setUnderlined(true);
                    break;
                case ITALIC:
                    element.setItalic(true);
                    break;
            }
            Parser.this.colorsQueue.add(element);
        }

        @Override
        boolean check(ComponentElement element)
        {
            if (this.color == NULL)
            {
                return false;
            }
            ChatColor byChar = ChatColor.getByChar(this.color);
            if (byChar == null)
            {
                throw new IllegalStateException("Unknown color: " + this.color);
            }
            switch (byChar)
            {
                case BLACK:
                case DARK_BLUE:
                case DARK_GREEN:
                case DARK_AQUA:
                case DARK_RED:
                case DARK_PURPLE:
                case GOLD:
                case GRAY:
                case DARK_GRAY:
                case BLUE:
                case GREEN:
                case AQUA:
                case RED:
                case LIGHT_PURPLE:
                case YELLOW:
                case WHITE:
                case RESET:
                    return element.getColor() == byChar;
                case OBFUSCATE:
                    return element.isObfuscated();
                case BOLD:
                    return element.isBold();
                case STRIKETHROUGH:
                    return element.isStrikethrough();
                case UNDERLINE:
                    return element.isUnderlined();
                case ITALIC:
                    return element.isItalic();
                default:
                    throw new AssertionError();
            }
        }
    }

    class FormatParser
    {
        final char key;
        int index = NONE;
        boolean active;

        @Nullable final BiConsumer<? super FormatParser, ComponentElement>  applyFunc;
        @Nullable final BiPredicate<? super FormatParser, ComponentElement> checkFunc;

        FormatParser(char key, @Nullable BiConsumer<? super FormatParser, ComponentElement> applyFunc,
                     @Nullable BiPredicate<? super FormatParser, ComponentElement> checkFunc)
        {
            this.key = key;
            this.applyFunc = applyFunc;
            this.checkFunc = checkFunc;
        }

        boolean isKey(char key) {return this.key == key;}

        void deactivate() {this.active = false;}

        boolean check(ComponentElement element)
        {
            if (this.checkFunc != null)
            {
                return this.checkFunc.test(this, element);
            }
            return false;
        }

        void apply(ComponentElement element)
        {
            if (this.applyFunc != null)
            {
                this.applyFunc.accept(this, element);
            }
        }

        void onKey(ParserContext context, char c)
        {
            if (c != this.key)
            {
                return;
            }
            if (Parser.this.escaped)
            {
                Parser.this.sb.append(c);
                Parser.this.escaped = false;
                return;
            }
            if (this.index == NONE)
            {
                char next = context.next();
                context.previous();
                if (next == SPACE)
                {
                    Parser.this.sb.append(c);
                    return;
                }
                if (next == END)
                {
                    Parser.this.sb.append(this.key);
                    return;
                }
                this.index = context.getIndex();
                if (Parser.this.sb.length() != 0)
                {
                    Parser.this.prepareElement();
                }
                Parser.this.resetStringBuilder();
                this.active = true;
                Parser.this.increaseLevel();
                return;
            }
            if (this.index == (context.getEndIndex() - 1))
            {
                Parser.this.sb.append('_');
            }
            if (Parser.this.sb.length() != 0)
            {
                Parser.this.prepareElement();
            }
            Parser.this.resetStringBuilder();
            Parser.this.indexOfText = context.getIndex() + 1;
            this.active = false;
            this.index = NONE;
            Parser.this.decreaseLevel();
        }
    }
}
