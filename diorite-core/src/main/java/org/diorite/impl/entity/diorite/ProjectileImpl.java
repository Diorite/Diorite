package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IProjectile;
import org.diorite.ImmutableLocation;

abstract class ProjectileImpl extends MonsterEntityImpl implements IProjectile
{
    ProjectileImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
    }
}

