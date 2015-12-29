package org.diorite.impl.entity;

import org.diorite.entity.Egg;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEgg extends IProjectile, Egg
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
