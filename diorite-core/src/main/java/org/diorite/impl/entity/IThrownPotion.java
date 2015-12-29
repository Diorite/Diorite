package org.diorite.impl.entity;

import org.diorite.entity.ThrownPotion;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IThrownPotion extends IProjectile, ThrownPotion
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
