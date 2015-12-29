package org.diorite.impl.entity;

import org.diorite.entity.FireCharge;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IFireCharge extends IProjectile, FireCharge
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.3125F, 0.3125F);
}
