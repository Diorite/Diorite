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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.recipe.RecipeItem;
import org.diorite.inventory.recipe.craft.RecipePattern;

/**
 * {@link RecipePattern} that use array of {@link org.diorite.inventory.recipe.RecipeItem} as implementation.
 */
public class ArrayRecipePattern implements RecipePattern
{
    protected final RecipeItem[][] items;

    public ArrayRecipePattern(final RecipeItem[][] items)
    {
        Validate.notNull(items, "Recipe pattern can't be null.");
        Validate.notEmpty(items, "Recipe pattern can't be empty.");
        Validate.noNullElements(items, "Recipe pattern elements can't be null.");
        this.items = items;
    }

    @Override
    public int getColumns()
    {
        return this.items[0].length;
    }

    @Override
    public int getRows()
    {
        return this.items.length;
    }

    @Override
    public RecipeItem getRecipeItem(final int row, final int column)
    {
        if ((row >= this.getRows()) || (column >= this.getColumns()))
        {
            return null;
        }
        return this.items[row][column];
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("items", this.items).toString();
    }
}
