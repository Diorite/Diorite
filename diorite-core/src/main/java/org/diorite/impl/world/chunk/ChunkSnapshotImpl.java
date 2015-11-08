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

package org.diorite.impl.world.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.AirMat;
import org.diorite.world.Biome;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkSnapshot;

public class ChunkSnapshotImpl implements ChunkSnapshot
{
    public static final int MAX_SKY_LIGHT = 15;
    private final int x, z;
    private final String world;
    private final long   time;

    private final ChunkPartImpl[] sections;

    private final byte[]   height;
    private final double[] temp, humid;
    private final byte[] biomes;

    public ChunkSnapshotImpl(final int x, final int z, final World world, final ChunkPartImpl[] sections, final byte[] height, final byte[] biomes, final boolean svTemp)
    {
        this.x = x;
        this.z = z;
        this.world = world.getName();
        this.time = world.getTime();

        final int numSections = (sections != null) ? sections.length : 0;
        this.sections = new ChunkPartImpl[numSections];
        for (int i = 0; i < numSections; ++ i)
        {
            if (sections[i] != null)
            {
                this.sections[i] = sections[i].snapshot();
            }
        }

        this.height = height;
        this.biomes = biomes;

        if (svTemp)
        {
            final int baseX = x << 4, baseZ = z << 4;
            this.temp = new double[Chunk.CHUNK_BIOMES_SIZE];
            this.humid = new double[Chunk.CHUNK_BIOMES_SIZE];
            for (int xx = 0; xx < Chunk.CHUNK_SIZE; ++ xx)
            {
                for (int zz = 0; zz < Chunk.CHUNK_SIZE; ++ zz)
                {
                    final Biome biome = world.getBiome(baseX + xx, - 1, baseZ + zz);
                    this.temp[this.coordToIndex(xx, zz)] = biome.getTemperature();
                    this.humid[this.coordToIndex(xx, zz)] = biome.getHumidity();
                }
            }
        }
        else
        {
            this.temp = this.humid = null;
        }
    }

    private ChunkPartImpl getSection(final int y)
    {
        final int idx = y >> 4;
        if ((idx < 0) || (idx >= this.sections.length))
        {
            return null;
        }
        return this.sections[idx];
    }

    /**
     * Get the ChunkPart array backing this snapshot. In general, it should not be modified.
     *
     * @return The array of ChunkParts.
     */
    public ChunkPartImpl[] getRawSections()
    {
        return this.sections;
    }

    public int[] getRawHeightmap()
    {
        final int[] result = new int[this.height.length];
        for (int i = 0; i < result.length; ++ i)
        {
            result[i] = this.height[i];
        }
        return result;
    }

    public byte[] getRawBiomes()
    {
        return this.biomes;
    }

    @Override
    public int getX()
    {
        return this.x;
    }

    @Override
    public int getZ()
    {
        return this.z;
    }

    @Override
    public String getWorldName()
    {
        return this.world;
    }

    @Override
    public long getCaptureFullTime()
    {
        return this.time;
    }

    @Override
    public boolean isSectionEmpty(final int sy)
    {
        return (sy < 0) || (sy >= this.sections.length) || (this.sections[sy] == null);
    }

    @Override
    public int getBlockTypeId(final int x, final int y, final int z)
    {
        final ChunkPartImpl section = this.getSection(y);
        return (section == null) ? 0 : section.getBlockType(x, y, z).ordinal();
    }

    @Override
    public int getBlockData(final int x, final int y, final int z)
    {
        final ChunkPartImpl section = this.getSection(y);
        return (section == null) ? 0 : section.getBlockType(x, y, z).getType();
    }

    @Override
    public BlockMaterialData getBlockType(final int x, final int y, final int z)
    {
        final ChunkPartImpl section = this.getSection(y);
        return (section == null) ? null : section.getBlockType(x, y, z);
    }

    @Override
    public int getBlockSkyLight(final int x, final int y, final int z)
    {
        final ChunkPartImpl section = this.getSection(y);
        return (section == null) ? MAX_SKY_LIGHT : section.getSkyLight().get(ChunkPartImpl.toArrayIndex(x, y, z));
    }

    @Override
    public int getBlockEmittedLight(final int x, final int y, final int z)
    {
        final ChunkPartImpl section = this.getSection(y);
        return (section == null) ? 0 : section.getBlockLight().get(ChunkPartImpl.toArrayIndex(x, y, z));
    }

    @Override
    public int getHighestBlockYAt(final int x, final int z)
    {
        return this.height[this.coordToIndex(x, z)];
    }

    @Override
    public Biome getBiome(final int x, final int z)
    {
        return Biome.getByBiomeId(this.biomes[this.coordToIndex(x, z)]);
    }

    @Override
    public double getRawBiomeTemperature(final int x, final int z)
    {
        return this.temp[this.coordToIndex(x, z)];
    }

    @Override
    public double getRawBiomeRainfall(final int x, final int z)
    {
        return this.humid[this.coordToIndex(x, z)];
    }

    private int coordToIndex(final int x, final int z)
    {
        if ((x < 0) || (z < 0) || (x >= Chunk.CHUNK_SIZE) || (z >= Chunk.CHUNK_SIZE))
        {
            throw new IndexOutOfBoundsException();
        }

        return (z * Chunk.CHUNK_SIZE) + x;
    }

    public static class EmptySnapshot extends ChunkSnapshotImpl
    {

        public EmptySnapshot(final int x, final int z, final World world, final boolean svBiome, final boolean svTemp)
        {
            super(x, z, world, null, null, svBiome ? new byte[Chunk.CHUNK_BIOMES_SIZE] : null, svTemp);
        }

        @Override
        public int getBlockTypeId(final int x, final int y, final int z)
        {
            return 0;
        }

        @Override
        public int getBlockData(final int x, final int y, final int z)
        {
            return 0;
        }

        @Override
        public int getBlockSkyLight(final int x, final int y, final int z)
        {
            return MAX_SKY_LIGHT;
        }

        @Override
        public int getBlockEmittedLight(final int x, final int y, final int z)
        {
            return 0;
        }

        @Override
        public int getHighestBlockYAt(final int x, final int z)
        {
            return 0;
        }

        @Override
        public BlockMaterialData getBlockType(final int x, final int y, final int z)
        {
            return AirMat.AIR;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).append("world", this.world).append("time", this.time).toString();
    }
}
