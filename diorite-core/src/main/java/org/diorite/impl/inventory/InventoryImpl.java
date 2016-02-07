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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSetSlot;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Human;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.InventoryHolder;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;
import org.diorite.utils.DioriteUtils;
import org.diorite.utils.collections.sets.ConcurrentSet;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortOpenHashSet;

public abstract class InventoryImpl<T extends InventoryHolder> implements Inventory
{
    protected final T holder;
    protected final Set<Human> viewers = new ConcurrentSet<>(5, 0.2f, 6);
    protected String title;

    protected InventoryImpl(final T holder)
    {
        this.holder = holder;
    }

    @Override
    public ItemStack[] getContents()
    {
        return this.getArray().toArray(new ItemStack[this.getArray().length()]);
    }

    /**
     * Returns raw atomic array with contents of eq.
     *
     * @return raw atomic array with contents of eq.
     */
    public abstract ItemStackImplArray getArray();

//    public abstract void softUpdate();

    final ShortCollection notNullItems = new ShortOpenHashSet(50, .2f); // used only for updates

    public boolean softUpdate()
    {
        final ItemStackImpl[] items = this.getArray().toArray(new ItemStackImpl[this.getArray().length()]);
        final Set<PacketPlayClientboundSetSlot> packets = new HashSet<>(items.length);
        for (short i = 0, itemsLength = (short) items.length; i < itemsLength; i++)
        {
            final ItemStackImpl item = items[i];

            if (item != null)
            {
                if ((item.getAmount() == 0) || (Material.AIR.simpleEquals(item.getMaterial())))
                {
                    this.replace(i, item, null);
                    packets.add(new PacketPlayClientboundSetSlot(this.getWindowId(), i, null));
                    continue;
                }
                this.notNullItems.add(i);
                if (item.setClean()) // returns true if item was dirty before cleaning
                {
                    packets.add(new PacketPlayClientboundSetSlot(this.getWindowId(), i, item));
                }
            }
            else if (this.notNullItems.remove(i))
            {
                packets.add(new PacketPlayClientboundSetSlot(this.getWindowId(), i, null));
            }
        }
        if (! packets.isEmpty())
        {
            final PacketPlayClientboundSetSlot[] packetsArray = packets.toArray(new PacketPlayClientboundSetSlot[packets.size()]);
            this.viewers.stream().filter(h -> h instanceof IPlayer).map(h -> (IPlayer) h).forEach(p -> p.getNetworkManager().sendPackets(packetsArray));
            return true;
        }
        return false;
    }

    /**
     * Completely replaces the inventory's contents. Removes all existing
     * contents and replaces it with the IItemStacks given in the array.
     *
     * @param items A complete replacement for the contents; the length must
     *              be less than or equal to {@link #size()}.
     *
     * @throws IllegalArgumentException If the array has more items than the
     *                                  inventory.
     */
    public void setContent(final ItemStackImplArray items)
    {
        final ItemStackImplArray content = this.getArray();
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, items.getOrNull(i));
        }
    }

    public void setContent(final ItemStackImpl[] items)
    {
        final ItemStackImplArray content = this.getArray();
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, (i >= items.length) ? null : items[i]);
        }
    }

    @Override
    public void setContent(final ItemStack[] items)
    {
        final ItemStackImplArray content = this.getArray();
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, (i >= items.length) ? null : ItemStackImpl.wrap(items[i]));
        }
    }

    @Override
    public int remove(final ItemStack itemStack)
    {
        final ItemStackImplArray content = this.getArray();

        if (itemStack == null)
        {
            return - 1;
        }
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (Objects.equals(item, itemStack))
            {
                content.compareAndSet(i, ItemStackImpl.wrap(item), null);
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int[] removeAll(final ItemStack itemStack)
    {
        final ItemStackImplArray content = this.getArray();
        if (itemStack == null)
        {
            return DioriteUtils.EMPTY_INT;
        }
        final IntCollection list = new IntArrayList(10);
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (Objects.equals(item, itemStack))
            {
                content.compareAndSet(i, ItemStackImpl.wrap(item), null);
                list.add(i);
            }
        }
        if (list.isEmpty())
        {
            return DioriteUtils.EMPTY_INT;
        }
        return list.toIntArray();
    }

    @Override
    public int remove(final Material material, final boolean ignoreType)
    {
        final ItemStackImplArray content = this.getArray();

        if (material == null)
        {
            return - 1;
        }
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (item == null)
            {
                continue;
            }
            final Material mat = item.getMaterial();
            if (ignoreType)
            {
                if ((material.ordinal() != mat.ordinal()))
                {
                    continue;
                }
            }
            else if (! Objects.equals(mat, material))
            {
                continue;
            }
            content.compareAndSet(i, ItemStackImpl.wrap(item), null);
            return i;
        }
        return - 1;
    }

    @Override
    public int[] removeAll(final Material material, final boolean ignoreType)
    {
        final ItemStackImplArray content = this.getArray();

        if (material == null)
        {
            return DioriteUtils.EMPTY_INT;
        }
        final IntCollection list = new IntArrayList(10);
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (item == null)
            {
                continue;
            }
            final Material mat = item.getMaterial();
            if (ignoreType)
            {
                if ((material.ordinal() != mat.ordinal()))
                {
                    continue;
                }
            }
            else if (! Objects.equals(mat, material))
            {
                continue;
            }
            content.compareAndSet(i, ItemStackImpl.wrap(item), null);
            list.add(i);
        }
        if (list.isEmpty())
        {
            return DioriteUtils.EMPTY_INT;
        }
        return list.toIntArray();
    }

    @Override
    public int removeFromEnd(final ItemStack itemStack)
    {
        final ItemStackImplArray content = this.getArray();

        if (itemStack == null)
        {
            return - 1;
        }
        for (int i = content.length() - 1; i >= 0; i--)
        {
            final ItemStack item = content.get(i);
            if (Objects.equals(item, itemStack))
            {
                content.compareAndSet(i, ItemStackImpl.wrap(item), null);
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int[] removeAllFromEnd(final ItemStack itemStack)
    {
        final ItemStackImplArray content = this.getArray();
        if (itemStack == null)
        {
            return DioriteUtils.EMPTY_INT;
        }
        final IntCollection list = new IntArrayList(10);
        for (int i = content.length() - 1; i >= 0; i--)
        {
            final ItemStack item = content.get(i);
            if (Objects.equals(item, itemStack))
            {
                content.compareAndSet(i, ItemStackImpl.wrap(item), null);
                list.add(i);
            }
        }
        if (list.isEmpty())
        {
            return DioriteUtils.EMPTY_INT;
        }
        return list.toIntArray();
    }

    @Override
    public int removeFromEnd(final Material material, final boolean ignoreType)
    {
        final ItemStackImplArray content = this.getArray();

        if (material == null)
        {
            return - 1;
        }
        for (int i = content.length() - 1; i >= 0; i--)
        {
            final ItemStack item = content.get(i);
            if (item == null)
            {
                continue;
            }
            final Material mat = item.getMaterial();
            if (ignoreType)
            {
                if ((material.ordinal() != mat.ordinal()))
                {
                    continue;
                }
            }
            else if (! Objects.equals(mat, material))
            {
                continue;
            }
            content.compareAndSet(i, ItemStackImpl.wrap(item), null);
            return i;
        }
        return - 1;
    }

    @Override
    public int[] removeAllFromEnd(final Material material, final boolean ignoreType)
    {
        final ItemStackImplArray content = this.getArray();

        if (material == null)
        {
            return DioriteUtils.EMPTY_INT;
        }
        final IntCollection list = new IntArrayList(10);
        for (int i = content.length() - 1; i >= 0; i--)
        {
            final ItemStack item = content.get(i);
            if (item == null)
            {
                continue;
            }
            final Material mat = item.getMaterial();
            if (ignoreType)
            {
                if ((material.ordinal() != mat.ordinal()))
                {
                    continue;
                }
            }
            else if (! Objects.equals(mat, material))
            {
                continue;
            }
            content.compareAndSet(i, ItemStackImpl.wrap(item), null);
            list.add(i);
        }
        if (list.isEmpty())
        {
            return DioriteUtils.EMPTY_INT;
        }
        return list.toIntArray();
    }

    @Override
    public int replace(final ItemStack excepted, final ItemStack newItem) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        final ItemStackImplArray content = this.getArray();
        for (int i = 0, size = content.length(); i < size; i++)
        {
            if (content.compareAndSet(i, (ItemStackImpl) excepted, ItemStackImpl.wrap(newItem)))
            {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int replaceFromEnd(final ItemStack excepted, final ItemStack newItem) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        final ItemStackImplArray content = this.getArray();
        for (int i = content.length() - 1; i >= 0; i--)
        {
            if (content.compareAndSet(i, (ItemStackImpl) excepted, ItemStackImpl.wrap(newItem)))
            {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public boolean replace(final int slot, final ItemStack excepted, final ItemStack newItem) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.getArray().compareAndSet(slot, (ItemStackImpl) excepted, ItemStackImpl.wrap(newItem));
    }

    @Override
    public ItemStackImpl getItem(final int index)
    {
        return this.getArray().get(index);
    }

    @Override
    public ItemStackImpl setItem(final int index, final ItemStack item)
    {
        return this.getArray().getAndSet(index, ItemStackImpl.wrap(item));
    }

    @Override
    public Map<Integer, ? extends ItemStack> all(final Material material, final boolean ignoreType)
    {
        final ItemStackImplArray content = this.getArray();
        final Map<Integer, ItemStack> slots = new HashMap<>(10);
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (item != null)
            {
                if (ignoreType)
                {
                    if (item.getMaterial().isThisSameID(material))
                    {
                        slots.put(i, item);
                    }
                }
                else if (item.getMaterial().equals(material))
                {
                    slots.put(i, item);
                }
            }
        }
        return slots;

    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(final ItemStack item)
    {
        final ItemStackImplArray content = this.getArray();

        final HashMap<Integer, ItemStack> slots = new HashMap<>(10);
        if (item != null)
        {
            for (int i = 0, size = content.length(); i < size; i++)
            {
                final ItemStack itemStack = content.get(i);
                if (item.equals(itemStack))
                {
                    slots.put(i, itemStack);
                }
            }
        }
        return slots;
    }

    @Override
    public int first(final Material material)
    {
        return this.first(material, 0);
    }

    @Override
    public int first(final Material material, final int startIndex)
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = startIndex, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if ((item != null) && (item.getMaterial().equals(material)))
            {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int first(final ItemStack item, final boolean withAmount)
    {
        return this.first(item, 0, withAmount);
    }

    @Override
    public int first(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = startIndex, size = content.length(); i < size; i++)
        {
            final ItemStack itemStack = content.get(i);
            //noinspection ObjectEquality
            if (item == itemStack)
            {
                return i;
            }
            if (item == null)
            {
                continue;
            }
            if (itemStack != null)
            {
                if (withAmount ? item.equals(itemStack) : item.isSimilar(itemStack))
                {
                    return i;
                }
            }
        }
        return - 1;
    }

    @Override
    public int firstNotFull(final Material material)
    {
        return this.firstNotFull(material, 0);
    }

    @Override
    public int firstNotFull(final Material material, final int startIndex)
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = startIndex, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if ((item != null) && (item.getMaterial().equals(material)) && (item.getAmount() < material.getMaxStack()))
            {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int firstNotFull(final ItemStack item, final boolean withAmount)
    {
        return this.firstNotFull(item, 0, withAmount);
    }

    @Override
    public int firstNotFull(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = startIndex, size = content.length(); i < size; i++)
        {
            final ItemStack itemStack = content.get(i);
            //noinspection ObjectEquality
            if (item == itemStack)
            {
                return i;
            }
            if (item == null)
            {
                continue;
            }
            if (itemStack != null)
            {
                if (withAmount)
                {
                    if (item.equals(itemStack) && ((item.getAmount() + itemStack.getAmount()) < itemStack.getMaterial().getMaxStack()))
                    {
                        return i;
                    }
                }
                else if (item.isSimilar(itemStack))
                {
                    return i;
                }
            }
        }
        return - 1;
    }

    @Override
    public int firstEmpty()
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack itemStack = content.get(i);
            if (itemStack == null)
            {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int last(final Material material)
    {
        return this.last(material, this.size() - 1);
    }

    @Override
    public int last(final Material material, final int startIndex)
    {
        final ItemStackImplArray content = this.getArray();
        for (int i = startIndex; i >= 0; i--)
        {
            final ItemStack item = content.get(i);
            if ((item != null) && (item.getMaterial().equals(material)))
            {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int last(final ItemStack item, final boolean withAmount)
    {
        return this.last(item, this.size() - 1, withAmount);
    }

    @Override
    public int last(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = startIndex; i >= 0; i--)
        {
            final ItemStack itemStack = content.get(i);
            //noinspection ObjectEquality
            if (item == itemStack)
            {
                return i;
            }
            if (itemStack != null)
            {
                if (withAmount ? item.equals(itemStack) : item.isSimilar(itemStack))
                {
                    return i;
                }
            }
        }
        return - 1;
    }

    @Override
    public int lastNotFull(final Material material)
    {
        return this.lastNotFull(material, this.size() - 1);
    }

    @Override
    public int lastNotFull(final Material material, final int startIndex)
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = startIndex; i >= 0; i--)
        {
            final ItemStack item = content.get(i);
            if ((item != null) && (item.getMaterial().equals(material)) && (item.getAmount() < material.getMaxStack()))
            {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int lastNotFull(final ItemStack item, final boolean withAmount)
    {
        return this.lastNotFull(item, this.size() - 1, withAmount);
    }

    @Override
    public int lastNotFull(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = startIndex; i >= 0; i--)
        {
            final ItemStack itemStack = content.get(i);
            //noinspection ObjectEquality
            if (item == itemStack)
            {
                return i;
            }
            if (itemStack != null)
            {
                if (withAmount)
                {
                    if (item.equals(itemStack) && ((item.getAmount() + itemStack.getAmount()) < itemStack.getMaterial().getMaxStack()))
                    {
                        return i;
                    }
                }
                else if (item.isSimilar(itemStack))
                {
                    return i;
                }
            }
        }
        return - 1;
    }

    @Override
    public int lastEmpty()
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = content.length() - 1; i >= 0; i--)
        {
            final ItemStack itemStack = content.get(i);
            if (itemStack == null)
            {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public void clear(final int index)
    {
        this.getArray().set(index, null);
    }

    @Override
    public void clear()
    {
        final ItemStackImplArray content = this.getArray();

        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, null);
        }
    }

    @Override
    public int size()
    {
        return this.getArray().length();
    }

    @Override
    public Set<? extends Human> getViewers()
    {
        return this.viewers;
    }

    @Override
    public String getTitle()
    {
        return this.title;
    }

    @Override
    public void setTitle(final String str)
    {
        this.title = str;
        this.update();
    }

    @Override
    public T getHolder()
    {
        return this.holder;
    }

    @Override
    public InventoryIterator iterator()
    {
        return new InventoryIterator(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("title", this.title).toString();
    }
}
