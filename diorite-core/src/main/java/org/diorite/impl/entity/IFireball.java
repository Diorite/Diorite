package org.diorite.impl.entity;

import org.diorite.entity.Fireball;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IFireball extends IProjectile, Fireball
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.0F, 1.0F);
}
