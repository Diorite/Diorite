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
import org.diorite.command.parser.ArgumentParseResultType;
import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * Short parser supporting multiple radix.
 */
public final class ShortParser extends NumberIntegerParser<Short>
{
    public static final ShortParser BINARY      = new ShortParser(2);
    public static final ShortParser OCTAL       = new ShortParser(8);
    public static final ShortParser DECIMAL     = new ShortParser(10);
    public static final ShortParser HEXADECIMAL = new ShortParser(16);

    private static final ShortParser[] parsers = new ShortParser[(Character.MAX_RADIX - Character.MIN_RADIX) + 1];

    static
    {
        parsers[BINARY.radix - 2] = BINARY;
        parsers[OCTAL.radix - 2] = OCTAL;
        parsers[DECIMAL.radix - 2] = DECIMAL;
        parsers[HEXADECIMAL.radix - 2] = HEXADECIMAL;
    }

    /**
     * Returns cached instance of parser for given radix.
     *
     * @param radix
     *         radix to use.
     *
     * @return cached instance of parser for given radix.
     *
     * @exception NumberFormatException
     *         if radix is invalid.
     */
    public static ShortParser forRadix(int radix) throws NumberFormatException
    {
        if (radix < Character.MIN_RADIX)
        {
            throw new NumberFormatException("radix " + radix + " less than Character.MIN_RADIX (" + Character.MIN_RADIX + ")");
        }
        if (radix > Character.MAX_RADIX)
        {
            throw new NumberFormatException("radix " + radix + " greater than Character.MAX_RADIX (" + Character.MAX_RADIX + ")");
        }
        ShortParser parser = parsers[radix - 2];
        if (parser == null)
        {
            parser = new ShortParser(radix);
            parsers[radix - 2] = parser;
        }
        return parser;
    }

    private ShortParser(int radix)
    {
        super(radix);
    }

    @Override
    public ArgumentParseResult<Short> parse(ParserContext context, CharPredicate endPredicate_)
    {
        CharPredicate endPredicate = c -> (c == SPACE) || endPredicate_.test(c);
        int radix = this.getRadix(context);

        short result = 0;
        boolean negative = false;
        short limit = - Short.MAX_VALUE;
        short multmin;
        int digit;
        char firstChar = context.next();
        if (firstChar < '0')
        { // Possible leading "+" or "-"
            if (firstChar == '-')
            {
                negative = true;
                limit = Short.MIN_VALUE;
            }
            else if (firstChar != '+')
            {
                return ArgumentParseResultType.SYNTAX_ERROR.empty();
            }

            if (! context.hasNext()) // Cannot have lone "+" or "-"
            {
                return ArgumentParseResultType.BAD_SIZE.empty();
            }
        }
        else
        {
            context.previous();
        }
        multmin = (short) (limit / 10);
        char next;
        while (context.hasNext() && (! endPredicate.test((next = context.next()))))
        {
            // Accumulating negatively avoids surprises near MAX_VALUE
            digit = Character.digit(next, radix);
            if (digit < 0)
            {
                return ArgumentParseResultType.SYNTAX_ERROR.empty();
            }
            if (result < multmin)
            {
                return ArgumentParseResultType.NUMBER_TOO_LARGE.empty();
            }
            result *= radix;
            if (result < (limit + digit))
            {
                return ArgumentParseResultType.NUMBER_TOO_LARGE.empty();
            }
            result -= digit;
        }
        context.previous();
        return ArgumentParseResultType.SUCCESS.of(negative ? result : (short) - result);
    }
}
