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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.IntFunction;

import org.apache.commons.lang3.ArrayUtils;

import org.diorite.command.annotation.arguments.MapArg;
import org.diorite.command.parser.ArgumentParseResult;
import org.diorite.command.parser.ArgumentParseResultType;
import org.diorite.command.parser.TypeParser;
import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * Map parser of fixed length - parser of collection of entries. <br>
 * Fixed length map does not need to follow normal map rules as in {@link MapParser} as parser knows where map should end.
 *
 * @param <K>
 *         type of map keys.
 * @param <K>
 *         type of map values.
 */
public final class FixedMapParser<K, V> implements TypeParser<Map<K, V>>
{
    public static int VARARGS_SIZE = - 1;

    private final MapArg.Tokens          mapTokens;
    private final TypeParser<K>          keyParser;
    private final TypeParser<V>          valueParser;
    private final IntFunction<Map<K, V>> mapSupplier;
    private       int                    i;

    public FixedMapParser(@Nullable MapArg.Tokens tokens, TypeParser<K> keyParser, TypeParser<V> valueParser, IntFunction<Map<K, V>> mapSupplier, int size)
    {
        this.mapTokens = (tokens == null) ? MapArg.Tokens.DEFAULT : tokens;
        this.keyParser = keyParser;
        this.valueParser = valueParser;
        this.mapSupplier = mapSupplier;
        this.i = size;
    }

    public FixedMapParser(TypeParser<K> keyParser, TypeParser<V> valueParser, IntFunction<Map<K, V>> mapSupplier, int size)
    {
        this(null, keyParser, valueParser, mapSupplier, size);
    }

    public FixedMapParser(TypeParser<K> keyParser, TypeParser<V> valueParser, int size)
    {
        this(null, keyParser, valueParser, LinkedHashMap::new, size);
    }

    public static <K, V> FixedMapParser<K, V> forVarargs(@Nullable MapArg.Tokens tokens, TypeParser<K> keyParser, TypeParser<V> valueParser,
                                                         IntFunction<Map<K, V>> mapSupplier)
    {
        return new FixedMapParser<>(tokens, keyParser, valueParser, mapSupplier, VARARGS_SIZE);
    }

    public static <K, V> FixedMapParser<K, V> forVarargs(TypeParser<K> keyParser, TypeParser<V> valueParser, IntFunction<Map<K, V>> mapSupplier)
    {
        return new FixedMapParser<>(keyParser, valueParser, mapSupplier, VARARGS_SIZE);
    }

    public static <K, V> FixedMapParser<K, V> forVarargs(TypeParser<K> keyParser, TypeParser<V> valueParser)
    {
        return new FixedMapParser<>(keyParser, valueParser, VARARGS_SIZE);
    }

    @Override
    public ArgumentParseResult<Map<K, V>> parse(ParserContext context, CharPredicate endPredicate)
    {
        MapArg.Tokens tokens = this.mapTokens;
        int size = this.i;
        Map<K, V> result = this.mapSupplier.apply((size == - 1) ? 5 : size);
        char c = context.next();
        int tokenIndex = ArrayUtils.indexOf(tokens.start(), c);
        if (tokenIndex != - 1)
        {
            while (true)
            {
                TypeParser.removeSpaces(context, true);
                ArgumentParseResult<? extends K> keyParse = ArgumentParseResultType.ensureNotNull(
                        this.keyParser.parse(context, e -> ArrayUtils.contains(tokens.entrySeparator(), e) || (e == SPACE)));
                if (! keyParse.isSuccess())
                {
                    return keyParse.getType().empty(keyParse);
                }
                K key = keyParse.getResult();
                if (key == null)
                {
                    return ArgumentParseResultType.SYNTAX_ERROR.empty(keyParse);
                }
                c = TypeParser.removeSpaces(context, false);
                if (! ArrayUtils.contains(tokens.entrySeparator(), c))
                {
                    return ArgumentParseResultType.SYNTAX_ERROR.empty();
                }
                TypeParser.removeSpaces(context, true);
                ArgumentParseResult<? extends V> valueParse = ArgumentParseResultType.ensureNotNull(
                        this.valueParser.parse(context, e -> ArrayUtils.contains(tokens.separator(), e) || (e == tokens.end()[tokenIndex]) || (e == SPACE)));
                if (! valueParse.isSuccess())
                {
                    return valueParse.getType().empty(valueParse);
                }
                V value = valueParse.getResult();
                if (value == null)
                {
                    return ArgumentParseResultType.SYNTAX_ERROR.empty(valueParse);
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
                        if ((size > - 1) && (-- size != 0))
                        {
                            return ArgumentParseResultType.BAD_SIZE.empty();
                        }
                        context.previous();
                        result.put(key, value);
                        return ArgumentParseResultType.SUCCESS.of(result);
                    }
                    return ArgumentParseResultType.SYNTAX_ERROR.empty();
                }
                result.put(key, value);
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
        else
        {
            context.previous();
            while (true)
            {
                ArgumentParseResult<? extends K> keyParse = ArgumentParseResultType.ensureNotNull(
                        this.keyParser.parse(context, e -> ArrayUtils.contains(tokens.entrySeparator(), e) || (e == SPACE)));
                if (! keyParse.isSuccess())
                {
                    return keyParse.getType().empty(keyParse);
                }
                K key = keyParse.getResult();
                if (key == null)
                {
                    return ArgumentParseResultType.SYNTAX_ERROR.empty(keyParse);
                }
                c = context.next();
                if (! ArrayUtils.contains(tokens.entrySeparator(), c))
                {
                    return ArgumentParseResultType.SYNTAX_ERROR.empty();
                }
                ArgumentParseResult<? extends V> valueParse = ArgumentParseResultType.ensureNotNull(
                        this.valueParser.parse(context, e -> ArrayUtils.contains(tokens.separator(), e) || (e == SPACE)));
                if (! valueParse.isSuccess())
                {
                    return valueParse.getType().empty(valueParse);
                }
                V value = valueParse.getResult();
                if (value == null)
                {
                    return ArgumentParseResultType.SYNTAX_ERROR.empty(valueParse);
                }
                c = context.next();
                if (! ArrayUtils.contains(tokens.separator(), c) && (c != SPACE))
                {
                    if ((size > - 1) && (-- size != 0))
                    {
                        return ArgumentParseResultType.BAD_SIZE.empty();
                    }
                    if (endPredicate.test(c))
                    {
                        context.previous();
                    }
                    result.put(key, value);
                    return ArgumentParseResultType.SUCCESS.of(result);
                }
                result.put(key, value);
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
