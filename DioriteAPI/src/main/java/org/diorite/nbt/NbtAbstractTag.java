package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class NbtAbstractTag implements NbtTag
{
    protected String name;
    protected NbtTagContainer parent = null;

    public NbtAbstractTag()
    {
    }

    public NbtAbstractTag(final String name)
    {
        this.setName(name);
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void setName(final String name)
    {
        if (this.parent != null)
        {
            this.parent.removeTag(this);
        }
        this.name = name;
        if (this.parent != null)
        {
            if (this.parent instanceof NbtAnonymousTagContainer)
            {
                ((NbtAnonymousTagContainer) this.parent).addTag(this);
            }
            else
            {
                ((NbtNamedTagContainer) this.parent).addTag(this);
            }
        }
    }

    @Override
    public NbtTagContainer getParent()
    {
        return this.parent;
    }

    @Override
    public void setParent(final NbtTagContainer parent)
    {
        if (this.parent != null)
        {
            this.parent.removeTag(this);
        }
        this.parent = parent;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        if (! anonymous)
        {
            final byte[] name = this.getNameBytes();
            outputStream.writeShort(name.length);
            outputStream.write(name);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        if (! anonymous)
        {
            final int nameSize = inputStream.readShort();
            final byte[] nameBytes = new byte[nameSize];
            inputStream.readFully(nameBytes);
            this.setName(new String(nameBytes, STRING_CHARSET));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("parent", this.parent).toString();
    }
}