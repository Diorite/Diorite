package org.diorite.impl.entity;

import org.diorite.entity.FireworkRocket;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IFireworkRocket extends IProjectile, FireworkRocket
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
