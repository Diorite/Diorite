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

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;

/**
 * Represent simple pattern that use map of chars to recipe items as implementation.
 */
public interface CharMapCraftingRecipePattern extends CraftingRecipePattern
{
    /**
     * Returns pattern string array representation, like [aaa,bbb,ccc] where each letter represent other {@link CraftingRecipeItem}
     *
     * @return pattern string array representation, like [aaa,bbb,ccc] where each letter represent other {@link CraftingRecipeItem}
     */
    String[] getPattern();

    /**
     * Returns ingredients used by this pattern.
     *
     * @return ingredients used by this pattern.
     */
    Char2ObjectMap<CraftingRecipeItem> getIngredients();

    /**
     * Returns ingredient for given char, may return null for unknown char.
     *
     * @param c char to get ingredient.
     *
     * @return ingredient for given char, may return null for unknown char.
     */
    CraftingRecipeItem getIngredient(char c);
}
