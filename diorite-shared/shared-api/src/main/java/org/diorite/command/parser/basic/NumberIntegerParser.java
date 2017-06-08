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

package org.diorite.command.parser.basic;

import org.diorite.commons.ParserContext;

/**
 * Abstract parser for integer numbers.
 *
 * @param <T>
 *         type of number.
 */
public abstract class NumberIntegerParser<T extends Number> extends NumberParser<T>
{
    protected final int radix;

    /**
     * Construct new number parser, with default radix of 10.
     */
    public NumberIntegerParser()
    {
        this(10);
    }

    /**
     * Construct new number parser with custom radix.
     *
     * @param radix
     *         number radix.
     */
    public NumberIntegerParser(int radix)
    {
        this.radix = radix;
        if (radix < Character.MIN_RADIX)
        {
            throw new NumberFormatException("radix " + radix + " less than Character.MIN_RADIX");
        }
        if (radix > Character.MAX_RADIX)
        {
            throw new NumberFormatException("radix " + radix + " greater than Character.MAX_RADIX");
        }
    }

    /**
     * Returns radix of this parser.
     *
     * @return radix of this parser.
     */
    public int getRadix()
    {
        return this.radix;
    }

    /**
     * Auto-detect radix, number that starts from 0 are octal, numbers that starts from 0b are binary, and numbers that starts with 0x are hexadecimal. <br>
     * All other numbers will use radix of parser.
     *
     * @param context
     *         parser context to check.
     *
     * @return radoix
     */
    @SuppressWarnings("MagicNumber")
    protected int getRadix(ParserContext context)
    {
        if (! context.hasNext())
        {
            return - 1;
        }
        char next = context.next();
        if (next != '0')
        {
            context.previous();
            return this.radix;
        }
        next = context.next();
        if (next == 'x')
        {
            return 16;
        }
        if (next == 'b')
        {
            return 2;
        }
        context.previous();
        return 8;
    }
}
