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

package org.diorite.inventory.recipe;

import org.diorite.inventory.Inventory;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.item.ItemStack;

import gnu.trove.map.TShortObjectMap;

/**
 * Represent any recipe, crafting, furnance, etc...
 */
public interface Recipe
{
    /**
     * Check if items in inventory matches pattern, and return crafted item if yes. <br>
     * If items aren't valid, method will return null.
     *
     * @param inv inventory to check.
     *
     * @return crafting result if items in inventory matches pattern, or null.
     */
    ItemStack craft(final Inventory inv);
//    {
//        final TShortObjectMap<SimpleRecipeItem> map = this.getValidItems();
//        final TShortObjectIterator<SimpleRecipeItem> it = map.iterator();
//        while (it.hasNext())
//        {
//            it.advance();
//            if (! it.value().isValid(inv.getItem(it.key())))
//            {
//                return null;
//            }
//        }
//    }

    /**
     * Returns map with valid recipe items, where key is slot id.
     *
     * @return map with valid recipe items, where key is slot id.
     */
    TShortObjectMap<SimpleRecipeItem> getValidItems();

    /**
     * Returns map with valid recipe items, where key is slot id.
     *
     * @return map with valid recipe items, where key is slot id.
     */
    TShortObjectMap<ItemStack> getResultItems();

    /**
     * Returns main result item, on slot 0.
     *
     * @return main result item, on slot 0.
     */
    ItemStack getMainResultItem();

    /**
     * Returns inventory with pattern and result items.
     *
     * @return inventory with pattern and result items.
     */
    Inventory getPatternInventory();

    /**
     * Returns type of inventory for this recipe.
     *
     * @return type of inventory for this recipe.
     */
    InventoryType getCraftingInventoryType();
}
