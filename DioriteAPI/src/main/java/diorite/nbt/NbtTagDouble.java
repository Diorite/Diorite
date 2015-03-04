package diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagDouble extends NbtAbstractTag<NbtTagDouble>
{
    protected double value;

    public NbtTagDouble()
    {
    }

    public NbtTagDouble(final String name)
    {
        super(name);
    }

    public NbtTagDouble(final String name, final double value)
    {
        super(name);
        this.value = value;
    }

    public NbtTagDouble(final String name, final NbtTagCompound parent, final double value)
    {
        super(name, parent);
        this.value = value;
    }

    @Override
    public NbtTagDouble read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        this.value = inputStream.readDouble();
        return this;
    }

    @Override
    public NbtTagDouble write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        outputStream.writeDouble(this.value);
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.DOUBLE;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }

    public double getValue()
    {
        return this.value;
    }

    public void setValue(final double value)
    {
        this.value = value;
    }
}