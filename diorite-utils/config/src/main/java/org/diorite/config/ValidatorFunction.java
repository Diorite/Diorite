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

package org.diorite.config;

import javax.annotation.Nullable;

/**
 * Represents validator function that can throw an exception.
 *
 * @param <C>
 *         type of config.
 * @param <T>
 *         type of validated argument.
 */
public interface ValidatorFunction<C extends Config, T>
{
    /**
     * Runs validator and return validated argument or throws exception. <br>
     * Notice that both input and output might be null!
     *
     * @param data
     *         data to validate
     * @param config
     *         config instance.
     *
     * @return validated value
     */
    @Nullable
    T validate(@Nullable T data, C config) throws Exception;

    /**
     * Create validator function from simple validator function.
     *
     * @param validatorFunction
     *         simple validator.
     * @param <C>
     *         type of config.
     * @param <T>
     *         type of validated argument.
     *
     * @return validator that validates the argument and always return this same value as given.
     */
    static <C extends Config, T> ValidatorFunction<C, T> ofSimple(SimpleValidatorFunction<C, T> validatorFunction)
    {
        return (data, cfg) -> {
            validatorFunction.validate(data, cfg);
            return data;
        };
    }

    /**
     * Returns a validator that always returns its input argument.
     *
     * @param <C>
     *         type of config.
     * @param <T>
     *         the type of the input and output objects to the function
     *
     * @return a function that always returns its input argument
     */
    static <C extends Config, T> ValidatorFunction<C, T> nothing()
    {
        return (t, c) -> t;
    }

    /**
     * Represents validator function that can throw an exception.
     *
     * @param <C>
     *         type of config.
     * @param <T>
     *         type of validated argument.
     */
    interface SimpleValidatorFunction<C extends Config, T>
    {
        /**
         * Runs validator. Note that input might be null!
         *
         * @param data
         *         data to validate.
         * @param config
         *         config instance.
         */
        void validate(@Nullable T data, C config) throws Exception;
    }
}
