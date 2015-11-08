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

package org.diorite.world.chunk;

import org.diorite.material.BlockMaterialData;
import org.diorite.world.Biome;

public interface ChunkSnapshot
{
    int getX();

    int getZ();

    String getWorldName();

    long getCaptureFullTime();

    boolean isSectionEmpty(int sy);

    int getBlockTypeId(int x, int y, int z);

    int getBlockData(int x, int y, int z);

    BlockMaterialData getBlockType(int x, int y, int z);

    int getBlockSkyLight(int x, int y, int z);

    int getBlockEmittedLight(int x, int y, int z);

    int getHighestBlockYAt(int x, int z);

    Biome getBiome(int x, int z);

    double getRawBiomeTemperature(int x, int z);

    double getRawBiomeRainfall(int x, int z);
}
