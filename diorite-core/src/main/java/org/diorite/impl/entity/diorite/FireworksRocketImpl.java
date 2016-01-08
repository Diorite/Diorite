package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IFireworksRocket;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class FireworksRocketImpl extends ProjectileImpl implements IFireworksRocket
{
    FireworksRocketImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IFireworksRocket.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.getMetadata().add(new EntityMetadataItemStackEntry(META_KEY_FIREWORK_ITEM, null));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.FIREWORK_ROCKET;
    }
}

