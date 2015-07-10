package org.diorite.impl.inventory;

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerArmorInventory;
import org.diorite.inventory.item.IItemStack;

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
    public IItemStack getHelmet()
    {
        return this.content.get(0);
    }

    @Override
    public IItemStack getChestplate()
    {
        return this.content.get(1);
    }

    @Override
    public IItemStack getLeggings()
    {
        return this.content.get(2);
    }

    @Override
    public IItemStack getBoots()
    {
        return this.content.get(3);
    }

    @Override
    public IItemStack setHelmet(final IItemStack helmet)
    {
        return this.content.getAndSet(0, ItemStackImpl.wrap(helmet));
    }

    @Override
    public IItemStack setChestplate(final IItemStack chestplate)
    {
        return this.content.getAndSet(1, ItemStackImpl.wrap(chestplate));
    }

    @Override
    public IItemStack setLeggings(final IItemStack leggings)
    {
        return this.content.getAndSet(2, ItemStackImpl.wrap(leggings));
    }

    @Override
    public IItemStack setBoots(final IItemStack boots)
    {
        return this.content.getAndSet(3, ItemStackImpl.wrap(boots));
    }

    @Override
    public boolean replaceHelmet(final IItemStack excepted, final IItemStack helmet) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(0, (ItemStackImpl) excepted, ItemStackImpl.wrap(helmet));
    }

    @Override
    public boolean replaceChestplate(final IItemStack excepted, final IItemStack chestplate) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(1, (ItemStackImpl) excepted, ItemStackImpl.wrap(chestplate));
    }

    @Override
    public boolean replaceLeggings(final IItemStack excepted, final IItemStack leggings) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(2, (ItemStackImpl) excepted, ItemStackImpl.wrap(leggings));
    }

    @Override
    public boolean replaceBoots(final IItemStack excepted, final IItemStack boots) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(3, (ItemStackImpl) excepted, ItemStackImpl.wrap(boots));
    }
}
