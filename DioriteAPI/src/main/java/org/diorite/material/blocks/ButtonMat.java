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
import org.diorite.material.PowerableMat;

/**
 * Abstract class for all button-type blocks.
 */
@SuppressWarnings("JavaDoc")
public abstract class ButtonMat extends BlockMaterialData implements PowerableMat, AttachableMat
{
    /**
     * Bit flag defining if button is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte POWERED_FLAG = 0x08;

    /**
     * Face direction of this button.
     */
    protected final BlockFace face;
    /**
     * If button is powered. (clicked)
     */
    protected final boolean   powered;

    protected ButtonMat(final String enumName, final int id, final String minecraftId, final BlockFace face, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name() + (powered ? "_POWERED" : ""), combine(face, powered), hardness, blastResistance);
        this.face = face;
        this.powered = powered;
    }

    protected ButtonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.powered = powered;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    /**
     * Returns one of Button sub-type based on {@link BlockFace} and powered state.
     * It will never return null.
     *
     * @param face    facing direction of piston.
     * @param powered if button should be powered.
     *
     * @return sub-type of Button
     */
    public abstract ButtonMat getType(BlockFace face, boolean powered);

    @Override
    public abstract ButtonMat getType(final int type);

    @Override
    public abstract ButtonMat getType(final String type);

    @Override
    public abstract ButtonMat[] types();

    @Override
    public abstract ButtonMat getBlockFacing(final BlockFace face);

    @Override
    public abstract ButtonMat getPowered(final boolean powered);

    @Override
    public abstract ButtonMat getAttachedFace(final BlockFace face);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("powered", this.powered).toString();
    }

    protected static byte combine(final BlockFace face, final boolean powered)
    {
        byte result = powered ? POWERED_FLAG : 0x0;
        switch (face)
        {
            case EAST:
                result |= 0x1;
                break;
            case WEST:
                result |= 0x2;
                break;
            case SOUTH:
                result |= 0x3;
                break;
            case NORTH:
                result |= 0x4;
                break;
            case UP:
                result |= 0x5;
                break;
            default:
                break;
        }
        return result;
    }
}
