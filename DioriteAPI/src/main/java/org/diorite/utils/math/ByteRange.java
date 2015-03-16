package org.diorite.utils.math;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ByteRange
{
    public static final ByteRange EMPTY = new ByteRange(0, 0);
    public static final ByteRange FULL  = new ByteRange(Byte.MIN_VALUE, Byte.MAX_VALUE);

    private final byte min;
    private final byte max;

    public ByteRange(final byte min, final byte max)
    {
        this.min = min;
        this.max = max;
    }

    public ByteRange(final int min, final int max)
    {
        this((byte) min, (byte) max);
    }

    public byte getMin()
    {
        return this.min;
    }

    public byte getMax()
    {
        return this.max;
    }

    public byte getRandom()
    {
        return (byte) DioriteRandomUtils.getRandInt(this.min, this.max);
    }

    public short size()
    {
        return (short) ((this.max - this.min) + 1);
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
        if (! (o instanceof ByteRange))
        {
            return false;
        }

        final ByteRange byteRange = (ByteRange) o;

        return (this.max == byteRange.max) && (this.min == byteRange.min);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("min", this.min).append("max", this.max).toString();
    }

    public static ByteRange fixed(final int num)
    {
        return new ByteRange(num, num);
    }

    public static ByteRange fixed(final byte num)
    {
        return new ByteRange(num, num);
    }
}
