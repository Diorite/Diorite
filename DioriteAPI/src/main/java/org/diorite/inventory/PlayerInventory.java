package org.diorite.inventory;

import org.diorite.inventory.item.IItemStack;

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
    IItemStack getCursorItem();

    IItemStack setCursorItem(IItemStack cursorItem);

    DragController getDragController();

    boolean atomicReplaceCursorItem(IItemStack excepted, IItemStack cursorItem);

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
