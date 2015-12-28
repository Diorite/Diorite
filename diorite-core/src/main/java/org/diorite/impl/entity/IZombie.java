package org.diorite.impl.entity;

import org.diorite.entity.Zombie;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IZombie extends IMonsterEntity, Zombie
{
    ImmutableEntityBoundingBox BASE_SIZE        = new ImmutableEntityBoundingBox(0.6F, 1.8F);
}
