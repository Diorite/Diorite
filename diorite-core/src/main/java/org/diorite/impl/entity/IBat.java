package org.diorite.impl.entity;

import org.diorite.entity.Bat;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IBat extends IAmbientEntity, Bat
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.5F, 0.9F);
}
