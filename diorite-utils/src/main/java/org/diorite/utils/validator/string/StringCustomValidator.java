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

package org.diorite.utils.validator.string;

import java.util.function.Predicate;

import org.diorite.utils.validator.Validator;

/**
 * Validator interface for custom String check.
 */
public interface StringCustomValidator<T extends Predicate<String>> extends Validator<String>
{
    /**
     * Returns predicate used be this validator.
     *
     * @return predicate used be this validator.
     */
    @SuppressWarnings("unchecked")
    default T getPredicate()
    {
        return (T) this;
    }

    @Override
    default boolean test(final String s)
    {
        return this.validate(s);
    }

    /**
     * Create new custom String validator.
     *
     * @param predicate predicate to check string.
     * @param <T>       type of Predicate.
     *
     * @return new custom string validator.
     */
    static <T extends Predicate<String>> StringCustomValidator<T> create(final T predicate)
    {
        return new StringCustomValidatorImpl<>(predicate);
    }
}
