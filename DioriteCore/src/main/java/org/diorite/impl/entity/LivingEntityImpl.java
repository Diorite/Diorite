package org.diorite.impl.entity;

import org.diorite.impl.ServerImpl;
import org.diorite.ImmutableLocation;
import org.diorite.entity.LivingEntity;

public abstract class LivingEntityImpl extends AttributableEntityImpl implements LivingEntity
{
    public LivingEntityImpl(final ServerImpl server, final int id, final ImmutableLocation location)
    {
        super(server, id, location);
    }
}
