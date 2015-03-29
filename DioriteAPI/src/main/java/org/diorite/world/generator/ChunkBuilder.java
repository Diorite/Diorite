package org.diorite.world.generator;

import org.diorite.material.BlockMaterialData;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

public interface ChunkBuilder
{
    void setBlock(int x, int y, int z, BlockMaterialData materialData);

    void setBlock(int x, int y, int z, int id, int meta);

    BlockMaterialData getBlockType(int x, int y, int z);

    byte[] getBiomes();

    Chunk createChunk(ChunkPos chunkPos);
}
