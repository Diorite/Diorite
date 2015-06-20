package org.diorite.impl.inventory;

import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerEqInventory;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStackArray;

public class PlayerEqInventoryImpl extends PlayerInventoryPartImpl implements PlayerEqInventory
{
    public PlayerEqInventoryImpl(final PlayerInventory playerInventory)
    {
        super(playerInventory, playerInventory.getContents().getSubArray(9, InventoryType.PLAYER_EQ.getSize()));
    }

    public PlayerEqInventoryImpl(final PlayerInventory playerInventory, final ItemStackArray content)
    {
        super(playerInventory, content);
    }

    @Override
    public int getWindowId()
    {
        return 0; // Unused in this inventory
    }
}
