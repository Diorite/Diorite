package diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ShortRange
{
    public static final ShortRange EMPTY = new ShortRange(0, 0);
    public static final ShortRange FULL  = new ShortRange(Short.MIN_VALUE, Short.MAX_VALUE);

    private final short min;
    private final short max;

    public ShortRange(final short min, final short max)
    {
        this.min = min;
        this.max = max;
    }

    public ShortRange(final int min, final int max)
    {
        this((short) min, (short) max);
    }

    public short getMin()
    {
        return this.min;
    }

    public short getMax()
    {
        return this.max;
    }

    public short getRandom()
    {
        return (short) DioriteRandomUtils.getRandInt(this.min, this.max);
    }

    public int size()
    {
        return ((this.max - this.min) + 1);
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

    public static ShortRange fixed(final int num)
    {
        return new ShortRange(num, num);
    }

    public static ShortRange fixed(final short num)
    {
        return new ShortRange(num, num);
    }
}
