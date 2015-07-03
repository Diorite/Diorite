package org.diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class defining range in short, may be used to validate numbers.
 */
public class ShortRange
{
    /**
     * Range from 0 to 0.
     */
    public static final ShortRange EMPTY = new ShortRange(0, 0);
    /**
     * Range from {@link Short#MIN_VALUE} to {@link Short#MAX_VALUE}
     */
    public static final ShortRange FULL  = new ShortRange(Short.MIN_VALUE, Short.MAX_VALUE);

    private final short min;
    private final short max;

    /**
     * Construct new range.
     *
     * @param min min value of range.
     * @param max max value of range.
     */
    public ShortRange(final short min, final short max)
    {
        this.min = min;
        this.max = max;
    }

    /**
     * Construct new range.
     *
     * @param min min value of range.
     * @param max max value of range.
     */
    public ShortRange(final int min, final int max)
    {
        this((short) min, (short) max);
    }

    /**
     * @return min value in range.
     */
    public short getMin()
    {
        return this.min;
    }

    /**
     * @return max value in range.
     */
    public short getMax()
    {
        return this.max;
    }

    /**
     * @return random value in range.
     */
    public short getRandom()
    {
        return (short) DioriteRandomUtils.getRandInt(this.min, this.max);
    }

    /**
     * @return size of range. (max - min + 1)
     */
    public int size()
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
    public boolean isIn(final int i)
    {
        return (i >= this.min) && (i <= this.max);
    }

    /**
     * Check if given number is in range.
     *
     * @param i number to check.
     *
     * @return true if it is in range
     */
    public boolean isIn(final short i)
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
    public int getIn(final short i)
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
    public int getIn(final short i, final short def)
    {
        if (! this.isIn(i))
        {
            return def;
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
    public int getIn(final short i, final int def)
    {
        if (! this.isIn(i))
        {
            return def;
        }
        return i;
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
    public short getIn(final int i)
    {
        if (i > this.max)
        {
            return this.max;
        }
        if (i < this.min)
        {
            return this.min;
        }
        return (short) i;
    }

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code i > max -> def}
     * {@code i < min -> def}
     * {@code else -> i}
     *
     * @param i   number to validate.
     * @param def default value.
     *
     * @return closest number in range.
     */
    public short getIn(final int i, final int def)
    {
        if (! this.isIn(i))
        {
            return (short) def;
        }
        return (short) i;
    }

    @Override
    public int hashCode()
    {
        int result = (int) this.min;
        result = (31 * result) + (int) this.max;
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ShortRange))
        {
            return false;
        }

        final ShortRange byteRange = (ShortRange) o;

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
    public static ShortRange fixed(final int num)
    {
        return new ShortRange(num, num);
    }

    /**
     * Create range with only gived value in range.
     *
     * @param num min and max of range.
     *
     * @return range with only one value in range.
     */
    public static ShortRange fixed(final short num)
    {
        return new ShortRange(num, num);
    }
}
