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

import java.util.EnumMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WhittakerMapLayer extends MapLayer
{
    protected static final EnumMap<ClimateType, Climate> MAP = new EnumMap<>(ClimateType.class);
    protected final MapLayer    belowLayer;
    protected final ClimateType type;

    public static EnumMap<ClimateType, Climate> getClimateMap()
    {
        return MAP;
    }

    public WhittakerMapLayer(final long seed, final MapLayer belowLayer, final ClimateType type)
    {
        super(seed);
        this.belowLayer = belowLayer;
        this.type = type;
    }

    @Override
    public int[] generateValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        if ((this.type == ClimateType.WARM_WET) || (this.type == ClimateType.COLD_DRY))
        {
            return this.swapValues(x, z, sizeX, sizeZ);
        }
        else
        {
            return this.modifyValues(x, z, sizeX, sizeZ);
        }
    }

    private int[] swapValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int gridX = x - 1;
        final int gridZ = z - 1;
        final int gridSizeX = sizeX + 2;
        final int gridSizeZ = sizeZ + 2;
        final int[] values = this.belowLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);

        final Climate climate = MAP.get(this.type);
        final int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                int centerVal = values[(j + 1 + ((i + 1) * gridSizeX))];
                if (centerVal == climate.value)
                {
                    final int upperVal = values[(j + 1 + (i * gridSizeX))];
                    final int lowerVal = values[(j + 1 + ((i + 2) * gridSizeX))];
                    final int leftVal = values[(j + ((i + 1) * gridSizeX))];
                    final int rightVal = values[(j + 2 + ((i + 1) * gridSizeX))];
                    for (final int type : climate.crossTypes)
                    {
                        if ((upperVal == type) || (lowerVal == type) || (leftVal == type) || (rightVal == type))
                        {
                            centerVal = climate.finalValue;
                            break;
                        }
                    }
                }
                finalValues[(j + (i * sizeX))] = centerVal;
            }
        }
        return finalValues;
    }

    @SuppressWarnings("MagicNumber")
    private int[] modifyValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int[] values = this.belowLayer.generateValues(x, z, sizeX, sizeZ);
        final int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                int val = values[(j + (i * sizeX))];
                if (val != 0)
                {
                    this.setCoordsSeed(x + j, z + i);
                    if (this.nextInt(13) == 0)
                    {
                        val += 1000;
                    }
                }
                finalValues[(j + (i * sizeX))] = val;
            }
        }
        return finalValues;
    }

    public enum ClimateType
    {
        WARM_WET,
        COLD_DRY,
        LARGER_BIOMES
    }

    static
    {
        MAP.put(ClimateType.WARM_WET, new Climate(2, new int[]{3, 1}, 4));
        MAP.put(ClimateType.COLD_DRY, new Climate(3, new int[]{2, 4}, 1));
    }

    public static class Climate
    {

        public final int   value;
        public final int[] crossTypes;
        public final int   finalValue;

        public Climate(final int value, final int[] crossTypes, final int finalValue)
        {
            this.value = value;
            this.crossTypes = crossTypes;
            this.finalValue = finalValue;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).append("crossTypes", this.crossTypes).append("finalValue", this.finalValue).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).append("type", this.type).toString();
    }
}
