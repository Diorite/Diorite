package org.diorite.impl.entity;

import org.diorite.entity.PigZombie;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IPigZombie extends IMonsterEntity, PigZombie
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);
}
