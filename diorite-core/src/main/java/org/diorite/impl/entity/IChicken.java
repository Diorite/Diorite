package org.diorite.impl.entity;

import org.diorite.entity.Chicken;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IChicken extends IAnimalEntity, Chicken
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.4F, 0.7F);
}
