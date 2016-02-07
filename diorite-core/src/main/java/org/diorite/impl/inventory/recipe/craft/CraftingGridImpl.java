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

package org.diorite.impl.inventory.recipe.craft;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingGrid;

public class CraftingGridImpl implements CraftingGrid
{
    private final int         rows;
    private final int         columns;
    private final ItemStack[] items;

    public CraftingGridImpl(final int rows, final int columns, final ItemStack[] items)
    {
        if (items.length != (rows * columns))
        {
            throw new IllegalArgumentException("items length don't match row*columns: items: " + items.length + ", rows: " + rows + ", columns: " + columns);
        }
        this.rows = rows;
        this.columns = columns;
        this.items = items;
    }

    public CraftingGridImpl(final int rows, final int columns)
    {
        this.rows = rows;
        this.columns = columns;
        this.items = new ItemStack[rows * columns];
    }

    @Override
    public int getColumns()
    {
        return this.columns;
    }

    @Override
    public int getRows()
    {
        return this.rows;
    }

    @Override
    public ItemStack[] getItems()
    {
        return this.items;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public CraftingGrid clone()
    {
        final ItemStack[] array = new ItemStack[this.rows * this.columns];
        int i = 0;
        for (final ItemStack item : this.items)
        {
            array[i++] = (item == null) ? null : item.clone();
        }
        return new CraftingGridImpl(this.rows, this.columns, array);
    }
}
