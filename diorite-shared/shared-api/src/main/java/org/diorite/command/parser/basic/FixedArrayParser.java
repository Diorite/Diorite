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
 * Array/collection parser of fixed length, result of parsing is always a collection. <br>
 * Fixed length array does not need to follow normal array rules as in {@link ArrayParser} as parser knows where array should end.
 *
 * @param <T>
 *         type of collection elements.
 */
public final class FixedArrayParser<T> implements TypeParser<Collection<T>>
{
    public static int VARARGS_SIZE = - 1;

    private final ArrayArg.Tokens            arrayTokens;
    private final TypeParser<? extends T>    typeParser;
    private final IntFunction<Collection<T>> collectionSupplier;
    private       int                        expectedSize;

    public FixedArrayParser(@Nullable ArrayArg.Tokens tokens, TypeParser<? extends T> typeParser, IntFunction<Collection<T>> collectionSupplier, int size)
    {
        this.arrayTokens = (tokens == null) ? ArrayArg.Tokens.DEFAULT : tokens;
        this.typeParser = typeParser;
        this.collectionSupplier = collectionSupplier;
        this.expectedSize = size;
    }

    public FixedArrayParser(TypeParser<? extends T> typeParser, IntFunction<Collection<T>> collectionSupplier, int size)
    {
        this(null, typeParser, collectionSupplier, size);
    }

    public FixedArrayParser(TypeParser<? extends T> typeParser, int size)
    {
        this(null, typeParser, ArrayList::new, size);
    }

    public static <T> FixedArrayParser<T> forVarargs(@Nullable ArrayArg.Tokens tokens, TypeParser<? extends T> typeParser,
                                                     IntFunction<Collection<T>> collectionSupplier)
    {
        return new FixedArrayParser<>(tokens, typeParser, collectionSupplier, VARARGS_SIZE);
    }

    public static <T> FixedArrayParser<T> forVarargs(TypeParser<? extends T> typeParser, IntFunction<Collection<T>> collectionSupplier)
    {
        return new FixedArrayParser<>(typeParser, collectionSupplier, VARARGS_SIZE);
    }

    public static <T> FixedArrayParser<T> forVarargs(TypeParser<? extends T> typeParser)
    {
        return new FixedArrayParser<>(typeParser, VARARGS_SIZE);
    }

    @Override
    public boolean mustBeLast()
    {
        return this.expectedSize == VARARGS_SIZE;
    }

    @Override
    public ArgumentParseResult<Collection<T>> parse(ParserContext context, CharPredicate endPredicate)
    {
        ArrayArg.Tokens tokens = this.arrayTokens;
        int size = this.expectedSize;
        Collection<T> result = this.collectionSupplier.apply((size == VARARGS_SIZE) ? 10 : size);
        char c = context.next();
        int tokenIndex = ArrayUtils.indexOf(tokens.start(), c);
        if (tokenIndex != - 1)
        {
            while (true)
            {
                char next = TypeParser.removeSpaces(context, false);
                if (next == tokens.end()[tokenIndex])
                {
                    if (result.size() != this.expectedSize)
                    {
                        return ArgumentParseResultType.BAD_SIZE.empty();
                    }
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
                        if ((size > VARARGS_SIZE) && (-- size != 0))
                        {
                            return ArgumentParseResultType.BAD_SIZE.empty();
                        }
                        context.previous();
                        result.add(parse);
                        return ArgumentParseResultType.SUCCESS.of(result);
                    }
                    return ArgumentParseResultType.SYNTAX_ERROR.empty();
                }
                result.add(parse);
                if (-- size == 0)
                {
                    return ArgumentParseResultType.SUCCESS.of(result);
                }

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
                if (! ArrayUtils.contains(tokens.separator(), c) && (c != SPACE))
                {
                    if ((size > VARARGS_SIZE) && (-- size != 0))
                    {
                        return ArgumentParseResultType.BAD_SIZE.empty();
                    }
                    if (endPredicate.test(c))
                    {
                        context.previous();
                    }
                    result.add(parse);
                    return ArgumentParseResultType.SUCCESS.of(result);
                }
                result.add(parse);
                if (-- size == 0)
                {
                    if (! endPredicate.test(c))
                    {
                        return ArgumentParseResultType.BAD_SIZE.empty();
                    }
                    return ArgumentParseResultType.SUCCESS.of(result);
                }
            }
        }
    }
}
