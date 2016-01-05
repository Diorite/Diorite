package org.diorite.impl.entity;

import org.diorite.entity.Creeper;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ICreeper extends IMonsterEntity, Creeper
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                = 14;
    /**
     * Int,  (-1 = Idle, 1 = Fuse), ignored if {@link #META_KEY_CREEPER_IGNITED} is true
     */
    byte META_KEY_CREEPER_STATE   = 11;
    /**
     * Boolean, powered, lightning creeper
     */
    byte META_KEY_CREEPER_POWERED = 12;
    /**
     * Boolean, if creepers is going to explode.
     */
    byte META_KEY_CREEPER_IGNITED = 13;
}
