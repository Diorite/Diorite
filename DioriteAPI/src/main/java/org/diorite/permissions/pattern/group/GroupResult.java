package org.diorite.permissions.pattern.group;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class GroupResult
{
    private final boolean valid; // if given string was parsed without problems, like no letters in number data.
    private final boolean matching; // if given string is in valid range, if hasPermission should return true.
    private final int     endIndex; // index of last char used for pattern.

    public GroupResult(final boolean valid, final boolean matching, final int endIndex)
    {
        this.valid = valid;
        this.matching = matching;
        this.endIndex = endIndex;
    }

    public boolean isValid()
    {
        return this.valid;
    }

    public boolean isMatching()
    {
        return this.matching;
    }

    public int getEndIndex()
    {
        return this.endIndex;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("valid", this.valid).append("matching", this.matching).append("endIndex", this.endIndex).toString();
    }
}
