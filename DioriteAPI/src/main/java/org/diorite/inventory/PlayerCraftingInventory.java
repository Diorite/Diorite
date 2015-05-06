package org.diorite.inventory;

public interface PlayerCraftingInventory extends Inventory, PlayerInventoryPart
{
    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_CRAFTING;
    }
}
