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

public interface CraftingInventory extends GridInventory
{
    /**
     * @return The ItemStack in the result slot
     */
    ItemStack getResult();

    /**
     * Put the given ItemStack into the result slot.
     *
     * @param result The ItemStack to use as result
     *
     * @return previous itemstack used as result.
     */
    ItemStack setResult(final ItemStack result);

    /**
     * Put the given ItemStack into the result slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param result   The ItemStack to use as result
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceResult(final ItemStack excepted, final ItemStack result) throws IllegalArgumentException;

    /**
     * @return copy of array with ItemStacks from the crafting slots.
     */
    ItemStack[] getCraftingSlots();
}
