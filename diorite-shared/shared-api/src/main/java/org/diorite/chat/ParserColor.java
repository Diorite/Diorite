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

    ParserColor(Parser parser, char key)
    {
        super(parser);
        this.active = true;
    }

    @Override
    boolean onKey(ParserContext context, char c)
    {
        if (c != '&')
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
        if (this.parser.sb.length() != 0)
        {
            this.parser.prepareElement();
        }
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
