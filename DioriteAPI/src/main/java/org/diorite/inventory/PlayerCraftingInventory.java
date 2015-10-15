package org.diorite.inventory;

public interface PlayerCraftingInventory extends Inventory, PlayerInventoryPart, CraftingInventory
{
    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_CRAFTING;
    }

    @Override
    default int getRows()
    {
        return 2;
    }

    @Override
    default int getColumns()
    {
        return 2;
    }
}
