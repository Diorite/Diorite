package org.diorite.impl.inventory;

import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerFullEqInventory;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStackArray;

public class PlayerFullEqInventoryImpl extends PlayerInventoryPartImpl implements PlayerFullEqInventory
{
    public PlayerFullEqInventoryImpl(final PlayerInventory playerInventory)
    {
        super(playerInventory, playerInventory.getContents().getSubArray(9, InventoryType.PLAYER_FULL_EQ.getSize()));

    }

    public PlayerFullEqInventoryImpl(final PlayerInventory playerInventory, final ItemStackArray content)
    {
        super(playerInventory, content);
    }

    @Override
    public int getWindowId()
    {
        return 0; // Unused in this inventory
    }
}
