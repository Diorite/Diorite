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
import org.diorite.material.BlockMaterialData;
import org.diorite.material.DirectionalMat;
import org.diorite.material.PowerableMat;

/**
 * Abstract class for all piston-based blocks.
 */
public abstract class PistonBaseMat extends BlockMaterialData implements DirectionalMat, PowerableMat
{
    /**
     * Bit flag defining if postion in extended.
     * If bit is set to 0, then it isn't extended..
     */
    public static final byte EXTENDED_FLAG = 0x08;

    protected final BlockFace facing;
    protected final boolean   extended;

    protected PistonBaseMat(final String enumName, final int id, final String minecraftId, final BlockFace facing, final boolean extended, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, facing.name() + (extended ? "_EXTENDED" : ""), combine(facing, extended), hardness, blastResistance);
        this.facing = facing;
        this.extended = extended;
    }

    protected PistonBaseMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean extended, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.facing = facing;
        this.extended = extended;
    }

    @Override
    public boolean isPowered()
    {
        return this.extended;
    }

    /**
     * Returns true if this is extended sub-type.
     *
     * @return true if this is extended sub-type.
     */
    public boolean isExtended()
    {
        return this.extended;
    }

    /**
     * Returns one of Piston sub-type based on extended state.
     *
     * @param extended if piston should be extended.
     *
     * @return sub-type of Piston
     */
    public PistonBaseMat getExtended(final boolean extended)
    {
        return this.getType(combine(this.facing, extended));
    }

    /**
     * Returns one of Piston sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of Piston
     */
    public PistonBaseMat getType(final BlockFace face, final boolean extended)
    {
        return this.getType(combine(face, extended));
    }

    @Override
    public abstract PistonBaseMat getType(int id);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("facing", this.facing).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.facing;
    }

    @Override
    public PistonBaseMat getBlockFacing(final BlockFace face)
    {
        return this.getType(combine(face, this.extended));
    }

    @Override
    public abstract PistonBaseMat getType(final String type);

    @Override
    public abstract PistonBaseMat[] types();

    @Override
    public abstract PistonBaseMat getPowered(final boolean powered);

    protected static byte combine(final BlockFace facing, final boolean extended)
    {
        byte result = extended ? EXTENDED_FLAG : 0x00;
        switch (facing)
        {
            case UP:
                result |= 0x01;
                break;
            case NORTH:
                result |= 0x02;
                break;
            case SOUTH:
                result |= 0x03;
                break;
            case WEST:
                result |= 0x04;
                break;
            case EAST:
                result |= 0x05;
                break;
            default:
                return result;
        }
        return result;
    }
}
