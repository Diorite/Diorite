package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;

public interface Inventory
{
    int remove(ItemStack itemStack);

    boolean removeItem(ItemStack itemStack);

    boolean contains(ItemStack itemStack);

    boolean containsAtLeast(ItemStack itemStack);

    boolean add(ItemStack... itemStack);
}
