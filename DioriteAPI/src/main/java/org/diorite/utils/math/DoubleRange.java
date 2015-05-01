package org.diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class defining range in doubles, may be used to validate numbers.
 */
public class DoubleRange
{
    /**
     * Range from 0 to 0.
     */
    public static final DoubleRange EMPTY = new DoubleRange(0, 0);

    private final double min;
    private final double max;

    /**
     * Construct new range.
     *
     * @param min min value of range.
     * @param max max value of range.
     */
    public DoubleRange(final double min, final double max)
    {
        this.min = min;
        this.max = max;
    }

    /**
     * @return min value in range.
     */
    public double getMin()
    {
        return this.min;
    }

    /**
     * @return max value in range.
     */
    public double getMax()
    {
        return this.max;
    }

    /**
     * @return random value in range.
     */
    public double getRandom()
    {
        return DioriteRandomUtils.getRandDouble(this.min, this.max);
    }

    /**
     * @return size of range. (max - min)
     */
    public double size()
    {
        return (this.max - this.min);
    }

    /**
     * Check if given number is in range.
     *
     * @param i number to check.
     *
     * @return true if it is in range
     */
    public boolean isIn(final double i)
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
    public double getIn(final double i)
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
    public double getIn(final double i, final double def)
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
        int result;
        long temp;
        temp = Double.doubleToLongBits(this.min);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.max);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof DoubleRange))
        {
            return false;
        }

        final DoubleRange that = (DoubleRange) o;

        return (Double.compare(that.max, this.max) == 0) && (Double.compare(that.min, this.min) == 0);

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
    public static DoubleRange fixed(final double num)
    {
        return new DoubleRange(num, num);
    }
}
