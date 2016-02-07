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

package org.diorite.inventory.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import org.diorite.inventory.item.ItemStack;

/**
 * Used in more advanced recipes to generate result item based on consumed items.
 */
public interface ConsumedItems extends Cloneable
{
    /**
     * Retruns raw array of items that you can edit.
     *
     * @return raw array of items that you can edit.
     */
    ItemStack[] getItems();

    /**
     * Returns list of items without null values.
     *
     * @return list of items without null values.
     */
    default List<ItemStack> getItemsList()
    {
        final List<ItemStack> list = Lists.newArrayList(this.getItems());
        list.removeIf(i -> i == null);
        return list;
    }

    /**
     * Returns copy of this consumed items.
     *
     * @return copy of this consumed items.
     */
    ConsumedItems clone();
}
