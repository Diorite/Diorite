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

public class ZoomMapLayer extends MapLayer
{
    protected final MapLayer belowLayer;
    protected final ZoomType zoomType;

    public ZoomMapLayer(final long seed, final MapLayer belowLayer)
    {
        this(seed, belowLayer, ZoomType.NORMAL);
    }

    public ZoomMapLayer(final long seed, final MapLayer belowLayer, final ZoomType zoomType)
    {
        super(seed);
        this.belowLayer = belowLayer;
        this.zoomType = zoomType;
    }

    @Override
    public int[] generateValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int gridX = x >> 1;
        final int gridZ = z >> 1;
        final int gridSizeX = (sizeX >> 1) + 2;
        final int gridSizeZ = (sizeZ >> 1) + 2;
        final int[] values = this.belowLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);

        final int zoomSizeX = (gridSizeX - 1) << 1;
        final int zoomSizeZ = (gridSizeZ - 1) << 1;
        final int[] tmpValues = new int[zoomSizeX * zoomSizeZ];
        for (int i = 0; i < (gridSizeZ - 1); i++)
        {
            int n = (i << 1) * zoomSizeX;
            int upperLeftVal = values[i * gridSizeX];
            int lowerLeftVal = values[(i + 1) * gridSizeX];
            for (int j = 0; j < (gridSizeX - 1); j++)
            {
                final int upperRightVal = values[(j + 1 + (i * gridSizeX))];
                final int lowerRightVal = values[(j + 1 + ((i + 1) * gridSizeX))];

                this.setCoordsSeed((gridX + j) << 1, (gridZ + i) << 1);
                tmpValues[n] = upperLeftVal;
                tmpValues[n + zoomSizeX] = (this.nextInt(2) > 0) ? upperLeftVal : lowerLeftVal;
                tmpValues[n + 1] = (this.nextInt(2) > 0) ? upperLeftVal : upperRightVal;
                tmpValues[n + 1 + zoomSizeX] = this.getNearest(upperLeftVal, upperRightVal, lowerLeftVal, lowerRightVal);
                upperLeftVal = upperRightVal;
                lowerLeftVal = lowerRightVal;
                n += 2;
            }
        }
        final int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                finalValues[(j + (i * sizeX))] = tmpValues[(j + ((i + (z & 1)) * zoomSizeX) + (x & 1))];
            }
        }

        return finalValues;
    }

    private int getNearest(final int upperLeftVal, final int upperRightVal, final int lowerLeftVal, final int lowerRightVal)
    {
        if (this.zoomType == ZoomType.NORMAL)
        {
            final boolean URandLL = upperRightVal == lowerLeftVal;
            final boolean ULandLL = upperLeftVal == lowerLeftVal;
            final boolean ULandLR = upperLeftVal == lowerRightVal;
            final boolean URandLR = upperRightVal == lowerRightVal;
            final boolean ULandUR = upperLeftVal == upperRightVal;
            final boolean LLandLR = lowerLeftVal == lowerRightVal;
            if (URandLL && LLandLR)
            {
                return upperRightVal;
            }
            else if ((ULandUR && ULandLL) || (ULandUR && ULandLR) || (ULandLL && ULandLR) || (ULandUR && ! LLandLR) || (ULandLL && ! URandLR) || (ULandLR && ! URandLL))
            {
                return upperLeftVal;
            }
            else if ((URandLL && ! ULandLR) || (URandLR && ! ULandLL))
            {
                return upperRightVal;
            }
            else if (LLandLR && ! ULandUR)
            {
                return lowerLeftVal;
            }
        }
        final int[] values = new int[]{upperLeftVal, upperRightVal, lowerLeftVal, lowerRightVal};
        return values[this.nextInt(values.length)];
    }

    public enum ZoomType
    {
        NORMAL,
        BLURRY
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).append("zoomType", this.zoomType).toString();
    }
}
