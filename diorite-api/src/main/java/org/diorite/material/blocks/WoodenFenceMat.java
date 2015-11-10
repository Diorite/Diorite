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

import org.diorite.material.FenceMat;
import org.diorite.material.FuelMat;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.SimpleEnumMap;

/**
 * Abstract class for all WoodenFence-based blocks
 */
public abstract class WoodenFenceMat extends WoodMat implements FenceMat, FuelMat
{
    protected WoodenFenceMat(final String enumName, final int id, final String minecraftId, final String typeName, final WoodType woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, (byte) 0, woodType, hardness, blastResistance);
    }

    protected WoodenFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    private static final SimpleEnumMap<WoodType, WoodenFenceMat> types = new SimpleEnumMap<>(6, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
    }

    @Override
    public WoodenFenceMat getWoodType(final WoodType woodType)
    {
        return types.get(woodType);
    }

    @Override
    public abstract WoodenFenceMat getType(final int type);

    @Override
    public abstract WoodenFenceMat getType(final String type);

    @Override
    public abstract WoodenFenceMat[] types();

    /**
     * Returns sub-type of {@link WoodenFenceMat}, based on {@link WoodType}.
     *
     * @param woodType {@link WoodType} of WoodenFence
     *
     * @return sub-type of {@link WoodenFenceMat}.
     */
    public static WoodenFenceMat getWoodenFence(final WoodType woodType)
    {
        return types.get(woodType);
    }

    /**
     * Register new wood type to one of fences, like OAK to OAK_FENCE.
     *
     * @param type type of wood.
     * @param mat  fence material.
     */
    public static void registerWoodType(final WoodType type, final WoodenFenceMat mat)
    {
        types.put(type, mat);
    }

    static
    {
        registerWoodType(WoodType.OAK, OAK_FENCE);
        registerWoodType(WoodType.SPRUCE, SPRUCE_FENCE);
        registerWoodType(WoodType.BIRCH, BIRCH_FENCE);
        registerWoodType(WoodType.JUNGLE, JUNGLE_FENCE);
        registerWoodType(WoodType.ACACIA, ACACIA_FENCE);
        registerWoodType(WoodType.DARK_OAK, DARK_OAK_FENCE);
    }
}
