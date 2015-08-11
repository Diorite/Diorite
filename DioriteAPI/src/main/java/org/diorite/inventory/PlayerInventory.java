package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;

public interface PlayerInventory extends Inventory, PlayerArmorInventory, PlayerCraftingInventory, PlayerFullEqInventory
{
    @Override
    default PlayerInventory getPlayerInventory()
    {
        return this;
    }

    /**
     * Get item hold in cursor by player.
     *
     * @return item hold in cursor by player.
     */
    ItemStack getCursorItem();

    ItemStack setCursorItem(ItemStack cursorItem);

    DragController getDragController();

    boolean atomicReplaceCursorItem(ItemStack excepted, ItemStack cursorItem);

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
