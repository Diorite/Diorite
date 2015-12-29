package org.diorite.impl.entity;

import org.diorite.entity.Skeleton;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISkeleton extends IMonsterEntity, Skeleton
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.95F);
}
