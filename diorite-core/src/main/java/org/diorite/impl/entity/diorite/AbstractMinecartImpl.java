package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IAbstractMinecart;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.ImmutableLocation;

abstract class AbstractMinecartImpl extends EntityImpl implements IAbstractMinecart
{
    AbstractMinecartImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IAbstractMinecart.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
    }
}

