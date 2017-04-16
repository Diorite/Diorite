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

import org.diorite.command.parser.ArgumentParseResult;
import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * Abstract parser for floating point number.
 *
 * @param <T>
 *         type of number.
 */
public abstract class NumberFloatParser<T extends Number> extends NumberParser<T>
{
    protected final boolean allowNaN;
    protected final boolean allowInfinity;

    /**
     * Construct new parser.
     *
     * @param allowNaN
     *         if parser should allow for NaN values.
     * @param allowInfinity
     *         if parser should allow for Infinity values.
     */
    public NumberFloatParser(boolean allowNaN, boolean allowInfinity)
    {
        this.allowNaN = allowNaN;
        this.allowInfinity = allowInfinity;
    }

    @Override
    public ArgumentParseResult<T> parse(ParserContext context, CharPredicate endPredicate)
    {
        StringBuilder sb = new StringBuilder(16);
        char next;
        while (context.hasNext() && (! endPredicate.test((next = context.next()))))
        {
            sb.append(next);
        }
        context.previous();
        return this.parse(sb.toString());
    }

    /**
     * Simplified parse method for number parser.
     *
     * @param context
     *         string to parse as number.
     *
     * @return parser result.
     */
    public abstract ArgumentParseResult<T> parse(String context);
}
