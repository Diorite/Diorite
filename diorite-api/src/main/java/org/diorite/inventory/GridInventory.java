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

package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;

/**
 * An GridInventory is an {@link Inventory}
 * which is ordered into a coherent grid format, meaning that its slots can be
 * referred to by X-Y coordinates as well as single indices.
 */
public interface GridInventory extends Inventory
{

    /**
     * Gets the number of columns in the inventory.
     *
     * @return The width of this ItemGrid.
     */
    int getColumns();

    /**
     * Gets the number of rows in the inventory.
     *
     * @return The height of this ItemGrid.
     */
    int getRows();

    default int getSlotIndex(final int row, final int column)
    {
        if ((column >= this.getColumns()) || (row >= this.getRows()))
        {
            return - 1;
        }
        return column + (row * this.getColumns());
    }

    /**
     * Replace item on given slot, only if it matches (==) given item.
     * NOTE: this is atomic operation.
     *
     * @param row      row index.
     * @param column   column index.
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of IItemStack, so it can't be == to any item from inventory.
     */
    default boolean atomicReplace(final int row, final int column, final ItemStack excepted, final ItemStack newItem) throws IllegalArgumentException
    {
        final int slot = this.getSlotIndex(row, column);
        return (slot != - 1) && this.replace(slot, excepted, newItem);
    }

    /**
     * Clears out a particular slot in the index.
     *
     * @param row    row index.
     * @param column column index.
     */
    default void clear(final int row, final int column)
    {
        final int slot = this.getSlotIndex(row, column);
        if (slot == - 1)
        {
            return;
        }
        this.clear(slot);
    }

    /**
     * Returns the IItemStack found in the slot at the given index
     *
     * @param row    row index.
     * @param column column index.
     *
     * @return The IItemStack in the slot
     */
    default ItemStack getItem(final int row, final int column)
    {
        final int slot = this.getSlotIndex(row, column);
        if (slot == - 1)
        {
            return null;
        }
        return this.getItem(slot);
    }

    /**
     * Stores the IItemStack at the given index of the inventory.
     *
     * @param row    row index.
     * @param column column index.
     * @param item   The IItemStack to set
     *
     * @return previous itemstack in this slot.
     */
    default ItemStack setItem(final int row, final int column, final ItemStack item)
    {
        final int slot = this.getSlotIndex(row, column);
        if (slot == - 1)
        {
            return null;
        }
        return this.setItem(slot, item);
    }

    /**
     * Get and remove the stack at the supplied position in this Inventory.
     *
     * @param row    row index.
     * @param column column index.
     *
     * @return ItemStack at the specified position or null if the slot is empty or out of bounds
     *
     * @see Inventory#poll(int)
     */
    default ItemStack poll(final int row, final int column)
    {
        final int slot = this.getSlotIndex(row, column);
        if (slot == - 1)
        {
            return null;
        }
        return this.poll(slot);
    }

    /**
     * Get the {@link Slot} at the specified position.
     *
     * @param row    row index.
     * @param column column index.
     *
     * @return {@link Slot} at the specified position or null if the coordinates are out of bounds
     */
    default Slot getSlot(final int row, final int column)
    {
        final int slot = this.getSlotIndex(row, column);
        if (slot == - 1)
        {
            return null;
        }
        return this.getSlot(slot);
    }

}