/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.inventory;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSetSlot;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWindowItems;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundWindowClick;
import org.diorite.impl.entity.IHuman;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Human;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;
import org.diorite.inventory.slot.SlotType;
import org.diorite.material.Material;

public class PlayerInventoryImpl extends InventoryImpl<IHuman> implements PlayerInventory
{
    public static final  int   SECOND_HAND_SLOT = 45;
    private static final short CURSOR_SLOT      = - 1;
    private static final int   CURSOR_WINDOW    = - 1;

    private final int    windowId;
    private final IHuman holder;
    private final DragControllerImpl drag       = new DragControllerImpl();
    private final ItemStackImplArray content    = ItemStackImplArray.create(InventoryType.PLAYER.getSize());
    private final Slot[]             slots      = new Slot[InventoryType.PLAYER.getSize()];
    private final VirtualSlot        cursorItem = new VirtualSlot(CURSOR_SLOT);
    private final VirtualSlot        secondHand = new VirtualSlot(SECOND_HAND_SLOT);

    private static class VirtualSlot
    {
        private final int slot;
        private final AtomicReference<ItemStackImpl> item = new AtomicReference<>();
        private boolean wasNotNull; // used only by softUpdate

        private VirtualSlot(final int slot)
        {
            this.slot = slot;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("item", this.item).append("wasNotNull", this.wasNotNull).toString();
        }
    }

    {
        int i = 0;
        this.slots[i] = Slot.BASE_RESULT_SLOT;
        Arrays.fill(this.slots, 1, i + InventoryType.PLAYER_CRAFTING.getSize(), Slot.BASE_CRAFTING_SLOT);
        i += InventoryType.PLAYER_CRAFTING.getSize();
        Arrays.fill(this.slots, i, (i + InventoryType.PLAYER_ARMOR.getSize()), Slot.BASE_ARMOR_SLOT);
        i += InventoryType.PLAYER_ARMOR.getSize();
        Arrays.fill(this.slots, i, (i + InventoryType.PLAYER_EQ.getSize()), Slot.BASE_CONTAINER_SLOT);
        i += InventoryType.PLAYER_EQ.getSize();
        Arrays.fill(this.slots, i, (i + InventoryType.PLAYER_HOTBAR.getSize()), Slot.BASE_HOTBAR_SLOT);
        i += InventoryType.PLAYER_HOTBAR.getSize();
        this.slots[i] = Slot.BASE_SECOND_HAND_SLOT;
    }

    public PlayerInventoryImpl(final IHuman holder, final int windowId)
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
    public Slot getSlot(final int slot)
    {
        return ((slot == PacketPlayServerboundWindowClick.SLOT_NOT_NEEDED) || (slot == PacketPlayServerboundWindowClick.INVALID_SLOT)) ? Slot.BASE_OUTSIDE_SLOT : this.slots[slot];
    }

    @Override
    public int firstEmpty()
    {
        int i = this.hotbar.firstEmpty();
        int offset = this.hotbar.getSlotOffset();
        if (i == - 1)
        {
            i = this.eq.firstEmpty();
            if (i == - 1)
            {
                return - 1;
            }
            offset = this.eq.getSlotOffset();
        }
        return offset + i;
    }

    @Override
    public int first(final Material material)
    {
        int i = this.hotbar.first(material);
        int offset = this.hotbar.getSlotOffset();
        if (i == - 1)
        {
            i = this.eq.first(material);
            if (i == - 1)
            {
                return - 1;
            }
            offset = this.eq.getSlotOffset();
        }
        return offset + i;
    }

    @Override
    public int first(final ItemStack item, final boolean withAmount)
    {
        int i = this.hotbar.first(item, withAmount);
        int offset = this.hotbar.getSlotOffset();
        if (i == - 1)
        {
            i = this.eq.first(item, withAmount);
            if (i == - 1)
            {
                return - 1;
            }
            offset = this.eq.getSlotOffset();
        }
        return offset + i;
    }

    @Override
    public int first(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        int offset = this.hotbar.getSlotOffset();
        int start = (startIndex >= offset) ? (startIndex - offset) : startIndex;
        int i = (start >= this.hotbar.size()) ? - 1 : this.hotbar.first(item, start, withAmount);
        if (i == - 1)
        {
            offset += this.eq.getSlotOffset();
            start = (startIndex >= offset) ? (startIndex - offset) : (startIndex);
            i = (start >= offset) ? - 1 : this.eq.first(item, start - this.eq.getSlotOffset(), withAmount);
            if ((i >= this.eq.size()) || (i == - 1))
            {
                return - 1;
            }
            i += this.eq.getSlotOffset();
            return i;
        }
        return offset + i;
    }

    @Override
    public int firstNotFull(final Material material)
    {
        int i = this.hotbar.firstNotFull(material);
        int offset = this.hotbar.getSlotOffset();
        if (i == - 1)
        {
            i = this.eq.firstNotFull(material);
            if (i == - 1)
            {
                return - 1;
            }
            offset = this.eq.getSlotOffset();
        }
        return offset + i;
    }

    @Override
    public int firstNotFull(final Material material, final int startIndex)
    {
        int offset = this.hotbar.getSlotOffset();
        int start = (startIndex >= offset) ? (startIndex - offset) : startIndex;
        int i = (start >= this.hotbar.size()) ? - 1 : this.hotbar.firstNotFull(material, start);
        if (i == - 1)
        {
            offset += this.eq.getSlotOffset();
            start = (startIndex >= offset) ? (startIndex - offset) : (startIndex);
            i = (start >= offset) ? - 1 : this.eq.firstNotFull(material, start - this.eq.getSlotOffset());
            if ((i >= this.eq.size()) || (i == - 1))
            {
                return - 1;
            }
            i += this.eq.getSlotOffset();
            return i;
        }
        return offset + i;
    }

    @Override
    public int firstNotFull(final ItemStack item, final boolean withAmount)
    {
        return this.firstNotFull(item, 0, withAmount);
    }

    @Override
    public int firstNotFull(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        int offset = this.hotbar.getSlotOffset();
        int start = (startIndex >= offset) ? (startIndex - offset) : startIndex;
        int i = (start >= this.hotbar.size()) ? - 1 : this.hotbar.firstNotFull(item, start, withAmount);
        if (i == - 1)
        {
            offset += this.eq.getSlotOffset();
            start = (startIndex >= offset) ? (startIndex - offset) : (startIndex);
            i = (start >= offset) ? - 1 : this.eq.firstNotFull(item, start - this.eq.getSlotOffset(), withAmount);
            if ((i >= this.eq.size()) || (i == - 1))
            {
                return - 1;
            }
            i += this.eq.getSlotOffset();
            return i;
        }
        return offset + i;
    }

    @Override
    public int lastEmpty()
    {
        int i = this.hotbar.lastEmpty();
        int offset = this.hotbar.getSlotOffset();
        if (i == - 1)
        {
            i = this.eq.lastEmpty();
            if (i == - 1)
            {
                return - 1;
            }
            offset = this.eq.getSlotOffset();
        }
        return offset + i;
    }

    @Override
    public int last(final Material material)
    {
        int i = this.hotbar.last(material);
        int offset = this.hotbar.getSlotOffset();
        if (i == - 1)
        {
            i = this.eq.last(material);
            if (i == - 1)
            {
                return - 1;
            }
            offset = this.eq.getSlotOffset();
        }
        return offset + i;
    }

    @Override
    public int last(final ItemStack item, final boolean withAmount)
    {
        int i = this.hotbar.last(item, withAmount);
        int offset = this.hotbar.getSlotOffset();
        if (i == - 1)
        {
            i = this.eq.last(item, withAmount);
            if (i == - 1)
            {
                return - 1;
            }
            offset = this.eq.getSlotOffset();
        }
        return offset + i;
    }

    @Override
    public int last(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        int offset = this.hotbar.getSlotOffset();
        int start = (startIndex >= offset) ? (startIndex - offset) : startIndex;
        int i = (start >= this.hotbar.size()) ? - 1 : this.hotbar.last(item, start, withAmount);
        if (i == - 1)
        {
            offset += this.eq.getSlotOffset();
            start = (startIndex >= offset) ? (startIndex - offset) : (startIndex);
            i = (start >= offset) ? - 1 : this.eq.last(item, start - this.eq.getSlotOffset(), withAmount);
            if ((i >= this.eq.size()) || (i == - 1))
            {
                return - 1;
            }
            i += this.eq.getSlotOffset();
            return i;
        }
        return offset + i;
    }

    @Override
    public int lastNotFull(final Material material)
    {
        int i = this.hotbar.lastNotFull(material);
        int offset = this.hotbar.getSlotOffset();
        if (i == - 1)
        {
            i = this.eq.lastNotFull(material);
            if (i == - 1)
            {
                return - 1;
            }
            offset = this.eq.getSlotOffset();
        }
        return offset + i;
    }

    @Override
    public int lastNotFull(final Material material, final int startIndex)
    {
        int offset = this.hotbar.getSlotOffset();
        int start = (startIndex >= offset) ? (startIndex - offset) : startIndex;
        int i = (start >= this.hotbar.size()) ? - 1 : this.hotbar.lastNotFull(material, start);
        if (i == - 1)
        {
            offset += this.eq.getSlotOffset();
            start = (startIndex >= offset) ? (startIndex - offset) : (startIndex);
            i = (start >= offset) ? - 1 : this.eq.lastNotFull(material, start - this.eq.getSlotOffset());
            if ((i >= this.eq.size()) || (i == - 1))
            {
                return - 1;
            }
            i += this.eq.getSlotOffset();
            return i;
        }
        return offset + i;
    }

    @Override
    public int lastNotFull(final ItemStack item, final boolean withAmount)
    {
        return this.lastNotFull(item, this.size() - 1, withAmount);
    }

    @Override
    public int lastNotFull(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        int offset = this.hotbar.getSlotOffset();
        int start = (startIndex >= offset) ? (startIndex - offset) : startIndex;
        int i = (start >= this.hotbar.size()) ? - 1 : this.hotbar.lastNotFull(item, start, withAmount);
        if (i == - 1)
        {
            offset += this.eq.getSlotOffset();
            start = (startIndex >= offset) ? (startIndex - offset) : (startIndex);
            i = (start >= offset) ? - 1 : this.eq.lastNotFull(item, start - this.eq.getSlotOffset(), withAmount);
            if ((i >= this.eq.size()) || (i == - 1))
            {
                return - 1;
            }
            i += this.eq.getSlotOffset();
            return i;
        }
        return offset + i;
    }

    @Override
    public ItemStack[] add(final ItemStack... items)
    {
        final ItemStack[] leftover = this.hotbar.add(items);
        return this.eq.add(leftover);
    }

    @Override
    public ItemStack[] addFromEnd(final ItemStack... items)
    {
        final ItemStack[] leftover = this.hotbar.addFromEnd(items);
        return this.eq.addFromEnd(leftover);
    }

    @Override
    public ItemStack getCursorItem()
    {
        return this.cursorItem.item.get();
    }

    @Override
    public ItemStackImpl setCursorItem(final ItemStack cursorItem)
    {
        return this.cursorItem.item.getAndSet(ItemStackImpl.wrap(cursorItem));
    }

    @Override
    public DragControllerImpl getDragController()
    {
        return this.drag;
    }

    @Override
    public boolean replaceCursorItem(final ItemStack excepted, final ItemStack cursorItem) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.cursorItem.item.compareAndSet((ItemStackImpl) excepted, ItemStackImpl.wrap(cursorItem));
    }

    @Override
    public ItemStack getOffHandItem()
    {
        return this.secondHand.item.get();
    }

    @Override
    public ItemStack setOffHandItem(final ItemStack offHandItem)
    {
        return this.secondHand.item.getAndSet(ItemStackImpl.wrap(offHandItem));
    }

    @Override
    public boolean replaceOffHandItem(final ItemStack excepted, final ItemStack offHandItem)
    {
        ItemStackImpl.validate(excepted);
        return this.secondHand.item.compareAndSet((ItemStackImpl) excepted, ItemStackImpl.wrap(offHandItem));
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

        ((IPlayer) player).getNetworkManager().sendPacket(new PacketPlayClientboundWindowItems(this.windowId, this.content));
    }

    private boolean isCraftingSlot(final int i)
    {
        final Slot slot = this.getSlot(i);
        if (slot == null)
        {
            return false;
        }
        final SlotType type = slot.getSlotType();
        return type.equals(SlotType.CRAFTING) || type.equals(SlotType.RESULT);
    }

    private Map<Short, PacketPlayClientboundSetSlot> softUpdate0() // TODO change if we will switch to fastutils
    {
        boolean craftingGridUpdated = false;
        boolean onlyResult = true;
        final int itemsLength = this.content.length();
        final Map<Short, PacketPlayClientboundSetSlot> packets = new LinkedHashMap<>(itemsLength);
        for (short i = 0; i < itemsLength; i++)
        {
            final ItemStackImpl item = this.content.get(i);

            if (item != null)
            {
                if ((item.getAmount() == 0) || (Material.AIR.simpleEquals(item.getMaterial())))
                {
                    this.replace(i, item, null);
                    packets.remove(i);
                    packets.put(i, new PacketPlayClientboundSetSlot(this.windowId, i, null));
                    continue;
                }
                this.notNullItems.add(i);
                if (item.setClean()) // returns true if item was dirty before cleaning
                {
                    if (this.isCraftingSlot(i))
                    {
                        craftingGridUpdated = true;
                        if (! this.getSlot(i).getSlotType().equals(SlotType.RESULT))
                        {
                            onlyResult = false;
                        }
                    }
                    packets.remove(i);
                    packets.put(i, new PacketPlayClientboundSetSlot(this.windowId, i, item));
                }
            }
            else if (this.notNullItems.remove(i))
            {
                if (! craftingGridUpdated && this.isCraftingSlot(i))
                {
                    craftingGridUpdated = true;
                    if (! this.getSlot(i).getSlotType().equals(SlotType.RESULT))
                    {
                        onlyResult = false;
                    }
                }
                packets.remove(i);
                packets.put(i, new PacketPlayClientboundSetSlot(this.windowId, i, null));
            }
        }
        if (craftingGridUpdated)
        {
            this.crafting.checkRecipe(onlyResult);
            for (int i = this.crafting.size(); i >= 0; i--)
            {
                final short slotIndex = (short) (this.crafting.getSlotOffset() + i);
                packets.remove(slotIndex);
                packets.put(slotIndex, new PacketPlayClientboundSetSlot(this.windowId, slotIndex, this.getItem(slotIndex)));
            }
        }
        // cursor
        {
            ItemStackImpl cursor = this.cursorItem.item.get();
            if ((this.cursorItem.wasNotNull && (cursor == null)) || ((cursor != null) && cursor.isDirty()))
            {
                if (cursor != null)
                {
                    if ((cursor.getAmount() == 0) || (Material.AIR.simpleEquals(cursor.getMaterial())))
                    {
                        this.replaceCursorItem(cursor, null);
                        cursor = null;
                    }
                    else
                    {
                        cursor.setClean();
                        this.cursorItem.wasNotNull = true;
                    }
                }
                packets.remove(CURSOR_SLOT);
                packets.put(CURSOR_SLOT, new PacketPlayClientboundSetSlot(CURSOR_WINDOW, CURSOR_SLOT, cursor));
            }
        }
        return packets;
    }

    @Override
    public boolean softUpdate()
    {
        final Map<Short, PacketPlayClientboundSetSlot> packets = this.softUpdate0();
        if (! packets.isEmpty())
        {
            final PacketPlayClientboundSetSlot[] packetsArray = packets.values().toArray(new PacketPlayClientboundSetSlot[packets.size()]);
            this.viewers.stream().filter(p -> p instanceof IPlayer).forEach(p -> ((IPlayer) p).getNetworkManager().sendPackets(packetsArray));
            return true;
        }
        return false;
    }

    @Override
    public void update()
    {
        this.viewers.stream().filter(h -> h instanceof Player).map(h -> (Player) h).forEach(this::update);
    }

    @Override
    public Human getEquipmentHolder()
    {
        return this.holder;
    }

    @Override
    public IHuman getHolder()
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

    @Override
    public int getSlotOffset()
    {
        return 0;
    }

}
