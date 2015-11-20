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
     * Default priority used by custom unknown recipes.
     */
    long DEFAULT_RECIPE_PRIORITY           = 200_000_000_000L;
    /**
     * Default priority used by custom shaped recipes.
     */
    long DEFAULT_SHAPED_RECIPE_PRIORITY    = 500_000_000_000L;
    /**
     * Default priority used by custom shapeless recipes.
     */
    long DEFAULT_SHAPELESS_RECIPE_PRIORITY = 800_000_000_000L;
    /**
     * First priority used by vanilla diorite recipes.
     */
    long DIORITE_START                     = 1_000_000_000_000L;
    /**
     * Last priority used by vanilla diorite recipes.
     */
    long DIORITE_END                       = 9_999_999_999_999L;
    /**
     * Free space between diorite recipes priority.
     */
    long DIORITE_SPACE                     = 1_000_000L;

    /**
     * Check if items in given GridInventory is valid for this recipe.
     *
     * @param inventory inventory to check.
     *
     * @return special object contains results of check, or null if recipe isn't matching.
     */
    RecipeCheckResult isMatching(final GridInventory inventory);

    /**
     * Returns basic result items. <br>
     * Item returned by this method may not match item returned by {@link #craft(GridInventory)} method. <br>
     * If some recipe replace item from inventory grid, like milk bucket to empty bucket, empty bucket will be returned too.
     *
     * @return basic result items.
     */
    List<ItemStack> getResult();

    /**
     * Recipes used more often than others should use lower priority, this is used to speedup looking for recipe. <br>
     * Also if two recipes will work for this same items, like stone can craft button,
     * but you want add stone named "special" to craft diamond, you should use lower priority than default recipe. <br>
     * Minecraft vanilla recipes use values from 1_000_000_000_000 to 9_999_999_999_999
     * with at least 1_000_000 free values between recipes.<br>
     * See {@link #DEFAULT_RECIPE_PRIORITY} {@link #DEFAULT_SHAPED_RECIPE_PRIORITY} and {@link #DEFAULT_SHAPELESS_RECIPE_PRIORITY} <br>
     * Priority should be constant, as changes of priority in runtime may not change order in recipe manager.
     *
     * @return priority of this recipe.
     */
    default long getPriority()
    {
        return DEFAULT_RECIPE_PRIORITY;
    }

    /**
     * Returns true if this is vanilla minecraft recipe.
     *
     * @return true if this is vanilla minecraft recipe.
     */
    default boolean isVanilla()
    {
        return false;
    }
}
