package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagLong extends NbtAbstractTag
{
    protected long value;

    public NbtTagLong()
    {
    }

    public NbtTagLong(final String name)
    {
        super(name);
    }

    public NbtTagLong(final String name, final long value)
    {
        super(name);
        this.value = value;
    }

    public long getValue()
    {
        return this.value;
    }

    public void setValue(final long l)
    {
        this.value = l;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.LONG;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeLong(this.value);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        this.value = inputStream.readLong();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}