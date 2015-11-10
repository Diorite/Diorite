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

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.Inventory;
import org.diorite.inventory.item.ItemStack;

public class InventoryIterator implements ListIterator<ItemStack>
{
    private final Inventory inventory;
    private       int       nextIndex;
    private       Boolean   lastDirection;

    InventoryIterator(final Inventory inventory)
    {
        this.inventory = inventory;
        this.nextIndex = 0;
    }

    InventoryIterator(final Inventory inventory, final int index)
    {
        this.inventory = inventory;
        this.nextIndex = index;
    }

    @Override
    public boolean hasNext()
    {
        return this.nextIndex < this.inventory.size();
    }

    @Override
    public ItemStack next()
    {
        this.lastDirection = true;
        if ((this.nextIndex >= this.inventory.size()) || (this.nextIndex < 0))
        {
            throw new NoSuchElementException("Index out of bounds, " + this.nextIndex + " from " + this.inventory.size());
        }
        return this.inventory.getItem(this.nextIndex++);
    }

    @Override
    public boolean hasPrevious()
    {
        return this.nextIndex > 0;
    }

    @Override
    public ItemStack previous()
    {
        this.lastDirection = false;
        return this.inventory.getItem(-- this.nextIndex);
    }

    @Override
    public int nextIndex()
    {
        return this.nextIndex;
    }

    @Override
    public int previousIndex()
    {
        return this.nextIndex - 1;
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Can't change the size of an inventory!");
    }

    @Override
    public void set(final ItemStack item)
    {
        if (this.lastDirection == null)
        {
            throw new IllegalStateException("No current item!");
        }
        final int i = this.lastDirection ? (this.nextIndex - 1) : this.nextIndex;
        this.inventory.setItem(i, item);
    }

    @Override
    public void add(final ItemStack item)
    {
        throw new UnsupportedOperationException("Can't change the size of an inventory!");
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("inventory", this.inventory).append("nextIndex", this.nextIndex).append("lastDirection", this.lastDirection).toString();
    }
}
