package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;
import org.diorite.utils.math.geometry.Vector3F;

public class EntityMetadataVector3FEntry extends EntityMetadataObjectEntry<Vector3F>
{
    public EntityMetadataVector3FEntry(final int index, final Vector3F data)
    {
        super(index, data);
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.ROTATION;
    }
}