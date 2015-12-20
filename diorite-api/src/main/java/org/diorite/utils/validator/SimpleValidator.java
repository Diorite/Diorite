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
