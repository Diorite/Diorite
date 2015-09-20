package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Additional nbt type, added by diorite, it will break minecraft compatybility, use with caution. <br>
 * NBT type for arrays of String values.
 */
public class NbtTagStringArray extends NbtAbstractTag
{
    public static final String[] EMPTY = new String[0];

    protected String[] value;

    public NbtTagStringArray()
    {
        this.value = EMPTY;
    }

    public NbtTagStringArray(final String name)
    {
        super(name);
        this.value = EMPTY;
    }

    public NbtTagStringArray(final String name, final String[] value)
    {
        super(name);
        Validate.notNull(value, "array can't be null.");
        this.value = value;
    }

    public String[] getValue()
    {
        return this.value;
    }

    public void setValue(final String[] i)
    {
        Validate.notNull(i, "array can't be null.");
        this.value = i;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.STRING_ARRAY;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        for (final String i : this.value)
        {
            if (i == null)
            {
                outputStream.writeShort(- 1);
                continue;
            }
            final byte[] outputBytes = i.getBytes(NbtTag.STRING_CHARSET);
            outputStream.writeShort(outputBytes.length);
            outputStream.write(outputBytes);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readInt();
        final String[] data = new String[size];
        for (int i = 0; i < size; i++)
        {
            final int strSize = inputStream.readShort();
            if (strSize == - 1)
            {
                data[i] = null;
                continue;
            }
            final byte[] strData = new byte[strSize];
            inputStream.readFully(strData);
            data[i] = new String(strData, NbtTag.STRING_CHARSET).intern();
        }
        this.value = data;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}