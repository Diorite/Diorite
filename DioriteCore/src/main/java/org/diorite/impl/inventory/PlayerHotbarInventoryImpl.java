package org.diorite.impl.inventory;

import org.diorite.inventory.PlayerHotbarInventory;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.ItemStackArray;

public class PlayerHotbarInventoryImpl extends PlayerInventoryPartImpl implements PlayerHotbarInventory
{
    protected PlayerHotbarInventoryImpl(final PlayerInventory playerInventory, final ItemStackArray sub)
    {
        super(playerInventory, sub);
    }

    @Override
    public ItemStack getItemInHand()
    {
        if (this.holder == null)
        {
            return null;
        }
        return this.sub.get(this.holder.getHeldItemSlot());
    }

    @Override
    public ItemStack setItemInHand(final ItemStack stack)
    {
        if (this.holder == null)
        {
            return null;
        }
        return this.sub.getAndSet(this.holder.getHeldItemSlot(), stack);
    }

    @Override
    public boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack)
    {
        return (this.holder != null) && this.sub.compareAndSet(this.holder.getHeldItemSlot(), excepted, stack);
    }

    @Override
    public int getHeldItemSlot()
    {
        if (this.holder == null)
        {
            return - 1;
        }
        return this.holder.getHeldItemSlot();
    }

    @Override
    public void setHeldItemSlot(final int slot)
    {
        if (this.holder == null)
        {
            return;
        }
        this.holder.setHeldItemSlot(slot);
    }
}
