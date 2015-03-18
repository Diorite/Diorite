package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;

public interface Inventory
{
    boolean contains(ItemStack itemStack);

    boolean add(ItemStack... itemStack);
}
