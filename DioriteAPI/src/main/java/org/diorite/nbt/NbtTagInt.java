package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagInt extends NbtAbstractTag
{
    protected int value;

    public NbtTagInt()
    {
    }

    public NbtTagInt(final String name)
    {
        super(name);
    }

    public NbtTagInt(final String name, final int value)
    {
        super(name);
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public void setValue(final int i)
    {
        this.value = i;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.INTEGER;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous) throws IOException
    {
        super.read(inputStream, anonymous);
        this.value = inputStream.readInt();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}