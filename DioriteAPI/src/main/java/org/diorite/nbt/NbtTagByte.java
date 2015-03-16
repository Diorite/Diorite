package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagByte extends NbtAbstractTag<NbtTagByte>
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

    public NbtTagByte(final String name, final NbtTagCompound parent, final byte value)
    {
        super(name, parent);
        this.value = value;
    }

    @Override
    public NbtTagByte read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        this.value = inputStream.readByte();
        return this;
    }

    @Override
    public NbtTagByte write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        outputStream.write(this.value);
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.BYTE;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }

    public byte getValue()
    {
        return this.value;
    }

    public void setValue(final byte value)
    {
        this.value = value;
    }
}