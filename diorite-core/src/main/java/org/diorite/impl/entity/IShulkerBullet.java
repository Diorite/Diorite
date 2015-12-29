package org.diorite.impl.entity;

import org.diorite.entity.ShulkerBullet;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IShulkerBullet extends IProjectile, ShulkerBullet
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.3125F, 0.3125F);
}
