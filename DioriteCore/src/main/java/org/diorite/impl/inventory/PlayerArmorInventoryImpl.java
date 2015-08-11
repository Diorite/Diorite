package org.diorite.impl.inventory;

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerArmorInventory;
import org.diorite.inventory.item.ItemStack;

public class PlayerArmorInventoryImpl extends PlayerInventoryPartImpl implements PlayerArmorInventory
{
    protected PlayerArmorInventoryImpl(final PlayerInventoryImpl playerInventory)
    {
        super(playerInventory, playerInventory.getArray().getSubArray(5, InventoryType.PLAYER_ARMOR.getSize()));
    }

    public PlayerArmorInventoryImpl(final PlayerInventoryImpl playerInventory, final ItemStackImplArray content)
    {
        super(playerInventory, content);
    }

    @Override
    public ItemStack getHelmet()
    {
        return this.content.get(0);
    }

    @Override
    public ItemStack getChestplate()
    {
        return this.content.get(1);
    }

    @Override
    public ItemStack getLeggings()
    {
        return this.content.get(2);
    }

    @Override
    public ItemStack getBoots()
    {
        return this.content.get(3);
    }

    @Override
    public ItemStack setHelmet(final ItemStack helmet)
    {
        return this.content.getAndSet(0, ItemStackImpl.wrap(helmet));
    }

    @Override
    public ItemStack setChestplate(final ItemStack chestplate)
    {
        return this.content.getAndSet(1, ItemStackImpl.wrap(chestplate));
    }

    @Override
    public ItemStack setLeggings(final ItemStack leggings)
    {
        return this.content.getAndSet(2, ItemStackImpl.wrap(leggings));
    }

    @Override
    public ItemStack setBoots(final ItemStack boots)
    {
        return this.content.getAndSet(3, ItemStackImpl.wrap(boots));
    }

    @Override
    public boolean replaceHelmet(final ItemStack excepted, final ItemStack helmet) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(0, (ItemStackImpl) excepted, ItemStackImpl.wrap(helmet));
    }

    @Override
    public boolean replaceChestplate(final ItemStack excepted, final ItemStack chestplate) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(1, (ItemStackImpl) excepted, ItemStackImpl.wrap(chestplate));
    }

    @Override
    public boolean replaceLeggings(final ItemStack excepted, final ItemStack leggings) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(2, (ItemStackImpl) excepted, ItemStackImpl.wrap(leggings));
    }

    @Override
    public boolean replaceBoots(final ItemStack excepted, final ItemStack boots) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(3, (ItemStackImpl) excepted, ItemStackImpl.wrap(boots));
    }
}
