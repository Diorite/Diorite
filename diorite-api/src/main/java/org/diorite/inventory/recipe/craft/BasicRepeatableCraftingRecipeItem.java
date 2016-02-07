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

import java.util.List;
import java.util.function.BiFunction;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RepeatableRecipeItem;

/**
 * Basic implementation of {@link RepeatableRecipeItem}
 */
public class BasicRepeatableCraftingRecipeItem implements CraftingRepeatableRecipeItem
{
    /**
     * Wrapped recipe item.
     */
    protected final CraftingRecipeItem                                item;
    /**
     * Transform result func.
     *
     * @see #transform(ItemStack, List)
     */
    protected final BiFunction<ItemStack, List<ItemStack>, ItemStack> transformFunc;
    /**
     * Transform result func.
     *
     * @see #transform(ItemStack, List)
     */
    protected final BiFunction<ItemStack, CraftingGrid, ItemStack>    transformAdvFunc;

    /**
     * Construct new BasicRepeatableRecipeItem for given recipe item and transform function.
     *
     * @param item             recipe item to wrap.
     * @param transformFunc    transform result item function.
     * @param transformAdvFunc transform result item function.
     */
    public BasicRepeatableCraftingRecipeItem(final CraftingRecipeItem item, final BiFunction<ItemStack, List<ItemStack>, ItemStack> transformFunc, final BiFunction<ItemStack, CraftingGrid, ItemStack> transformAdvFunc)
    {
        this.item = item;
        this.transformFunc = transformFunc;
        this.transformAdvFunc = transformAdvFunc;
    }

    @Override
    public ItemStack transform(final ItemStack currentResult, final List<ItemStack> ingredients)
    {
        return (this.transformFunc == null) ? currentResult : this.transformFunc.apply(currentResult, ingredients);
    }

    @Override
    public ItemStack transform(final ItemStack currentResult, final CraftingGrid ingredients)
    {
        return (this.transformAdvFunc == null) ? currentResult : this.transformAdvFunc.apply(currentResult, ingredients.clone());
    }

    @Override
    public ItemStack isValid(final Player player, final ItemStack item)
    {
        return this.item.isValid(player, item);
    }

    @Override
    public BiFunction<Player, ItemStack, ItemStack> asFunction()
    {
        return this.item.asFunction();
    }

    @Override
    public ItemStack getReplacement()
    {
        return this.item.getReplacement();
    }

    @Override
    public ItemStack getItem()
    {
        return this.item.getItem();
    }

    @Override
    public ItemStack getReplacement(final Player player, final CraftingGrid craftingGrid)
    {
        return this.item.getReplacement(player, craftingGrid);
    }
}
