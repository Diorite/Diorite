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

package org.diorite.impl.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.CraftingRecipeCheckResult;
import org.diorite.inventory.recipe.craft.CraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingRecipePattern;
import org.diorite.inventory.recipe.craft.ShapedCraftingRecipe;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

/**
 * Implementation of shaped recipe.
 */
public class ShapedCraftingRecipeImpl extends CraftingRecipeImpl implements ShapedCraftingRecipe
{
    protected final CraftingRecipePattern pattern;

    public ShapedCraftingRecipeImpl(final CraftingRecipePattern pattern, final ItemStack result, final long priority, final boolean vanilla, final BiFunction<Player, CraftingGrid, ItemStack> resultFunc)
    {
        super(extractResults(result, pattern.getRecipeItems()), priority, vanilla, resultFunc);
        this.pattern = pattern;
    }

    @Override
    public CraftingRecipeCheckResult isMatching(final GridInventory inventory)
    {
        final CraftingRecipePattern pattern = this.pattern;
        final int patRows = pattern.getRows(), patCols = pattern.getColumns();
        final int invRows = inventory.getRows(), invCols = inventory.getColumns();
        if ((patRows > invRows) || (patCols > invCols)) // too big pattern for thix eq
        {
            return null;
        }
        final Player player = (inventory.getHolder() instanceof Player) ? (Player) inventory.getHolder() : null;
        final int deltaRows = invRows - patRows, deltaCols = invCols - patCols;
        final Short2ObjectMap<ItemStack> onCraft = new Short2ObjectOpenHashMap<>(2, .5F);
        final CraftingGrid items = new CraftingGridImpl(invRows, invCols);
        final Collection<BiConsumer<Player, CraftingGrid>> reps = new ArrayList<>(invCols * invRows);

        int firstPatRow = - 1;
        int firstPatCol = - 1;
        int firstInvRow = - 1;
        int firstInvCol = - 1;
        if ((patRows == invRows) && (patCols == invCols))
        {
            for (int row = 0; row < patRows; row++)
            {
                for (int col = 0; col < patCols; col++)
                {
                    final CraftingRecipeItem recipeItem = pattern.getRecipeItem(row, col);
                    final ItemStack invItem = inventory.getItem(row, col);
                    if (recipeItem == null)
                    {
                        if (invItem != null)
                        {
                            return null;
                        }
                        continue;
                    }
                    final ItemStack valid = recipeItem.isValid(player, invItem);
                    if (valid == null) // fast check first item
                    {
                        return null;
                    }
                    items.setItem(row, col, valid);

                    reps.add((p, c) -> {
                        final ItemStack repl = recipeItem.getReplacement(p, c);
                        if (repl != null)
                        {
                            onCraft.put((short) 0, repl);
                        }
                    });
                }
            }
            reps.forEach(c -> c.accept(player, items));
            return new CraftingRecipeCheckResultImpl(this, (this.resultFunc == null) ? this.result : this.resultFunc.apply(player, items.clone()), items, onCraft);
        } // inventory have more columns, so some columns on right or left should be empty

        // we need find first pattern element that isn't null
        patLoop:
        for (int row = 0; row < patRows; row++)
        {
            for (int col = 0; col < patCols; col++)
            {
                if (pattern.getRecipeItem(row, col) != null)
                {
                    firstPatRow = row;
                    firstPatCol = col;
                    break patLoop;
                }
            }
        }
        // and inventory item
        invLoop:
        for (int row = 0; row < invRows; row++)
        {
            for (int col = 0; col < invCols; col++)
            {
                if (inventory.getItem(row, col) != null)
                {
                    firstInvRow = row;
                    firstInvCol = col;
                    break invLoop;
                }
            }
        }
        if ((firstPatCol == - 1) || (firstInvCol == - 1)) // no items in pattern or inventory
        {
            return null;
        }

        final int deltaCol = firstInvCol - firstPatCol;
        final int deltaRow = firstInvRow - firstPatRow;

        if (((deltaCols - deltaCol) < 0) || ((deltaRows - deltaRow) < 0)) // too big pattern.
        {
            return null;
        }

        for (int patRow = 0; patRow < invRows; patRow++)
        {
            for (int patCol = 0; patCol < invCols; patCol++)
            {
                final int invRow = patRow + deltaRow;
                final int invCol = patCol + deltaCol;

                final CraftingRecipeItem recipeItem = pattern.getRecipeItem(patRow, patCol);
                final ItemStack invItem = inventory.getItem(invRow, invCol);
                if (recipeItem == null)
                {
                    if (invItem != null)
                    {
                        return null;
                    }
                    continue;
                }
                final ItemStack valid = recipeItem.isValid(player, invItem);
                if (valid == null)
                {
                    return null;
                }
                items.setItem(invRow, invCol, valid);

                reps.add((p, c) -> {
                    final ItemStack repl = recipeItem.getReplacement(p, c);
                    if (repl != null)
                    {
                        onCraft.put((short) 0, repl);
                    }
                });
            }
        }
        return new CraftingRecipeCheckResultImpl(this, (this.resultFunc == null) ? this.result : this.resultFunc.apply(player, items.clone()), items, onCraft);
    }

    @Override
    public CraftingRecipePattern getPattern()
    {
        return this.pattern;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }
}
