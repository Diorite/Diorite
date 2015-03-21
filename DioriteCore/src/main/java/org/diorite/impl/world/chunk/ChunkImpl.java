package org.diorite.impl.world.chunk;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.world.WorldImpl;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

public class ChunkImpl implements Chunk
{
    private final WorldImpl       world;
    private final ChunkPos        pos;
    private final ChunkPartImpl[] chunkParts; // size of 16, parts can be null
    private final byte[]          biomes;
    private final AtomicInteger usages = new AtomicInteger(0);

    public ChunkImpl(final WorldImpl world, final ChunkPos pos, final byte[] biomes, final ChunkPartImpl[] chunkParts)
    {
        this.world = world;
        this.pos = pos;
        this.biomes = biomes;
        this.chunkParts = chunkParts;
    }

    public ChunkImpl(final WorldImpl world, final ChunkPos pos, final ChunkPartImpl[] chunkParts)
    {
        this.world = world;
        this.pos = pos;
        this.chunkParts = chunkParts;
        this.biomes = new byte[CHUNK_SIZE * CHUNK_SIZE];
    }

    public ChunkImpl(final WorldImpl world, final ChunkPos pos)
    {
        this.world = world;
        this.pos = pos;
        this.chunkParts = new ChunkPartImpl[CHUNK_PARTS];
        this.biomes = new byte[CHUNK_SIZE * CHUNK_SIZE];
    }

    public void setBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        final byte chunkPosY = (byte) (y >> 4);
        ChunkPartImpl chunkPart = this.chunkParts[chunkPosY];
        if (chunkPart == null)
        {
            chunkPart = new ChunkPartImpl(this, chunkPosY);
            this.chunkParts[chunkPosY] = chunkPart;
        }
        chunkPart.setBlock(x, y >> 4, z, id, meta);
    }

    public BlockMaterialData getBlockType(final int x, final int y, final int z)
    {
        final byte chunkPosY = (byte) (y >> 4);
        final ChunkPartImpl chunkPart = this.chunkParts[chunkPosY];
        if (chunkPart == null)
        {
            return Material.AIR;
        }
        return chunkPart.getBlockType(x, y >> 4, z);
    }

    @Override
    public WorldImpl getWorld()
    {
        return this.world;
    }

    public int addUsage()
    {
        return this.usages.incrementAndGet();
    }

    public int removeUsage()
    {
        return this.usages.decrementAndGet();
    }

    public int getUsages()
    {
        return this.usages.intValue();
    }

    @Override
    public ChunkPos getPos()
    {
        return this.pos;
    }

    public byte[] getBiomes()
    {
        return this.biomes;
    }

//    public void setBlock(final int x, final int y, final int z, final int id, final int meta)
//    {
//        this.setBlock(x, y, z, id, meta);
//    }

    // set bit to 1: variable |= (1 << bit)
    // switch bit  : variable ^= (1 << bit)
    // set bit to 0: variable &= ~(1 << bit)
    public int getMask()
    {
        int mask = 0x0;
        for (int i = 0, chunkPartsLength = this.chunkParts.length; i < chunkPartsLength; i++)
        {
            if ((this.chunkParts[i] != null) && ! this.chunkParts[i].isEmpty())
            {
                mask |= (1 << i);
            }
        }
        return mask;
    }

    public void recalculateBlockCounts()
    {
        for (final ChunkPartImpl chunkPart : this.chunkParts)
        {
            if (chunkPart == null)
            {
                continue;
            }
            chunkPart.recalculateBlockCount();
        }
    }

    public ChunkPartImpl[] getChunkParts()
    {
        return this.chunkParts;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pos", this.pos).append("usages", this.usages).toString();
    }
}
