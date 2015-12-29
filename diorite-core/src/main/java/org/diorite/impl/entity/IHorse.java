package org.diorite.impl.entity;

import org.diorite.entity.Horse;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IHorse extends IAnimalEntity, Horse
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.4F, 2.9F);
}
