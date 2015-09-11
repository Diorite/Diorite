package org.diorite.impl.permissions.pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class GroupResult
{
    private final boolean valid; // if given string was parsed without problems, like no letters in number data.
    private final boolean matching; // if given string is in valid range, if hasPermission should return true.

    public GroupResult(final boolean valid, final boolean matching)
    {
        this.valid = valid;
        this.matching = matching;
    }

    public boolean isValid()
    {
        return this.valid;
    }

    public boolean isMatching()
    {
        return this.matching;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof GroupResult))
        {
            return false;
        }

        final GroupResult that = (GroupResult) o;

        return (this.valid == that.valid) && (this.matching == that.matching);

    }

    @Override
    public int hashCode()
    {
        int result = (this.valid ? 1 : 0);
        result = (31 * result) + (this.matching ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("valid", this.valid).append("matching", this.matching).toString();
    }
}
