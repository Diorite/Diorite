package org.diorite.impl.permissions.pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SpecialGroup
{
    protected final int index; // index of basePermission where level should be, like in "foo.{$++}.bar.{$--}" -> "foo..bar." it will be 3 and 8

    public SpecialGroup(final int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return this.index;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("index", this.index).toString();
    }
}
