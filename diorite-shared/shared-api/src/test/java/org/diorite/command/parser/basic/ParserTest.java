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

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import org.diorite.command.parser.ArgumentParseResult;
import org.diorite.command.parser.FakeParsersManager;
import org.diorite.command.parser.ParserContext;
import org.diorite.command.parser.TypeParser;

public class ParserTest<T>
{
    private static final FakeParsersManager manager = new FakeParsersManager();
    private final TypeParser<T> parser;

    public ParserTest(TypeParser<T> parser)
    {
        this.parser = parser;
    }

    void assertResult(String input, boolean expected, @Nullable T expectedResult, String... note)
    {
        this.assertResult(this.parser, input, expected, expectedResult, note);
    }

    void assertResult(TypeParser<T> parser, String input, boolean expected, @Nullable T expectedResult, String... note)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(parser.getClass().getSimpleName());
        if (note.length != 0)
        {
            sb.append("(");
            sb.append(StringUtils.join(note, ", "));
            sb.append(")");
        }
        sb.append(": `").append(input).append("` -> ");
        ParserContext context = manager.createContext(input);
        ArgumentParseResult<? extends T> parseResult = parser.checkAndParse(context, TypeParser.SPACE_PREDICATE);
        if (parseResult.isSuccess())
        {
            sb.append(parseResult.getResult());
            System.out.println(sb);
            if (parseResult.isSuccess() && expected)
            {
                Assert.assertEquals(sb.toString(), expectedResult, parseResult.getResult());
            }
        }
        else
        {
            sb.append("failed -> ").append(parseResult.getId());
            System.out.println(sb);
        }
        if (expected != parseResult.isSuccess())
        {
            Exception exception = parseResult.getException();
            if (exception != null)
            {
                exception.printStackTrace();
            }
        }
        Assert.assertSame(sb.toString(), expected, parseResult.isSuccess());
    }
}
