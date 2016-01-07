package org.diorite.impl.entity;

import org.diorite.entity.MinecartFurnace;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IMinecartFurnace extends IAbstractMinecart, MinecartFurnace, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.7F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                            = 12;
    /**
     * String
     */
    byte META_KEY_MINECART_FURNACE_IS_POWERED = 11;
}
