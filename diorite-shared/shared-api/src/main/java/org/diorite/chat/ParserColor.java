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

import org.diorite.commons.ParserContext;

class ParserColor extends ParserApplicableElement
{
    char color = Parser.NULL;

    ParserColor(Parser parser)
    {
        super(parser);
    }

    @Override
    boolean onKey(ParserContext context, char c)
    {
        if (c != this.parser.settings.alternateColorChar)
        {
            return false;
        }
        if (this.parser.escaped)
        {
            this.parser.sb.append(c);
            this.parser.escaped = false;
            return true;
        }
        char next = context.next();
        ChatColor byChar = ChatColor.getByChar(next);
        if (byChar == null)
        {
            context.previous();
            this.parser.sb.append(c);
            return true;
        }
        this.active = true;
        this.parser.prepareElement();
        this.parser.resetStringBuilder();
        this.color = next;
        this.parser.increaseLevel();
        return true;
    }

    @Override
    void apply(ComponentElement element)
    {
        if (this.color == Parser.NULL)
        {
            return;
        }
        ChatColor byChar = ChatColor.getByChar(this.color);
        if (byChar == null)
        {
            throw new IllegalStateException("Unknown color: " + this.color);
        }
        this.color = Parser.NULL;
        element.setColor(byChar);
        this.parser.colorsQueue.add(element);
    }

    @Override
    boolean check(ComponentElement element)
    {
        if (this.color == Parser.NULL)
        {
            return false;
        }
        ChatColor byChar = ChatColor.getByChar(this.color);
        if (byChar == null)
        {
            throw new IllegalStateException("Unknown color: " + this.color);
        }
        return false;
    }
}
