package org.diorite.impl.inventory;

import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerArmorInventory;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStackArray;

public class PlayerArmorInventoryImpl extends PlayerInventoryPartImpl implements PlayerArmorInventory
{
    protected PlayerArmorInventoryImpl(final PlayerInventory playerInventory)
    {
        super(playerInventory, playerInventory.getContents().getSubArray(5, InventoryType.PLAYER_ARMOR.getSize()));
    }

    public PlayerArmorInventoryImpl(final PlayerInventory playerInventory, final ItemStackArray content)
    {
        super(playerInventory, content);
    }

    @Override
    public int getWindowId()
    {
        return 0; // Unused in this inventory
    }
}
