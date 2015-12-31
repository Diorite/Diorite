package org.diorite.impl.entity;

import org.diorite.entity.PrimedTNT;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IPrimedTNT extends IEntity, PrimedTNT, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.98F);
}
