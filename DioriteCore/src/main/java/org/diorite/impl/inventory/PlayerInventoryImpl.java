package org.diorite.impl.inventory;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerArmorInventory;
import org.diorite.inventory.PlayerCraftingInventory;
import org.diorite.inventory.PlayerEqInventory;
import org.diorite.inventory.PlayerFullEqInventory;
import org.diorite.inventory.PlayerHotbarInventory;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.ItemStackArray;

public class PlayerInventoryImpl extends InventoryImpl<Player> implements PlayerInventory
{
    private final Player holder;
    private final ItemStackArray content = ItemStackArray.create(InventoryType.PLAYER.getSize());

    public PlayerInventoryImpl(final Player holder)
    {
        super(holder);
        this.holder = holder;
    }

    @Override
    public ItemStack getHelmet()
    {
        return this.content.get(5);
    }

    @Override
    public ItemStack getChestplate()
    {
        return this.content.get(6);
    }

    @Override
    public ItemStack getLeggings()
    {
        return this.content.get(7);
    }

    @Override
    public ItemStack getBoots()
    {
        return this.content.get(8);
    }

    @Override
    public ItemStack setHelmet(final ItemStack helmet)
    {
        return this.content.getAndSet(5, helmet);
    }

    @Override
    public ItemStack setChestplate(final ItemStack chestplate)
    {
        return this.content.getAndSet(6, chestplate);
    }

    @Override
    public ItemStack setLeggings(final ItemStack leggings)
    {
        return this.content.getAndSet(7, leggings);
    }

    @Override
    public ItemStack setBoots(final ItemStack boots)
    {
        return this.content.getAndSet(8, boots);
    }

    @Override
    public boolean replaceHelmet(final ItemStack excepted, final ItemStack helmet)
    {
        return this.content.compareAndSet(5, excepted, helmet);
    }

    @Override
    public boolean replaceChestplate(final ItemStack excepted, final ItemStack chestplate)
    {
        return this.content.compareAndSet(6, excepted, chestplate);
    }

    @Override
    public boolean replaceLeggings(final ItemStack excepted, final ItemStack leggings)
    {
        return this.content.compareAndSet(7, excepted, leggings);
    }

    @Override
    public boolean replaceBoots(final ItemStack excepted, final ItemStack boots)
    {
        return this.content.compareAndSet(8, excepted, boots);
    }

    @Override
    public ItemStack getItemInHand()
    {
        if (this.holder == null)
        {
            return null;
        }
        return this.content.get(this.holder.getHeldItemSlot());
    }

    @Override
    public ItemStack setItemInHand(final ItemStack stack)
    {
        if (this.holder == null)
        {
            return null;
        }
        return this.content.getAndSet(this.holder.getHeldItemSlot(), stack);
    }

    @Override
    public boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack)
    {
        return (this.holder != null) && this.content.compareAndSet(this.holder.getHeldItemSlot(), excepted, stack);
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

    @Override
    public ItemStackArray getContents()
    {
        return this.content;
    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {
        if (! this.viewers.contains(player))
        {
            throw new IllegalArgumentException("Player must be a viewer of inventoy.");
        }
//        ServerImpl.getInstance().getPlayersManager().getRawPlayers().get(player.getUniqueID()).getNetworkManager().sendPacket(new );
        // TODO: implement
    }

    @Override
    public void update()
    {
        for (final Player player : this.viewers)
        {
            // TODO: implement
        }
    }

    @Override
    public Player getHolder()
    {
        return this.holder;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("holder", this.holder).append("content", this.content).toString();
    }

    @Override
    public PlayerArmorInventory getArmorInventory()
    {
        return new PlayerArmorInventoryImpl(this);
    }

    @Override
    public PlayerCraftingInventory getCraftingInventory()
    {
        return new PlayerCraftingInventoryImpl(this);
    }

    @Override
    public PlayerFullEqInventory getFullEqInventory()
    {
        return new PlayerFullEqInventoryImpl(this);
    }

    @Override
    public PlayerEqInventory getEqInventory()
    {
        return new PlayerEqInventoryImpl(this);
    }

    @Override
    public PlayerHotbarInventory getHotbarInventory()
    {
        return new PlayerHotbarInventoryImpl(this);
    }
}
