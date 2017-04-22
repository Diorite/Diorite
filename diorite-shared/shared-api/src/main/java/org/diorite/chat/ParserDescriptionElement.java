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

import java.util.Deque;
import java.util.LinkedList;

import org.diorite.KeyBind;
import org.diorite.commons.ParserContext;
import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.nbt.NbtSerialization;
import org.diorite.nbt.NbtTag;

class ParserDescriptionElement extends ParserAbstractElement
{
    private static final char START_DESC  = '[';
    private static final char END_DESC    = ']';
    private static final char START_EVENT = '(';
    private static final char END_EVENT   = ')';

    Deque<ComponentElement> elementsStack = new LinkedList<>();

    ParserDescriptionElement(Parser parser)
    {
        super(parser);
        this.active = true;
    }

    @Override
    boolean onKey(ParserContext context, char c)
    {
        if ((c != START_DESC) && (c != END_DESC))
        {
            return false;
        }
        if (this.parser.escaped)
        {
            this.parser.sb.append(c);
            this.parser.escaped = false;
            return true;
        }
        if (c == START_DESC)
        {
            this.parser.prepareElement();
            this.parser.resetStringBuilder();
            ComponentElement componentElement = this.parser.increaseLevel();
            this.elementsStack.addLast(componentElement);
            return true;
        }
        this.parser.prepareElement();
        this.parser.resetStringBuilder();
        this.parser.decreaseLevel();
        ComponentElement element = this.elementsStack.pollLast();
        if (element == null)
        {
            return true;
        }
        char next = context.next();
        if (next == '(')
        {
            Object parsedEvent = this.parseEventObject(context);
            if (parsedEvent instanceof ChatMessageEvent)
            {
                element.addEvent((ChatMessageEvent) parsedEvent);
            }
            else
            {
                element.addExtra(new ComponentElement().setText(parsedEvent.toString()));
            }
        }
        if (next == '<')
        {
            // TODO: hover
        }
        if (next == '{')
        {
            // TODO: translatable
        }
        return true;
    }

    Object parseEventObject(ParserContext context)
    {
        char next = context.next();
        if (next == '<')
        {
            try
            {
                String type = "";
                StringBuilder sb = new StringBuilder(30);
                while (context.hasNext())
                {
                    char c = context.next();
                    if ((c == END_EVENT) || (c == '>')) // end before type, we should try to recover and pick up one of types anyway.
                    {
                        break;
                    }
                    if (c == ':')
                    {
                        type = sb.toString();
                        break;
                    }
                    sb.append(Character.toLowerCase(c));
                }
                if (type.startsWith("e")) // entity
                {
                    NbtTag nbtTag = NbtSerialization.fromMojangson(context, true);
                    if (this.parser.settings.hoverShowEntityEnabled)
                    {
                        return ChatMessageEvent.showEntity(nbtTag);
                    }
                    return "(<entity:" + nbtTag + ">)";
                }
                if (type.startsWith("i")) // item
                {
                    NbtTag nbtTag = NbtSerialization.fromMojangson(context, true);
                    if (this.parser.settings.hoverShowItemEnabled)
                    {
                        return ChatMessageEvent.showItem(nbtTag);
                    }
                    return "(<item:" + nbtTag + ">)";
                }
                if (type.startsWith("k")) // keyBind
                {
                    String text = this.readFully(context);
                    String fullText = "(<" + type + ":" + text + ">)";
                    if (this.parser.settings.keyBindEnabled)
                    {
                        try
                        {
                            return KeyBind.valueOf(text);
                        }
                        catch (IllegalArgumentException e)
                        {
                            return fullText;
                        }
                    }
                    return fullText;
                }
//                if ( type.startsWith("a") || type.startsWith("s")) // achievement
                {
                    String text = this.readFully(context);
                    String fullText = "(<" + type + ":" + text + ">)";
                    if (! this.parser.settings.hoverAchievementEnabled && ! this.parser.settings.hoverStatisticsEnabled)
                    {
                        return fullText;
                    }
                    if (type.startsWith("a") && ! text.startsWith("achievement"))
                    {
                        if (! this.parser.settings.hoverAchievementEnabled)
                        {
                            return fullText;
                        }
                        text = "achievement." + text;
                    }
                    else if (type.startsWith("s") && ! text.startsWith("stat"))
                    {
                        if (! this.parser.settings.hoverStatisticsEnabled)
                        {
                            return fullText;
                        }
                        text = "stat." + text;
                    }
                    else if ((text.startsWith("achievement") && ! this.parser.settings.hoverAchievementEnabled) ||
                             ! this.parser.settings.hoverStatisticsEnabled)
                    {
                        return fullText;
                    }
                    return ChatMessageEvent.showAchievement(text);
                }
            }
            finally
            {
                if (context.next() != '>')
                {
                    context.previous();
                }
                if (context.next() != ')')
                {
                    context.previous();
                }
            }
        }
        context.previous();
        String text = this.readFully(context);
        Integer page = DioriteMathUtils.asInt(text);
        if ((page != null) && this.parser.settings.pageLinksEnabled)
        {
            return ChatMessageEvent.changePage(page);
        }
        if (text.startsWith("/") && this.parser.settings.commandSuggestionEnabled)
        {
            return ChatMessageEvent.suggestCommand(text.substring("/".length()));
        }
        if (text.startsWith("?") && this.parser.settings.insertionEnabled)
        {
            return ChatMessageEvent.appendChat(text.substring("?".length()));
        }
        if (text.startsWith("!/") && this.parser.settings.commandInvokeEnabled)
        {
            return ChatMessageEvent.runCommand(text.substring("!/".length()));
        }
        if (text.startsWith("file:") && this.parser.settings.fileLinksEnabled)
        {
            return ChatMessageEvent.openFile(text.substring("file:".length()));
        }
        if (this.parser.settings.linksEnabled)
        {
            return ChatMessageEvent.openURL(text);
        }
        return "(" + text + ")";
    }

    String readFully(ParserContext context)
    {
        StringBuilder sb = new StringBuilder(30);
        boolean escaped = false;
        while (context.hasNext())
        {
            char c = context.next();
            if (c == '\\')
            {
                escaped = true;
                continue;
            }
            if (escaped)
            {
                sb.append(c);
                escaped = false;
                continue;
            }
            if (c == END_EVENT)
            {
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
