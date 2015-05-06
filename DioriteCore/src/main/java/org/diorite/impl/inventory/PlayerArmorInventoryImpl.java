package org.diorite.impl.inventory;

import org.diorite.inventory.PlayerArmorInventory;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;

public class PlayerArmorInventoryImpl extends PlayerInventoryPartImpl implements PlayerArmorInventory
{
    protected PlayerArmorInventoryImpl(final PlayerInventory playerInventory)
    {
        super(playerInventory, playerInventory.getContent().getSubArray(5, 4));
    }

    @Override
    public ItemStack getHelmet()
    {
        return this.sub.get(0);
    }

    @Override
    public ItemStack getChestplate()
    {
        return this.sub.get(1);
    }

    @Override
    public ItemStack getLeggings()
    {
        return this.sub.get(2);
    }

    @Override
    public ItemStack getBoots()
    {
        return this.sub.get(3);
    }

    @Override
    public ItemStack setHelmet(final ItemStack helmet)
    {
        return this.sub.getAndSet(0, helmet);
    }

    @Override
    public ItemStack setChestplate(final ItemStack chestplate)
    {
        return this.sub.getAndSet(1, chestplate);
    }

    @Override
    public ItemStack setLeggings(final ItemStack leggings)
    {
        return this.sub.getAndSet(2, leggings);
    }

    @Override
    public ItemStack setBoots(final ItemStack boots)
    {
        return this.sub.getAndSet(3, boots);
    }

    @Override
    public boolean replaceHelmet(final ItemStack excepted, final ItemStack helmet)
    {
        return this.sub.compareAndSet(0, excepted, helmet);
    }

    @Override
    public boolean replaceChestplate(final ItemStack excepted, final ItemStack chestplate)
    {
        return this.sub.compareAndSet(0, excepted, chestplate);
    }

    @Override
    public boolean replaceLeggings(final ItemStack excepted, final ItemStack leggings)
    {
        return this.sub.compareAndSet(0, excepted, leggings);
    }

    @Override
    public boolean replaceBoots(final ItemStack excepted, final ItemStack boots)
    {
        return this.sub.compareAndSet(0, excepted, boots);
    }
}
