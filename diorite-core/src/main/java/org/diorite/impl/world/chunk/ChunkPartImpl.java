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
import org.diorite.material.Material;
import org.diorite.utils.collections.arrays.NibbleArray;
import org.diorite.utils.concurrent.atomic.AtomicShortArray;
import org.diorite.world.chunk.Chunk;

public class ChunkPartImpl // part of chunk 16x16x16
{
    public static final int CHUNK_DATA_SIZE = Chunk.CHUNK_SIZE * Chunk.CHUNK_PART_HEIGHT * Chunk.CHUNK_SIZE;
    private final    byte             yPos; // from 0 to 15
    private volatile int              blocksCount;
    private          AtomicShortArray blocks; // id and sub-id(0-15) of every block
    private          NibbleArray      skyLight;
    private          NibbleArray      blockLight;

    public ChunkPartImpl(final byte yPos, final boolean hasSkyLight)
    {
        this.yPos = yPos;
        this.blocks = new AtomicShortArray(CHUNK_DATA_SIZE);
        this.blockLight = new NibbleArray(CHUNK_DATA_SIZE);
        if (hasSkyLight)
        {
            this.skyLight = new NibbleArray(CHUNK_DATA_SIZE);
            //noinspection MagicNumber
            this.skyLight.fill((byte) 0xf);
        }
        this.blockLight.fill((byte) 0x0);
    }

    public ChunkPartImpl(final AtomicShortArray blocks, final byte yPos, final boolean hasSkyLight)
    {
        this.blocks = blocks;
        this.blockLight = new NibbleArray(CHUNK_DATA_SIZE);
        this.yPos = yPos;
        if (hasSkyLight)
        {
            this.skyLight = new NibbleArray(CHUNK_DATA_SIZE);
            //noinspection MagicNumber
            this.skyLight.fill((byte) 0xf);
        }
        this.blockLight.fill((byte) 0x0);
    }

    public ChunkPartImpl(final AtomicShortArray blocks, final NibbleArray skyLight, final NibbleArray blockLight, final byte yPos)
    {
        this.blocks = blocks;
        this.skyLight = skyLight;
        this.blockLight = blockLight;
        this.yPos = yPos;
    }

    /**
     * Take a snapshot of this section which will not reflect future changes.
     *
     * @return snapshot of this section which will not reflect future changes.
     */
    public ChunkPartImpl snapshot()
    {
        return new ChunkPartImpl(new AtomicShortArray(this.blocks.getArray()), this.skyLight.snapshot(), this.blockLight.snapshot(), this.yPos);
    }

    public BlockMaterialData setBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        final BlockMaterialData old = this.getBlockType(x, y, z);
        if ((id == old.ordinal()) && (meta == old.getType()))
        {
            return old;
        }
        // TODO: check this
        if (this.blocks.compareAndSet(toArrayIndex(x, y, z), (short) ((old.ordinal() << 4) | old.getType()), (short) ((id << 4) | meta)))
        {
            if (old.getType() != 0)
            {
                if (id == 0)
                {
                    this.blocksCount--;
                }
            }
            else if (id != 0)
            {
                this.blocksCount++;
            }
            return old;
        }
        return this.getBlockType(x, y, z);
    }

    @SuppressWarnings("MagicNumber")
    public BlockMaterialData rawSetBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        final short data = this.blocks.getAndSet(toArrayIndex(x, y, z), (short) ((id << 4) | meta));
        final BlockMaterialData type = (BlockMaterialData) Material.getByID(data >> 4, data & 15);
        return (type == null) ? Material.AIR : type;
    }

    public BlockMaterialData setBlock(final int x, final int y, final int z, final BlockMaterialData material)
    {
        return this.setBlock(x, y, z, material.ordinal(), material.getType());
    }

    @SuppressWarnings("MagicNumber")
    public BlockMaterialData getBlockType(final int x, final int y, final int z)
    {
        final short data = this.blocks.get(toArrayIndex(x, y, z));
        final BlockMaterialData type = (BlockMaterialData) Material.getByID(data >> 4, data & 15);
        return (type == null) ? Material.AIR : type;
    }

    public AtomicShortArray getBlocks()
    {
        return this.blocks;
    }

    public void setBlocks(final AtomicShortArray blocks)
    {
        this.blocks = blocks;
    }

    public int recalculateBlockCount()
    {
        this.blocksCount = 0;
        for (final short type : this.blocks.getArray())
        {
            if (type != 0)
            {
                this.blocksCount++;
            }
        }
        return this.blocksCount;
    }

    public int getBlocksCount()
    {
        return this.blocksCount;
    }

    public NibbleArray getBlockLight()
    {
        return this.blockLight;
    }

    public void setBlockLight(final NibbleArray blockLight)
    {
        this.blockLight = blockLight;
    }

    public NibbleArray getSkyLight()
    {
        return this.skyLight;
    }

    public void setSkyLight(final NibbleArray skyLight)
    {
        this.skyLight = skyLight;
    }

    public byte getYPos()
    {
        return this.yPos;
    }

    public boolean isEmpty()
    {
        return this.blocksCount == 0;
    }

    @SuppressWarnings("MagicNumber")
    public static int toArrayIndex(final int x, final int y, final int z)
    {
        return ((y & 0xf) << 8) | (z << 4) | x;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("yPos", this.yPos).append("blocks", this.blocks).append("skyLight", this.skyLight).append("blockLight", this.blockLight).append("blocksCount", this.blocksCount).toString();
    }
}
