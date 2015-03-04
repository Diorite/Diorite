package diorite.nbt;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class NbtAbstractTag<T extends NbtAbstractTag<?>>
{
    public static final Charset CHARSET = Charset.forName("UTF-8");

    protected String name;

    protected NbtTagCompound parent = null;

    public NbtAbstractTag()
    {
    }

    public NbtAbstractTag(final String name)
    {
        this.updateName(name);
    }

    public NbtAbstractTag(final String name, final NbtTagCompound parent)
    {
        this.name = name;
        this.parent = parent;
    }

    public NbtAbstractTag<T> read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        if (hasName)
        {
            final byte[] bytes = new byte[inputStream.readShort()];
            inputStream.readFully(bytes);
            this.updateName(new String(bytes, CHARSET));
        }
        return this;
    }

    public NbtAbstractTag<T> write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        outputStream.writeByte(this.getTypeId());
        if (hasName)
        {
            final byte[] name = this.getNameBytes();
            outputStream.writeShort(name.length);
            outputStream.write(name);
        }
        return this;
    }

    public String getName()
    {
        return this.name;
    }

    protected void setName(final String name)
    {
        this.name = name;
    }

    public byte[] getNameBytes()
    {
        return this.name.getBytes(CHARSET);
    }

    public NbtTagCompound getParent()
    {
        return this.parent;
    }

    public void setParent(final NbtTagCompound parent)
    {
        if (this.parent != null)
        {
            this.parent.removeTag(this);
        }

        this.parent = parent;
    }

    public abstract NbtTagType getType();

    public byte getTypeId()
    {
        return this.getType().getTypeID();
    }

    public void updateName(final String name)
    {
        Validate.notNull(name, "Name can't be null");

        if (this.parent != null)
        {
            this.parent.removeTag(this);
        }
        this.name = name;
        if (this.parent != null)
        {
            this.parent.setTag(this);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("parent", this.parent).toString();
    }
}