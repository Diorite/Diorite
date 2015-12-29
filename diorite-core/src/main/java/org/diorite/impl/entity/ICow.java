package org.diorite.impl.entity;

import org.diorite.entity.Cow;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ICow extends IAnimalEntity, Cow
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.9F, 1.3F);
}
