package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;

public interface CraftingInventory extends Inventory
{
    /**
     * @return The ItemStack in the result slot
     */
    ItemStack getResult();

    /**
     * Put the given ItemStack into the result slot.
     *
     * @param boots The ItemStack to use as result
     *
     * @return previous itemstack used as result.
     */
    ItemStack setResult(final ItemStack result);

    /**
     * Put the given ItemStack into the result slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted   excepted item to replace.
     * @param chestplate The ItemStack to use as result
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceResult(final ItemStack excepted, final ItemStack result) throws IllegalArgumentException;

    /**
     * @return copy of array with ItemStacks from the crafting slots.
     */
    ItemStack[] getCraftingSlots();
}
