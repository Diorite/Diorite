package org.diorite.utils.validator.string;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class StringLengthValidatorImpl implements StringLengthValidator
{
    private final int minLength;
    private final int maxLength;

    StringLengthValidatorImpl(final int minLength, final int maxLength)
    {
        Validate.isTrue((maxLength >= minLength) || ((maxLength == - 1) ^ (minLength == - 1)), "Min length can't be higher than max. (" + minLength + " > " + maxLength + ")");
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public int getMinLength()
    {
        return this.minLength;
    }

    @Override
    public int getMaxLength()
    {
        return this.maxLength;
    }

    @Override
    public boolean validate(final String s) throws RuntimeException
    {
        if (s == null)
        {
            return false;
        }
        final int l = s.length();
        if (this.minLength == - 1)
        {
            return l <= this.maxLength;
        }
        if (this.maxLength == - 1)
        {
            return l >= this.minLength;
        }
        return (l >= this.minLength) && (l <= this.maxLength);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof StringLengthValidator))
        {
            return false;
        }

        final StringLengthValidator that = (StringLengthValidator) o;
        return (this.minLength == that.getMinLength()) && (this.maxLength == that.getMaxLength());
    }

    @Override
    public int hashCode()
    {
        int result = this.minLength;
        result = (31 * result) + this.maxLength;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("minLength", this.minLength).append("maxLength", this.maxLength).toString();
    }
}
