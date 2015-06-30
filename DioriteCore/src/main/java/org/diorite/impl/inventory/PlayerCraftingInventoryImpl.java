package org.diorite.impl.inventory;

import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerCraftingInventory;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStackArray;

public class PlayerCraftingInventoryImpl extends PlayerInventoryPartImpl implements PlayerCraftingInventory
{
    protected PlayerCraftingInventoryImpl(final PlayerInventory playerInventory)
    {
        super(playerInventory, playerInventory.getContents().getSubArray(0, InventoryType.PLAYER_CRAFTING.getSize()));
    }

    public PlayerCraftingInventoryImpl(final PlayerInventory playerInventory, final ItemStackArray content)
    {
        super(playerInventory, content);
    }
}
