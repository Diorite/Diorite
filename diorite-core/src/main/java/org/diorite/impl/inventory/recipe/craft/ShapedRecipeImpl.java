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

import java.util.function.BiFunction;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeItem;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;
import org.diorite.inventory.recipe.craft.RecipePattern;
import org.diorite.inventory.recipe.craft.ShapedRecipe;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

/**
 * Implementation of shaped recipe.
 */
public class ShapedRecipeImpl extends RecipeImpl implements ShapedRecipe
{
    protected final RecipePattern pattern;

    public ShapedRecipeImpl(final RecipePattern pattern, final ItemStack result, final long priority, final boolean vanilla, final BiFunction<Player, CraftingGrid, ItemStack> resultFunc)
    {
        super(extractResults(result, pattern.getRecipeItems()), priority, vanilla, resultFunc);
        this.pattern = pattern;
    }

    public ShapedRecipeImpl(final RecipePattern pattern, final ItemStack result, final long priority, final boolean vanilla)
    {
        super(extractResults(result, pattern.getRecipeItems()), priority, vanilla);
        this.pattern = pattern;
    }

    @Override
    public RecipeCheckResult isMatching(final GridInventory inventory)
    {
        final Player player = (inventory.getHolder() instanceof Player) ? (Player) inventory.getHolder() : null;
        final Short2ObjectMap<ItemStack> onCraft = new Short2ObjectOpenHashMap<>(2, .5F);

        final RecipePattern pattern = this.pattern;
        final int maxPatRow = pattern.getRows(), maxPatCol = pattern.getColumns();
        final int maxInvRow = inventory.getRows(), maxInvCol = inventory.getColumns();
        final CraftingGrid items = new CraftingGridImpl(maxInvRow, maxInvCol);

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
        for (int row = 0; row < maxInvRow; row++)
        {
            for (int col = 0; col < maxInvCol; col++)
            {
                if ((maxInvRow - row) < maxPatRow) // pattern will not fit into this inventory
                {
                    return null;
                }
                if ((maxInvCol - col) < maxPatCol) // pattern will not fit into this line in this inventory
                {
                    if ((maxInvRow - row - 1) < maxPatRow) // pattern will not fit into this inventory
                    {
                        return null;
                    }
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
        ItemStack valid = recipeItem.isValid(player, invItem);
        if (valid == null) // fast check first item
        {
            return null;
        }
        items.setItem(startInvRow, startInvCol, valid);
        ItemStack repl = recipeItem.getReplacement();
        if (repl != null)
        {
            onCraft.put((short) 0, repl);
        }
        int invRow = startInvRow, invCol = startInvCol + 1;
        int patRow = startPatRow, patCol = startPatCol;
        if ((maxPatCol != 1) || (maxPatRow != 1))
        {
            // scan whole pattern size field
            boolean first = true;

            if (maxPatCol == 1)
            {
                for (int i = invCol; i < maxInvCol; i++)
                {
                    if (inventory.getItem(invRow, i) != null)
                    {
                        return null;
                    }
                }
                invCol = 0;
                invRow++;
                patRow++;
            }
            for (int row = 0; (patRow < maxPatRow) || (invRow < maxInvRow); row++, patRow++, invRow++)
            {
                if (startInvCol > 0)
                {
                    for (int i = 0; i < startInvCol; i++)
                    {
                        if (inventory.getItem(invRow, i) != null)
                        {
                            return null;
                        }
                    }
                }
                invCol = startInvCol;
                patCol = first ? patCol : startPatCol;
                for (int col = 0; (patCol < maxPatCol) || (invCol < maxInvRow); col++, patCol++, invCol++)
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
                    valid = recipeItem.isValid(player, invItem);
                    if (valid == null)
                    {
                        return null;
                    }
                    items.setItem(invRow, invCol, valid);
                    repl = recipeItem.getReplacement();
                    if (repl != null)
                    {
                        onCraft.put((short) inventory.getSlotIndex(row, col), repl);
                    }
                }
                first = false;
            }
            invRow--;
        }
        // check if rest of inventory is free
        for (; invRow < maxInvRow; invRow++)
        {
            for (; invCol < maxInvCol; invCol++)
            {
                invItem = inventory.getItem(invRow, invCol);
                if (invItem != null)
                {
                    return null;
                }
            }
            invCol = 0;
        }
        return new RecipeCheckResultImpl(this, (this.resultFunc == null) ? this.result : this.resultFunc.apply(player, items.clone()), items, onCraft);
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
