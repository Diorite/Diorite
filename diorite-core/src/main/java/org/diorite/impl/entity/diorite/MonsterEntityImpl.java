package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IMonsterEntity;
import org.diorite.ImmutableLocation;

abstract class MonsterEntityImpl extends CreatureEntityImpl implements IMonsterEntity
{
    MonsterEntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }
}
