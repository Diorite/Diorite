package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.ItemStackArray;

public interface CraftingInventory extends Inventory
{
    default ItemStack getResult()
    {
        return this.getContents().get(0);
    }

    default ItemStack setResult(final ItemStack result)
    {
        return this.getContents().getAndSet(0, result);
    }

    default boolean replaceResult(final ItemStack excepted, final ItemStack result)
    {
        return this.getContents().compareAndSet(0, excepted, result);
    }

    default ItemStackArray getCraftingSlots()
    {
        return this.getContents().getSubArray(1);
    }
}
