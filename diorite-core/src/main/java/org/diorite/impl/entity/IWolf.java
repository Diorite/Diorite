package org.diorite.impl.entity;

import org.diorite.entity.Wolf;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IWolf extends IAnimalEntity, Wolf
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 0.8F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                  = 17;
    /**
     * Float
     */
    byte META_KEY_WOLF_DAMAGE_TAKEN = 14;
    /**
     * Boolean
     */
    byte META_KEY_WOLF_IS_BEGGING   = 15;
    /**
     * int, color
     */
    byte META_KEY_WOLF_COLLAR_COLOR = 16;
}
