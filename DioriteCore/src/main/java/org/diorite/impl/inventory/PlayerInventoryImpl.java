package org.diorite.impl.inventory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSetSlot;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutWindowItems;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;

public class PlayerInventoryImpl extends InventoryImpl<Player> implements PlayerInventory
{
    private final int    windowId;
    private final Player holder;
    private final ItemStackImplArray             content    = ItemStackImplArray.create(InventoryType.PLAYER.getSize());
    private final AtomicReference<ItemStackImpl> cursorItem = new AtomicReference<>();

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
        final int i = this.fullEq.firstEmpty();
        if (i == - 1)
        {
            return - 1;
        }
        return 9 + i;
    }

    @Override
    public ItemStack[] add(final ItemStack... items)
    {
        return this.fullEq.add(items);
    }

    @Override
    public ItemStack getCursorItem()
    {
        return this.cursorItem.get();
    }

    @Override
    public ItemStackImpl setCursorItem(final ItemStack cursorItem)
    {
        return this.cursorItem.getAndSet(ItemStackImpl.wrap(cursorItem, 0));
    }

    @Override
    public boolean atomicReplaceCursorItem(final ItemStack excepted, final ItemStack cursorItem) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.cursorItem.compareAndSet((ItemStackImpl) excepted, ItemStackImpl.wrap(cursorItem, 0));
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
        return this.content.getAndSet(5, this.wrap(helmet, 5));
    }

    @Override
    public ItemStack setChestplate(final ItemStack chestplate)
    {
        return this.content.getAndSet(6, this.wrap(chestplate, 6));
    }

    @Override
    public ItemStack setLeggings(final ItemStack leggings)
    {
        return this.content.getAndSet(7, this.wrap(leggings, 7));
    }

    @Override
    public ItemStack setBoots(final ItemStack boots)
    {
        return this.content.getAndSet(8, this.wrap(boots, 8));
    }

    @Override
    public boolean replaceHelmet(final ItemStack excepted, final ItemStack helmet) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(5, (ItemStackImpl) excepted, this.wrap(helmet, 5));
    }

    @Override
    public boolean replaceChestplate(final ItemStack excepted, final ItemStack chestplate) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(6, (ItemStackImpl) excepted, this.wrap(chestplate, 6));
    }

    @Override
    public boolean replaceLeggings(final ItemStack excepted, final ItemStack leggings) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(7, (ItemStackImpl) excepted, this.wrap(leggings, 7));
    }

    @Override
    public boolean replaceBoots(final ItemStack excepted, final ItemStack boots) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(8, (ItemStackImpl) excepted, this.wrap(boots, 8));
    }

    @Override
    public ItemStack getItemInHand()
    {
        if (this.holder == null)
        {
            return null;
        }
        return this.hotbar.getArray().get(this.holder.getHeldItemSlot());
    }

    @Override
    public ItemStack setItemInHand(final ItemStack stack)
    {
        if (this.holder == null)
        {
            return null;
        }
        final int i = this.holder.getHeldItemSlot();
        return this.content.getAndSet(i, this.wrap(stack, i));
    }

    @Override
    public boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return (this.holder != null) && this.content.compareAndSet(this.holder.getHeldItemSlot(), (ItemStackImpl) excepted, this.wrap(stack, this.holder.getHeldItemSlot()));
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

    @Override
    public ItemStackImplArray getArray()
    {
        return this.content;
    }

    @Override
    public void softUpdate()
    {
        if (this.dirty.isEmpty())
        {
            return;
        }
        if (this.viewers.isEmpty())
        {
            final short[] cpy;
            synchronized (this.dirty)
            {
                cpy = this.dirty.toArray();
                this.dirty.clear();
            }
            for (final short i : cpy)
            {
                final ItemStackImpl item = this.getItem(i);
                if (item != null)
                {
                    item.setClean();
                }
            }
            return;
        }
        final Set<PacketPlayOutSetSlot> packets = new HashSet<>(this.dirty.size());
        final short[] cpy;
        synchronized (this.dirty) // TODO: maybe better way?
        {
            cpy = this.dirty.toArray();
            this.dirty.clear();
        }
        for (final short i : cpy)
        {
            final ItemStackImpl item = this.getItem(i);
            if (item != null)
            {
                item.setClean();
            }
            packets.add(new PacketPlayOutSetSlot(this.windowId, i, item));
        }
        final PacketPlayOutSetSlot[] packetsArray = packets.toArray(new PacketPlayOutSetSlot[packets.size()]);
        this.viewers.forEach(p -> ((PlayerImpl) p).getNetworkManager().sendPackets(packetsArray));
    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {
        if (! this.viewers.contains(player))
        {
            throw new IllegalArgumentException("Player must be a viewer of inventoy.");
        }

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
    public PlayerArmorInventoryImpl getArmorInventory()
    {
        return this.armor;
    }

    @Override
    public PlayerCraftingInventoryImpl getCraftingInventory()
    {
        return this.crafting;
    }

    @Override
    public PlayerFullEqInventoryImpl getFullEqInventory()
    {
        return this.fullEq;
    }

    @Override
    public PlayerEqInventoryImpl getEqInventory()
    {
        return this.eq;
    }

    @Override
    public PlayerHotbarInventoryImpl getHotbarInventory()
    {
        return this.hotbar;
    }

    @Override
    public int getWindowId()
    {
        return this.windowId;
    }

}
