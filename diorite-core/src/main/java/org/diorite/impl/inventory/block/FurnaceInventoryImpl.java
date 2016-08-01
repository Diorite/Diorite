package org.diorite.impl.inventory.block;

import org.diorite.impl.inventory.InventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.block.Furnace;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.block.FurnaceInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;

public class FurnaceInventoryImpl extends InventoryImpl<Furnace> implements FurnaceInventory
{
    public FurnaceInventoryImpl(final Furnace holder)
    {
        super(holder);
    }

    @Override
    public ItemStack getFuel()
    {
        return getItem(1);
    }

    @Override
    public void setFuel(final ItemStack fuel)
    {
        setItem(1, fuel);
    }

    @Override
    public ItemStack getSmelting()
    {
        return getItem(0);
    }

    @Override
    public void setSmelting(final ItemStack smelting)
    {
        setItem(0, smelting);
    }

    @Override
    public ItemStack getResult()
    {
        return getItem(2);
    }

    @Override
    public void setResult(final ItemStack result)
    {
        setItem(2, result);
    }

    //TODO all methods below
    @Override
    public ItemStackImplArray getArray()
    {
        return null;
    }

    @Override
    public Slot getSlot(final int slot)
    {
        return null;
    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {

    }

    @Override
    public void update()
    {

    }

    @Override
    public InventoryType getType()
    {
        return null;
    }

    @Override
    public Furnace getHolder()
    {
        return (Furnace) super.getHolder();
    }

    @Override
    public int getWindowId()
    {
        return 0;
    }
}
