package org.diorite.impl.entity;

import org.diorite.entity.Wither;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IWither extends IMonsterEntity, Wither
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.9F, 3.5F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                          = 15;
    /**
     * int, entity id
     */
    byte META_KEY_WITHER_FIRST_HEAD_TARGET  = 11;
    /**
     * int, entity id
     */
    byte META_KEY_WITHER_SECOND_HEAD_TARGET = 12;
    /**
     * int, entity id
     */
    byte META_KEY_WITHER_THIRD_HEAD_TARGET  = 13;
    /**
     * int, entity id
     */
    byte META_KEY_WITHER_INVULNERABLE_TIME  = 14;
}
