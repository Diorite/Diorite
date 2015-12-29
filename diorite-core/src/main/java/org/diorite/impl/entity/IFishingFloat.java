package org.diorite.impl.entity;

import org.diorite.entity.FishingFloat;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IFishingFloat extends IEntity, FishingFloat
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
