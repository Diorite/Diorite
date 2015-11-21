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

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeItem;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;
import org.diorite.inventory.recipe.craft.ShapelessRecipe;
import org.diorite.inventory.slot.SlotType;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Implementation of shapeless recipe
 */
public class ShapelessSingleRecipeImpl extends RecipeImpl implements ShapelessRecipe
{
    protected final RecipeItem ingredient;

    private final transient List<RecipeItem> ingredientList;

    public ShapelessSingleRecipeImpl(final RecipeItem ingredient, final ItemStack result, final long priority, final boolean vanilla, final BiFunction<Player, ItemStack[], ItemStack> resultFunc)
    {
        super(extractResults(result, ingredient), priority, vanilla, resultFunc);
        this.ingredient = ingredient;
        this.ingredientList = Collections.singletonList(ingredient);
    }

    @Override
    public RecipeCheckResult isMatching(final GridInventory inventory)
    {
        final Player player = (inventory.getHolder() instanceof Player) ? (Player) inventory.getHolder() : null;
        final TShortObjectMap<ItemStack> onCraft = new TShortObjectHashMap<>(2, .5F, Short.MIN_VALUE);

        boolean matching = false;
        final ItemStack[] result = new ItemStack[1];
        for (short i = 0, size = (short) inventory.size(); i < size; i++)
        {
            if (inventory.getSlot(i).getSlotType().equals(SlotType.RESULT))
            {
                continue;
            }
            final ItemStack item = inventory.getItem(i);
            if (item == null)
            {
                continue;
            }
            final ItemStack valid = this.ingredient.isValid(player, item);
            if (valid != null)
            {
                if (matching)
                {
                    return null;
                }
                matching = true;
                result[0] = valid;
                final ItemStack repl = this.ingredient.getReplacement();
                if (repl != null)
                {
                    onCraft.put(i, repl);
                }
                continue;
            }
            return null;
        }
        return matching ? new RecipeCheckResultImpl(this, this.resultFunc.apply(player, result), result, onCraft) : null;
    }

    @Override
    public List<RecipeItem> getIngredients()
    {
        return this.ingredientList;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ingredient", this.ingredient).toString();
    }
}
