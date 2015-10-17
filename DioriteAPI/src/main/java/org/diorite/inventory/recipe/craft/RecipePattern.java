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

import org.diorite.inventory.recipe.RecipeItem;

/**
 * Represent recipe pattern for {@link ShapedRecipe}.
 */
public interface RecipePattern
{
    /**
     * Returns width of recipe pattern.
     *
     * @return width of recipe pattern.
     */
    int getColumns();

    /**
     * Returns height of recipe pattern.
     *
     * @return height of recipe pattern.
     */
    int getRows();

    /**
     * Returns recipe item on given pattern slot. <br>
     * Returns null if there is no recipe item on given slot or slot is bigger than pattern size.
     *
     * @param row    row index of pattern.
     * @param column column index of pattern.
     *
     * @return recipe item on given pattern slot or null.
     */
    RecipeItem getRecipeItem(int row, int column);
}
