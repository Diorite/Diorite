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
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.AnyCraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingRecipeItemBuilder;
import org.diorite.inventory.recipe.craft.MultiCraftingRecipeItem;
import org.diorite.inventory.recipe.craft.SimpleCraftingRecipeItem;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;

@SuppressWarnings("unchecked")
public abstract class BaseCraftingRecipeItemBuilderImpl<T extends CraftingRecipeBuilder, B extends CraftingRecipeItemBuilder<T, B>> implements CraftingRecipeItemBuilder<T, B>
{
    protected final T                                           builder;
    protected       boolean                                     any;
    protected       ItemStack                                   item;
    protected       boolean                                     ignoreType;
    protected       ItemStack                                   replacement;
    protected       BiFunction<Player, CraftingGrid, ItemStack> replacmentFunc;
    protected final Collection<BiPredicate<Player, ItemStack>> validators = new ArrayList<>(4);
    protected       Object2BooleanMap<ItemStack>               otherItems = new Object2BooleanOpenHashMap<>(4);

    protected BaseCraftingRecipeItemBuilderImpl(final T builder)
    {
        this.builder = builder;
    }

    @Override
    public B replacement(final ItemStack replacement)
    {
        this.replacement = replacement;
        return (B) this;
    }

    @Override
    public B replacement(final ItemStack basicReplacement, final BiFunction<Player, CraftingGrid, ItemStack> replacement)
    {
        this.replacement = basicReplacement;
        this.replacmentFunc = replacement;
        return (B) this;
    }

    @Override
    public B item(final ItemStack pattern, final boolean ignoreType)
    {
        this.otherItems.put(pattern, ignoreType);
        if (this.item == null)
        {
            this.item = pattern;
            this.ignoreType = ignoreType;
        }
        return (B) this;
    }

    @Override
    public B any()
    {
        this.any = true;
        return (B) this;
    }

    @Override
    public B validator(final BiPredicate<Player, ItemStack> validator)
    {
        this.validators.add(validator);
        return (B) this;
    }

    protected CraftingRecipeItem createItem()
    {
        if (this.any)
        {
            return new AnyCraftingRecipeItem(this.item, this.replacmentFunc, this.replacement, this.validators);
        }
        if (this.otherItems.size() == 1)
        {
            return new SimpleCraftingRecipeItem(this.item, this.ignoreType, this.replacmentFunc, this.replacement, this.validators);
        }
        return new MultiCraftingRecipeItem(this.item, this.otherItems, this.replacmentFunc, this.replacement, this.validators);
    }

    protected abstract void addItem(CraftingRecipeItem item);

    @Override
    public T build()
    {
        if (((this.item == null) && ! this.any) && this.validators.isEmpty())
        {
            throw new RuntimeException("Can't create recipe item without any pattern data.");
        }
        this.addItem(this.createItem());
        return this.builder;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("builder", this.builder).toString();
    }
}
