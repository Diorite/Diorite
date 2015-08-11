package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagByte extends NbtAbstractTag
{
    protected byte value;

    public NbtTagByte()
    {
    }

    public NbtTagByte(final String name)
    {
        super(name);
    }

    public NbtTagByte(final String name, final byte value)
    {
        super(name);
        this.value = value;
    }

    public NbtTagByte(final String name, final int value)
    {
        super(name);
        this.value = (byte) value;
    }

    public byte getValue()
    {
        return this.value;
    }

    public void setValue(final byte b)
    {
        this.value = b;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.BYTE;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.write(this.value);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        this.value = inputStream.readByte();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}