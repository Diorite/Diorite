package org.diorite.impl.entity;

import org.diorite.entity.MinecartCommandBlock;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IMinecartCommandBlock extends IAbstractMinecart, MinecartCommandBlock, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.7F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                               = 13;
    /**
     * String
     */
    byte META_KEY_MINECART_COMMAND_BLOCK_COMMAND = 11;
    /**
     * Chat
     */
    byte META_KEY_MINECART_COMMAND_BLOCK_OUTPUT  = 12;
}
