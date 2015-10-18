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

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeItem;

/**
 * Represent recipe where items in crafting grid must me placed in valid shape.
 */
public interface ShapedRecipe extends Recipe
{
    /**
     * Returns width of recipe.
     *
     * @return width of recipe.
     */
    default int getWidth()
    {
        return this.getPattern().getColumns();
    }

    /**
     * Returns height of recipe.
     *
     * @return height of recipe.
     */
    default int getHeight()
    {
        return this.getPattern().getRows();
    }

    @Override
    default boolean isValid(final GridInventory inventory)
    {
        final RecipePattern pattern = this.getPattern();
        int startPatRow = - 1;
        int startPatCol = - 1;
        int startInvRow = - 1;
        int startInvCol = - 1;
        findPatternStart:
        // we need find first pattern element that isn't null
        for (int row = 0, maxRow = pattern.getRows(); row < maxRow; row++)
        {
            for (int col = 0, maxCol = pattern.getColumns(); col < maxCol; col++)
            {
                if (pattern.getRecipeItem(row, col) != null)
                {
                    startPatRow = row;
                    startPatCol = col;
                    break findPatternStart;
                }
            }
        }
        if ((startPatRow == - 1) || (startPatCol == - 1))
        {
            return false;
        }
        findCraftingStart:
        // we need find first inventory item that isn't null
        for (int row = 0, maxRow = inventory.getRows(); row < maxRow; row++)
        {
            for (int col = 0, maxCol = inventory.getColumns(); col < maxCol; col++)
            {
                if (((maxRow - row) < pattern.getRows()) || ((maxCol - col) < pattern.getColumns())) // pattern will not fit into this inventory
                {
                    return false;
                }
                if (inventory.getItem(row, col) != null)
                {
                    startInvRow = row;
                    startInvCol = col;
                    break findCraftingStart;
                }
            }
        }
        if ((startInvRow == - 1) || (startInvCol == - 1))
        {
            return false;
        }
        // pattern can fit into crafting table

        RecipeItem recipeItem = pattern.getRecipeItem(startPatRow, startPatCol);
        ItemStack invItem = inventory.getItem(startInvRow, startInvCol);
        if (! recipeItem.isValid(invItem)) // fast check first item
        {
            return false;
        }
        // scan whole pattern size field
        int invRow = startInvRow, invCol = startInvCol;
        for (int row = 0, patRow = startPatRow, maxRow = pattern.getRows(); row < maxRow; row++, patRow++, invRow++)
        {
            for (int col = 0, patCol = startPatCol, maxCol = pattern.getColumns(); col < maxCol; col++, patCol++, invCol++)
            {
                recipeItem = pattern.getRecipeItem(patRow, patCol);
                invItem = inventory.getItem(invRow, invCol);
                if (recipeItem == null)
                {
                    if (invItem != null)
                    {
                       return false;
                    }
                    continue;
                }
                if (!recipeItem.isValid(invItem))
                {
                    return false;
                }
            }
        }
        // check if rest of inventory is free
        for (final int maxRow = inventory.getRows(); invRow < maxRow; invRow++)
        {
            for (final int maxCol = inventory.getColumns(); invCol < maxCol; invCol++)
            {
                invItem = inventory.getItem(invRow, invCol);
                if (invItem != null)
                {
                    return false;
                }
            }
        }
        return true; // YeY
    }

    /**
     * Returns pattern for this recipe.
     *
     * @return pattern for this recipe.
     */
    RecipePattern getPattern();
}
