package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;

@SuppressWarnings("ClassHasNoToStringMethod")
public class EntityMetadataByteEntry extends EntityMetadataEntry<Byte>
{
    private byte value;

    public EntityMetadataByteEntry(final int index, final byte value)
    {
        super(index);
        this.value = value;
    }

    public EntityMetadataByteEntry(final int index, final int value)
    {
        super(index);
        this.value = (byte) value;
    }

    public byte getValue()
    {
        return this.value;
    }

    public void setValue(final byte value)
    {
        this.value = value;
    }

    public void setValue(final int value)
    {
        this.value = (byte) value;
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.BYTE;
    }

    @Override
    public Byte getData()
    {
        return this.value;
    }

    @Override
    public void setData(final Byte data)
    {
        this.value = data;
    }
}