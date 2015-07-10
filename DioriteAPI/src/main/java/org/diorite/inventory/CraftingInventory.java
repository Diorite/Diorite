package org.diorite.inventory;

import org.diorite.inventory.item.IItemStack;

public interface CraftingInventory extends Inventory
{
    /**
     * @return The ItemStack in the result slot
     */
    IItemStack getResult();

    /**
     * Put the given ItemStack into the result slot.
     *
     * @param boots The ItemStack to use as result
     *
     * @return previous itemstack used as result.
     */
    IItemStack setResult(final IItemStack result);

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
    boolean replaceResult(final IItemStack excepted, final IItemStack result) throws IllegalArgumentException;

    /**
     * @return copy of array with ItemStacks from the crafting slots.
     */
    IItemStack[] getCraftingSlots();
}
