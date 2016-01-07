package org.diorite.impl.entity;

import org.diorite.entity.Witch;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IWitch extends IMonsterEntity, Witch
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                   = 12;
    /**
     * bool
     */
    byte META_KEY_WITCH_IS_AGGRESIVE = 11;
}
