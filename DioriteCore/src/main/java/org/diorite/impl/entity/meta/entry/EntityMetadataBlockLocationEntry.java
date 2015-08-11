package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;
import org.diorite.BlockLocation;

public class EntityMetadataBlockLocationEntry extends EntityMetadataObjectEntry<BlockLocation>
{
    public EntityMetadataBlockLocationEntry(final int index, final BlockLocation data)
    {
        super(index, data);
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.LOCATION;
    }
}