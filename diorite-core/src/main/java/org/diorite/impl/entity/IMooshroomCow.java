package org.diorite.impl.entity;

import org.diorite.entity.MooshroomCow;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IMooshroomCow extends IAnimalEntity, MooshroomCow
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.9F, 1.3F);
}
