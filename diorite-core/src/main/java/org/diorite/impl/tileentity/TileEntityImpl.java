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

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.GameObjectImpl;
import org.diorite.impl.Tickable;
import org.diorite.block.BlockLocation;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.tileentity.TileEntity;

public abstract class TileEntityImpl extends GameObjectImpl implements TileEntity, Tickable
{
    private BlockLocation location;

    public TileEntityImpl(final UUID uuid, final BlockLocation location)
    {
        super(uuid);
        this.location = location;
    }

    public TileEntityImpl(final BlockLocation location)
    {
        super(UUID.randomUUID());
        this.location = location;
    }

    @Override
    public BlockLocation getLocation()
    {
        return this.location;
    }

    @Override
    public void loadFromNbt(final NbtTagCompound nbtTileEntity)
    {
        location = new BlockLocation(nbtTileEntity.getInt("LocationX"), nbtTileEntity.getInt("LocationY"), nbtTileEntity.getInt("LocationZ"));
    }

    @Override
    public void saveToNbt(final NbtTagCompound nbtTileEntity)
    {
        nbtTileEntity.setInt("LocationX", location.getX());
        nbtTileEntity.setInt("LocationY", location.getY());
        nbtTileEntity.setInt("LocationZ", location.getZ());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).toString();
    }
}
