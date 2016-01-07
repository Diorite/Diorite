package org.diorite.impl.entity;

import org.diorite.entity.PrimedTNT;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IPrimedTNT extends IEntity, PrimedTNT, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.98F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                     = 6;
    /**
     * int
     */
    byte META_KEY_PRIMED_TNT_FUSE_TIME = 5;
}
