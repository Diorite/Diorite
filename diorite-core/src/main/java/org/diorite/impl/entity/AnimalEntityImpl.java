package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.ImmutableLocation;
import org.diorite.entity.AnimalEntity;

abstract class AnimalEntityImpl extends CreatureEntityImpl implements IAnimalEntity
{
    public AnimalEntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }
}
