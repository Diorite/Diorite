package diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagInt extends NbtAbstractTag<NbtTagInt>
{
    protected int value;

    public NbtTagInt()
    {
    }

    public NbtTagInt(final String name)
    {
        super(name);
    }

    public NbtTagInt(final String name, final int value)
    {
        super(name);
        this.value = value;
    }

    public NbtTagInt(final String name, final NbtTagCompound parent, final int value)
    {
        super(name, parent);
        this.value = value;
    }

    @Override
    public NbtTagInt read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        this.value = inputStream.readInt();
        return this;
    }

    @Override
    public NbtTagInt write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        outputStream.writeInt(this.value);
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.INTEGER;
    }

    public int getValue()
    {
        return this.value;
    }

    public void setValue(final int value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}