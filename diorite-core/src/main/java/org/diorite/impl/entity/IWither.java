package org.diorite.impl.entity;

import org.diorite.entity.Wither;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IWither extends IMonsterEntity, Wither
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.9F, 3.5F);
}
