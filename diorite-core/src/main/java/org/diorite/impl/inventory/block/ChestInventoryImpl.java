package org.diorite.impl.inventory.block;

import org.diorite.impl.inventory.InventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.block.Chest;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.block.ChestInventory;
import org.diorite.inventory.slot.Slot;

public class ChestInventoryImpl extends InventoryImpl<Chest> implements ChestInventory
{
    public ChestInventoryImpl(final Chest holder)
    {
        super(holder);
    }

    @Override
    public ItemStackImplArray getArray()
    {
        return null; //TODO
    }

    @Override
    public Slot getSlot(final int slot)
    {
        return null; //TODO
    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {
        //TODO
    }

    @Override
    public void update()
    {
        //TODO
    }

    @Override
    public InventoryType getType()
    {
        return null; //TODO
    }
}
