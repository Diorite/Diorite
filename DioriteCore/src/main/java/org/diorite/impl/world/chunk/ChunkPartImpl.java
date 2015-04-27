package org.diorite.impl.world.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.collections.NibbleArray;
import org.diorite.world.chunk.Chunk;

public class ChunkPartImpl // part of chunk 16x16x16
{
    public static final int CHUNK_DATA_SIZE = Chunk.CHUNK_SIZE * Chunk.CHUNK_PART_HEIGHT * Chunk.CHUNK_SIZE;
    private final    ChunkImpl   chunk;
    private final    byte        yPos; // from 0 to 15
    private volatile int         blocksCount;
    private          char[]      blocks; // id and sub-id(0-15) of every block
    private          NibbleArray skyLight;
    private          NibbleArray blockLight;

    public ChunkPartImpl(final ChunkImpl chunk, final byte yPos, final boolean hasSkyLight)
    {
        this.chunk = chunk;
        this.yPos = yPos;
        this.blocks = new char[CHUNK_DATA_SIZE];
        this.blockLight = new NibbleArray(CHUNK_DATA_SIZE);
        if (hasSkyLight)
        {
            this.skyLight = new NibbleArray(CHUNK_DATA_SIZE);
            //noinspection MagicNumber
            this.skyLight.fill((byte) 0xf);
        }
        this.blockLight.fill((byte) 0x0);
    }

    public ChunkPartImpl(final ChunkImpl chunk, final char[] blocks, final byte yPos, final boolean hasSkyLight)
    {
        this.chunk = chunk;
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

    public ChunkPartImpl(final ChunkImpl chunk, final char[] blocks, final NibbleArray skyLight, final NibbleArray blockLight, final byte yPos)
    {
        this.chunk = chunk;
        this.blocks = blocks;
        this.skyLight = skyLight;
        this.blockLight = blockLight;
        this.yPos = yPos;
    }

    public void setBlocks(final char[] blocks)
    {
        this.blocks = blocks;
    }

    public void setBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        //Main.debug("setBlock(" + x + ", " + y + ", " + z + ", " + id + ", " + meta + ") -> " + this.yPos + ", " + this.hashCode());
        final BlockMaterialData old = this.getBlockType(x, y, z);
        if ((id == old.getId()) && (meta == old.getType()))
        {
            return;
        }
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
        this.blocks[this.toArrayIndex(x, y, z)] = (char) ((id << 4) | meta);
    }

    public void setBlock(final int x, final int y, final int z, final BlockMaterialData material)
    {
        this.setBlock(x, y, z, material.getId(), material.getType());
    }

    @SuppressWarnings("MagicNumber")
    public BlockMaterialData getBlockType(final int x, final int y, final int z)
    {
        final char data = this.blocks[this.toArrayIndex(x, y, z)];
        return (BlockMaterialData) Material.getByID(data >> 4, data & 15);
    }

    public void setBlockLight(final NibbleArray blockLight)
    {
        this.blockLight = blockLight;
    }

    public void setSkyLight(final NibbleArray skyLight)
    {
        this.skyLight = skyLight;
    }

    public ChunkImpl getChunk()
    {
        return this.chunk;
    }

    public char[] getBlocks()
    {
        return this.blocks;
    }

    public int recalculateBlockCount()
    {
        this.blocksCount = 0;
        for (final char type : this.blocks)
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

    public NibbleArray getSkyLight()
    {
        return this.skyLight;
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
    public int toArrayIndex(final int x, final int y, final int z)
    {
        return ((y & 0xf) << 8) | (z << 4) | x;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("yPos", this.yPos).append("blocks", this.blocks).append("skyLight", this.skyLight).append("blockLight", this.blockLight).append("blocksCount", this.blocksCount).toString();
    }
}
