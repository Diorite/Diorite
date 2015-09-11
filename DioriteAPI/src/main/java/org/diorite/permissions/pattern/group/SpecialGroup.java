package org.diorite.permissions.pattern.group;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class SpecialGroup<R>
{
    /**
     * index of basePermission where data should be, like in "foo.{$++}.bar.{$--}" -> "foo..bar." it will be 3 and 8
     */
    protected final int index;

    /**
     * Construct new special group on given index.
     *
     * @param index index of basePermission where data should be stored.
     */
    public SpecialGroup(final int index)
    {
        this.index = index;
    }

    /**
     * Returns index of basePermission where data should be stored, like in "foo.{$++}.bar.{$--}" -> "foo..bar." it will be 3 and 8;
     *
     * @return index of basePermission where data should be stored.
     */
    public int getIndex()
    {
        return this.index;
    }

    public abstract GroupResult parse(final String string, final R data);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("index", this.index).toString();
    }
}
