package org.diorite.impl.entity;

import org.diorite.entity.Shulker;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IShulker extends IMonsterEntity, Shulker
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1F, 1F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                            = 14;
    /**
     * direction
     */
    byte META_KEY_SHULKER_FACING_DIRECTION    = 11;
    /**
     * opt position
     */
    byte META_KEY_SHULKER_ATTACHMENT_POSITION = 12;
    /**
     * byte
     */
    byte META_KEY_SHULKER_SHIELD_HEIGHT       = 13;
}
