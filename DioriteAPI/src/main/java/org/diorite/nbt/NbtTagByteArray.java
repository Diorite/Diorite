package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagByteArray extends NbtAbstractTag<NbtTagByteArray>
{
    protected byte[] value;

    public NbtTagByteArray()
    {
    }

    public NbtTagByteArray(final String name)
    {
        super(name);
    }

    public NbtTagByteArray(final String name, final byte[] value)
    {
        super(name);
        this.value = value;
    }

    public NbtTagByteArray(final String name, final NbtTagCompound parent, final byte[] value)
    {
        super(name, parent);
        this.value = value;
    }

    @Override
    public NbtTagByteArray read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        final byte[] bytes = new byte[inputStream.readInt()];
        inputStream.readFully(bytes);
        this.value = bytes;
        return this;
    }

    @Override
    public NbtTagByteArray write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        outputStream.writeInt(this.value.length);
        outputStream.write(this.value);
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.BYTE_ARRAY;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }

    public byte[] getValue()
    {
        return this.value;
    }

    public void setValue(final byte[] value)
    {
        Validate.notNull(value, "bytes can't be null");
        this.value = value;
    }
}