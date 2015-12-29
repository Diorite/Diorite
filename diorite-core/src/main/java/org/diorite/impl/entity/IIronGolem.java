package org.diorite.impl.entity;

import org.diorite.entity.IronGolem;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IIronGolem extends IAnimalEntity, IronGolem
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.4F, 2.9F);
}
