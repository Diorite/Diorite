package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagByteArray extends NbtAbstractTag
{
    public static final byte[] EMPTY = new byte[0];

    protected byte[] value;

    public NbtTagByteArray()
    {
        this.value = EMPTY;
    }

    public NbtTagByteArray(final String name)
    {
        super(name);
        this.value = EMPTY;
    }

    public NbtTagByteArray(final String name, final byte[] value)
    {
        super(name);
        this.value = value;
    }

    public byte[] getValue()
    {
        return this.value;
    }

    public void setValue(final byte[] b)
    {
        this.value = b;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.BYTE_ARRAY;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        outputStream.write(this.value);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readInt();
        final byte[] data = new byte[size];
        inputStream.readFully(data);
        this.value = data;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}