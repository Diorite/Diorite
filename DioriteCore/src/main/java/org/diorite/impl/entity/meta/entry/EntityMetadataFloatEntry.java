package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;

@SuppressWarnings("ClassHasNoToStringMethod")
public class EntityMetadataFloatEntry extends EntityMetadataEntry<Float>
{
    private float value;

    public EntityMetadataFloatEntry(final int index, final float value)
    {
        super(index);
        this.value = value;
    }

    public EntityMetadataFloatEntry(final int index, final double value)
    {
        super(index);
        this.value = (float) value;
    }

    public float getValue()
    {
        return this.value;
    }

    public void setValue(final float value)
    {
        this.value = value;
    }

    public void setValue(final double value)
    {
        this.value = (float) value;
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.FLOAT;
    }

    @Override
    public Float getData()
    {
        return this.value;
    }

    @Override
    public void setData(final Float data)
    {
        this.value = data;
    }
}