/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.utils.validator;

import java.util.function.Predicate;

/**
 * Basic validator interface, validators are created by builders and they may throw exception or return true/false.
 *
 * @param <T> type of validator.
 */
@FunctionalInterface
public interface Validator<T> extends Predicate<T>
{
    /**
     * Validate given object using this validator, method may throw exception on retun false if validation will fail.
     * <br>
     * There is also {@link #test(Object)} method that never throws exception, but should be used with caution as
     * it may affect performace.
     *
     * @param object object to validate.
     *
     * @return true if object is valid for this validator.
     *
     * @throws RuntimeException if object isn't valid and validator is set to throw error on invalid objects.
     */
    boolean validate(T object) throws RuntimeException;

    @Override
    default boolean test(final T t)
    {
        try
        {
            return this.validate(t);
        } catch (final RuntimeException exception)
        {
            return false;
        }
    }

    /**
     * Creates new Validator instance using sub-validators.
     *
     * @param validators validators to be used.
     * @param <T>        type of validator.
     *
     * @return created validator.
     */
    @SafeVarargs
    static <T> Validator<T> create(final Validator<T>... validators)
    {
        return new SimpleValidator<>(validators);
    }
}
