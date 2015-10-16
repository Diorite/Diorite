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

package org.diorite.world.generator.maplayer;

import static org.diorite.world.Biome.*;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BiomeVariationMapLayer extends MapLayer
{
    protected static final TIntList                ISLANDS       = TIntArrayList.wrap(new int[]{PLAINS.getBiomeId(), FOREST.getBiomeId()});
    protected static final TIntObjectMap<TIntList> VARIATIONS    = new TIntObjectHashMap<>(16);
    public static final    int                     VARIATION_MOD = 128;

    protected final MapLayer belowLayer;
    protected final MapLayer variationLayer;

    public static TIntList getIslands()
    {
        return ISLANDS;
    }

    public static TIntObjectMap<TIntList> getVariations()
    {
        return VARIATIONS;
    }

    public BiomeVariationMapLayer(final long seed, final MapLayer belowLayer)
    {
        this(seed, belowLayer, null);
    }

    public BiomeVariationMapLayer(final long seed, final MapLayer belowLayer, final MapLayer variationLayer)
    {
        super(seed);
        this.belowLayer = belowLayer;
        this.variationLayer = variationLayer;
    }

    @Override
    public int[] generateValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        if (this.variationLayer == null)
        {
            return this.generateRandomValues(x, z, sizeX, sizeZ);
        }
        return this.mergeValues(x, z, sizeX, sizeZ);
    }

    public int[] generateRandomValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int[] values = this.belowLayer.generateValues(x, z, sizeX, sizeZ);

        final int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                int val = values[(j + (i * sizeX))];
                if (val > 0)
                {
                    this.setCoordsSeed(x + j, z + i);
                    val = this.nextInt(30) + 2;
                }
                finalValues[(j + (i * sizeX))] = val;
            }
        }
        return finalValues;
    }

    public int[] mergeValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int gridX = x - 1;
        final int gridZ = z - 1;
        final int gridSizeX = sizeX + 2;
        final int gridSizeZ = sizeZ + 2;

        final int[] values = this.belowLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);
        final int[] eValues = this.variationLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);

        final int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                this.setCoordsSeed(x + j, z + i);
                final int centerValue = values[(j + 1 + ((i + 1) * gridSizeX))];
                final int variationValue = eValues[(j + 1 + ((i + 1) * gridSizeX))];
                if ((centerValue != 0) && (variationValue == 3) && (centerValue < VARIATION_MOD))
                {
                    finalValues[(j + (i * sizeX))] = (getByBiomeId(centerValue + VARIATION_MOD) != null) ? (centerValue + VARIATION_MOD) : centerValue;
                }
                else if ((variationValue == 2) || (this.nextInt(3) == 0))
                {
                    int val = centerValue;
                    if (VARIATIONS.containsKey(centerValue))
                    {
                        val = VARIATIONS.get(centerValue).get(this.nextInt(VARIATIONS.get(centerValue).size()));
                    }
                    else if ((centerValue == DEEP_OCEAN.getBiomeId()) && (this.nextInt(3) == 0))
                    {
                        val = ISLANDS.get(this.nextInt(ISLANDS.size()));
                    }
                    if ((variationValue == 2) && (val != centerValue))
                    {
                        val = (getByBiomeId(val + VARIATION_MOD) != null) ? (val + VARIATION_MOD) : centerValue;
                    }
                    if (val != centerValue)
                    {
                        int count = 0;
                        if (values[(j + 1 + (i * gridSizeX))] == centerValue)
                        { // upper value
                            count++;
                        }
                        if (values[(j + 1 + ((i + 2) * gridSizeX))] == centerValue)
                        { // lower value
                            count++;
                        }
                        if (values[(j + ((i + 1) * gridSizeX))] == centerValue)
                        { // left value
                            count++;
                        }
                        if (values[(j + 2 + ((i + 1) * gridSizeX))] == centerValue)
                        { // right value
                            count++;
                        }
                        // spread mountains if not too close from an edge
                        finalValues[(j + (i * sizeX))] = (count < 3) ? centerValue : val;
                    }
                    else
                    {
                        finalValues[(j + (i * sizeX))] = val;
                    }
                }
                else
                {
                    finalValues[(j + (i * sizeX))] = centerValue;
                }
            }
        }
        return finalValues;
    }

    static
    {
        VARIATIONS.put(DESERT.getBiomeId(), TIntArrayList.wrap(new int[]{DESERT_HILLS.getBiomeId()}));
        VARIATIONS.put(FOREST.getBiomeId(), TIntArrayList.wrap(new int[]{FOREST_HILLS.getBiomeId()}));
        VARIATIONS.put(BIRCH_FOREST.getBiomeId(), TIntArrayList.wrap(new int[]{BIRCH_FOREST_HILLS.getBiomeId()}));
        VARIATIONS.put(ROOFED_FOREST.getBiomeId(), TIntArrayList.wrap(new int[]{PLAINS.getBiomeId()}));
        VARIATIONS.put(TAIGA.getBiomeId(), TIntArrayList.wrap(new int[]{TAIGA_HILLS.getBiomeId()}));
        VARIATIONS.put(MEGA_TAIGA.getBiomeId(), TIntArrayList.wrap(new int[]{MEGA_TAIGA_HILLS.getBiomeId()}));
        VARIATIONS.put(COLD_TAIGA.getBiomeId(), TIntArrayList.wrap(new int[]{COLD_TAIGA_HILLS.getBiomeId()}));
        VARIATIONS.put(PLAINS.getBiomeId(), TIntArrayList.wrap(new int[]{FOREST.getBiomeId(), FOREST.getBiomeId(), FOREST_HILLS.getBiomeId()}));
        VARIATIONS.put(ICE_PLAINS.getBiomeId(), TIntArrayList.wrap(new int[]{ICE_MOUNTAINS.getBiomeId()}));
        VARIATIONS.put(JUNGLE.getBiomeId(), TIntArrayList.wrap(new int[]{JUNGLE_HILLS.getBiomeId()}));
        VARIATIONS.put(OCEAN.getBiomeId(), TIntArrayList.wrap(new int[]{DEEP_OCEAN.getBiomeId()}));
        VARIATIONS.put(EXTREME_HILLS.getBiomeId(), TIntArrayList.wrap(new int[]{EXTREME_HILLS_PLUS.getBiomeId()}));
        VARIATIONS.put(SAVANNA.getBiomeId(), TIntArrayList.wrap(new int[]{SAVANNA_PLATEAU.getBiomeId()}));
        VARIATIONS.put(MESA_PLATEAU_FOREST.getBiomeId(), TIntArrayList.wrap(new int[]{MESA.getBiomeId()}));
        VARIATIONS.put(MESA_PLATEAU.getBiomeId(), TIntArrayList.wrap(new int[]{MESA.getBiomeId()}));
        VARIATIONS.put(MESA.getBiomeId(), TIntArrayList.wrap(new int[]{MESA.getBiomeId()}));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).append("variationLayer", this.variationLayer).toString();
    }
}
