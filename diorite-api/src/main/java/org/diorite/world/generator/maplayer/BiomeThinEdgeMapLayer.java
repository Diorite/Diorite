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
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

public class BiomeThinEdgeMapLayer extends MapLayer
{
    protected static final TIntSet                   OCEANS       = new TIntHashSet(2);
    protected static final TIntIntMap                MESA_EDGES   = new TIntIntHashMap(6);
    protected static final TIntIntMap                JUNGLE_EDGES = new TIntIntHashMap(4);
    protected static final Map<TIntIntMap, TIntList> EDGES        = new HashMap<>(2);

    protected final MapLayer belowLayer;

    public static TIntSet getOceans()
    {
        return OCEANS;
    }

    public static TIntIntMap getMesaEdges()
    {
        return MESA_EDGES;
    }

    public static TIntIntMap getJungleEdges()
    {
        return JUNGLE_EDGES;
    }

    public static Map<TIntIntMap, TIntList> getEdges()
    {
        return EDGES;
    }

    public BiomeThinEdgeMapLayer(final long seed, final MapLayer belowLayer)
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
                // This applies biome thin edges using Von Neumann neighborhood
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
                        if (((entry.getValue() == null) && ((! OCEANS.contains(upperVal) && ! map.containsKey(upperVal)) || (! OCEANS.contains(lowerVal) && ! map.containsKey(lowerVal)) || (! OCEANS.contains(leftVal) && ! map.containsKey(leftVal)) || (! OCEANS.contains(rightVal) && ! map.containsKey(rightVal)))) || ((entry.getValue() != null) && ((! OCEANS.contains(upperVal) && ! entry.getValue().contains(upperVal)) || (! OCEANS.contains(lowerVal) && ! entry.getValue().contains(lowerVal)) || (! OCEANS.contains(leftVal) && ! entry.getValue().contains(leftVal)) || (! OCEANS.contains(rightVal) && ! entry.getValue().contains(rightVal)))))
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
        OCEANS.add(OCEAN.getBiomeId());
        OCEANS.add(DEEP_OCEAN.getBiomeId());

        MESA_EDGES.put(MESA.getBiomeId(), DESERT.getBiomeId());
        MESA_EDGES.put(MESA_BRYCE.getBiomeId(), DESERT.getBiomeId());
        MESA_EDGES.put(MESA_PLATEAU_FOREST.getBiomeId(), DESERT.getBiomeId());
        MESA_EDGES.put(MESA_PLATEAU_FOREST_MOUNTAINS.getBiomeId(), DESERT.getBiomeId());
        MESA_EDGES.put(MESA_PLATEAU.getBiomeId(), DESERT.getBiomeId());
        MESA_EDGES.put(MESA_PLATEAU_MOUNTAINS.getBiomeId(), DESERT.getBiomeId());

        JUNGLE_EDGES.put(JUNGLE.getBiomeId(), JUNGLE_EDGE.getBiomeId());
        JUNGLE_EDGES.put(JUNGLE_HILLS.getBiomeId(), JUNGLE_EDGE.getBiomeId());
        JUNGLE_EDGES.put(JUNGLE_MOUNTAINS.getBiomeId(), JUNGLE_EDGE.getBiomeId());
        JUNGLE_EDGES.put(JUNGLE_EDGE_MOUNTAINS.getBiomeId(), JUNGLE_EDGE.getBiomeId());

        EDGES.put(MESA_EDGES, null);
        EDGES.put(JUNGLE_EDGES, TIntArrayList.wrap(new int[]{JUNGLE.getBiomeId(), JUNGLE_HILLS.getBiomeId(), JUNGLE_MOUNTAINS.getBiomeId(), JUNGLE_EDGE_MOUNTAINS.getBiomeId(), FOREST.getBiomeId(), TAIGA.getBiomeId()}));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).toString();
    }
}
