package org.diorite.impl.entity;

import org.diorite.entity.Sheep;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISheep extends IAnimalEntity, Sheep
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.9F, 1.3F);
}
