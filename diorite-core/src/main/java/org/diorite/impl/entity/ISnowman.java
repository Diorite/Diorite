package org.diorite.impl.entity;

import org.diorite.entity.Snowman;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISnowman extends IAnimalEntity, Snowman
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.7F, 1.9F);
// TODO: boolean metdata for head?
}
