package org.diorite.impl.entity;

import org.diorite.entity.FallingDragonEgg;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IFallingDragonEgg extends IEntity, FallingDragonEgg, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.98F);
}
