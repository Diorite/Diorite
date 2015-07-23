package org.diorite.impl.inventory;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.play.out.PacketPlayOutSetSlot;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutWindowItems;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

public class PlayerInventoryImpl extends InventoryImpl<PlayerImpl> implements PlayerInventory
{
    private static final short CURSOR_SLOT   = - 1;
    private static final int   CURSOR_WINDOW = - 1;

    private final int        windowId;
    private final PlayerImpl holder;
    private final DragControllerImpl             drag       = new DragControllerImpl();
    private final ItemStackImplArray             content    = ItemStackImplArray.create(InventoryType.PLAYER.getSize());
    private final AtomicReference<ItemStackImpl> cursorItem = new AtomicReference<>();
    private boolean wasCursorNotNull; // used only by softUpdate

    public PlayerInventoryImpl(final PlayerImpl holder, final int windowId)
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
        return this.cursorItem.getAndSet(ItemStackImpl.wrap(cursorItem));
    }

    @Override
    public DragControllerImpl getDragController()
    {
        return this.drag;
    }

    @Override
    public boolean atomicReplaceCursorItem(final ItemStack excepted, final ItemStack cursorItem) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.cursorItem.compareAndSet((ItemStackImpl) excepted, ItemStackImpl.wrap(cursorItem));
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
        return this.content.getAndSet(5, ItemStackImpl.wrap(helmet));
    }

    @Override
    public ItemStack setChestplate(final ItemStack chestplate)
    {
        return this.content.getAndSet(6, ItemStackImpl.wrap(chestplate));
    }

    @Override
    public ItemStack setLeggings(final ItemStack leggings)
    {
        return this.content.getAndSet(7, ItemStackImpl.wrap(leggings));
    }

    @Override
    public ItemStack setBoots(final ItemStack boots)
    {
        return this.content.getAndSet(8, ItemStackImpl.wrap(boots));
    }

    @Override
    public boolean replaceHelmet(final ItemStack excepted, final ItemStack helmet) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(5, (ItemStackImpl) excepted, ItemStackImpl.wrap(helmet));
    }

    @Override
    public boolean replaceChestplate(final ItemStack excepted, final ItemStack chestplate) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(6, (ItemStackImpl) excepted, ItemStackImpl.wrap(chestplate));
    }

    @Override
    public boolean replaceLeggings(final ItemStack excepted, final ItemStack leggings) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(7, (ItemStackImpl) excepted, ItemStackImpl.wrap(leggings));
    }

    @Override
    public boolean replaceBoots(final ItemStack excepted, final ItemStack boots) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(8, (ItemStackImpl) excepted, ItemStackImpl.wrap(boots));
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
        return this.content.getAndSet(i, ItemStackImpl.wrap(stack));
    }

    @Override
    public boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return (this.holder != null) && this.content.compareAndSet(this.holder.getHeldItemSlot(), (ItemStackImpl) excepted, ItemStackImpl.wrap(stack));
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
        return this.content.getAndSet(0, ItemStackImpl.wrap(result));
    }

    @Override
    public boolean replaceResult(final ItemStack excepted, final ItemStack result)
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(0, (ItemStackImpl) excepted, ItemStackImpl.wrap(result));
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
    public void update(final Player player) throws IllegalArgumentException
    {
        if (! this.viewers.contains(player))
        {
            throw new IllegalArgumentException("Player must be a viewer of inventory.");
        }

        ((PlayerImpl) player).getNetworkManager().sendPacket(new PacketPlayOutWindowItems(this.windowId, this.content));
    }

    @Override
    public void softUpdate()
    {
        ItemStackImpl cursor = this.cursorItem.get();
        if ((this.wasCursorNotNull && (cursor == null)) || ((cursor != null) && cursor.isDirty()))
        {
            if (cursor != null)
            {
                if ((cursor.getAmount() == 0) || (Material.AIR.simpleEquals(cursor.getMaterial())))
                {
                    this.atomicReplaceCursorItem(cursor, null);
                    cursor = null;
                }
                else
                {
                    cursor.setClean();
                    this.wasCursorNotNull = true;
                }
            }
            this.holder.getNetworkManager().sendPacket(new PacketPlayOutSetSlot(CURSOR_WINDOW, CURSOR_SLOT, cursor));

        }
        super.softUpdate();
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
    public PlayerImpl getHolder()
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
