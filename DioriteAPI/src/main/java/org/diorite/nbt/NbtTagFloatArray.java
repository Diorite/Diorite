package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Additional nbt type, added by diorite, it will break minecraft compatybility, use with caution. <br>
 * NBT type for arrays of float values.
 */
public class NbtTagFloatArray extends NbtAbstractTag
{
    public static final float[] EMPTY = new float[0];

    protected float[] value;

    public NbtTagFloatArray()
    {
        this.value = EMPTY;
    }

    public NbtTagFloatArray(final String name)
    {
        super(name);
        this.value = EMPTY;
    }

    public NbtTagFloatArray(final String name, final float[] value)
    {
        super(name);
        Validate.notNull(value, "array can't be null.");
        this.value = value;
    }

    public float[] getValue()
    {
        return this.value;
    }

    public void setValue(final float[] i)
    {
        Validate.notNull(i, "array can't be null.");
        this.value = i;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.FLOAT_ARRAY;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        for (final float i : this.value)
        {
            outputStream.writeFloat(i);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readInt();
        final float[] data = new float[size];
        for (int i = 0; i < size; i++)
        {
            data[i] = inputStream.readFloat();
        }
        this.value = data;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}