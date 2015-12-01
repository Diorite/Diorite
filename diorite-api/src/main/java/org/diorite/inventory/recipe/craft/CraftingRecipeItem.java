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

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeItem;

/**
 * Represent recipe item, it may use custom validate code.
 */
public interface CraftingRecipeItem extends RecipeItem
{
    /**
     * Returns item that will be placed in place of recipe item after crafting some recipe. <br>
     * Like recipe item using Milk bucket and replacing into empty bucket when recipe is used. <br>
     * Method will return null if recipe item don't use replacement item.
     *
     * @param player       player that used recipe, may be null.
     * @param craftingGrid items consumed by recipe.
     *
     * @return item that will be placed in place of recipe item after crafting some recipe.
     */
    default ItemStack getReplacement(final Player player, final CraftingGrid craftingGrid)
    {
        return null;
    }
}
