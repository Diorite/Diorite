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

package org.diorite.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.world.chunk.Chunk;

/**
 * Special object used to store blocks to change
 */
public class BlockBag
{
    private final Map<Long, BlockMaterialData[/* X */][/* Z */][/* Y */]> data;

    public BlockBag(final Map<Long, BlockMaterialData[][][]> data)
    {
        this.data = data;
    }

    public Map<Long, BlockMaterialData[][][]> getData()
    {
        return this.data;
    }

    public static class Builder
    {
        private final Map<BlockLocation, BlockMaterialData> data = new HashMap<>(100, .5f);
        private final World world;

        private Builder(final World world)
        {
            this.world = world;
        }

        public static Builder start(final World world)
        {
            return new Builder(world);
        }

        public Builder remove(final BlockLocation loc)
        {
            this.data.remove(loc);
            return this;
        }

        public Builder add(final BlockLocation loc, final BlockMaterialData mat)
        {
            if (mat == null)
            {
                this.data.remove(loc);
            }
            else
            {
                this.data.put(loc, mat);
            }
            return this;
        }

        public Builder add(final int x, final int y, final int z, final BlockMaterialData mat)
        {
            return this.add(new BlockLocation(x, y, z, this.world), mat);
        }

        public BlockBag build()
        {
            final Map<Long, BlockMaterialData[][][]> data = new HashMap<>(25);
            for (final Entry<BlockLocation, BlockMaterialData> entry : this.data.entrySet())
            {
                final BlockLocation loc = entry.getKey();
                BlockMaterialData[][][] blocks = data.get(loc.getChunkPos().asLong());
                if (blocks == null)
                {
                    blocks = new BlockMaterialData[Chunk.CHUNK_SIZE][Chunk.CHUNK_FULL_HEIGHT][Chunk.CHUNK_SIZE];
                    data.put(loc.getChunkPos().asLong(), blocks);
                }
                blocks[loc.getX() & (Chunk.CHUNK_SIZE - 1)][loc.getZ() & (Chunk.CHUNK_SIZE - 1)][loc.getY()] = entry.getValue();
            }
            return new BlockBag(data);
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("data", this.data.size()).append("world", this.world).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("data", this.data).toString();
    }
}
