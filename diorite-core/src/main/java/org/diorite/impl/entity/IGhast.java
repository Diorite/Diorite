package org.diorite.impl.entity;

import org.diorite.entity.Ghast;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IGhast extends IMonsterEntity, Ghast
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(4F, 4F);
}
