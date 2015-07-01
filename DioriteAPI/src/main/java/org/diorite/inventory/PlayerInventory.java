package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.ItemStackArray;

public interface PlayerInventory extends Inventory, PlayerArmorInventory, PlayerCraftingInventory, PlayerFullEqInventory
{
    @Override
    default PlayerInventory getPlayerInventory()
    {
        return this;
    }

    /**
     * Completely replaces the inventory's contents. Removes all existing
     * contents and replaces it with the ItemStacks given in the array.
     *
     * @param items A complete replacement for the contents; the length must
     *              be less than or equal to {@link #size()}.
     *
     * @throws IllegalArgumentException If the array has more items than the
     *                                  inventory.
     */
    @Override
    default void setContent(final ItemStackArray items)
    {
        final ItemStackArray content = this.getContents();
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, items.getOrNull(i));
        }
    }

    /**
     * Completely replaces the inventory's contents. Removes all existing
     * contents and replaces it with the ItemStacks given in the array.
     *
     * @param items A complete replacement for the contents; the length must
     *              be less than or equal to {@link #size()}.
     *
     * @throws IllegalArgumentException If the array has more items than the
     *                                  inventory.
     */
    @Override
    default void setContent(final ItemStack[] items)
    {
        final ItemStackArray content = this.getContents();
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, (i >= items.length) ? null : items[i]);
        }
    }

    /**
     * @return The size of the inventory
     */
    @Override
    default int size()
    {
        return this.getContents().length();
    }

    /**
     * Get item hold in cursor by player.
     *
     * @return item hold in cursor by player.
     */
    ItemStack getCursorItem();

    /**
     * Returns sub-part of player inventory, contains only
     * slots for armor stuff, indexed from 0.
     *
     * @return sub-part of player inventory.
     */
    PlayerArmorInventory getArmorInventory();

    /**
     * Returns sub-part of player inventory, contains only
     * slots for crafting stuff, indexed from 0.
     *
     * @return sub-part of player inventory.
     */
    PlayerCraftingInventory getCraftingInventory();

    /**
     * Returns sub-part of player inventory, contains only
     * eq (with hotbar) slots, indexed from 0.
     *
     * @return sub-part of player inventory.
     */
    PlayerFullEqInventory getFullEqInventory();

    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER;
    }
}
