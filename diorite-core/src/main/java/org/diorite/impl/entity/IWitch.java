package org.diorite.impl.entity;

import org.diorite.entity.Witch;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IWitch extends IMonsterEntity, Witch
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);
}
