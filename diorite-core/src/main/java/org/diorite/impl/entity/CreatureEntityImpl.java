package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.ImmutableLocation;

abstract class CreatureEntityImpl extends InsentientEntityImpl implements ICreatureEntity
{
    public CreatureEntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }
}
