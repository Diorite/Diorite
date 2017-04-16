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

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import org.diorite.command.annotation.arguments.StrArg;
import org.diorite.command.parser.ArgumentParseResult;
import org.diorite.command.parser.ArgumentParseResultType;
import org.diorite.command.parser.TypeParser;
import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * String parser with support for escaping sequences and quotes.
 */
public final class StringParser implements TypeParser<String>
{
    private final StrArg.Tokens stringTokens;

    public StringParser(@Nullable StrArg.Tokens tokens)
    {
        this.stringTokens = (tokens == null) ? StrArg.Tokens.DEFAULT : tokens;
    }

    public StringParser()
    {
        this(null);
    }

    @Override
    public ArgumentParseResult<String> parse(ParserContext context, CharPredicate endPredicate_)
    {
        CharPredicate endPredicate = c -> (c == END) || endPredicate_.test(c);
        StrArg.Tokens tokens = this.stringTokens;
        StringBuilder result = new StringBuilder(16);
        char next = context.next();
        if (endPredicate.test(next))
        {
            return ArgumentParseResultType.EMPTY.empty();
        }
        int tokenIndex = ArrayUtils.indexOf(tokens.start(), next);
        if (tokenIndex == - 1)
        {
            context.previous();
        }
        int trailingSpaces = 0;
        boolean escaped = false;
        while (context.hasNext())
        {
            next = context.next();
            if (! escaped && (next == ESCAPE))
            {
                escaped = true;
                continue;
            }
            if (! escaped)
            {
                if (tokenIndex == - 1)
                {
                    if ((next == SPACE) && ! endPredicate.test(' '))
                    {
                        trailingSpaces += 1;
                    }
                    if (endPredicate.test(next))
                    {
                        context.previous();
                        return ArgumentParseResultType.SUCCESS.of(result.toString().substring(0, result.length() - trailingSpaces));
                    }
                    if (next != SPACE)
                    {
                        trailingSpaces = 0;
                    }
                }
                else if (next == tokens.end()[tokenIndex])
                {
                    if (endPredicate.test(context.next()) || endPredicate.test(TypeParser.removeSpaces(context, false)))
                    {
                        context.previous();
                        return ArgumentParseResultType.SUCCESS.of(result.toString());
                    }
                    return ArgumentParseResultType.SYNTAX_ERROR.empty();
                }
            }
            escaped = false;
            result.append(next);
        }
        return ArgumentParseResultType.SUCCESS.of(result.toString());
    }
}
