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
import org.diorite.material.DirectionalMat;

/**
 * Abstract class for all pumpkin-based items.
 */
@SuppressWarnings("JavaDoc")
public abstract class AbstractPumpkinMat extends PlantMat implements DirectionalMat
{
    /**
     * facing direction of pumpkin.
     */
    protected final BlockFace face;

    protected AbstractPumpkinMat(final String enumName, final int id, final String minecraftId, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name(), combine(face), hardness, blastResistance);
        this.face = face;
    }

    protected AbstractPumpkinMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public abstract AbstractPumpkinMat getType(final int type);

    @Override
    public abstract AbstractPumpkinMat getType(final String type);

    @Override
    public abstract AbstractPumpkinMat[] types();

    @Override
    public abstract AbstractPumpkinMat getBlockFacing(final BlockFace face);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    protected static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case WEST:
                return 0x1;
            case NORTH:
                return 0x2;
            case EAST:
                return 0x3;
            case SELF:
                return 0x4;
            default:
                return 0x0;
        }
    }
}
