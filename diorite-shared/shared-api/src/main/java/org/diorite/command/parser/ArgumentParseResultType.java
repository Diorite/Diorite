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

/**
 * Represent parsing result of single argument, plugins may use (and create) own results using{@link #getOrCreate(String)}.
 */
public interface ArgumentParseResultType
{
    // plugin may create own results
    ArgumentParseResultType SUCCESS                = getOrCreate("diorite.arguments.success");
    ArgumentParseResultType SYNTAX_ERROR           = getOrCreate("diorite.arguments.syntax_error");
    ArgumentParseResultType INVALID_KEYWORD        = getOrCreate("diorite.arguments.invalid_keyword");
    ArgumentParseResultType BAD_SIZE               = getOrCreate("diorite.arguments.bad_size");
    ArgumentParseResultType EMPTY                  = getOrCreate("diorite.arguments.empty");
    ArgumentParseResultType NUMBER_TOO_LARGE       = getOrCreate("diorite.arguments.number_too_large");
    ArgumentParseResultType NUMBER_TOO_SMALL       = getOrCreate("diorite.arguments.number_too_small");
    ArgumentParseResultType ARRAY_TOO_LARGE        = getOrCreate("diorite.arguments.array_too_large");
    ArgumentParseResultType ARRAY_TOO_SMALL        = getOrCreate("diorite.arguments.array_too_small");
    ArgumentParseResultType STRING_TOO_LARGE       = getOrCreate("diorite.arguments.string_too_large");
    ArgumentParseResultType STRING_TOO_SMALL       = getOrCreate("diorite.arguments.string_too_small");
    ArgumentParseResultType STRING_INVALID_CHAR    = getOrCreate("diorite.arguments.string_invalid_char");
    ArgumentParseResultType STRING_INVALID_PATTERN = getOrCreate("diorite.arguments.string_invalid_pattern");
    ArgumentParseResultType BOOLEAN_UNKNOWN        = getOrCreate("diorite.arguments.boolean_unknown");

    /**
     * Returns id of parser result. Id might be used to fetch error message.
     *
     * @return id of parser result.
     */
    String getId();

    /**
     * Returns parse result with given result object.
     *
     * @param object
     *         result of parse.
     * @param <T>
     *         type of result.
     *
     * @return instance of parse result.
     */
    <T> ArgumentParseResult<T> of(T object);

    /**
     * Returns empty instance of this result.
     *
     * @param <T>
     *         type of result.
     *
     * @return empty instance of this result.
     */
    <T> ArgumentParseResult<T> empty();

    /**
     * Returns empty instance of this result.
     *
     * @param last
     *         last parsing result that caused this result.
     * @param <T>
     *         type of result.
     *
     * @return empty instance of this result.
     */
    <T> ArgumentParseResult<T> empty(ArgumentParseResult<?> last);

    /**
     * Returns empty instance of this result.
     *
     * @param <T>
     *         type of result.
     * @param exception
     *         exception that caused this result.
     *
     * @return empty instance of this result.
     */
    <T> ArgumentParseResult<T> empty(Exception exception);

    String toString();

    /**
     * Returns result type by id.
     *
     * @param id
     *         id of result type.
     *
     * @return exiting result type or null.
     */
    @Nullable
    static ArgumentParseResultType get(String id)
    {
        return SimpleArgumentParseResultType.results.get(id);
    }

    /**
     * Returns result type by id. <br>
     * If type does not exist yet it wil be created.
     *
     * @param id
     *         id of result type.
     *
     * @return exiting result type or newly created one.
     */
    static ArgumentParseResultType getOrCreate(String id)
    {
        ArgumentParseResultType result = get(id);
        if (result != null)
        {
            return result;
        }
        SimpleArgumentParseResultType parseResult = new SimpleArgumentParseResultType(id);
        SimpleArgumentParseResultType.results.put(id, parseResult);
        return parseResult;
    }

    /**
     * For null input returns instance of {@link #EMPTY}, otherwise returns this same object as given.
     *
     * @param result
     *         result to validate.
     * @param <T>
     *         type of result.
     *
     * @return always non-null parser result.
     */
    static <T> ArgumentParseResult<T> ensureNotNull(@Nullable ArgumentParseResult<T> result)
    {
        if (result == null)
        {
            return EMPTY.empty();
        }
        return result;
    }
}
