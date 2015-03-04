package diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagFloat extends NbtAbstractTag<NbtTagFloat>
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

    public NbtTagFloat(final String name, final NbtTagCompound parent, final float value)
    {
        super(name, parent);
        this.value = value;
    }

    @Override
    public NbtTagFloat read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        this.value = inputStream.readFloat();
        return this;
    }

    @Override
    public NbtTagFloat write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        outputStream.writeFloat(this.value);
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.FLOAT;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }

    public float getValue()
    {
        return this.value;
    }

    public void setValue(final float value)
    {
        this.value = value;
    }
}