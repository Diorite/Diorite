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
import org.diorite.command.parser.TypeParser;
import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * Raw string parser that does not support any escape sequences or ways to include spaces in it.
 */
public final class RawStringParser implements TypeParser<String>
{
    /**
     * Instance of raw string parser.
     */
    public static final RawStringParser INSTANCE = new RawStringParser();

    private RawStringParser() {}

    @Override
    public ArgumentParseResult<String> parse(ParserContext context, CharPredicate endPredicate_)
    {
        CharPredicate endPredicate = c -> (c == END) || endPredicate_.test(c);
        StringBuilder result = new StringBuilder(16);
        while (context.hasNext())
        {
            char next = context.next();
            if (endPredicate.test(next))
            {
                String string = result.toString();
                if (string.isEmpty())
                {
                    return ArgumentParseResultType.EMPTY.empty();
                }
                context.previous();
                return ArgumentParseResultType.SUCCESS.of(string);
            }
            result.append(next);
        }
        return ArgumentParseResultType.SUCCESS.of(result.toString());
    }
}
