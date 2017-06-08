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

import org.diorite.command.annotation.arguments.BoolArg;
import org.diorite.command.parser.ArgumentParseResult;
import org.diorite.command.parser.ArgumentParseResultType;
import org.diorite.command.parser.TypeParser;
import org.diorite.command.parser.basic.tokens.BooleanTokens;
import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * Boolean parser that can accept selected strings as true/false values.
 */
public final class BooleanParser implements TypeParser<Boolean>
{
    private final BoolArg.Tokens     booleanTokens;
    private final TypeParser<String> stringTypeParser;

    public BooleanParser(@Nullable BoolArg.Tokens tokens, @Nullable TypeParser<String> stringTypeParser)
    {
        this.booleanTokens = (tokens == null) ? BoolArg.Tokens.DEFAULT : tokens;
        this.stringTypeParser = (stringTypeParser == null) ? RawStringParser.INSTANCE : stringTypeParser;
    }

    public BooleanParser(@Nullable BoolArg.Tokens tokens)
    {
        this(tokens, RawStringParser.INSTANCE);
    }

    public BooleanParser()
    {
        this(null, RawStringParser.INSTANCE);
    }

    @Override
    public ArgumentParseResult<Boolean> parse(ParserContext context, CharPredicate endPredicate)
    {
        ArgumentParseResult<? extends String> parse = ArgumentParseResultType.ensureNotNull(this.stringTypeParser.parse(context, endPredicate));
        if (! parse.isSuccess())
        {
            return parse.getType().empty();
        }
        String result = parse.getResult();
        if (result == null)
        {
            return parse.getType().empty();
        }
        Boolean bool = BooleanTokens.parse(result.trim(), this.booleanTokens);
        if (bool == null)
        {
            return ArgumentParseResultType.BOOLEAN_UNKNOWN.empty();
        }
        return ArgumentParseResultType.SUCCESS.of(bool);
    }
}
