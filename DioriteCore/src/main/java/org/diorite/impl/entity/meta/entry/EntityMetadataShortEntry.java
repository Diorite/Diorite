package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;

@SuppressWarnings("ClassHasNoToStringMethod")
public class EntityMetadataShortEntry extends EntityMetadataEntry<Short>
{
    private short value;

    public EntityMetadataShortEntry(final int index, final short value)
    {
        super(index);
        this.value = value;
    }

    public EntityMetadataShortEntry(final int index, final int value)
    {
        super(index);
        this.value = (short) value;
    }

    public short getValue()
    {
        return this.value;
    }

    public void setValue(final short value)
    {
        this.value = value;
    }

    public void setValue(final int value)
    {
        this.value = (short) value;
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.SHORT;
    }

    @Override
    public Short getData()
    {
        return this.value;
    }

    @Override
    public void setData(final Short data)
    {
        this.value = data;
    }
}