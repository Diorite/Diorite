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

import org.diorite.inventory.recipe.craft.CraftingRecipe;

/**
 * Advanced class for creating recipes
 */
public interface RecipeBuilder
{
    /**
     * Sets if this recipe is vanilla one, false by default. <br>
     * Non vanilla recipes often create ghost items in inventory, if recipe isn't vanilla, diorite will fix that bugs.
     *
     * @param vanilla if recipe should be vanilla.
     *
     * @return this same builder for method chains.
     */
    RecipeBuilder vanilla(boolean vanilla);

    /**
     * Sets priority of this recipe, see {@link CraftingRecipe#getPriority()}
     *
     * @param priority priority of recipe.
     *
     * @return this same builder for method chains.
     *
     * @see CraftingRecipe#getPriority()
     */
    RecipeBuilder priority(long priority);

    /**
     * Builds and create recipe using all given data.
     *
     * @return created recipe.
     *
     * @throws RuntimeException if creating recipe fail.
     */
    Recipe build() throws RuntimeException;

    /**
     * Builds, create recipe using all given data and adds it to diorite {@link RecipeManager}.
     *
     * @return created recipe.
     *
     * @throws RuntimeException if creating recipe fail.
     */
    Recipe buildAndAdd() throws RuntimeException;
}
