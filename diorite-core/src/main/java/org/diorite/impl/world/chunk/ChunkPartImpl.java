/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
import org.diorite.impl.world.chunk.palette.Palette;
import org.diorite.impl.world.chunk.palette.PaletteImpl;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.collections.arrays.NibbleArray;
import org.diorite.world.chunk.Chunk;

public class ChunkPartImpl // part of chunk 16x16x16
{
    public static final int CHUNK_DATA_SIZE = Chunk.CHUNK_SIZE * Chunk.CHUNK_PART_HEIGHT * Chunk.CHUNK_SIZE;
    private final byte           yPos; // from 0 to 15
    private final PaletteImpl    palette;
    private final ChunkBlockData chunkBlockData;
    private       NibbleArray    skyLight;
    private       NibbleArray    blockLight;
    private       int            nonEmptyBlockCount;

    public ChunkPartImpl(final byte yPos, final boolean hasSkyLight)
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

    public ChunkPartImpl(final ChunkBlockData blocks, final PaletteImpl palette, final byte yPos, final boolean hasSkyLight, final int nonNull)
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

    public ChunkPartImpl(final ChunkBlockData blocks, final PaletteImpl palette, final NibbleArray skyLight, final NibbleArray blockLight, final byte yPos)
    {
        this.palette = palette;
        this.chunkBlockData = blocks;
        this.skyLight = skyLight;
        this.blockLight = blockLight;
        this.yPos = yPos;
    }

    public void write(final PacketDataSerializer data)
    {
        final Palette palette = this.palette;
        palette.write(data);
        final long[] longs = this.chunkBlockData.getDataArray();
        data.writeVarInt(longs.length);
        for (final long aLong : longs)
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
        return new ChunkPartImpl(this.chunkBlockData.clone(), this.palette.clone(), this.skyLight.snapshot(), this.blockLight.snapshot(), this.yPos);
    }

    public BlockMaterialData setBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        final BlockMaterialData old = this.getBlockType(x, y, z);
        if ((id == old.ordinal()) && (meta == old.getType()))
        {
            return old;
        }
        this.chunkBlockData.set(toArrayIndex(x, y, z), this.palette.put(id, (byte) meta));

        if ((old.getId() == 0) && (id != 0))
        {
            this.nonEmptyBlockCount++;
        }
        else if ((old.getId() != 0) && (id == 0))
        {
            this.nonEmptyBlockCount--;
        }

        return old;
    }

    public BlockMaterialData rawSetBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        final BlockMaterialData type = this.chunkBlockData.getAndSet(toArrayIndex(x, y, z), this.palette.put(id, (byte) meta), this.palette);
        return (type == null) ? Material.AIR : type;
    }

    public BlockMaterialData setBlock(final int x, final int y, final int z, final BlockMaterialData material)
    {
        return this.setBlock(x, y, z, material.ordinal(), material.getType());
    }

    public BlockMaterialData getBlockType(final int x, final int y, final int z)
    {
        final BlockMaterialData type = this.chunkBlockData.get(toArrayIndex(x, y, z), this.palette);
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
        this.nonEmptyBlockCount = 0;

        for (int i = 0; i < CHUNK_DATA_SIZE; i++)
        {
            final BlockMaterialData type = this.chunkBlockData.get(i, this.palette);
            if ((type != null) && ! type.isThisSameID(Material.AIR))
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
        return this.nonEmptyBlockCount == 0;
    }

    @SuppressWarnings("MagicNumber")
    public static int toArrayIndex(final int x, final int y, final int z)
    {
        return ((y & 0xf) << 8) | (z << 4) | x;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("yPos", this.yPos).append("blocks", this.chunkBlockData).append("skyLight", this.skyLight).append("blockLight", this.blockLight).toString();
    }
}
