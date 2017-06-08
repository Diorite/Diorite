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

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import org.apache.commons.lang3.ArrayUtils;

import org.diorite.command.annotation.arguments.EntryArg;
import org.diorite.command.parser.ArgumentParseResult;
import org.diorite.command.parser.ArgumentParseResultType;
import org.diorite.command.parser.TypeParser;
import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * Parser of map entries of given key and value parsers.
 */
public final class EntryParser<K, V> implements TypeParser<Entry<K, V>>
{
    private final EntryArg.Tokens               entryTokens;
    private final TypeParser<K>                 keyParser;
    private final TypeParser<V>                 valueParser;
    private final BiFunction<K, V, Entry<K, V>> entrySupplier;

    public EntryParser(@Nullable EntryArg.Tokens tokens, TypeParser<K> keyParser, TypeParser<V> valueParser, BiFunction<K, V, Entry<K, V>> entrySupplier)
    {
        this.entryTokens = (tokens == null) ? EntryArg.Tokens.DEFAULT : tokens;
        this.keyParser = keyParser;
        this.valueParser = valueParser;
        this.entrySupplier = entrySupplier;
    }

    public EntryParser(TypeParser<K> keyParser, TypeParser<V> valueParser, BiFunction<K, V, Entry<K, V>> entrySupplier)
    {
        this(null, keyParser, valueParser, entrySupplier);
    }

    public EntryParser(TypeParser<K> keyParser, TypeParser<V> valueParser)
    {
        this(null, keyParser, valueParser, Map::entry);
    }

    @Override
    public ArgumentParseResult<Entry<K, V>> parse(ParserContext context, CharPredicate endPredicate)
    {
        EntryArg.Tokens entryTokens = this.entryTokens;
        ArgumentParseResult<? extends K> keyParseResult = ArgumentParseResultType.ensureNotNull(
                this.keyParser.parse(context, e -> ArrayUtils.contains(entryTokens.separator(), e) || (e == SPACE)));
        if (! keyParseResult.isSuccess())
        {
            return keyParseResult.getType().empty(keyParseResult);
        }
        K key = keyParseResult.getResult();
        if (key == null)
        {
            return ArgumentParseResultType.SYNTAX_ERROR.empty(keyParseResult);
        }
        char c = context.next();
        if (! ArrayUtils.contains(entryTokens.separator(), c))
        {
            return ArgumentParseResultType.SYNTAX_ERROR.empty();
        }
        ArgumentParseResult<? extends V> valueParseResult = ArgumentParseResultType.ensureNotNull(this.valueParser.parse(context, SPACE_PREDICATE));
        if (! valueParseResult.isSuccess())
        {
            return valueParseResult.getType().empty(valueParseResult);
        }
        V value = valueParseResult.getResult();
        if (value == null)
        {
            return ArgumentParseResultType.SYNTAX_ERROR.empty(valueParseResult);
        }
        if (endPredicate.test(context.next()))
        {
            context.previous();
        }
        return ArgumentParseResultType.SUCCESS.of(this.entrySupplier.apply(key, value));
    }
}
