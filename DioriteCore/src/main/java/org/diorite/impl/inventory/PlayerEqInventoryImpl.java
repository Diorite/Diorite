package org.diorite.impl.inventory;

import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerEqInventory;

public class PlayerEqInventoryImpl extends PlayerInventoryPartImpl implements PlayerEqInventory
{
    public PlayerEqInventoryImpl(final PlayerInventoryImpl playerInventory)
    {
        super(playerInventory, playerInventory.getArray().getSubArray(9, InventoryType.PLAYER_EQ.getSize()));
    }

    public PlayerEqInventoryImpl(final PlayerInventoryImpl playerInventory, final ItemStackImplArray content)
    {
        super(playerInventory, content);
    }
}
