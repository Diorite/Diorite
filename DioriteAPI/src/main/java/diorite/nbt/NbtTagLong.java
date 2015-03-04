package diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagLong extends NbtAbstractTag<NbtTagLong>
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

    public NbtTagLong(final String name, final NbtTagCompound parent, final long value)
    {
        super(name, parent);
        this.value = value;
    }

    @Override
    public NbtTagLong read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        this.value = inputStream.readLong();
        return this;
    }

    @Override
    public NbtTagLong write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        outputStream.writeLong(this.value);
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.LONG;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }

    public long getValue()
    {
        return this.value;
    }

    public void setValue(final long value)
    {
        this.value = value;
    }
}