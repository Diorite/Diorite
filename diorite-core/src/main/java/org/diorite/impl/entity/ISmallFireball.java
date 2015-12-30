package org.diorite.impl.entity;

import org.diorite.entity.SmallFireball;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISmallFireball extends IProjectile, SmallFireball
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.3125F, 0.3125F);
}
