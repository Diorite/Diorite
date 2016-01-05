package org.diorite.impl.entity;

import org.diorite.entity.EnderCrystal;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEnderCrystal extends IEntity, EnderCrystal, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(2.0F, 2.0F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                          = 7;
    /**
     * optional location.
     */
    byte META_KEY_ENDER_CRYSTAL_TARGET      = 5;
    /**
     * boolean
     */
    byte META_KEY_ENDER_CRYSTAL_SHOW_BOTTOM = 6;
}
