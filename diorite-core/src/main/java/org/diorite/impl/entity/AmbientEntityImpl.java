package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.ImmutableLocation;

abstract class AmbientEntityImpl extends CreatureEntityImpl implements IAmbientEntity
{
    public AmbientEntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }
}
