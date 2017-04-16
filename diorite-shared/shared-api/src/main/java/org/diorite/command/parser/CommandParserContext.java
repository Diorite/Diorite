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

package org.diorite.command.parser;

import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

import org.diorite.command.Argument;
import org.diorite.commons.ParserContext;
import org.diorite.commons.arrays.DioriteArrayUtils;

public class CommandParserContext extends ParserContext
{
    private final Deque<Argument<?>> arguments = new LinkedBlockingDeque<>();
    private final Object[] result;
    private int index = 0;

    CommandParserContext(String data, Collection<? extends Argument<?>> arguments)
    {
        super(data);
        this.arguments.addAll(arguments);
        this.result = this.arguments.isEmpty() ? DioriteArrayUtils.EMPTY_OBJECT : new Object[this.arguments.size()];
    }

    /**
     * Run parser.
     *
     * @return parser result object.
     */
    public ParserResult parse()
    {
        if (this.arguments.isEmpty())
        {
            this.next();
            if (this.hasNext())
            {
                return SimpleParserResult.badSize(this, this.result, 0, ArgumentParseResultType.EMPTY.empty());
            }
            return SimpleParserResult.success(this, this.result, ArgumentParseResultType.SUCCESS.empty());
        }
        Argument<?> poll;
        ArgumentParseResult<?> parseResult = null;
        while ((poll = this.arguments.poll()) != null)
        {
            parseResult = poll.tryParse(this, r -> this.result[this.index++] = r);
            if (! parseResult.isSuccess())
            {
                if (parseResult.is(ArgumentParseResultType.EMPTY))
                {
                    return SimpleParserResult.badSize(this, this.result, this.index, parseResult);
                }
                return SimpleParserResult.error(this, this.result, this.index, parseResult);
            }
            char next = this.next();
            if (next != TypeParser.SPACE)
            {
                if (next == CommandParserContext.DONE)
                {
                    if (! this.arguments.isEmpty())
                    {
                        return SimpleParserResult.badSize(this, this.result, this.index, parseResult);
                    }
                    break;
                }
                return SimpleParserResult.error(this, this.result, this.index, parseResult);
            }
        }
        if (parseResult == null)
        {
            return SimpleParserResult.badSize(this, this.result, 0, ArgumentParseResultType.EMPTY.empty());
        }
        if (this.hasNext())
        {
            return SimpleParserResult.badSize(this, this.result, this.index - 1, parseResult);
        }
        return SimpleParserResult.success(this, this.result, parseResult);
    }

    /**
     * Returns expected amount of arguments.
     *
     * @return expected amount of arguments.
     */
    public int getExpectedSize()
    {
        return this.result.length;
    }

    @Override
    public CommandParserContext clone()
    {
        return (CommandParserContext) super.clone();
    }
}
