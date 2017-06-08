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

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.IntFunction;

import org.apache.commons.lang3.ArrayUtils;

import org.diorite.command.annotation.arguments.ArrayArg;
import org.diorite.command.parser.ArgumentParseResult;
import org.diorite.command.parser.ArgumentParseResultType;
import org.diorite.command.parser.TypeParser;
import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * Array/collection parser, result of parsing is always a collection.
 *
 * @param <T>
 *         type of collection elements.
 */
public final class ArrayParser<T> implements TypeParser<Collection<T>>
{
    private final ArrayArg.Tokens            arrayTokens;
    private final TypeParser<T>              typeParser;
    private final IntFunction<Collection<T>> collectionSupplier;

    public ArrayParser(TypeParser<T> typeParser, IntFunction<Collection<T>> collectionSupplier)
    {
        this(null, typeParser, collectionSupplier);
    }

    public ArrayParser(TypeParser<T> typeParser)
    {
        this(null, typeParser, ArrayList::new);
    }

    public ArrayParser(@Nullable ArrayArg.Tokens tokens, TypeParser<T> typeParser, IntFunction<Collection<T>> collectionSupplier)
    {
        this.arrayTokens = (tokens == null) ? ArrayArg.Tokens.DEFAULT : tokens;
        this.typeParser = typeParser;
        this.collectionSupplier = collectionSupplier;
    }

    @Override
    public ArgumentParseResult<Collection<T>> parse(ParserContext context, CharPredicate endPredicate)
    {
        ArrayArg.Tokens tokens = this.arrayTokens;
        Collection<T> result = this.collectionSupplier.apply(10);
        char c = context.next();
        int tokenIndex = ArrayUtils.indexOf(tokens.start(), c);
        if (tokenIndex != - 1)
        {
            while (true)
            {
                char next = TypeParser.removeSpaces(context, false);
                if (next == tokens.end()[tokenIndex])
                {
                    return ArgumentParseResultType.SUCCESS.of(result);
                }
                context.previous();
                ArgumentParseResult<? extends T> parseResult = ArgumentParseResultType.ensureNotNull(
                        this.typeParser.parse(context, e -> ArrayUtils.contains(tokens.separator(), e) || (e == tokens.end()[tokenIndex])));
                if (! parseResult.isSuccess())
                {
                    return parseResult.getType().empty(parseResult);
                }
                T parse = parseResult.getResult();
                if (parse == null)
                {
                    return ArgumentParseResultType.SYNTAX_ERROR.empty(parseResult);
                }
                c = TypeParser.removeSpaces(context, false);
                if (! ArrayUtils.contains(tokens.separator(), c))
                {
                    if (c == tokens.end()[tokenIndex])
                    {
                        c = context.next();
                        if (! endPredicate.test(c))
                        {
                            return ArgumentParseResultType.SYNTAX_ERROR.empty();
                        }
                        context.previous();
                        result.add(parse);
                        return ArgumentParseResultType.SUCCESS.of(result);
                    }
                    return ArgumentParseResultType.SYNTAX_ERROR.empty();
                }
                result.add(parse);
            }
        }
        else
        {
            context.previous();
            while (true)
            {
                ArgumentParseResult<? extends T> parseResult = ArgumentParseResultType.ensureNotNull(
                        this.typeParser.parse(context, e -> ArrayUtils.contains(tokens.separator(), e) || (e == SPACE)));
                if (! parseResult.isSuccess())
                {
                    return parseResult.getType().empty(parseResult);
                }
                T parse = parseResult.getResult();
                if (parse == null)
                {
                    return ArgumentParseResultType.SYNTAX_ERROR.empty(parseResult);
                }
                c = context.next();
                if (! ArrayUtils.contains(tokens.separator(), c))
                {
                    result.add(parse);
                    if (endPredicate.test(c))
                    {
                        context.previous();
                    }
                    return ArgumentParseResultType.SUCCESS.of(result);
                }
                result.add(parse);
            }
        }
    }
}
