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

package org.diorite.impl.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.block.Block;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.FuelMat;
import org.diorite.material.items.SmeltableMat;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.tileentity.TileEntityFurnace;
import org.diorite.utils.math.DioriteRandom;

public class TileEntityFurnaceImpl extends TileEntityImpl implements TileEntityFurnace
{
    private static final int SMELT_DURATION = 200;
    private final Block block;
    private       short burnTime;
    private       short cookTime; //AS TICKS
    private ItemStack[] items = new ItemStack[InventoryType.FURNACE.getSize()];

    public TileEntityFurnaceImpl(final Block block)
    {
        super(block.getLocation());
        this.block = block;
    }

    @Override
    public void doTick(final int tps)
    {
        //TODO check if logic is correct
        if (this.burnTime > 0)
        {
            this.burnTime--;
        }
        else if (! isFuel(this.items[1]))
        {
            return;
        }

        if ((this.items[0] == null) || this.items[0].isAir() || (this.items[0].getAmount() < 1))
        {
            return;
        }

        if (! (this.items[0].getMaterial() instanceof SmeltableMat))
        {
            return;
        }

        if (this.items[2] != null)
        {
            if (! this.items[0].isSimilar(this.items[2]))
            {
                return;
            }

            if (this.items[2].getAmount() >= this.items[2].getMaterial().getMaxStack())
            {
                return;
            }
        }
        else
        {
            this.items[2] = new BaseItemStack(((SmeltableMat) this.items[0].getMaterial()).getSmeltResult(), 0);
        }

        if (this.burnTime < 1)
        {
            this.items[2].setAmount(this.items[2].getAmount() - 1);
            this.burnTime = (short) getFuelTime(this.items[2]);
        }

        if (this.cookTime++ >= SMELT_DURATION)
        {
            this.items[2].setAmount(this.items[2].getAmount() + 1);
            this.items[0].setAmount(this.items[0].getAmount() - 1);
            this.cookTime = 0;
        }
    }

    @Override
    public Block getBlock()
    {
        return this.block;
    }

    @Override
    public void simulateDrop(final DioriteRandom rand, final Set<ItemStack> drops)
    {
        //TODO
    }

    @Override
    public short getBurnTime()
    {
        return this.burnTime;
    }

    @Override
    public void setBurnTime(final short burnTime)
    {
        this.burnTime = burnTime;
    }

    @Override
    public short getCookTime()
    {
        return this.cookTime;
    }

    @Override
    public void setCookTime(final short cookTime)
    {
        this.cookTime = cookTime;
    }

    public static int getFuelTime(final ItemStack fuel)
    {
        if (fuel == null)
        {
            return 0;
        }

        if (! (fuel.getMaterial() instanceof FuelMat))
        {
            return 0;
        }

        final FuelMat mat = (FuelMat) fuel.getMaterial();
        return mat.getFuelPower();
    }

    public static boolean isFuel(final ItemStack fuel)
    {
        return getFuelTime(fuel) > 0;
    }

    @Override
    public void loadFromNbt(final NbtTagCompound nbtTileEntity)
    {
        super.loadFromNbt(nbtTileEntity);

        this.burnTime = nbtTileEntity.getShort("BurnTime");
        this.cookTime = nbtTileEntity.getShort("CookTime");
        //TODO
        //this.items = nbtTileEntity.getList("Items", );
    }

    @Override
    public void saveToNbt(final NbtTagCompound nbtTileEntity)
    {
        super.saveToNbt(nbtTileEntity);

        nbtTileEntity.setShort("BurnTime", this.burnTime);
        nbtTileEntity.setShort("CookTime", this.cookTime);
        final List<NbtTag> nbtItems = new ArrayList<>(3);
        for (int i = 0; i < this.items.length; i++)
        {
            nbtItems.set(i, this.items[i].serializeToNBT());
        }
        nbtTileEntity.setList("Items", nbtItems);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).append("burnTime", this.burnTime).append("cookTime", this.cookTime).append("items", this.items).toString();
    }
}
