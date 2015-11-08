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
import org.diorite.material.FuelMat;
import org.diorite.material.StairsMat;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.SimpleEnumMap;

/**
 * Abstract class for all WoodenStairs-based blocks
 */
public abstract class WoodenStairsMat extends WoodMat implements StairsMat, FuelMat
{
    protected final BlockFace face;
    protected final boolean   upsideDown;

    protected WoodenStairsMat(final String enumName, final int id, final String minecraftId, final WoodType woodType, final BlockFace face, final boolean upsideDown, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name() + (upsideDown ? "_UPSIDE_DOWN" : ""), (byte) 0, woodType, hardness, blastResistance);
        this.face = face;
        this.upsideDown = upsideDown;
    }

    protected WoodenStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final BlockFace face, final boolean upsideDown, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
        this.face = face;
        this.upsideDown = upsideDown;
    }

    private static final SimpleEnumMap<WoodType, WoodenStairsMat> types = new SimpleEnumMap<>(6, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
    }

    @Override
    public abstract WoodenStairsMat getUpsideDown(final boolean upsideDown);

    @Override
    public abstract WoodenStairsMat getType(final BlockFace face, final boolean upsideDown);

    @Override
    public abstract WoodenStairsMat getBlockFacing(final BlockFace face);

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public WoodenStairsMat getWoodType(final WoodType woodType)
    {
        return types.get(woodType).getType(this.face, this.upsideDown);
    }

    @Override
    public abstract WoodenStairsMat getType(final int type);

    @Override
    public abstract WoodenStairsMat getType(final String type);

    @Override
    public abstract WoodenStairsMat[] types();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }

    /**
     * Returns sub-type of {@link WoodType} WoodenStairs, based on {@link BlockFace} and upside-down state.
     *
     * @param woodType   {@link WoodType} of WoodenStairs
     * @param face       facing direction of WoodenStairs
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of {@link WoodType} WoodenStairs.
     */
    public static WoodenStairsMat getWoodenStairs(final WoodType woodType, final BlockFace face, final boolean upsideDown)
    {
        return types.get(woodType).getType(face, upsideDown);
    }

    /**
     * Register new wood type to one of stairs, like OAK to OAK_STAIRS.
     *
     * @param type type of wood.
     * @param mat  stairs material.
     */
    public static void registerWoodType(final WoodType type, final WoodenStairsMat mat)
    {
        types.put(type, mat);
    }

    static
    {
        registerWoodType(WoodType.OAK, OAK_STAIRS);
        registerWoodType(WoodType.SPRUCE, SPRUCE_STAIRS);
        registerWoodType(WoodType.BIRCH, BIRCH_STAIRS);
        registerWoodType(WoodType.JUNGLE, JUNGLE_STAIRS);
        registerWoodType(WoodType.ACACIA, ACACIA_STAIRS);
        registerWoodType(WoodType.DARK_OAK, DARK_OAK_STAIRS);
    }
}
