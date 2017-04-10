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

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represent result of single argument parse.
 *
 * @param <T>
 *         type of argument.
 */
public class ArgumentParseResult<T>
{
    private final @Nullable T                       result;
    private final           ArgumentParseResultType type;
    private final @Nullable Exception               exception;

    protected ArgumentParseResult(@Nullable T result, ArgumentParseResultType type, @Nullable Exception exception)
    {
        this.result = result;
        this.type = type;
        if (type == ArgumentParseResultType.SUCCESS)
        {
            this.exception = exception;
        }
        else
        {
            this.exception = (exception == null) ? CommandParserException.fromResultType(type, null) : null;
        }
    }
    protected ArgumentParseResult(@Nullable T result, ArgumentParseResultType type, @Nullable Exception exception, ArgumentParseResult<?> last)
    {
        this.result = result;
        this.type = type;
        if (type == ArgumentParseResultType.SUCCESS)
        {
            this.exception = exception;
        }
        else
        {
            this.exception = (exception == null) ? CommandParserException.fromResultType(type, last.getException()) : null;
        }
    }

    /**
     * Returns exception related to this result.
     *
     * @return exception related to this result.
     */
    @Nullable
    public Exception getException()
    {
        return this.exception;
    }

    /**
     * Returns true if this result is of given type.
     *
     * @param type
     *         expected type.
     *
     * @return true if this result is of given type.
     */
    @SuppressWarnings("ObjectEquality")
    public boolean is(ArgumentParseResultType type)
    {
        return this.type == type;
    }

    /**
     * Returns true if this type of {@link ArgumentParseResultType#SUCCESS} type.
     *
     * @return true if this type of {@link ArgumentParseResultType#SUCCESS} type.
     */
    public boolean isSuccess()
    {
        return this.is(ArgumentParseResultType.SUCCESS);
    }

    /**
     * Returns type of this parse result.
     *
     * @return type of this parse result.
     */
    public ArgumentParseResultType getType()
    {
        return this.type;
    }

    /**
     * Returns parser result object. (if any)
     *
     * @return parser result object. (if any)
     */
    @Nullable
    public T getResult()
    {
        if (! this.isSuccess())
        {
            throw new IllegalStateException("getResult called for unsuccessful parse result.");
        }
        return this.result;
    }

    /**
     * Returns id of parse result.
     *
     * @return id of parse result.
     */
    public String getId()
    {
        return this.type.getId();
    }

    @Override
    public String toString()
    {
        if (this.isSuccess())
        {
            return new ToStringBuilder(this).appendSuper(super.toString()).append("result", this.result).toString();
        }
        return new ToStringBuilder(this).appendSuper(super.toString()).append("type", this.type).toString();
    }
}
