package org.diorite.inventory;

public interface PlayerCraftingInventory extends Inventory, PlayerInventoryPart, CraftingInventory
{
    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_CRAFTING;
    }
}
