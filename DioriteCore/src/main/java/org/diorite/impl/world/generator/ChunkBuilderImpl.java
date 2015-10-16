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

package org.diorite.impl.world.generator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkPartImpl;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.concurrent.atomic.AtomicShortArray;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.generator.BiomeGrid;
import org.diorite.world.generator.ChunkBuilder;
import org.diorite.world.generator.maplayer.MapLayer;

public class ChunkBuilderImpl implements ChunkBuilder
{
    private final ChunkPartBuilder[] chunkParts = new ChunkPartBuilder[Chunk.CHUNK_PARTS]; // size of 16, parts can be null
    private MapLayer[] biomesInput;
    private BiomeGrid  biomeGrid;

    public ChunkBuilderImpl(final MapLayer[] biomesInput)
    {
        this.biomesInput = biomesInput;
    }

    @Override
    public MapLayer[] getBiomesInput()
    {
        return this.biomesInput;
    }

    @Override
    public void setBiomesInput(final MapLayer[] biomesInput)
    {
        this.biomesInput = biomesInput;
    }

    public ChunkPartBuilder[] getChunkParts()
    {
        return this.chunkParts;
    }

    @Override
    public BiomeGrid getBiomeGrid()
    {
        return this.biomeGrid;
    }

    @Override
    public void setBiomeGrid(final BiomeGrid biomeGrid)
    {
        this.biomeGrid = biomeGrid;
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final BlockMaterialData materialData)
    {
        final byte chunkPosY = (byte) (y >> 4);
        ChunkPartBuilder chunkPart = this.chunkParts[chunkPosY];
        if (chunkPart == null)
        {
            chunkPart = new ChunkPartBuilder(this, chunkPosY);
            this.chunkParts[chunkPosY] = chunkPart;
        }
        chunkPart.setBlock(x, y % Chunk.CHUNK_PART_HEIGHT, z, materialData);
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        final byte chunkPosY = (byte) (y >> 4);
        ChunkPartBuilder chunkPart = this.chunkParts[chunkPosY];
        if (chunkPart == null)
        {
            chunkPart = new ChunkPartBuilder(this, chunkPosY);
            this.chunkParts[chunkPosY] = chunkPart;
        }
        chunkPart.setBlock(x, y % Chunk.CHUNK_PART_HEIGHT, z, id, meta);
    }

    @Override
    public BlockMaterialData getBlockType(final int x, final int y, final int z)
    {
        final byte chunkPosY = (byte) (y >> 4);
        final ChunkPartBuilder chunkPart = this.chunkParts[chunkPosY];
        if (chunkPart == null)
        {
            return Material.AIR;
        }
        return chunkPart.getBlockType(x, y % Chunk.CHUNK_PART_HEIGHT, z);
    }

    @Override
    public void init(final Chunk bChunk)
    {
        final ChunkImpl chunk = (ChunkImpl) bChunk;
        final ChunkPartImpl[] chunkParts = new ChunkPartImpl[this.chunkParts.length];
        final ChunkPartBuilder[] chunkPartBuilders = this.chunkParts;
        for (int i = 0, buildersLength = chunkPartBuilders.length; i < buildersLength; i++)
        {
            final ChunkPartBuilder chunkPart = chunkPartBuilders[i];
            if (chunkPart == null)
            {
                continue;
            }
            chunkParts[i] = new ChunkPartImpl(chunkPart.blocks, (byte) i, chunk.getWorld().getDimension().hasSkyLight());
            chunkParts[i].recalculateBlockCount();
        }
        chunk.setChunkParts(chunkParts);
        chunk.initHeightMap();
        chunk.setBiomes(this.biomeGrid.rawData().clone());
        chunk.init();
    }

    private static class ChunkPartBuilder // part of chunk 16x16x16
    {
        public static final int CHUNK_DATA_SIZE = Chunk.CHUNK_SIZE * Chunk.CHUNK_PART_HEIGHT * Chunk.CHUNK_SIZE;
        private final ChunkBuilderImpl chunk;
        private final AtomicShortArray blocks; // id and sub-id(0-15) of every block
        private final byte             yPos; // from 0 to 15

        private ChunkPartBuilder(final ChunkBuilderImpl chunk, final byte yPos)
        {
            this.chunk = chunk;
            this.yPos = yPos;
            this.blocks = new AtomicShortArray(CHUNK_DATA_SIZE);
        }

        private void setBlock(final int x, final int y, final int z, final int id, final int meta)
        {
            this.blocks.set(this.toArrayIndex(x, y, z), (short) ((id << 4) | meta));
        }

        private void setBlock(final int x, final int y, final int z, final BlockMaterialData material)
        {
            this.setBlock(x, y, z, material.ordinal(), material.getType());
        }

        @SuppressWarnings("MagicNumber")
        private BlockMaterialData getBlockType(final int x, final int y, final int z)
        {
            final short data = this.blocks.get(this.toArrayIndex(x, y, z));
            return (BlockMaterialData) Material.getByID(data >> 4, data & 15);
        }

        @SuppressWarnings("MagicNumber")
        private int toArrayIndex(final int x, final int y, final int z)
        {
            return ((y & 0xf) << 8) | (z << 4) | x;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunk", this.chunk).append("yPos", this.yPos).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}
