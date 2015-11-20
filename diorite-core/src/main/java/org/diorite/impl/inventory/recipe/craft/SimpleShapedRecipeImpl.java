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

package org.diorite.impl.inventory.recipe.craft;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeItem;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;
import org.diorite.inventory.recipe.craft.RecipePattern;
import org.diorite.inventory.recipe.craft.ShapedRecipe;

/**
 * Implementation of shaped recipe.
 */
public class SimpleShapedRecipeImpl extends SimpleRecipeImpl implements ShapedRecipe
{
    protected final RecipePattern pattern;

    public SimpleShapedRecipeImpl(final RecipePattern pattern, final ItemStack result, final long priority)
    {
        super(result, priority);
        this.pattern = pattern;
    }

    @Override
    public RecipeCheckResult isMatching(final GridInventory inventory)
    {
        final RecipePattern pattern = this.pattern;
        final ItemStack[] items = new ItemStack[pattern.getColumns() * pattern.getRows()];

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
            return null;
        }
        findCraftingStart:
        // we need find first inventory item that isn't null
        for (int row = 0, maxRow = inventory.getRows(); row < maxRow; row++)
        {
            for (int col = 0, maxCol = inventory.getColumns(); col < maxCol; col++)
            {
                if (((maxRow - row) < pattern.getRows()) || ((maxCol - col) < pattern.getColumns())) // pattern will not fit into this inventory
                {
                    return null;
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
            return null;
        }
        // pattern can fit into crafting table

        RecipeItem recipeItem = pattern.getRecipeItem(startPatRow, startPatCol);
        ItemStack invItem = inventory.getItem(startInvRow, startInvCol);
        ItemStack valid = recipeItem.isValid(invItem);
        if (valid == null) // fast check first item
        {
            return null;
        }
        items[0] = valid;
        // scan whole pattern size field
        int invRow = startInvRow, invCol = startInvCol;
        for (int row = 0, patRow = startPatRow, maxRow = pattern.getRows(); row < maxRow; row++, patRow++, invRow++)
        {
            invCol = startInvCol;
            for (int col = 0, patCol = startPatCol, maxCol = pattern.getColumns(); col < maxCol; col++, patCol++, invCol++)
            {
                recipeItem = pattern.getRecipeItem(patRow, patCol);
                invItem = inventory.getItem(invRow, invCol);
                if (recipeItem == null)
                {
                    if (invItem != null)
                    {
                        return null;
                    }
                    continue;
                }
                valid = recipeItem.isValid(invItem);
                if (valid == null)
                {
                    return null;
                }
                items[col + (row * pattern.getColumns())] = valid;
            }
        }
        invRow--;
        // check if rest of inventory is free
        for (final int maxRow = inventory.getRows(); invRow < maxRow; invRow++)
        {
            for (final int maxCol = inventory.getColumns(); invCol < maxCol; invCol++)
            {
                invItem = inventory.getItem(invRow, invCol);
                if (invItem != null)
                {
                    return null;
                }
            }
            invCol = 0;
        }
        return new RecipeCheckResultImpl(this, this.result, items);
    }

    @Override
    public RecipePattern getPattern()
    {
        return this.pattern;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }
}
