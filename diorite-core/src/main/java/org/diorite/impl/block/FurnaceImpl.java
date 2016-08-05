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

package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.block.FurnaceInventoryImpl;
import org.diorite.impl.tileentity.TileEntityFurnaceImpl;
import org.diorite.block.Block;
import org.diorite.block.Furnace;
import org.diorite.inventory.block.FurnaceInventory;

public class FurnaceImpl extends BlockStateImpl implements Furnace
{
    private final TileEntityFurnaceImpl tileEntity;
    private final FurnaceInventory      inventory;

    public FurnaceImpl(final Block block)
    {
        super(block);

        this.tileEntity = (TileEntityFurnaceImpl) block.getWorld().getTileEntity(block.getLocation());
        this.inventory = new FurnaceInventoryImpl(this);
    }

    @Override
    public boolean update(final boolean force, final boolean applyPhysics)
    {
        final boolean result = super.update(force, applyPhysics);

        if (result)
        {
            //TODO update tentity
        }

        return result;
    }

    @Override
    public short getBurnTime()
    {
        return this.tileEntity.getBurnTime();
    }

    @Override
    public void setBurnTime(final short burnTime)
    {
        this.tileEntity.setBurnTime(burnTime);
    }

    @Override
    public short getCookTime()
    {
        return this.tileEntity.getCookTime();
    }

    @Override
    public void setCookTime(final short cookTime)
    {
        this.tileEntity.setCookTime(cookTime);
    }

    @Override
    public FurnaceInventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tileEntity", this.tileEntity).append("inventory", this.inventory).toString();
    }
}
