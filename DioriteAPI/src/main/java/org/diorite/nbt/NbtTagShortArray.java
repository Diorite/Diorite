package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Additional nbt type, added by diorite, it will break minecraft compatybility, use with caution. <br>
 * NBT type for arrays of short values.
 */
public class NbtTagShortArray extends NbtAbstractTag
{
    public static final short[] EMPTY = new short[0];

    protected short[] value;

    public NbtTagShortArray()
    {
        this.value = EMPTY;
    }

    public NbtTagShortArray(final String name)
    {
        super(name);
        this.value = EMPTY;
    }

    public NbtTagShortArray(final String name, final short[] value)
    {
        super(name);
        Validate.notNull(value, "array can't be null.");
        this.value = value;
    }

    public short[] getValue()
    {
        return this.value;
    }

    public void setValue(final short[] i)
    {
        Validate.notNull(i, "array can't be null.");
        this.value = i;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.SHORT_ARRAY;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        for (final short i : this.value)
        {
            outputStream.writeShort(i);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readInt();
        final short[] data = new short[size];
        for (int i = 0; i < size; i++)
        {
            data[i] = inputStream.readShort();
        }
        this.value = data;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}