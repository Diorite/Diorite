package org.diorite.impl.world.generator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkPartImpl;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.ChunkBuilder;

public class ChunkBuilderImpl implements ChunkBuilder
{
    private final ChunkPartBuilder[] chunkParts = new ChunkPartBuilder[Chunk.CHUNK_PARTS]; // size of 16, parts can be null
    private final byte[]             biomes     = new byte[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];

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
    public byte[] getBiomes()
    {
        return this.biomes;
    }

    @Override
    public ChunkImpl createChunk(final ChunkPos pos)
    {
        final ChunkImpl chunk = new ChunkImpl(pos, this.biomes);
        final ChunkPartImpl[] chunkParts = new ChunkPartImpl[this.chunkParts.length];
        final ChunkPartBuilder[] chunkPartBuilders = this.chunkParts;
        for (int i = 0, buildersLength = chunkPartBuilders.length; i < buildersLength; i++)
        {
            final ChunkPartBuilder chunkPart = chunkPartBuilders[i];
            if (chunkPart == null)
            {
                continue;
            }
            chunkParts[i] = new ChunkPartImpl(chunk, chunkPart.blocks, (byte) i, chunk.getWorld().getDimension().hasSkyLight());
            chunkParts[i].recalculateBlockCount();
            chunk.getChunkParts()[i] = chunkParts[i];
        }
        chunk.initHeightMap();
        return chunk;
    }

    private static class ChunkPartBuilder // part of chunk 16x16x16
    {
        public static final int CHUNK_DATA_SIZE = Chunk.CHUNK_SIZE * Chunk.CHUNK_PART_HEIGHT * Chunk.CHUNK_SIZE;
        private final ChunkBuilderImpl chunk;
        private final char[]           blocks; // id and sub-id(0-15) of every block
        private final byte             yPos; // from 0 to 15

        private ChunkPartBuilder(final ChunkBuilderImpl chunk, final byte yPos)
        {
            this.chunk = chunk;
            this.yPos = yPos;
            this.blocks = new char[CHUNK_DATA_SIZE];
        }

        private void setBlock(final int x, final int y, final int z, final int id, final int meta)
        {
            this.blocks[this.toArrayIndex(x, y, z)] = (char) ((id << 4) | meta);
        }

        private void setBlock(final int x, final int y, final int z, final BlockMaterialData material)
        {
            this.setBlock(x, y, z, material.getId(), material.getType());
        }

        @SuppressWarnings("MagicNumber")
        private BlockMaterialData getBlockType(final int x, final int y, final int z)
        {
            final char data = this.blocks[this.toArrayIndex(x, y, z)];
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
