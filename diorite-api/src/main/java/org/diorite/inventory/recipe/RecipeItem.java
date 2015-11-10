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

import org.diorite.inventory.item.ItemStack;

/**
 * Represent recipe item, it may use custom validate code.
 */
public interface RecipeItem
{
    /**
     * Check if given item is valid for this recipe item stack.
     *
     * @param item item to check.
     *
     * @return return true if item is valid.
     */
    default boolean isValid(final ItemStack item)
    {
        return this.getItem().isSimilar(item);
    }

    /**
     * Returns item that will be placed in place of recipe item after crafting some recipe. <br>
     * Like recipe item using Milk bucket and replacing into empty bucket when recipe is used. <br>
     * Method will return null if recipe item don't use replacement item.
     *
     * @return item that will be placed in place of recipe item after crafting some recipe.
     */
    default ItemStack getReplacement()
    {
        return null;
    }

    /**
     * Returns valid item for this recipe item stack. <br>
     * This item may not be the only one matching item stack for this recipe,
     * like if recipe need any color of wool, this method may return only one color.
     *
     * @return valid item for this recipe item stack.
     */
    ItemStack getItem();
}
