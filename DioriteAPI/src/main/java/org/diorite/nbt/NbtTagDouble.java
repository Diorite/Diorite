package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagDouble extends NbtAbstractTag
{
    protected double value;

    public NbtTagDouble()
    {
    }

    public NbtTagDouble(final String name)
    {
        super(name);
    }

    public NbtTagDouble(final String name, final double value)
    {
        super(name);
        this.value = value;
    }

    public double getValue()
    {
        return this.value;
    }

    public void setValue(final double d)
    {
        this.value = d;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.DOUBLE;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeDouble(this.value);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous) throws IOException
    {
        super.read(inputStream, anonymous);
        this.value = inputStream.readDouble();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}