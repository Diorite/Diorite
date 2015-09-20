package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Additional nbt type, added by diorite, it will break minecraft compatybility, use with caution. <br>
 * NBT type for arrays of double values.
 */
public class NbtTagDoubleArray extends NbtAbstractTag
{
    public static final double[] EMPTY = new double[0];

    protected double[] value;

    public NbtTagDoubleArray()
    {
        this.value = EMPTY;
    }

    public NbtTagDoubleArray(final String name)
    {
        super(name);
        this.value = EMPTY;
    }

    public NbtTagDoubleArray(final String name, final double[] value)
    {
        super(name);
        Validate.notNull(value, "array can't be null.");
        this.value = value;
    }

    public double[] getValue()
    {
        return this.value;
    }

    public void setValue(final double[] i)
    {
        Validate.notNull(i, "array can't be null.");
        this.value = i;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.DOUBLE_ARRAY;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        for (final double i : this.value)
        {
            outputStream.writeDouble(i);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readInt();
        final double[] data = new double[size];
        for (int i = 0; i < size; i++)
        {
            data[i] = inputStream.readDouble();
        }
        this.value = data;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}