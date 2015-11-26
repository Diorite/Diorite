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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeItem;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;
import org.diorite.inventory.recipe.craft.ShapelessRecipe;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Implementation of shapeless recipe
 */
public class ShapelessRecipeImpl extends RecipeImpl implements ShapelessRecipe
{
    protected final List<RecipeItem> ingredients;

    public ShapelessRecipeImpl(final List<RecipeItem> ingredients, final ItemStack result, final long priority, final boolean vanilla, final BiFunction<Player, CraftingGrid, ItemStack> resultFunc)
    {
        super(extractResults(result, ingredients), priority, vanilla, resultFunc);
        this.ingredients = new ArrayList<>(ingredients);
    }

    public ShapelessRecipeImpl(final List<RecipeItem> ingredients, final ItemStack result, final long priority, final boolean vanilla)
    {
        super(extractResults(result, ingredients), priority, vanilla);
        this.ingredients = new ArrayList<>(ingredients);
    }

    @Override
    public RecipeCheckResult isMatching(final GridInventory inventory)
    {
        final Player player = (inventory.getHolder() instanceof Player) ? (Player) inventory.getHolder() : null;
        final TShortObjectMap<ItemStack> onCraft = new TShortObjectHashMap<>(2, .5F, Short.MIN_VALUE);

        final int maxInvRow = inventory.getRows(), maxInvCol = inventory.getColumns();
        final LinkedList<RecipeItem> ingredients = new LinkedList<>(this.getIngredients());
        final CraftingGrid items = new CraftingGridImpl(maxInvRow, maxInvCol);
        int col = - 1, row = 0;
        for (short i = 1, size = (short) inventory.size(); i < size; i++)
        {
            if (++ col >= maxInvCol)
            {
                col = 0;
                if (++ row > maxInvRow)
                {
                    throw new IllegalStateException("Inventory is larger than excepted.");
                }
            }
            final ItemStack item = inventory.getItem(i);
            if (item == null)
            {
                continue;
            }
            boolean matching = false;
            for (final Iterator<RecipeItem> iterator = ingredients.iterator(); iterator.hasNext(); )
            {
                final RecipeItem ingredient = iterator.next();
                final ItemStack valid = ingredient.isValid(player, item);
                if (valid != null)
                {
                    final ItemStack repl = ingredient.getReplacement();
                    if (repl != null)
                    {
                        onCraft.put(i, repl);
                    }
                    items.setItem(row, col, valid);
                    iterator.remove();
                    matching = true;
                    break;
                }
            }
            if (! matching)
            {
                return null;
            }
        }
        return ingredients.isEmpty() ? new RecipeCheckResultImpl(this, (this.resultFunc == null) ? this.result : this.resultFunc.apply(player, items.clone()), items, onCraft) : null;
    }

    @Override
    public List<RecipeItem> getIngredients()
    {
        return new ArrayList<>(this.ingredients);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ingredients", this.ingredients).toString();
    }
}
