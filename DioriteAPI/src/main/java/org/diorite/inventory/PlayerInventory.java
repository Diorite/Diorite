package org.diorite.inventory;

public interface PlayerInventory extends Inventory, PlayerArmorInventory, PlayerCraftingInventory, PlayerFullEqInventory
{
    @Override
    default PlayerInventory getPlayerInventory()
    {
        return this;
    }

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
