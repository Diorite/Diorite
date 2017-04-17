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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.diorite.commons.ParserContext;

public class Parser
{
    static final Pattern URL_PATTERN = Pattern.compile(
            "(?:(?:https?)://)?(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\" +
            ".\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}" +
            "(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:\\." +
            "(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?",
            Pattern.UNICODE_CASE & Pattern.CASE_INSENSITIVE);

    static final int NONE = - 1;

    static final char ESCAPE = '\\';
    static final char SPACE  = ' ';
    static final char NULL   = '\0';
    static final char END    = CharacterIterator.DONE;

    final ParserSettings settings;
    final ParserContext  context;
    final ComponentElement                    rootElement       = new ComponentElement().setText("");
    final Collection<ParserAbstractElement>   parsers           = new ArrayList<>(20);
    final Collection<ParserApplicableElement> applicableParsers = new ArrayList<>(20);

    public Parser(String toParse, @Nullable ParserSettings settings)
    {
        this.context = new ParserContext(toParse);
        this.settings = (settings == null) ? ParserSettings.ALL_ALLOWED : settings;

        this.initParsers();
    }

    private void initParsers()
    {
        if (this.settings.underlineEnabled)
        {
            this.addParser(new ParserFormat(this, '_', (p, e) -> e.setUnderlined(true), (p, e) -> e.isUnderlined()));
        }
        if (this.settings.italicEnabled)
        {
            this.addParser(new ParserFormat(this, '/', (p, e) -> e.setItalic(true), (p, e) -> e.isItalic()));
        }
        if (this.settings.boldEnabled)
        {
            this.addParser(new ParserFormat(this, '*', (p, e) -> e.setBold(true), (p, e) -> e.isBold()));
        }
        if (this.settings.strikethroughEnabled)
        {
            this.addParser(new ParserFormat(this, '~', (p, e) -> e.setStrikethrough(true), (p, e) -> e.isStrikethrough()));
        }
        if (this.settings.obfuscateEnabled)
        {
            this.addParser(new ParserFormat(this, '%', (p, e) -> e.setObfuscated(true), (p, e) -> e.isObfuscated()));
        }
        if (this.settings.alternateColorCharEnabled)
        {
            this.addParser(this.colorParser = new ParserColor(this, '&'));
        }
        if (! this.settings.colorCharEnabled)
        {
            this.addParser(new ParserSkipColor(this));
        }
    }

    private void addParser(ParserAbstractElement parser)
    {
        this.parsers.add(parser);
        if (parser instanceof ParserApplicableElement)
        {
            this.applicableParsers.add((ParserApplicableElement) parser);
        }
    }

    Deque<ComponentElement> levelQueue  = new LinkedList<>(List.of(this.rootElement));
    Deque<ComponentElement> colorsQueue = new LinkedList<>();

    @Nullable ParserColor colorParser;
    boolean       escaped     = false;
    StringBuilder sb          = new StringBuilder(128);
    int           indexOfText = 0;
    int level;

    public String parse()
    {
        ParserContext context = this.context;
        while (context.hasNext())
        {
            char c = context.next();
            boolean any = false;
            for (ParserAbstractElement parser : this.parsers)
            {
                any |= parser.onKey(context, c);
            }
            if (! any)
            {
                if (c == '\\')
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
        }

        this.prepareElement();
        this.rootElement.optimize();
        return this.rootElement.toString();
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
        ComponentElement element;
        if (this.settings.autoLinksEnabled)
        {
            List<ComponentElement> allElements = new ArrayList<>(5);
            Matcher matcher = URL_PATTERN.matcher(text);
            int lastMatchEnd = 0;
            while (matcher.find())
            {
                String group = matcher.group();
                if (group.startsWith("http://"))
                {
                    group = group.substring(7);
                }
                else if (group.startsWith("https://"))
                {
                    group = group.substring(8);
                }
                int start = matcher.start();
                if (start != lastMatchEnd)
                {
                    allElements.add(new ComponentElement().setText(text.substring(lastMatchEnd, start)));
                }
                allElements.add(new ComponentElement().setText(group).setClickEvent(ChatMessageEvent.openURL(matcher.group())));
                lastMatchEnd = matcher.end();
            }
            if (lastMatchEnd < (text.length() - 1))
            {
                allElements.add(new ComponentElement().setText(text.substring(lastMatchEnd)));
            }
            element = new ComponentElement().setText("");
            element.setExtra(allElements);
        }
        else
        {
            element = new ComponentElement().setText(text);
        }
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
        for (ParserAbstractElement abstractElementParser : this.parsers)
        {
            if (abstractElementParser instanceof ParserApplicableElement)
            {
                ParserApplicableElement parser = (ParserApplicableElement) abstractElementParser;
                if (parser.active && ! parser.check(element))
                {
                    parser.apply(element);
                }
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

}
