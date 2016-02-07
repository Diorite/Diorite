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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.CraftingRecipe;
import org.diorite.inventory.recipe.craft.CraftingRecipeCheckResult;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

/**
 * Represent results after checking if recipe is valid for given inventory.
 *
 * @see CraftingRecipe#isMatching(GridInventory)
 */
public class CraftingRecipeCheckResultImpl implements CraftingRecipeCheckResult
{
    private final CraftingRecipe             recipe;
    private final ItemStack                  result;
    private final CraftingGrid               itemsToConsume;
    private final Short2ObjectMap<ItemStack> onCraft;

    /**
     * Construct new CraftResult for given recipe and items.
     *
     * @param recipe         matched recipe.
     * @param result         result item stack.
     * @param itemsToConsume array of items that should be removed from inventory on craft.
     * @param onCraft        map of items that should be changed in inventory
     */
    public CraftingRecipeCheckResultImpl(final CraftingRecipe recipe, final ItemStack result, final CraftingGrid itemsToConsume, final Short2ObjectMap<ItemStack> onCraft)
    {
        this.recipe = recipe;
        this.result = result;
        this.itemsToConsume = itemsToConsume;
        this.onCraft = onCraft;
    }

    /**
     * Construct new CraftResult for given recipe and items.
     *
     * @param recipe         matched recipe.
     * @param result         result item stack.
     * @param itemsToConsume array of items that should be removed from inventory on craft.
     */
    public CraftingRecipeCheckResultImpl(final CraftingRecipe recipe, final ItemStack result, final CraftingGrid itemsToConsume)
    {
        this.recipe = recipe;
        this.result = result;
        this.itemsToConsume = itemsToConsume;
        this.onCraft = new Short2ObjectOpenHashMap<>(2, .5F);
    }

    @Override
    public CraftingRecipe getRecipe()
    {
        return this.recipe;
    }

    @Override
    public ItemStack getResult()
    {
        return this.result;
    }

    @Override
    public CraftingGrid getItemsToConsume()
    {
        return this.itemsToConsume;
    }

    @Override
    public Short2ObjectMap<ItemStack> getOnCraft()
    {
        return this.onCraft;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof CraftingRecipeCheckResultImpl))
        {
            return false;
        }

        final CraftingRecipeCheckResultImpl that = (CraftingRecipeCheckResultImpl) o;

        return this.recipe.equals(that.recipe) && this.result.equals(that.result) && this.itemsToConsume.equals(that.itemsToConsume) && ! ((this.onCraft != null) ? ! this.onCraft.equals(that.onCraft) : (that.onCraft != null));

    }

    @Override
    public int hashCode()
    {
        int result1 = this.recipe.hashCode();
        result1 = (31 * result1) + this.result.hashCode();
        result1 = (31 * result1) + this.itemsToConsume.hashCode();
        result1 = (31 * result1) + ((this.onCraft != null) ? this.onCraft.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("recipe", this.recipe).append("result", this.result).append("itemsToConsume", this.itemsToConsume).append("onCraft", this.onCraft).toString();
    }
}
