package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagIntArray extends NbtAbstractTag
{
    public static final int[] EMPTY = new int[0];

    protected int[] value;

    public NbtTagIntArray()
    {
        this.value = EMPTY;
    }

    public NbtTagIntArray(final String name)
    {
        super(name);
        this.value = EMPTY;
    }

    public NbtTagIntArray(final String name, final int[] value)
    {
        super(name);
        this.value = value;
    }

    public int[] getValue()
    {
        return this.value;
    }

    public void setValue(final int[] i)
    {
        this.value = i;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.INTEGER_ARRAY;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        for (final int i : this.value)
        {
            outputStream.writeInt(i);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readInt();
        final int[] data = new int[size];
        for (int i = 0; i < size; i++)
        {
            data[i] = inputStream.readInt();
        }
        this.value = data;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}