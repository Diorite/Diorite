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

package org.diorite.commons.function.function;

import javax.annotation.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.diorite.commons.DioriteUtils;

/**
 * A function that can throw an exception. <br>
 * It will catch any exception and sneaky throw it back, or use factory method to handle it.
 *
 * @param <T>
 *         the type of the input to the function
 * @param <R>
 *         the type of the result of the function
 */
public interface ExceptionalFunction<T, R> extends Function<T, R>
{
    /**
     * Applies this function to the given argument.
     *
     * @param t
     *         the function argument
     *
     * @return the function result
     *
     * @throws Throwable
     *         if operator will throw it.
     */
    @Nullable
    R applyOrThrow(T t) throws Throwable;

    /**
     * Applies this function to the given argument.
     *
     * @param t
     *         the function argument
     *
     * @return the function result
     */
    @Nullable
    @Override default R apply(T t)
    {
        try
        {
            return this.applyOrThrow(t);
        }
        catch (Throwable e)
        {
            throw DioriteUtils.sneakyThrow(e);
        }
    }

    /**
     * Helper method for easier casting.
     *
     * @param function
     *         function that might throw error.
     * @param <T>
     *         the type of the input to the function
     * @param <R>
     *         the type of the result of the function
     *
     * @return exceptional function instance.
     */
    static <T, R> ExceptionalFunction<T, R> of(ExceptionalFunction<T, R> function)
    {
        return function;
    }

    /**
     * Crates exceptional function with throwable handler.
     *
     * @param function
     *         function that might throw error.
     * @param consumer
     *         handler of error.
     * @param <T>
     *         the type of the input to the function
     * @param <R>
     *         the type of the result of the function
     *
     * @return created function.
     */
    static <T, R> ExceptionalFunction<T, R> withHandler(ExceptionalFunction<T, R> function, BiFunction<T, Throwable, R> consumer)
    {
        return (t) ->
        {
            try
            {
                return function.applyOrThrow(t);
            }
            catch (Throwable throwable)
            {
                return consumer.apply(t, throwable);
            }
        };
    }
}
