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

import org.diorite.material.BlockMaterialData;

@SuppressWarnings("JavaDoc")
public abstract class LiquidMat extends BlockMaterialData
{
    /**
     * Bit flag for falling state of liquid.
     */
    public static final byte FALLING_FLAG = 0x08;

    /**
     * Stage/level of liquid.
     */
    protected final LiquidStageMat stage;
    /**
     * Type of liquid block.
     */
    protected final LiquidTypeMat  liquidType;

    protected LiquidMat(final String enumName, final int id, final String minecraftId, final String typeName, final LiquidStageMat stage, final LiquidTypeMat liquidType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, stage.getDataValue(), hardness, blastResistance);
        this.stage = stage;
        this.liquidType = liquidType;
    }

    protected LiquidMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final LiquidStageMat stage, final LiquidTypeMat liquidType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, stage.getDataValue(), hardness, blastResistance);
        this.stage = stage;
        this.liquidType = liquidType;
    }

    /**
     * Get sub-type of this liquid based on stage.
     *
     * @param stage stage of liquid to get.
     *
     * @return sub-type of liquid or null.
     */
    public abstract LiquidMat getStage(LiquidStageMat stage);

    /**
     * Get sub-type of this liquid based on type.
     *
     * @param type type of liquid to get.
     *
     * @return sub-type of liquid or null.
     */
    public abstract LiquidMat getLiquidType(LiquidTypeMat type);

    /**
     * Returns {@link LiquidTypeMat#NORMAL} type of liquid.
     *
     * @return {@link LiquidTypeMat#NORMAL} type of liquid.
     */
    public LiquidMat getNormalType()
    {
        return this.getLiquidType(LiquidTypeMat.NORMAL);
    }

    /**
     * Returns {@link LiquidTypeMat#STILL} type of liquid.
     *
     * @return {@link LiquidTypeMat#STILL} type of liquid.
     */
    public LiquidMat getStillType()
    {
        return this.getLiquidType(LiquidTypeMat.STILL);
    }

    /**
     * Returns opposite type of liquid.
     *
     * @return opposite type of liquid.
     */
    public LiquidMat getOtherType()
    {
        return this.getLiquidType((this.liquidType.isStill()) ? LiquidTypeMat.NORMAL : LiquidTypeMat.STILL);
    }

    /**
     * Returns next stage of this liquid, can't return null.
     *
     * @return next stage of this liquid.
     */
    public LiquidMat nextStage()
    {
        return this.getStage(this.stage.getNextStage());
    }

    /**
     * Returns previous stage of this liquid, can't return null.
     *
     * @return previous stage of this liquid.
     */
    public LiquidMat previousStage()
    {
        return this.getStage(this.stage.getPreviousStage());
    }

    /**
     * Returns falling type of stage for this liquid.
     *
     * @return falling type of stage for this liquid.
     */
    public LiquidMat falling()
    {
        return this.getStage(this.stage.getFalling());
    }

    /**
     * Returns normal type of stage for this liquid.
     *
     * @return normal type of stage for this liquid.
     */
    public LiquidMat normal()
    {
        return this.getStage(this.stage.getNormal());
    }

    /**
     * Returns opposite type of stage for this liquid.
     *
     * @return opposite type of stage for this liquid.
     */
    public LiquidMat switchFalling()
    {
        return this.getStage(this.stage.switchFalling());
    }

    /**
     * Returns source type of stage for this liquid.
     *
     * @return source type of stage for this liquid.
     */
    public LiquidMat source()
    {
        return this.getStage(this.stage.getSource());
    }

    /**
     * Returns true if this is source stage of liquid.
     *
     * @return true if this is source stage of liquid.
     */
    public boolean isSource()
    {
        return this.stage.isSource();
    }

    /**
     * Returns true if this is normal stage of liquid.
     *
     * @return true if this is normal stage of liquid.
     */
    public boolean isNormalStage()
    {
        return this.stage.isNormal();
    }

    /**
     * Returns true if this is falling stage of liquid.
     *
     * @return true if this is falling stage of liquid.
     */
    public boolean isFalling()
    {
        return this.stage.isFalling();
    }

    /**
     * Returns stage of this liquid.
     *
     * @return stage of this liquid.
     */
    public LiquidStageMat getStage()
    {
        return this.stage;
    }

    /**
     * Returns type of block for this liquid.
     *
     * @return type of block for this liquid.
     */
    public LiquidTypeMat getLiquidType()
    {
        return this.liquidType;
    }

    /**
     * Returns true if this is {@link LiquidTypeMat#STILL} type of block.
     *
     * @return true if this is {@link LiquidTypeMat#STILL} type of block.
     */
    public boolean isStill()
    {
        return this.liquidType.isStill();
    }

    @Override
    public boolean isSolid()
    {
        return false;
    }

    @Override
    public abstract LiquidMat getType(final int type);

    @Override
    public abstract LiquidMat getType(final String type);

    @Override
    public abstract LiquidMat[] types();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("stage", this.stage).append("liquidType", this.liquidType).toString();
    }
}
