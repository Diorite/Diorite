package org.diorite.utils.validator.string;

import java.util.function.Predicate;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class StringCustomValidatorImpl<T extends Predicate<String>> implements StringCustomValidator<T>
{
    private final T test;

    StringCustomValidatorImpl(final T pattern)
    {
        Validate.notNull(pattern, "Predicate can't be null.");
        this.test = pattern;
    }

    @Override
    public T getPredicate()
    {
        return this.test;
    }

    @Override
    public boolean validate(final String s)
    {
        return (s != null) && this.test.test(s);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof StringCustomValidator))
        {
            return false;
        }

        final StringCustomValidator<?> that = (StringCustomValidator<?>) o;
        return this.test.equals(that.getPredicate());
    }

    @Override
    public int hashCode()
    {
        return this.test.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("test", this.test).toString();
    }
}
