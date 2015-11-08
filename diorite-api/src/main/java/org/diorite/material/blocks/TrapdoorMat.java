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
import org.diorite.material.OpenableMat;

public abstract class TrapdoorMat extends BlockMaterialData implements OpenableMat, AttachableMat
{
    /**
     * Bit flag defining if trapdoor is closed.
     * If bit is set to 0, then trapdror is closed
     */
    public static final byte OPEN_FLAG = 0x04;
    /**
     * Bit flag defining if trapdoor is on top part of block.
     * If bit is set to 0, then trapdror is on bottom part of block.
     */
    public static final byte TOP_FLAG  = 0x08;

    protected final BlockFace face;
    protected final boolean   open;
    protected final boolean   onTop;

    protected TrapdoorMat(final String enumName, final int id, final String minecraftId, final BlockFace face, final boolean open, final boolean onTop, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name() + (onTop ? "_TOP" : "_BOTTOM") + (open ? "_OPEN" : ""), combine(face, open, onTop), hardness, blastResistance);
        this.face = face;
        this.open = open;
        this.onTop = onTop;
    }

    protected TrapdoorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean open, final boolean onTop, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.open = open;
        this.onTop = onTop;
    }

    /**
     * @return true if trapdoor is on top of the block.
     */
    public boolean isOnTop()
    {
        return this.onTop;
    }

    /**
     * Returns sub-type of trapdoor with selected on top state.
     *
     * @param onTop if trapdoor should be on top of the block.
     *
     * @return sub-type of trapdoor.
     */
    public abstract TrapdoorMat getOnTop(boolean onTop);

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isOpen()
    {
        return this.open;
    }

    /**
     * Returns one of IronTrapdoor sub-type based on facing direction, open state and on top state.
     * It will never return null.
     *
     * @param face  facing direction of trapdoor.
     * @param open  if trapdoor should be open.
     * @param onTop if trapdoor should be on top of the block.
     *
     * @return sub-type of IronTrapdoor
     */
    public abstract TrapdoorMat getType(BlockFace face, boolean open, boolean onTop);

    @Override
    public abstract TrapdoorMat getType(final int type);

    @Override
    public abstract TrapdoorMat getType(final String type);

    @Override
    public abstract TrapdoorMat[] types();

    @Override
    public abstract TrapdoorMat getAttachedFace(final BlockFace face);

    @Override
    public abstract TrapdoorMat getBlockFacing(final BlockFace face);

    @Override
    public abstract TrapdoorMat getOpen(final boolean open);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("open", this.open).append("onTop", this.onTop).toString();
    }

    protected static byte combine(final BlockFace face, final boolean open, final boolean onTop)
    {
        byte result = onTop ? TOP_FLAG : 0x0;
        switch (face)
        {
            case SOUTH:
                result |= 0x1;
                break;
            case EAST:
                result |= 0x2;
                break;
            case NORTH:
                result |= 0x3;
                break;
            default:
                break;
        }
        if (open)
        {
            result |= OPEN_FLAG;
        }
        return result;
    }
}
