package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagString extends NbtAbstractTag
{
    protected String value;

    public NbtTagString()
    {
    }

    public NbtTagString(final String name)
    {
        super(name);
    }

    public NbtTagString(final String name, final String value)
    {
        super(name);
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public void setValue(final String s)
    {
        this.value = s;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.STRING;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        final byte[] outputBytes = this.value.getBytes(NbtTag.STRING_CHARSET);
        outputStream.writeShort(outputBytes.length);
        outputStream.write(outputBytes);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readShort();
        final byte[] data = new byte[size];
        inputStream.readFully(data);
        this.value = new String(data, NbtTag.STRING_CHARSET);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}