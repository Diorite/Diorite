package org.diorite.impl.entity;

import org.diorite.entity.Giant;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IGiant extends IMonsterEntity, Giant
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F * 6, 1.8F * 6);
}
