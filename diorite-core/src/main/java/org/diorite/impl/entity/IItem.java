package org.diorite.impl.entity;

import org.diorite.entity.Human;
import org.diorite.entity.Item;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;
import org.diorite.utils.others.NamedUUID;

public interface IItem extends IEntity, Item
{
    int DEFAULT_BLOCK_DROP_PICKUP_DELAY = 50;
    int DEFAULT_DROP_PICKUP_DELAY       = 200;

    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);

    /**
     * Size of metadata.
     */
    byte META_KEYS          = 6;
    /**
     * ItemStack entry
     */
    byte META_KEY_ITEM_ITEM = 5;

    ItemStack getItemStack();

    void setItemStack(ItemStack item);

    boolean canPickup();

    NamedUUID getThrower();

    void setThrower(NamedUUID thrower);

    boolean pickUpItem(Human human);
}
