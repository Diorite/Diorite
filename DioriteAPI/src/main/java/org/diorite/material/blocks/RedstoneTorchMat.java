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

package org.diorite.material.blocks;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.AttachableMat;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.PowerableMat;

/**
 * Abstract class for all RedstoneTorch-based blocks
 */
public abstract class RedstoneTorchMat extends BlockMaterialData implements PowerableMat, AttachableMat
{
    protected final BlockFace face;

    protected RedstoneTorchMat(final String enumName, final int id, final String minecraftId, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, (face == BlockFace.SELF) ? "ITEM" : face.name(), combine(face), hardness, blastResistance);
        this.face = face;
    }

    protected RedstoneTorchMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public RedstoneTorchMat getPowered(final boolean powered)
    {
        return getRedstoneTorch(powered);
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public abstract RedstoneTorchMat getType(final int type);

    @Override
    public abstract RedstoneTorchMat getType(final String type);

    @Override
    public abstract RedstoneTorchMat[] types();

    @Override
    public abstract RedstoneTorchMat getAttachedFace(final BlockFace face);

    @Override
    public abstract RedstoneTorchMat getBlockFacing(final BlockFace face);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    protected static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case SELF:
                return 0x0;
            case EAST:
                return 0x2;
            case SOUTH:
                return 0x3;
            case NORTH:
                return 0x4;
            case UP:
                return 0x5;
            default:
                return 0x1;
        }
    }

    /**
     * Returns one of RedstoneTorch sub-type based on powered state.
     * It will never return null.
     *
     * @param powered if RedstoneTorch should be powered.
     *
     * @return sub-type of RedstoneTorch
     */
    public static RedstoneTorchMat getRedstoneTorch(final boolean powered)
    {
        if (powered)
        {
            return Material.REDSTONE_TORCH_ON;
        }
        else
        {
            return Material.REDSTONE_TORCH_OFF;
        }
    }

    /**
     * Returns one of RedstoneTorch sub-type based on powered state and {@link BlockFace}.
     * It will never return null.
     *
     * @param powered if RedstoneTorch should be powered.
     * @param face    facing direction of torch.
     *
     * @return sub-type of RedstoneTorch
     */
    public static RedstoneTorchMat getRedstoneTorch(final boolean powered, final BlockFace face)
    {
        if (powered)
        {
            return RedstoneTorchOnMat.getRedstoneTorchOn(face);
        }
        else
        {
            return RedstoneTorchOffMat.getRedstoneTorchOff(face);
        }
    }
}
