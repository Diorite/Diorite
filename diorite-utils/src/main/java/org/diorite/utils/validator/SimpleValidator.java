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

import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class SimpleValidator<T> implements Validator<T>
{
    private final Validator<T>[] validatorEntries;

    @SafeVarargs
    SimpleValidator(final Validator<T>... validatorEntries)
    {
        this.validatorEntries = validatorEntries.clone();
    }

    @Override
    public boolean validate(final T object) throws RuntimeException
    {
        for (final Validator<T> validatorEntry : this.validatorEntries)
        {
            if (! validatorEntry.validate(object))
            {
                return false;
            }
        }
        return true;
    }

    static class ValidatorEntry<T, P extends Predicate<T>> implements Validator<T>
    {
        private final P                                            predicate;
        private final BiFunction<T, P, ? extends RuntimeException> exception;

        ValidatorEntry(final P predicate, final BiFunction<T, P, ? extends RuntimeException> exception)
        {
            this.predicate = predicate;
            this.exception = exception;
        }

        @Override
        public boolean validate(final T object) throws RuntimeException
        {
            if (this.predicate.test(object))
            {
                return true;
            }
            if (this.exception == null)
            {
                return false;
            }
            final RuntimeException exception = this.exception.apply(object, this.predicate);
            if (exception == null)
            {
                return false;
            }
            throw exception;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("predicate", this.predicate).append("exception", this.exception).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("validatorEntries", this.validatorEntries).toString();
    }
}
