package org.diorite.impl.entity;

import org.diorite.entity.Enderman;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEnderman extends IMonsterEntity, Enderman
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 2.9F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                      = 13;
    /**
     * ItemStack in hand.
     */
    byte META_KEY_ENDERMAN_CARRIED_ITEM = 11;
    /**
     * boolean
     */
    byte META_KEY_ENDERMAN_IS_SCREAMING = 12;
}
