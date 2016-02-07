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

import java.util.Collections;
import java.util.List;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingRecipe;
import org.diorite.inventory.recipe.craft.CraftingRecipeCheckResult;
import org.diorite.material.Material;

public class FireworkChargeCraftingRecipe implements CraftingRecipe
{
    protected final long priority;
    protected final List<ItemStack> result = Collections.singletonList(new BaseItemStack(Material.FIREWORK_CHARGE));

    public FireworkChargeCraftingRecipe(final long priority)
    {
        this.priority = priority;
    }

    @Override
    public CraftingRecipeCheckResult isMatching(final GridInventory inventory)
    {
        return null;
    }

    @Override
    public List<ItemStack> getResult()
    {
        return this.result;
    }

    @Override
    public long getPriority()
    {
        return this.priority;
    }


    @Override
    public boolean isVanilla()
    {
        return true;
    }
}
