package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;

@SuppressWarnings("ClassHasNoToStringMethod")
public class EntityMetadataIntEntry extends EntityMetadataEntry<Integer>
{
    private int value;

    public EntityMetadataIntEntry(final int index, final int value)
    {
        super(index);
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public void setValue(final int value)
    {
        this.value = value;
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.INT;
    }

    @Override
    public Integer getData()
    {
        return this.value;
    }

    @Override
    public void setData(final Integer data)
    {
        this.value = data;
    }
}