package diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagShort extends NbtAbstractTag<NbtTagShort>
{
    protected short value;

    public NbtTagShort()
    {
    }

    public NbtTagShort(final String name)
    {
        super(name);
    }

    public NbtTagShort(final String name, final short value)
    {
        super(name);
        this.value = value;
    }

    public NbtTagShort(final String name, final NbtTagCompound parent, final short value)
    {
        super(name, parent);
        this.value = value;
    }

    @Override
    public NbtTagShort read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        this.value = inputStream.readShort();
        return this;
    }

    @Override
    public NbtTagShort write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        outputStream.writeShort(this.value);
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.SHORT;
    }

    public short getValue()
    {
        return this.value;
    }

    public void setValue(final short value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}