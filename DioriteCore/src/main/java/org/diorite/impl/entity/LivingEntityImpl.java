package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.ServerImpl;
import org.diorite.ImmutableLocation;
import org.diorite.entity.LivingEntity;

public abstract class LivingEntityImpl extends AttributableEntityImpl implements LivingEntity
{
    public LivingEntityImpl(final UUID uuid, final ServerImpl server, final int id, final ImmutableLocation location)
    {
        super(uuid, server, id, location);
    }
}
