package org.diorite.impl.entity;

import org.diorite.entity.ItemFrame;

public interface IItemFrame extends IEntity, ItemFrame, EntityObject
{
    /**
     * Size of metadata.
     */
    byte META_KEYS                    = 7;
    /**
     * ItemStack
     */
    byte META_KEY_ITEM_FRAME_ITEM     = 5;
    /**
     * int, enum, item rotation.
     */
    byte META_KEY_ITEM_FRAME_ROTATION = 6;
}
