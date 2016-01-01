package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IAbstractArrow;
import org.diorite.ImmutableLocation;

abstract class AbstractArrowImpl extends ProjectileImpl implements IAbstractArrow
{
    AbstractArrowImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }
}

