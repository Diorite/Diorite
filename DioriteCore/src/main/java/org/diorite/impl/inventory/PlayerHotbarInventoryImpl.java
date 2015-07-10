package org.diorite.impl.inventory;

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerHotbarInventory;
import org.diorite.inventory.item.IItemStack;

public class PlayerHotbarInventoryImpl extends PlayerInventoryPartImpl implements PlayerHotbarInventory
{
    protected PlayerHotbarInventoryImpl(final PlayerInventoryImpl playerInventory)
    {
        //noinspection MagicNumber
        super(playerInventory, playerInventory.getArray().getSubArray(36, InventoryType.PLAYER_HOTBAR.getSize()));
    }

    public PlayerHotbarInventoryImpl(final PlayerInventoryImpl playerInventory, final ItemStackImplArray content)
    {
        super(playerInventory, content);
    }

    @Override
    public IItemStack getItemInHand()
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return null;
        }
        return this.content.get(holder.getHeldItemSlot());
    }

    @Override
    public IItemStack setItemInHand(final IItemStack stack)
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return null;
        }
        return this.content.getAndSet(holder.getHeldItemSlot(), ItemStackImpl.wrap(stack));
    }

    @Override
    public boolean replaceItemInHand(final IItemStack excepted, final IItemStack stack) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        final Player holder = this.getHolder();
        return (holder != null) && this.content.compareAndSet(holder.getHeldItemSlot(), (ItemStackImpl) excepted, ItemStackImpl.wrap(stack));
    }
}
