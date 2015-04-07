package org.diorite.impl.connection.packets;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EntityMetadataObject
{
    private EntityMetadataCodec.DataType dataType;
    private int index;
    private Object data;

    public EntityMetadataObject(final EntityMetadataCodec.DataType dataType, final int index, final Object data)
    {
        this.dataType = dataType;
        this.index = index;
        this.data = data;
    }

    public EntityMetadataCodec.DataType getDataType()
    {
        return dataType;
    }

    public void setDataType(final EntityMetadataCodec.DataType dataType)
    {
        this.dataType = dataType;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(final int index)
    {
        this.index = index;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(final Object data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("dataType", this.dataType).append("index", this.index).append("data", this.data).toString();
    }
}