package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IShulkerBullet;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class ShulkerBulletImpl extends ProjectileImpl implements IShulkerBullet
{
    ShulkerBulletImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
    }

    @Override
    public EntityType getType()
    {
        return EntityType.SHULKER_BULLET;
    }
}

