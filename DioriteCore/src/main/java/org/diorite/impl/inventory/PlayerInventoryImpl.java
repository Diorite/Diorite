package org.diorite.impl.inventory;

import org.diorite.inventory.item.ItemStack;

public class PlayerInventoryImpl extends InventoryImpl
{
    @Override
    public boolean contains(final ItemStack itemStack)
    {
        return false;
    }

    @Override
    public boolean add(final ItemStack... itemStack)
    {
        return false;
    }
}
