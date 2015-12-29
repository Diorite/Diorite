package org.diorite.impl.entity;

import org.diorite.entity.ThrownExpBottle;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IThrownExpBottle extends IProjectile, ThrownExpBottle
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
