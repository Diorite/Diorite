package org.diorite.impl.entity;

import org.diorite.entity.Rabbit;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IRabbit extends IAnimalEntity, Rabbit
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 0.7F);
}
