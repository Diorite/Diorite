package org.diorite.impl.inventory;

import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerHotbarInventory;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStackArray;

public class PlayerHotbarInventoryImpl extends PlayerInventoryPartImpl implements PlayerHotbarInventory
{
    protected PlayerHotbarInventoryImpl(final PlayerInventory playerInventory)
    {
        //noinspection MagicNumber
        super(playerInventory, playerInventory.getContents().getSubArray(36, InventoryType.PLAYER_HOTBAR.getSize()));
    }

    public PlayerHotbarInventoryImpl(final PlayerInventory playerInventory, final ItemStackArray content)
    {
        super(playerInventory, content);
    }
}
