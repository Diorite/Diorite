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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.recipe.craft.CraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CharMapCraftingRecipePattern;
import org.diorite.inventory.recipe.craft.CraftingRecipePattern;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMaps;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;

/**
 * {@link CraftingRecipePattern} that use array of {@link CraftingRecipeItem} as implementation.
 */
public class CharMapCraftingRecipePatternImpl implements CharMapCraftingRecipePattern
{
    protected final Char2ObjectMap<CraftingRecipeItem> items;
    protected final String[]                           pattern;

    public CharMapCraftingRecipePatternImpl(final Char2ObjectMap<CraftingRecipeItem> items, final String[] pattern)
    {
        Validate.notNull(pattern, "Recipe pattern can't be null.");
        Validate.notEmpty(pattern, "Recipe pattern can't be empty.");
        Validate.noNullElements(pattern, "Recipe pattern elements can't be null.");
        Validate.notNull(items, "Recipe pattern items can't be null.");
        this.items = Char2ObjectMaps.unmodifiable(new Char2ObjectOpenHashMap<>(items));
        this.pattern = pattern.clone();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }

    @Override
    public String[] getPattern()
    {
        return this.pattern.clone();
    }

    @Override
    public Char2ObjectMap<CraftingRecipeItem> getIngredients()
    {
        return this.items;
    }

    @Override
    public CraftingRecipeItem getIngredient(final char c)
    {
        return this.items.get(c);
    }

    @Override
    public int getColumns()
    {
        return this.pattern[0].length();
    }

    @Override
    public int getRows()
    {
        return this.pattern.length;
    }

    @Override
    public CraftingRecipeItem getRecipeItem(final int row, final int column)
    {
        if ((row >= this.getRows()) || (column >= this.getColumns()))
        {
            return null;
        }
        return this.getIngredient(this.pattern[row].charAt(column));
    }

    private transient List<CraftingRecipeItem> itemsList;

    @Override
    public synchronized Collection<CraftingRecipeItem> getRecipeItems()
    {
        if (this.itemsList == null)
        {
            this.itemsList = Collections.unmodifiableList(this.items.values().stream().filter(item -> item != null).collect(Collectors.toList()));
        }
        return this.itemsList;
    }
}
