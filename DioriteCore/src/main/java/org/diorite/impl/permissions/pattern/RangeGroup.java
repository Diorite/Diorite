package org.diorite.impl.permissions.pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.utils.math.IntRange;

public class RangeGroup extends SpecialGroup
{
    protected final IntRange range;

    public RangeGroup(final int index, final IntRange range)
    {
        super(index);
        this.range = range;
    }

    public IntRange getRange()
    {
        return this.range;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("range", this.range).toString();
    }
}
