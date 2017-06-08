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

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import org.diorite.commons.ParserContext;

class ParserFormat extends ParserApplicableElement
{
    final char key;

    @Nullable final BiConsumer<? super ParserFormat, ComponentElement>  applyFunc;
    @Nullable final BiPredicate<? super ParserFormat, ComponentElement> checkFunc;

    ParserFormat(Parser parser, char key, @Nullable BiConsumer<? super ParserFormat, ComponentElement> applyFunc,
                 @Nullable BiPredicate<? super ParserFormat, ComponentElement> checkFunc)
    {
        super(parser);
        this.key = key;
        this.applyFunc = applyFunc;
        this.checkFunc = checkFunc;
    }

    @Override
    boolean check(ComponentElement element)
    {
        if (this.checkFunc != null)
        {
            return this.checkFunc.test(this, element);
        }
        return false;
    }

    @Override
    void apply(ComponentElement element)
    {
        if (this.applyFunc != null)
        {
            this.applyFunc.accept(this, element);
        }
    }

    @Override
    boolean onKey(ParserContext context, char c)
    {
        if (c != this.key)
        {
            return false;
        }
        if (this.parser.escaped)
        {
            this.parser.sb.append(c);
            this.parser.escaped = false;
            return true;
        }
        int currentIndex = context.getIndex();
        char checkNext = context.next();
        context.setIndex(currentIndex);
        char checkPrev = context.previous();
        context.setIndex(currentIndex);
        if ((checkNext == this.key) || (checkPrev == this.key))
        {
            this.parser.sb.append(c);
            return true;
        }
        if (this.index == Parser.NONE)
        {
            char next = context.next();
            context.previous();
            if (next == Parser.SPACE)
            {
                this.parser.sb.append(c);
                return true;
            }
            if (next == Parser.END)
            {
                this.parser.sb.append(this.key);
                return true;
            }
            this.index = context.getIndex();
            this.parser.prepareElement();
            this.parser.resetStringBuilder();
            this.active = true;
            this.parser.increaseLevel();
            return true;
        }
        if (this.index == (context.getEndIndex() - 1))
        {
            this.parser.sb.append(this.key);
        }
        this.parser.prepareElement();
        this.parser.resetStringBuilder();
        this.parser.indexOfText = context.getIndex() + 1;
        this.active = false;
        this.index = Parser.NONE;
        this.parser.decreaseLevel();
        return true;
    }
}
