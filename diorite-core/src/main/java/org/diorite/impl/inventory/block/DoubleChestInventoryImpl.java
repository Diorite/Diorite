package org.diorite.impl.inventory.block;

import org.diorite.impl.inventory.InventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.block.DoubleChest;
import org.diorite.entity.Player;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.block.DoubleChestInventory;
import org.diorite.inventory.slot.Slot;

public class DoubleChestInventoryImpl extends InventoryImpl<DoubleChest> implements DoubleChestInventory
{
    public DoubleChestInventoryImpl(final DoubleChest holder)
    {
        super(holder);
    }

    @Override
    public ItemStackImplArray getArray()
    {
        return null;
    }

    @Override
    public Inventory getLeftSide()
    {
        return holder.getLeftSide().getInventory();
    }

    @Override
    public Inventory getRightSide()
    {
        return holder.getRightSide().getInventory();
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
        return InventoryType.LARGE_CHEST;
    }
}
