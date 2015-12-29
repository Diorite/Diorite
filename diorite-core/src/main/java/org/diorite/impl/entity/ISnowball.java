package org.diorite.impl.entity;

import org.diorite.entity.Snowball;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISnowball extends IProjectile, Snowball
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
