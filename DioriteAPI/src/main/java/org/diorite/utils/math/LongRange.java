package org.diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LongRange
{
    public static final LongRange EMPTY = new LongRange(0, 0);
    public static final LongRange FULL  = new LongRange(Long.MIN_VALUE, Long.MAX_VALUE);

    private final long min;
    private final long max;

    public LongRange(final long min, final long max)
    {
        this.min = min;
        this.max = max;
    }

    public long getMin()
    {
        return this.min;
    }

    public long getMax()
    {
        return this.max;
    }

    public long getRandom()
    {
        return DioriteRandomUtils.getRandLong(this.min, this.max);
    }

    public long size()
    {
        return ((this.max - this.min) + 1);
    }

    public boolean isIn(final long i)
    {
        return (i >= this.min) && (i <= this.max);
    }

    @Override
    public int hashCode()
    {
        int result = (int) (this.min ^ (this.min >>> 32));
        result = (31 * result) + (int) (this.max ^ (this.max >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof LongRange))
        {
            return false;
        }

        final LongRange longRange = (LongRange) o;

        return (this.max == longRange.max) && (this.min == longRange.min);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("min", this.min).append("max", this.max).toString();
    }

    public static LongRange fixed(final long num)
    {
        return new LongRange(num, num);
    }
}
