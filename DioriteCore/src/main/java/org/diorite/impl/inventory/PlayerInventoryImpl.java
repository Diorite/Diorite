package org.diorite.impl.inventory;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutWindowItems;
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
    private final int    windowId;
    private final Player holder;
    private final ItemStackArray             content    = ItemStackArray.create(InventoryType.PLAYER.getSize());
    private final AtomicReference<ItemStack> cursorItem = new AtomicReference<>();

    public PlayerInventoryImpl(final Player holder, final int windowId)
    {
        super(holder);
        this.windowId = windowId;
        this.holder = holder;
        if (windowId == 0) // Owner of inventory always must be in viewers to be able to update
        {
            this.viewers.add(holder);
        }
    }

    @Override
    public int firstEmpty()
    {
        return this.getFullEqInventory().firstEmpty();
    }

    @Override
    public ItemStack[] add(final ItemStack... items)
    {
        return this.getFullEqInventory().add(items);
    }

    @Override
    public ItemStack getCursorItem()
    {
        return this.cursorItem.get();
    }

    // should be in API too?
    public void setCursorItem(final ItemStack cursorItem)
    {
        this.cursorItem.set(cursorItem);
    }

    // atomic
    public boolean setCursorItem(final ItemStack excepted, final ItemStack cursorItem)
    {
        return this.cursorItem.compareAndSet(excepted, cursorItem);
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
        return this.getHotbarInventory().getContents().get(this.holder.getHeldItemSlot());
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

        //noinspection unchecked
        ServerImpl.getInstance().getPlayersManager().getRawPlayers().get(player.getUniqueID()).getNetworkManager().sendPacket(new PacketPlayOutWindowItems(this.windowId, this.content));
    }

    @Override
    public void update()
    {
        this.viewers.forEach(this::update);
    }

    @Override
    public Player getEquipmentHolder()
    {
        return this.holder;
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

    private final PlayerArmorInventoryImpl    armor    = new PlayerArmorInventoryImpl(this);
    private final PlayerCraftingInventoryImpl crafting = new PlayerCraftingInventoryImpl(this);
    private final PlayerFullEqInventoryImpl   fullEq   = new PlayerFullEqInventoryImpl(this);
    private final PlayerEqInventoryImpl       eq       = new PlayerEqInventoryImpl(this);
    private final PlayerHotbarInventoryImpl   hotbar   = new PlayerHotbarInventoryImpl(this);

    @Override
    public PlayerArmorInventory getArmorInventory()
    {
        return this.armor;
    }

    @Override
    public PlayerCraftingInventory getCraftingInventory()
    {
        return this.crafting;
    }

    @Override
    public PlayerFullEqInventory getFullEqInventory()
    {
        return this.fullEq;
    }

    @Override
    public PlayerEqInventory getEqInventory()
    {
        return this.eq;
    }

    @Override
    public PlayerHotbarInventory getHotbarInventory()
    {
        return this.hotbar;
    }

    @Override
    public int getWindowId()
    {
        return this.windowId;
    }
}
