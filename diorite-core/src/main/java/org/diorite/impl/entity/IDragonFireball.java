package org.diorite.impl.entity;

import org.diorite.entity.DragonFireball;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IDragonFireball extends IProjectile, DragonFireball
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.3125F, 0.3125F);
}
