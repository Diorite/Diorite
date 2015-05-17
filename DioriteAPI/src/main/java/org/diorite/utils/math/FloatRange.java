package org.diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class defining range in floats, may be used to validate numbers.
 */
public class FloatRange
{
    /**
     * Range from 0 to 0.
     */
    public static final FloatRange EMPTY = new FloatRange(0, 0);

    private final float min;
    private final float max;

    /**
     * Construct new range.
     *
     * @param min min value of range.
     * @param max max value of range.
     */
    public FloatRange(final float min, final float max)
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
    public FloatRange(final double min, final double max)
    {
        this((float) min, (float) max);
    }

    /**
     * @return min value in range.
     */
    public float getMin()
    {
        return this.min;
    }

    /**
     * @return max value in range.
     */
    public float getMax()
    {
        return this.max;
    }

    /**
     * @return random value in range.
     */
    public float getRandom()
    {
        return DioriteRandomUtils.getRandFloat(this.min, this.max);
    }

    /**
     * @return size of range. (max - min)
     */
    public double size()
    {
        return ((double) this.max - (double) this.min);
    }

    /**
     * Check if given number is in range.
     *
     * @param i number to check.
     *
     * @return true if it is in range
     */
    public boolean isIn(final float i)
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
    public double getIn(final float i)
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
    public double getIn(final float i, final float def)
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
    public double getIn(final float i, final double def)
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
    public float getIn(final double i)
    {
        if (i > this.max)
        {
            return this.max;
        }
        if (i < this.min)
        {
            return this.min;
        }
        return (float) i;
    }

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code i > max -> def}
     * {@code i < min -> def}
     * {@code else -> i}
     *
     * @param i number to validate.
     * @param def default value.
     *
     * @return closest number in range.
     */
    public float getIn(final double i, final double def)
    {
        if (! this.isIn(i))
        {
            return (float) def;
        }
        return (float) i;
    }

    @Override
    public int hashCode()
    {
        int result = ((this.min != + 0.0f) ? Float.floatToIntBits(this.min) : 0);
        result = (31 * result) + ((this.max != + 0.0f) ? Float.floatToIntBits(this.max) : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof FloatRange))
        {
            return false;
        }

        final FloatRange that = (FloatRange) o;

        return (Float.compare(that.max, this.max) == 0) && (Float.compare(that.min, this.min) == 0);

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
    public static FloatRange fixed(final float num)
    {
        return new FloatRange(num, num);
    }

    /**
     * Create range with only gived value in range.
     *
     * @param num min and max of range.
     *
     * @return range with only one value in range.
     */
    public static FloatRange fixed(final double num)
    {
        return new FloatRange(num, num);
    }
}
