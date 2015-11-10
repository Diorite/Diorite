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

package org.diorite.impl.inventory.item.meta;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.RepairableMeta;
import org.diorite.nbt.NbtTagCompound;

public class RepairableMetaImpl extends SimpleItemMetaImpl implements RepairableMeta
{
    protected static final String UNBREAKABLE = "Unbreakable";
    protected static final String REPAIR_COST = "RepairCost";

    public RepairableMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public RepairableMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public boolean hasRepairCost()
    {
        return (this.tag != null) && this.tag.containsTag(REPAIR_COST);
    }

    @Override
    public int getRepairCost()
    {
        if (this.tag == null)
        {
            return 0;
        }
        return this.tag.getInt(REPAIR_COST);
    }

    @Override
    public void setRepairCost(final int cost)
    {
        this.checkTag(true);
        this.tag.setInt(REPAIR_COST, cost);
        this.setDirty();
    }

    @Override
    public void setUnbreakable(final boolean unbreakable)
    {
        this.checkTag(true);
        this.tag.setBoolean(UNBREAKABLE, unbreakable);
        this.setDirty();
    }

    @Override
    public boolean isUnbreakable()
    {
        return (this.tag != null) && this.tag.getBoolean(UNBREAKABLE);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public ToolMetaImpl clone()
    {
        return new ToolMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
