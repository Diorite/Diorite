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

package org.diorite.inventory.recipe.craft;

import java.util.List;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;

/**
 * Represent crafting recipe.
 */
public interface Recipe
{
    /**
     * Check if items in inventory matches pattern, and return crafted item if yes. <br>
     * Method will remove recipe items for inventory too. <br>
     * Method may add items to crafting grid too. (like replacing milk bucket with empty bucket) <br>
     * If items aren't valid, method will return null.
     *
     * @param inv inventory to check.
     *
     * @return crafting result if items in inventory matches pattern, or null.
     */
    ItemStack craft(GridInventory inv);

    /**
     * Check if items in given GridInventory is valid for this recipe.
     *
     * @param inventory inventory to check.
     *
     * @return true if items in given inventory can be used by this recipe.
     */
    boolean isValid(GridInventory inventory);

    /**
     * Returns basic result items. <br>
     * Item returned by this method may not match item returned by {@link #craft(GridInventory)} method. <br>
     * If some recipe replace item from inventory grid, like milk bucket to empty bucket, empty bucket will be returned too.
     *
     * @return basic result items.
     */
    List<ItemStack> getResult();
}
