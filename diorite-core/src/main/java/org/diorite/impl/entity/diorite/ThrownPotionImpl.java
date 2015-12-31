package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IThrownPotion;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class ThrownPotionImpl extends ProjectileImpl implements IThrownPotion
{
    ThrownPotionImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
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
        return EntityType.THROWN_POTION;
    }
}

