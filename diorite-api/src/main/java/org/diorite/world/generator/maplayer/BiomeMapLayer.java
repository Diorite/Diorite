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

@SuppressWarnings("MagicNumber")
public class BiomeMapLayer extends MapLayer
{
    protected static final TIntList WARM       = TIntArrayList.wrap(new int[]{DESERT.getBiomeId(), DESERT.getBiomeId(), DESERT.getBiomeId(), SAVANNA.getBiomeId(), SAVANNA.getBiomeId(), PLAINS.getBiomeId()});
    protected static final TIntList WET        = TIntArrayList.wrap(new int[]{PLAINS.getBiomeId(), PLAINS.getBiomeId(), FOREST.getBiomeId(), BIRCH_FOREST.getBiomeId(), ROOFED_FOREST.getBiomeId(), EXTREME_HILLS.getBiomeId(), SWAMPLAND.getBiomeId()});
    protected static final TIntList DRY        = TIntArrayList.wrap(new int[]{PLAINS.getBiomeId(), FOREST.getBiomeId(), TAIGA.getBiomeId(), EXTREME_HILLS.getBiomeId()});
    protected static final TIntList COLD       = TIntArrayList.wrap(new int[]{ICE_PLAINS.getBiomeId(), ICE_PLAINS.getBiomeId(), COLD_TAIGA.getBiomeId()});
    protected static final TIntList WARM_LARGE = TIntArrayList.wrap(new int[]{MESA_PLATEAU_FOREST.getBiomeId(), MESA_PLATEAU_FOREST.getBiomeId(), MESA_PLATEAU.getBiomeId()});
    protected static final TIntList DRY_LARGE  = TIntArrayList.wrap(new int[]{MEGA_TAIGA.getBiomeId()});
    protected static final TIntList WET_LARGE  = TIntArrayList.wrap(new int[]{JUNGLE.getBiomeId()});

    protected final MapLayer belowLayer;

    public static TIntList getWarm()
    {
        return WARM;
    }

    public static TIntList getWet()
    {
        return WET;
    }

    public static TIntList getDry()
    {
        return DRY;
    }

    public static TIntList getCold()
    {
        return COLD;
    }

    public static TIntList getWarmLarge()
    {
        return WARM_LARGE;
    }

    public static TIntList getDryLarge()
    {
        return DRY_LARGE;
    }

    public static TIntList getWetLarge()
    {
        return WET_LARGE;
    }

    public BiomeMapLayer(final long seed, final MapLayer belowLayer)
    {
        super(seed);
        this.belowLayer = belowLayer;
    }

    @Override
    public int[] generateValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int[] values = this.belowLayer.generateValues(x, z, sizeX, sizeZ);

        final int[] finalValues = new int[sizeX * sizeZ];
        for (int zz = 0; zz < sizeZ; zz++)
        {
            for (int xx = 0; xx < sizeX; xx++)
            {
                int val = values[(xx + (zz * sizeX))];
                if (val != 0)
                {
                    this.setCoordsSeed(x + xx, z + zz);
                    switch (val)
                    {
                        case 1:
                            val = DRY.get(this.nextInt(DRY.size()));
                            break;
                        case 2:
                            val = WARM.get(this.nextInt(WARM.size()));
                            break;
                        case 3:
                        case 1003:
                            val = COLD.get(this.nextInt(COLD.size()));
                            break;
                        case 4:
                            val = WET.get(this.nextInt(WET.size()));
                            break;
                        case 1001:
                            val = DRY_LARGE.get(this.nextInt(DRY_LARGE.size()));
                            break;
                        case 1002:
                            val = WARM_LARGE.get(this.nextInt(WARM_LARGE.size()));
                            break;
                        case 1004:
                            val = WET_LARGE.get(this.nextInt(WET_LARGE.size()));
                            break;
                        default:
                            break;
                    }
                }
                finalValues[(xx + (zz * sizeX))] = val;
            }
        }
        return finalValues;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).toString();
    }
}
