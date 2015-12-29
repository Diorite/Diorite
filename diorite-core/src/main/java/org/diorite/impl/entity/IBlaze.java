package org.diorite.impl.entity;

import org.diorite.entity.Blaze;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IBlaze extends IMonsterEntity, Blaze
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);
}
