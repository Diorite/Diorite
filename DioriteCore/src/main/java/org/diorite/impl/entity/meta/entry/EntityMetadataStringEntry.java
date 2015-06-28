package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;

public class EntityMetadataStringEntry extends EntityMetadataObjectEntry<String>
{
    public EntityMetadataStringEntry(final int index, final String data)
    {
        super(index, data);
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.STRING;
    }
}