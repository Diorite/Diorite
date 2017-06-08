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

class SimpleParserResult implements ParserResult
{
    private final CommandParserContext   context;
    private final Object[]               parsed;
    private final int                    lastIndex;
    private final ArgumentParseResult<?> lastResult;
    private final ParserResultType       type;

    SimpleParserResult(CommandParserContext context, Object[] parsed, int lastIndex, ArgumentParseResult<?> lastResult, ParserResultType type)
    {
        this.context = context;
        this.parsed = parsed;
        this.lastIndex = lastIndex;
        this.lastResult = lastResult;
        this.type = type;
    }

    @Override
    public Object[] getParsed()
    {
        return this.parsed;
    }

    @Override
    public int getLastIndex()
    {
        return this.lastIndex;
    }

    @Override
    public ArgumentParseResult<?> getLastResult()
    {
        return this.lastResult;
    }

    @Override
    public CommandParserContext getContext()
    {
        return this.context;
    }

    @Override
    public ParserResultType getType()
    {
        return this.type;
    }

    public static SimpleParserResult success(CommandParserContext context, Object[] parsed, ArgumentParseResult<?> last)
    {
        return new SimpleParserResult(context, parsed, parsed.length - 1, last, ParserResultType.SUCCESS);
    }

    public static SimpleParserResult error(CommandParserContext context, Object[] parsed, int index, ArgumentParseResult<?> last)
    {
        return new SimpleParserResult(context, parsed, index, last, ParserResultType.ERROR);
    }

    public static SimpleParserResult badSize(CommandParserContext context, Object[] parsed, int index, ArgumentParseResult<?> last)
    {
        return new SimpleParserResult(context, parsed, index, last, ParserResultType.BAD_SIZE);
    }
}
