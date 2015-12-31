package org.diorite.impl.entity;

import org.diorite.entity.FishingHook;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IFishingHook extends IEntity, FishingHook, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
