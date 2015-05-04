package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagFloat extends NbtAbstractTag
{
    protected float value;

    public NbtTagFloat()
    {
    }

    public NbtTagFloat(final String name)
    {
        super(name);
    }

    public NbtTagFloat(final String name, final float value)
    {
        super(name);
        this.value = value;
    }

    public NbtTagFloat(final String name, final double value)
    {
        super(name);
        this.value = (float) value;
    }

    public float getValue()
    {
        return this.value;
    }

    public void setValue(final float f)
    {
        this.value = f;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.FLOAT;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeFloat(this.value);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        this.value = inputStream.readFloat();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}