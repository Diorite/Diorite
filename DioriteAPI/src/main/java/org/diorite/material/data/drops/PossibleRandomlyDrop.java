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

package org.diorite.material.data.drops;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Entity;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.utils.math.IntRange;
import org.diorite.world.Block;

public class PossibleRandomlyDrop extends PossibleDrop
{
    protected final IntRange amount;
    protected final boolean  dropAsOne;

    public PossibleRandomlyDrop(final ItemStack itemStack, final IntRange amount, final boolean dropAsOne)
    {
        super(itemStack);
        this.amount = amount;
        this.dropAsOne = dropAsOne;
    }

    public PossibleRandomlyDrop(final ItemStack itemStack, final int min, final int max, final boolean dropAsOne)
    {
        super(itemStack);
        this.dropAsOne = dropAsOne;
        this.amount = new IntRange(min, max);
    }

    public PossibleRandomlyDrop(final ItemStack itemStack, final IntRange amount)
    {
        super(itemStack);
        this.amount = amount;
        this.dropAsOne = true;
    }

    public PossibleRandomlyDrop(final ItemStack itemStack, final int min, final int max)
    {
        super(itemStack);
        this.dropAsOne = true;
        this.amount = new IntRange(min, max);
    }

    @Override
    public void simulateDrop(final Entity entity, final DioriteRandom rand, final Set<ItemStack> drops, final ItemStack usedTool, final Block block)
    {
        final int amount = this.amount.getRandom();
        if (amount == 0)
        {
            return;
        }
        final ItemStack item = this.getItemStack();
        if ((this.itemStack.getMaterial().getId() == 0) || (this.itemStack.getAmount() == 0))
        {
            return;
        }
        if (this.dropAsOne)
        {
            item.setAmount(item.getAmount() * amount);
            drops.add(item);
            return;
        }
        if (amount == 1)
        {
            drops.add(item);
            return;
        }
        for (int i = 0; i < amount; i++)
        {
            drops.add(item.clone());
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("amount", this.amount).toString();
    }
}
