package org.diorite.impl.entity;

import org.diorite.entity.Boat;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IBoat extends IEntity, Boat, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.5F, 0.6F);
}
