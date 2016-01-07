package org.diorite.impl.entity;

import org.diorite.entity.Skeleton;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISkeleton extends IMonsterEntity, Skeleton
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.95F);

    /**
     * Size of metadata.
     */
    byte META_KEYS              = 13;
    /**
     * int, enum
     */
    byte META_KEY_SKELETON_TYPE = 11;
    /**
     * TODO bool, something hand related
     */
    byte META_KEY_SKELETON_HAND = 12;
}
