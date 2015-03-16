package org.diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DoubleRange
{
    public static final DoubleRange EMPTY = new DoubleRange(0, 0);

    private final double min;
    private final double max;

    public DoubleRange(final double min, final double max)
    {
        this.min = min;
        this.max = max;
    }

    public double getMin()
    {
        return this.min;
    }

    public double getMax()
    {
        return this.max;
    }

    public double getRandom()
    {
        return DioriteRandomUtils.getRandDouble(this.min, this.max);
    }

    public double size()
    {
        return (this.max - this.min);
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

    public static DoubleRange fixed(final double num)
    {
        return new DoubleRange(num, num);
    }
}
