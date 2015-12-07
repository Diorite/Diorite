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
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RepeatableRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.CraftingRecipeCheckResult;
import org.diorite.inventory.recipe.craft.CraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingRepeatableRecipeItem;
import org.diorite.inventory.recipe.craft.ShapelessCraftingRecipe;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

/**
 * Implementation of shapeless recipe
 */
public class ShapelessSingleCraftingRecipeImpl extends CraftingRecipeImpl implements ShapelessCraftingRecipe
{
    protected final CraftingRecipeItem ingredient;
    protected final boolean            isRepeated;

    private final transient List<CraftingRecipeItem>           ingredientList;
    private final transient List<CraftingRepeatableRecipeItem> repeatableIngredientList;

    public ShapelessSingleCraftingRecipeImpl(final CraftingRecipeItem ingredient, final ItemStack result, final long priority, final boolean vanilla, final BiFunction<Player, CraftingGrid, ItemStack> resultFunc)
    {
        super(extractResults(result, ingredient), priority, vanilla, resultFunc);
        this.ingredient = ingredient;
        this.isRepeated = (ingredient instanceof CraftingRepeatableRecipeItem);
        if (this.isRepeated)
        {
            this.ingredientList = Collections.emptyList();
            this.repeatableIngredientList = Collections.singletonList((CraftingRepeatableRecipeItem) ingredient);
        }
        else
        {
            this.ingredientList = Collections.singletonList(ingredient);
            this.repeatableIngredientList = Collections.emptyList();
        }
    }

    @Override
    public CraftingRecipeCheckResult isMatching(final GridInventory inventory)
    {
        final Player player = (inventory.getHolder() instanceof Player) ? (Player) inventory.getHolder() : null;
        final Short2ObjectMap<ItemStack> onCraft = new Short2ObjectOpenHashMap<>(2, .5F);

        boolean matching = false;
        final int maxInvRow = inventory.getRows(), maxInvCol = inventory.getColumns();
        final CraftingGrid items = new CraftingGridImpl(maxInvRow, maxInvCol);
        int col = - 1, row = 0;
        ItemStack result;
        if (this.isRepeated)
        {
            final List<ItemStack> repeatedIngredients = new ArrayList<>(10);
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
                final ItemStack valid = this.ingredient.isValid(player, item);
                if (valid != null)
                {
                    matching = true;
                    items.setItem(row, col, valid);
                    repeatedIngredients.add(valid);
                    final ItemStack repl = this.ingredient.getReplacement(player, items);
                    if (repl != null)
                    {
                        onCraft.put(i, repl);
                    }
                    continue;
                }
                return null;
            }
            result = (this.resultFunc == null) ? this.result : this.resultFunc.apply(player, items.clone());
            result = ((RepeatableRecipeItem) this.ingredient).transform(result, repeatedIngredients);
            result = ((CraftingRepeatableRecipeItem) this.ingredient).transform(result, items);
        }
        else
        {
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
                if (matching)
                {
                    return null;
                }
                final ItemStack valid = this.ingredient.isValid(player, item);
                if (valid != null)
                {
                    matching = true;
                    items.setItem(row, col, valid);
                    final ItemStack repl = this.ingredient.getReplacement(player, items);
                    if (repl != null)
                    {
                        onCraft.put(i, repl);
                    }
                    continue;
                }
                return null;
            }
            result = (this.resultFunc == null) ? this.result : this.resultFunc.apply(player, items.clone());
        }
        return matching ? new CraftingRecipeCheckResultImpl(this, result, items, onCraft) : null;
    }

    @Override
    public List<? extends CraftingRecipeItem> getIngredients()
    {
        return this.ingredientList;
    }

    @Override
    public List<? extends CraftingRepeatableRecipeItem> getRepeatableIngredients()
    {
        return this.repeatableIngredientList;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ingredient", this.ingredient).toString();
    }
}
