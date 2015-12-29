package org.diorite.impl.entity;

import org.diorite.entity.GiantZombie;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IGiantZombie extends IMonsterEntity, GiantZombie
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F * 6, 1.8F * 6);
}
