package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IAnimalEntity;
import org.diorite.ImmutableLocation;

abstract class AnimalEntityImpl extends CreatureEntityImpl implements IAnimalEntity
{
    AnimalEntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }
}
