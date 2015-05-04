package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagShort extends NbtAbstractTag
{
    protected short value;

    public NbtTagShort()
    {
    }

    public NbtTagShort(final String name)
    {
        super(name);
    }

    public NbtTagShort(final String name, final short value)
    {
        super(name);
        this.value = value;
    }

    public NbtTagShort(final String name, final int value)
    {
        super(name);
        this.value = (short) value;
    }

    public short getValue()
    {
        return this.value;
    }

    public void setValue(final short s)
    {
        this.value = s;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.SHORT;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeShort(this.value);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        this.value = inputStream.readShort();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}