package diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class IntRange
{
    public static final IntRange EMPTY = new IntRange(0, 0);
    public static final IntRange FULL  = new IntRange(Integer.MIN_VALUE, Integer.MAX_VALUE);

    private final int min;
    private final int max;

    public IntRange(final int min, final int max)
    {
        this.min = min;
        this.max = max;
    }

    public int getMin()
    {
        return this.min;
    }

    public int getMax()
    {
        return this.max;
    }

    public int getRandom()
    {
        return DioriteRandomUtils.getRandInt(this.min, this.max);
    }

    public long size()
    {
        return (((long) this.max - (long) this.min) + 1L);
    }

    @Override
    public int hashCode()
    {
        int result = this.min;
        result = (31 * result) + this.max;
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof IntRange))
        {
            return false;
        }

        final IntRange byteRange = (IntRange) o;

        return (this.max == byteRange.max) && (this.min == byteRange.min);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("min", this.min).append("max", this.max).toString();
    }

    public static IntRange fixed(final int num)
    {
        return new IntRange(num, num);
    }
}
