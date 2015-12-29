package org.diorite.impl.entity;

import org.diorite.entity.Wolf;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IWolf extends IAnimalEntity, Wolf
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 0.8F);
}
