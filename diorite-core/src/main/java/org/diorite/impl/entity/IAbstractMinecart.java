package org.diorite.impl.entity;

import org.diorite.entity.Minecart;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IAbstractMinecart extends IEntity, Minecart, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.7F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                            = 11;
    /**
     * int
     */
    byte META_KEY_MINECART_SHAKING_POWER      = 5;
    /**
     * int, enum?
     */
    byte META_KEY_MINECART_SHAKING_DIRECTION  = 6;
    /**
     * float
     */
    byte META_KEY_MINECART_SHAKING_MULTIPLIER = 7;
    /**
     * int, id and meta of material.
     */
    byte META_KEY_MINECART_BLOCK              = 8;
    /**
     * int
     */
    byte META_KEY_MINECART_BLOCK_Y_POSITION   = 9;
    /**
     * boolean
     */
    byte META_KEY_MINECART_SHOW_BLOCK         = 10;
}
