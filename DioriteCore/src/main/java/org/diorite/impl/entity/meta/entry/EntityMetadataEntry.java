package org.diorite.impl.entity.meta.entry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.entity.meta.EntityMetadataType;

public abstract class EntityMetadataEntry<T>
{
    private final byte               index;
    private       boolean            dirty; // if true, will be send to clients on next update tick

    public EntityMetadataEntry(final byte index)
    {
        this.index = index;
    }

    public EntityMetadataEntry(final int index)
    {
        this.index = (byte) index;
    }

    public abstract EntityMetadataType getDataType();

    public byte getIndex()
    {
        return this.index;
    }

    public abstract T getData();

    public abstract void setData(final T data);

    public boolean isDirty()
    {
        return this.dirty;
    }

    public void setDirty(final boolean dirty)
    {
        this.dirty = dirty;
    }

    public void setDirty()
    {
        this.dirty = true;
    }

    public void setClean()
    {
        this.dirty = false;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof EntityMetadataEntry))
        {
            return false;
        }

        final EntityMetadataEntry<?> that = (EntityMetadataEntry<?>) o;

        if (this.dirty != that.dirty)
        {
            return false;
        }
        if (this.getDataType() != that.getDataType())
        {
            return false;
        }
        final T data = this.getData();
        final Object otherData = that.getData();
        return ! ((data != null) ? ! data.equals(otherData) : (otherData != null));

    }

    @Override
    public int hashCode()
    {
        int result = this.getDataType().hashCode();
        final T data = this.getData();
        result = (31 * result) + ((data != null) ? data.hashCode() : 0);
        result = (31 * result) + (this.dirty ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("dataType", this.getDataType()).append("index", this.index).append("data", this.getData()).toString();
    }
}