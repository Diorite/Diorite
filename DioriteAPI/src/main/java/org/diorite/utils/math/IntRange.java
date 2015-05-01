package org.diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class defining range in ints, may be used to validate numbers.
 */
public class IntRange
{
    /**
     * Range from 0 to 0.
     */
    public static final IntRange EMPTY = new IntRange(0, 0);
    /**
     * Range from {@link Integer.MIN_VALUE} to {@link Integer.MAX_VALUE}
     */
    public static final IntRange FULL  = new IntRange(Integer.MIN_VALUE, Integer.MAX_VALUE);

    private final int min;
    private final int max;

    /**
     * Construct new range.
     *
     * @param min min value of range.
     * @param max max value of range.
     */
    public IntRange(final int min, final int max)
    {
        this.min = min;
        this.max = max;
    }

    /**
     * @return min value in range.
     */
    public int getMin()
    {
        return this.min;
    }

    /**
     * @return max value in range.
     */
    public int getMax()
    {
        return this.max;
    }

    /**
     * @return random value in range.
     */
    public int getRandom()
    {
        return DioriteRandomUtils.getRandInt(this.min, this.max);
    }

    /**
     * @return size of range. (max - min + 1)
     */
    public long size()
    {
        return (((long) this.max - (long) this.min) + 1L);
    }

    /**
     * Check if given number is in range.
     *
     * @param i number to check.
     *
     * @return true if it is in range
     */
    public boolean isIn(final int i)
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
    public int getIn(final int i)
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
    public int getIn(final int i, final int def)
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

    /**
     * Create range with only gived value in range.
     *
     * @param num min and max of range.
     *
     * @return range with only one value in range.
     */
    public static IntRange fixed(final int num)
    {
        return new IntRange(num, num);
    }
}
