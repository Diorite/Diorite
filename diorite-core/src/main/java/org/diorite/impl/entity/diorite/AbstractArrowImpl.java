package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IAbstractArrow;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.ImmutableLocation;

abstract class AbstractArrowImpl extends ProjectileImpl implements IAbstractArrow
{
    AbstractArrowImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.getMetadata().add(new EntityMetadataByteEntry(META_KEY_IS_CRITICAL, 0));
    }
}

