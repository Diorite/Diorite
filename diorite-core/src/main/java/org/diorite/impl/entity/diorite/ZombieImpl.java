package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IZombie;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class ZombieImpl extends MonsterEntityImpl implements IZombie
{
    ZombieImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IZombie.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
    }

    @Override
    public EntityType getType()
    {
        return EntityType.ZOMBIE;
    }
}