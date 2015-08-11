package org.diorite.impl.inventory;

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerFullEqInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

public class PlayerFullEqInventoryImpl extends PlayerInventoryPartImpl implements PlayerFullEqInventory
{
    public PlayerFullEqInventoryImpl(final PlayerInventoryImpl playerInventory)
    {
        super(playerInventory, playerInventory.getArray().getSubArray(9, InventoryType.PLAYER_FULL_EQ.getSize()));
    }

    public PlayerFullEqInventoryImpl(final PlayerInventoryImpl playerInventory, final ItemStackImplArray content)
    {
        super(playerInventory, content);
    }

    @Override
    public int firstEmpty()
    {
        return this.playerInventory.firstEmpty();
    }

    @Override
    public int first(final Material material)
    {
        return this.playerInventory.first(material);
    }

    @Override
    public int first(final ItemStack item, final boolean withAmount)
    {
        return this.playerInventory.first(item, withAmount);
    }

    @Override
    public int first(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        return this.playerInventory.first(item, startIndex, withAmount);
    }

    @Override
    public ItemStack[] add(final ItemStack... items)
    {
        return this.playerInventory.add(items);
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
        return this.content.getAndSet(holder.getHeldItemSlot(), ItemStackImpl.wrap(stack));
    }

    @Override
    public boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        final Player holder = this.getHolder();
        return (holder != null) && this.content.compareAndSet(holder.getHeldItemSlot(), (ItemStackImpl) excepted, ItemStackImpl.wrap(stack));
    }
}
