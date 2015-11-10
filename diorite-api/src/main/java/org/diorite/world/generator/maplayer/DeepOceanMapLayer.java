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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.Biome;

public class DeepOceanMapLayer extends MapLayer
{
    protected final MapLayer belowLayer;

    public DeepOceanMapLayer(final long seed, final MapLayer belowLayer)
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
                // This applies deep oceans using Von Neumann neighborhood
                // it takes a 3x3 grid with a cross shape and analyzes values as follow
                // 0X0
                // XxX
                // 0X0
                // the grid center value decides how we are proceeding:
                // - if it's ocean and it's surrounded by 4 ocean cells we spread deep ocean.
                final int centerVal = values[(j + 1 + ((i + 1) * gridSizeX))];
                if (centerVal == 0)
                {
                    final int upperVal = values[(j + 1 + (i * gridSizeX))];
                    final int lowerVal = values[(j + 1 + ((i + 2) * gridSizeX))];
                    final int leftVal = values[(j + ((i + 1) * gridSizeX))];
                    final int rightVal = values[(j + 2 + ((i + 1) * gridSizeX))];
                    if ((upperVal == 0) && (lowerVal == 0) && (leftVal == 0) && (rightVal == 0))
                    {
                        this.setCoordsSeed(x + j, z + i);
                        finalValues[(j + (i * sizeX))] = (this.nextInt(100) == 0) ? Biome.MUSHROOM_ISLAND.getBiomeId() : Biome.DEEP_OCEAN.getBiomeId();
                    }
                    else
                    {
                        finalValues[(j + (i * sizeX))] = centerVal;
                    }
                }
                else
                {
                    finalValues[(j + (i * sizeX))] = centerVal;
                }
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
