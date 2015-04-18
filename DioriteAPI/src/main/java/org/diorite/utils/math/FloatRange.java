package org.diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FloatRange
{
    public static final FloatRange EMPTY = new FloatRange(0, 0);

    private final float min;
    private final float max;

    public FloatRange(final float min, final float max)
    {
        this.min = min;
        this.max = max;
    }

    public FloatRange(final double min, final double max)
    {
        this((float) min, (float) max);
    }

    public float getMin()
    {
        return this.min;
    }

    public float getMax()
    {
        return this.max;
    }

    public float getRandom()
    {
        return DioriteRandomUtils.getRandFloat(this.min, this.max);
    }

    public double size()
    {
        return ((double) this.max - (double) this.min);
    }

    public boolean isIn(final float i)
    {
        return (i >= this.min) && (i <= this.max);
    }

    public boolean isIn(final double i)
    {
        return (i >= this.min) && (i <= this.max);
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

    public static FloatRange fixed(final float num)
    {
        return new FloatRange(num, num);
    }

    public static FloatRange fixed(final double num)
    {
        return new FloatRange(num, num);
    }
}
