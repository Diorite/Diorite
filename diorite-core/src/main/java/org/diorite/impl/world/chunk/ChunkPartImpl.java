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

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.world.chunk.pattern.PatternImpl;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.collections.arrays.NibbleArray;
import org.diorite.world.chunk.Chunk;

public class ChunkPartImpl // part of chunk 16x16x16
{
    public static final int CHUNK_DATA_SIZE = Chunk.CHUNK_SIZE * Chunk.CHUNK_PART_HEIGHT * Chunk.CHUNK_SIZE;
    private final byte        yPos; // from 0 to 15
    private final PatternImpl pattern;
    private final ChunkBuffer chunkBuffer;
    private       NibbleArray skyLight;
    private       NibbleArray blockLight;

    public ChunkPartImpl(final byte yPos, final boolean hasSkyLight)
    {
        this.yPos = yPos;
        this.pattern = new PatternImpl();
        this.chunkBuffer = new ChunkBuffer(this.pattern.bitsPerBlock());
        this.blockLight = new NibbleArray(CHUNK_DATA_SIZE);
        if (hasSkyLight)
        {
            this.skyLight = new NibbleArray(CHUNK_DATA_SIZE);
            //noinspection MagicNumber
            this.skyLight.fill((byte) 0xf);
        }
        this.blockLight.fill((byte) 0x0);
    }

    public ChunkPartImpl(final ChunkBuffer blocks, final PatternImpl pattern, final byte yPos, final boolean hasSkyLight)
    {
        this.pattern = pattern;
        this.chunkBuffer = blocks;
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

    public ChunkPartImpl(final ChunkBuffer blocks, final PatternImpl pattern, final NibbleArray skyLight, final NibbleArray blockLight, final byte yPos)
    {
        this.pattern = pattern;
        this.chunkBuffer = blocks;
        this.skyLight = skyLight;
        this.blockLight = blockLight;
        this.yPos = yPos;
    }

    public void write(final PacketDataSerializer data)
    {
        this.pattern.write(data);
        final byte[] bytes = this.chunkBuffer.getBytes();
        data.writeVarInt(bytes.length / 8);

    }

    public static class ChunkSectionData
    {
        public final PatternImpl pattern;
        public final byte[]      blocks;
        public final byte[]      blockLight;
        public final byte[]      skyLight;
        public final int         size;

        public ChunkSectionData(final ChunkPartImpl part, final boolean appendSkyLight)
        {
            int size = 0;
            this.pattern = part.getPattern();
            this.blocks = part.chunkBuffer.getBytes();
            this.blockLight = part.blockLight.getRawData();
            size = this.blocks.length + this.blockLight.length;
            if (appendSkyLight)
            {
                this.skyLight = part.skyLight.getRawData();
                size += this.skyLight.length;
            }
            else
            {
                this.skyLight = null;
            }
            this.size = size;
        }
    }

    public PatternImpl getPattern()
    {
        return this.pattern;
    }

    public ChunkBuffer getChunkBuffer()
    {
        return this.chunkBuffer;
    }

    /**
     * Take a snapshot of this section which will not reflect future changes.
     *
     * @return snapshot of this section which will not reflect future changes.
     */
    public ChunkPartImpl snapshot()
    {
        return new ChunkPartImpl(this.chunkBuffer.clone(), this.pattern.clone(), this.skyLight.snapshot(), this.blockLight.snapshot(), this.yPos);
    }

    public BlockMaterialData setBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        final BlockMaterialData old = this.getBlockType(x, y, z);
        if ((id == old.ordinal()) && (meta == old.getType()))
        {
            return old;
        }
        this.chunkBuffer.set(toArrayIndex(x, y, z), this.pattern.put(id, (byte) meta));
        return old;
    }

    @SuppressWarnings("MagicNumber")
    public BlockMaterialData rawSetBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        final int data = this.chunkBuffer.getAndSet(toArrayIndex(x, y, z), this.pattern.put(id, (byte) meta));
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
        final int data = this.chunkBuffer.get(toArrayIndex(x, y, z));
        final BlockMaterialData type = (BlockMaterialData) Material.getByID(data >> 4, data & 15);
        return (type == null) ? Material.AIR : type;
    }
//
//    public AtomicShortArray getBlocks()
//    {
//        return this.blocks;
//    }
//
//    public void setBlocks(final AtomicShortArray blocks)
//    {
//        this.blocks = blocks;
//    }

    public int recalculateBlockCount()
    {
        this.chunkBuffer.recalculateNonZero();
        return this.chunkBuffer.nonZero();
    }

    public int getBlocksCount()
    {
        return this.chunkBuffer.nonZero();
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
        return this.chunkBuffer.nonZero() == 0;
    }

    @SuppressWarnings("MagicNumber")
    public static int toArrayIndex(final int x, final int y, final int z)
    {
        return ((y & 0xf) << 8) | (z << 4) | x;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("yPos", this.yPos).append("blocks", this.chunkBuffer).append("skyLight", this.skyLight).append("blockLight", this.blockLight).toString();
    }
}
