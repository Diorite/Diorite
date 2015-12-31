package org.diorite.impl.entity;

import org.diorite.entity.LeashKnot;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ILeashKnot extends IEntity, LeashKnot, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.5F, 0.5F);
}
