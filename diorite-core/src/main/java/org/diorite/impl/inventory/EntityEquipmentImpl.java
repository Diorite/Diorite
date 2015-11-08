/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.ArmoredEntity;
import org.diorite.inventory.EntityEquipment;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.others.Dirtable;

public class EntityEquipmentImpl implements EntityEquipment, Dirtable
{
    protected final ItemStackImplArray content = ItemStackImplArray.create(4);
    protected final ArmoredEntity entity;
    protected       boolean       dirty;

    public EntityEquipmentImpl(final ArmoredEntity entity)
    {
        this.entity = entity;
    }

    @Override
    public ItemStack[] getContents()
    {
        return this.content.toArray(new ItemStack[this.content.length()]);
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

    /**
     * Completely replaces the inventory's contents. Removes all existing
     * contents and replaces it with the ItemStacks given in the array.
     *
     * @param items A complete replacement for the contents; the length must
     *              be less than or equal to {@link #size()}.
     *
     * @throws IllegalArgumentException If the array has more items than the
     *                                  inventory.
     */
    public void setContent(final ItemStackImplArray items)
    {
        final ItemStackImplArray content = this.content;
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, items.getOrNull(i));
        }
    }

    @Override
    public void setContent(final ItemStack[] items)
    {
        final ItemStackImplArray content = this.content;
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, (i >= items.length) ? null : ItemStackImpl.wrap(items[i]));
        }
    }

    @Override
    public int size()
    {
        return this.content.length();
    }

    @Override
    public ArmoredEntity getEquipmentHolder()
    {
        return this.entity;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("content", this.content).append("entity", this.entity.getId()).toString();
    }

    @Override
    public boolean isDirty()
    {
        return this.dirty;
    }

    @Override
    public boolean setDirty(final boolean dirty)
    {
        final boolean b = this.dirty;
        this.dirty = dirty;
        return b;
    }
}
