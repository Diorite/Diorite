package org.diorite.inventory;

public interface PlayerFullEqInventory extends Inventory, PlayerEqInventory, PlayerHotbarInventory, PlayerInventoryPart
{
    /**
     * Returns sub-part of player inventory, contains only
     * eq (without hotbar) slots, indexed from 0.
     *
     * @return sub-part of player inventory.
     */
    PlayerEqInventory getEqInventory();

    /**
     * Returns sub-part of player inventory, contains only
     * hotbar slots, indexed from 0.
     *
     * @return sub-part of player inventory.
     */
    PlayerHotbarInventory getHotbarInventory();

    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_FULL_EQ;
    }
}
