package diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagString extends NbtAbstractTag<NbtTagString>
{
    protected String value;

    public NbtTagString()
    {
    }

    public NbtTagString(final String name)
    {
        super(name);
    }

    public NbtTagString(final String name, final String value)
    {
        super(name);
        this.value = value;
    }

    public NbtTagString(final String name, final NbtTagCompound parent, final String value)
    {
        super(name, parent);
        this.value = value;
    }

    @Override
    public NbtTagString read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        final byte[] data = new byte[inputStream.readShort()];
        inputStream.readFully(data);
        this.value = new String(data, CHARSET);
        return this;
    }

    @Override
    public NbtTagString write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        final byte[] bytes = this.value.getBytes(CHARSET);
        outputStream.writeShort(bytes.length);
        outputStream.write(bytes);
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.STRING;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }

    public String getValue()
    {
        return this.value;
    }

    public void setValue(final String value)
    {
        this.value = value;
    }
}