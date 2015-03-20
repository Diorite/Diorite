package org.diorite.world;

import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.world.chunk.ChunkManager;

public interface World
{
    ChunkManager getChunkManager();

    void setBlock(int x, int y, int z, BlockMaterialData material);

    void setBlock(BlockLocation location, BlockMaterialData material);
}
