/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.core.world.chunk;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.commons.arrays.NibbleArray;
import org.diorite.core.material.InternalBlockType;
import org.diorite.core.protocol.AbstractPacketDataSerializer;
import org.diorite.core.world.chunk.palette.Palette;
import org.diorite.core.world.chunk.palette.PaletteImpl;
import org.diorite.material.Blocks;

class ChunkPartImpl // part of chunk 16x16x16
{
    public static final int CHUNK_DATA_SIZE = 16 * 16 * 16;
    private final     byte           yPos; // from 0 to 15
    private final     PaletteImpl    palette;
    private final     ChunkBlockData chunkBlockData;
    private @Nullable NibbleArray    skyLight;
    private           NibbleArray    blockLight;
    private           int            nonEmptyBlockCount;

    ChunkPartImpl(byte yPos, boolean hasSkyLight)
    {
        this.yPos = yPos;
        this.palette = new PaletteImpl();
        this.chunkBlockData = new ChunkBlockData(this.palette.bitsPerBlock(), CHUNK_DATA_SIZE);
        this.blockLight = new NibbleArray(CHUNK_DATA_SIZE);
        if (hasSkyLight)
        {
            this.skyLight = new NibbleArray(CHUNK_DATA_SIZE);
            //noinspection MagicNumber
            this.skyLight.fill((byte) 0xf);
        }
        this.blockLight.fill((byte) 0x0);
    }

    ChunkPartImpl(ChunkBlockData blocks, PaletteImpl palette, byte yPos, boolean hasSkyLight, int nonNull)
    {
        this.palette = palette;
        this.chunkBlockData = blocks;
        this.blockLight = new NibbleArray(CHUNK_DATA_SIZE);
        this.yPos = yPos;
        if (hasSkyLight)
        {
            this.skyLight = new NibbleArray(CHUNK_DATA_SIZE);
            //noinspection MagicNumber
            this.skyLight.fill((byte) 0xf);
        }
        this.blockLight.fill((byte) 0x0);
        if (nonNull == - 1)
        {
            this.recalculateBlockCount();
        }
        else
        {
            this.nonEmptyBlockCount = nonNull;
        }
    }

    ChunkPartImpl(ChunkBlockData blocks, PaletteImpl palette, @Nullable NibbleArray skyLight, NibbleArray blockLight, byte yPos)
    {
        this.palette = palette;
        this.chunkBlockData = blocks;
        this.skyLight = skyLight;
        this.blockLight = blockLight;
        this.yPos = yPos;
    }

    public void write(AbstractPacketDataSerializer data)
    {
        Palette palette = this.palette;
        palette.write(data);
        long[] longs = this.chunkBlockData.getDataArray();
        data.writeVarInt(longs.length);
        for (long aLong : longs)
        {
            data.writeLong(aLong);
        }
    }

    public PaletteImpl getPalette()
    {
        return this.palette;
    }

    public ChunkBlockData getBlockData()
    {
        return this.chunkBlockData;
    }

    /**
     * Take a snapshot of this section which will not reflect future changes.
     *
     * @return snapshot of this section which will not reflect future changes.
     */
    public ChunkPartImpl snapshot()
    {
        return new ChunkPartImpl(this.chunkBlockData.clone(), this.palette.clone(), (this.skyLight == null) ? null : this.skyLight.snapshot(),
                                 this.blockLight.snapshot(), this.yPos);
    }

    public InternalBlockType setBlock(int x, int y, int z, int id, int meta)
    {
        InternalBlockType old = this.getBlockType(x, y, z);
        if ((id == old.getMinecraftId()) && (meta == old.getMinecraftId()))
        {
            return old;
        }
        this.chunkBlockData.set(toArrayIndex(x, y, z), this.palette.put(id, (byte) meta));

        if ((old.getMinecraftId() == 0) && (id != 0))
        {
            this.nonEmptyBlockCount++;
        }
        else if ((old.getMinecraftId() != 0) && (id == 0))
        {
            this.nonEmptyBlockCount--;
        }

        return old;
    }

    public InternalBlockType rawSetBlock(int x, int y, int z, int id, int meta)
    {
        return this.chunkBlockData.getAndSet(toArrayIndex(x, y, z), this.palette.put(id, (byte) meta), this.palette);
    }

    public InternalBlockType setBlock(int x, int y, int z, InternalBlockType material)
    {
        return this.setBlock(x, y, z, material.getMinecraftId(), material.getMinecraftData());
    }

    public InternalBlockType getBlockType(int x, int y, int z)
    {
        return this.chunkBlockData.get(toArrayIndex(x, y, z), this.palette);
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
        this.nonEmptyBlockCount = 0;

        for (int i = 0; i < CHUNK_DATA_SIZE; i++)
        {
            InternalBlockType type = this.chunkBlockData.get(i, this.palette);
            if (type.getMinecraftId() != ((InternalBlockType) Blocks.AIR).getMinecraftId())
            {
                this.nonEmptyBlockCount++;
            }
        }
        return this.nonEmptyBlockCount;
    }

    public int getBlocksCount()
    {
        return this.nonEmptyBlockCount;
    }

    public NibbleArray getBlockLight()
    {
        return this.blockLight;
    }

    public void setBlockLight(NibbleArray blockLight)
    {
        this.blockLight = blockLight;
    }

    @Nullable
    public NibbleArray getSkyLight()
    {
        return this.skyLight;
    }

    public void setSkyLight(NibbleArray skyLight)
    {
        this.skyLight = skyLight;
    }

    public byte getYPos()
    {
        return this.yPos;
    }

    public boolean isEmpty()
    {
        return this.nonEmptyBlockCount == 0;
    }

    @SuppressWarnings("MagicNumber")
    public static int toArrayIndex(int x, int y, int z)
    {
        return ((y & 0xf) << 8) | (z << 4) | x;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("yPos", this.yPos)
                                                                          .append("blocks", this.chunkBlockData).append("skyLight", this.skyLight)
                                                                          .append("blockLight", this.blockLight).toString();
    }
}
