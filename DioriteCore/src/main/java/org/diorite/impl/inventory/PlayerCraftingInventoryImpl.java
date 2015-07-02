package org.diorite.impl.inventory;

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerCraftingInventory;
import org.diorite.inventory.item.ItemStack;

public class PlayerCraftingInventoryImpl extends PlayerInventoryPartImpl implements PlayerCraftingInventory
{
    protected PlayerCraftingInventoryImpl(final PlayerInventoryImpl playerInventory)
    {
        super(playerInventory, playerInventory.getArray().getSubArray(0, InventoryType.PLAYER_CRAFTING.getSize()));
    }

    public PlayerCraftingInventoryImpl(final PlayerInventoryImpl playerInventory, final ItemStackImplArray content)
    {
        super(playerInventory, content);
    }

    @Override
    public ItemStack getResult()
    {
        return this.content.get(0);
    }

    @Override
    public ItemStack setResult(final ItemStack result)
    {
        return this.content.getAndSet(0, this.wrap(result, 0));
    }

    @Override
    public boolean replaceResult(final ItemStack excepted, final ItemStack result)
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(0, (ItemStackImpl) excepted, this.wrap(result, 0));
    }

    @Override
    public ItemStack[] getCraftingSlots()
    {
        return this.content.getSubArray(1).toArray(new ItemStack[this.content.length() - 1]);
    }
}
