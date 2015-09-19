package org.diorite.utils.math;

import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class defining range in longs, may be used to validate numbers.
 */
public class LongRange
{
    /**
     * Range from 0 to 0.
     */
    public static final LongRange EMPTY = new LongRange(0, 0);
    /**
     * Range from {@link Long#MIN_VALUE} to {@link Long#MAX_VALUE}
     */
    public static final LongRange FULL  = new LongRange(Long.MIN_VALUE, Long.MAX_VALUE);

    private final long min;
    private final long max;

    /**
     * Construct new range.
     *
     * @param min min value of range.
     * @param max max value of range.
     */
    public LongRange(final long min, final long max)
    {
        this.min = min;
        this.max = max;
    }

    /**
     * @return min value in range.
     */
    public long getMin()
    {
        return this.min;
    }

    /**
     * @return max value in range.
     */
    public long getMax()
    {
        return this.max;
    }

    /**
     * @return random value in range.
     */
    public long getRandom()
    {
        return ((this.max - this.min) == 0) ? this.max : DioriteRandomUtils.getRandLong(this.min, this.max);
    }

    /**
     * Returns random value in range.
     *
     * @param random random instance to use.
     *
     * @return random value in range.
     */
    public long getRandom(final Random random)
    {
        return ((this.max - this.min) == 0) ? this.max : DioriteRandomUtils.getRandLong(random, this.min, this.max);
    }

    /**
     * @return size of range. (max - min + 1) may overflow
     */
    public long size()
    {
        return ((this.max - this.min) + 1);
    }

    /**
     * Check if given number is in range.
     *
     * @param i number to check.
     *
     * @return true if it is in range
     */
    public boolean isIn(final long i)
    {
        return (i >= this.min) && (i <= this.max);
    }

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code i > max -> max}
     * {@code i < min -> min}
     * {@code else -> i}
     *
     * @param i number to validate.
     *
     * @return closest number in range.
     */
    public long getIn(final long i)
    {
        if (i > this.max)
        {
            return this.max;
        }
        if (i < this.min)
        {
            return this.min;
        }
        return i;
    }

    /**
     * Return given number if it is in range, or default value.
     * {@code i > max -> def}
     * {@code i < min -> def}
     * {@code else -> i}
     *
     * @param i   number to validate.
     * @param def default value.
     *
     * @return given number or default value.
     */
    public long getIn(final long i, final long def)
    {
        if (! this.isIn(i))
        {
            return def;
        }
        return i;
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

    /**
     * Create range with only gived value in range.
     *
     * @param num min and max of range.
     *
     * @return range with only one value in range.
     */
    public static LongRange fixed(final long num)
    {
        return new LongRange(num, num);
    }
}
