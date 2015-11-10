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


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;

public class BiomeEdgeMapLayer extends MapLayer
{
    protected static final TIntIntMap                MESA_EDGES       = new TIntIntHashMap(2);
    protected static final TIntIntMap                MEGA_TAIGA_EDGES = new TIntIntHashMap(1);
    protected static final TIntIntMap                DESERT_EDGES     = new TIntIntHashMap(1);
    protected static final TIntIntMap                SWAMP1_EDGES     = new TIntIntHashMap(1);
    protected static final TIntIntMap                SWAMP2_EDGES     = new TIntIntHashMap(1);
    protected static final Map<TIntIntMap, TIntList> EDGES            = new HashMap<>(5);

    protected final MapLayer belowLayer;

    public BiomeEdgeMapLayer(final long seed, final MapLayer belowLayer)
    {
        super(seed);
        this.belowLayer = belowLayer;
    }

    @Override
    public int[] generateValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int gridX = x - 1;
        final int gridZ = z - 1;
        final int gridSizeX = sizeX + 2;
        final int gridSizeZ = sizeZ + 2;
        final int[] values = this.belowLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);

        final int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                // This applies biome large edges using Von Neumann neighborhood
                final int centerVal = values[(j + 1 + ((i + 1) * gridSizeX))];
                int val = centerVal;
                for (final Entry<TIntIntMap, TIntList> entry : EDGES.entrySet())
                {
                    final TIntIntMap map = entry.getKey();
                    if (map.containsKey(centerVal))
                    {
                        final int upperVal = values[(j + 1 + (i * gridSizeX))];
                        final int lowerVal = values[(j + 1 + ((i + 2) * gridSizeX))];
                        final int leftVal = values[(j + ((i + 1) * gridSizeX))];
                        final int rightVal = values[(j + 2 + ((i + 1) * gridSizeX))];
                        if (((entry.getValue() == null) && (! map.containsKey(upperVal) || ! map.containsKey(lowerVal) || ! map.containsKey(leftVal) || ! map.containsKey(rightVal))) || ((entry.getValue() != null) && (entry.getValue().contains(upperVal) || entry.getValue().contains(lowerVal) || entry.getValue().contains(leftVal) || entry.getValue().contains(rightVal))))
                        {
                            val = map.get(centerVal);
                            break;
                        }
                    }
                }

                finalValues[(j + (i * sizeX))] = val;
            }
        }
        return finalValues;
    }

    static
    {
        MESA_EDGES.put(MESA_PLATEAU_FOREST.getBiomeId(), MESA.getBiomeId());
        MESA_EDGES.put(MESA_PLATEAU.getBiomeId(), MESA.getBiomeId());

        MEGA_TAIGA_EDGES.put(MEGA_TAIGA.getBiomeId(), TAIGA.getBiomeId());

        DESERT_EDGES.put(DESERT.getBiomeId(), EXTREME_HILLS_PLUS.getBiomeId());

        SWAMP1_EDGES.put(SWAMPLAND.getBiomeId(), PLAINS.getBiomeId());
        SWAMP2_EDGES.put(SWAMPLAND.getBiomeId(), JUNGLE_EDGE.getBiomeId());

        EDGES.put(MESA_EDGES, null);
        EDGES.put(MEGA_TAIGA_EDGES, null);
        EDGES.put(DESERT_EDGES, TIntArrayList.wrap(new int[]{ICE_PLAINS.getBiomeId()}));
        EDGES.put(SWAMP1_EDGES, TIntArrayList.wrap(new int[]{DESERT.getBiomeId(), COLD_TAIGA.getBiomeId(), ICE_PLAINS.getBiomeId()}));
        EDGES.put(SWAMP2_EDGES, TIntArrayList.wrap(new int[]{JUNGLE.getBiomeId()}));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).toString();
    }
}
