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
 * Simple single character parser.
 */
public final class CharParser implements TypeParser<Character>
{
    /**
     * Instance of char parser.
     */
    public static final CharParser INSTANCE = new CharParser();

    private CharParser() {}

    @Override
    public ArgumentParseResult<Character> parse(ParserContext context, CharPredicate endPredicate_)
    {
        CharPredicate endPredicate = c -> ((c == SPACE) || (c == END)) || endPredicate_.test(c);
        char result = context.next();
        if (endPredicate.test(result))
        {
            if (endPredicate.test(context.next()))
            {
                context.previous();
                return ArgumentParseResultType.SUCCESS.of(result);
            }
            return ArgumentParseResultType.SYNTAX_ERROR.empty();
        }
        char next = context.next();
        if (! endPredicate.test(next))
        {
            return ArgumentParseResultType.SYNTAX_ERROR.empty();
        }
        context.previous();
        return ArgumentParseResultType.SUCCESS.of(result);
    }
}
