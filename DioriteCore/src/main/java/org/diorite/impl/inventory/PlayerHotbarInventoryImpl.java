package org.diorite.impl.inventory;

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerHotbarInventory;
import org.diorite.inventory.item.ItemStack;

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
    public ItemStack getItemInHand()
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return null;
        }
        return this.content.get(holder.getHeldItemSlot());
    }

    @Override
    public ItemStack setItemInHand(final ItemStack stack)
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return null;
        }
        return this.content.getAndSet(holder.getHeldItemSlot(), this.wrap(stack, holder.getHeldItemSlot()));
    }

    @Override
    public boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        final Player holder = this.getHolder();
        return (holder != null) && this.content.compareAndSet(holder.getHeldItemSlot(), (ItemStackImpl) excepted, this.wrap(stack, holder.getHeldItemSlot()));
    }
}
