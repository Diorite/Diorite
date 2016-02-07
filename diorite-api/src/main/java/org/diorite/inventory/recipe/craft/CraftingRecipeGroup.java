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

package org.diorite.inventory.recipe.craft;

import java.util.Iterator;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.recipe.RecipeGroup;

/**
 * RecipeGroup for crafting recipes.
 *
 * @see org.diorite.inventory.recipe.RecipeGroup
 */
public interface CraftingRecipeGroup extends RecipeGroup, CraftingRecipe
{
    /**
     * Returns new instance of crafting recipe builder.
     *
     * @return new instance of crafting recipe builder.
     */
    CraftingRecipeBuilder craftingBuilder();

    /**
     * Adds new crafting recipe to this recipe group, returns true if recipe was added.
     *
     * @param recipe recipe to be added.
     *
     * @return true if recipe was added.
     */
    boolean add(CraftingRecipe recipe);

    /**
     * Remove given crafting recipe from this recipe group, return true if any recipe was removed.
     *
     * @param recipe recipe to be added.
     *
     * @return true if any recipe was removed.
     */
    boolean remove(CraftingRecipe recipe);

    /**
     * Returns iterator of recipes, may be used to remove recipes.
     *
     * @return iterator of recipes, may be used to remove recipes.
     */
    @Override
    Iterator<? extends CraftingRecipe> recipeIterator();

    /**
     * Try find possible recipe for given inventory. <br>
     * Returns object that contains matched recipe, crafted itemstack and items to remove or change,
     * or returns null if it didn't find any recipe.
     *
     * @param grid inventory to be used.
     *
     * @return object that contains matched recipe, crafted itemstack and items to remove or change.
     */
    CraftingRecipeCheckResult matchCraftingRecipe(GridInventory grid);
}
