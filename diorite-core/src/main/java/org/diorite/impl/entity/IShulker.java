package org.diorite.impl.entity;

import org.diorite.entity.Shulker;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IShulker extends IMonsterEntity, Shulker
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1F, 1F);
}
