package org.diorite.inventory.item;

import org.diorite.material.Material;

public interface ItemStack
{
    ItemStack[] EMPTY = new ItemStack[0];

    Material getMaterial();

    int getAmount();

    short getDurability();

    ItemMeta getItemMeta();
}
