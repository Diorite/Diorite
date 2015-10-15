package org.diorite.inventory;

public interface PlayerEqInventory extends GridInventory, PlayerInventoryPart
{
    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_EQ;
    }

    @Override
    default int getRows()
    {
        return 3;
    }

    @Override
    default int getColumns()
    {
        return 9;
    }
}
