package org.diorite.impl.entity;

import org.diorite.entity.Pig;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IPig extends IAnimalEntity, Pig
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.9F, 0.9F);
}
