package org.diorite.inventory;

public interface PlayerEqInventory extends Inventory, PlayerInventoryPart
{
    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_EQ;
    }
}
