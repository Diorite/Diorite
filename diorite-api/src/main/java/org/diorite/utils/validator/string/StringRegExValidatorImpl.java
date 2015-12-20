package org.diorite.utils.validator.string;

import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class StringRegExValidatorImpl implements StringRegExValidator
{
    private final Pattern pattern;

    StringRegExValidatorImpl(final Pattern pattern)
    {
        Validate.notNull(pattern, "Pattern can't be null.");
        this.pattern = pattern;
    }

    @Override
    public Pattern getPattern()
    {
        return this.pattern;
    }

    @Override
    public boolean validate(final String s)
    {
        return (s != null) && this.pattern.matcher(s).matches();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof StringRegExValidator))
        {
            return false;
        }

        final StringRegExValidator that = (StringRegExValidator) o;
        return this.pattern.equals(that.getPattern());
    }

    @Override
    public int hashCode()
    {
        return this.pattern.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }
}
