package org.diorite.impl.entity;

import org.diorite.entity.Zombie;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IZombie extends IMonsterEntity, Zombie
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                          = 15;
    /**
     * boolean
     */
    byte META_KEY_ZOMBIE_IS_BABY            = 11;
    /**
     * int, profession type
     */
    byte META_KEY_ZOMBIE_VILLAGER           = 12;
    /**
     * boolean
     */
    byte META_KEY_ZOMBIE_IS_CONVERTING      = 13;
    /**
     * boolean
     */
    byte META_KEY_ZOMBIE_ARE_HANDS_RISED_UP = 14;
}
