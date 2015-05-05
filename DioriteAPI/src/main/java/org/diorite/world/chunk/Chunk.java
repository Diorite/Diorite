package org.diorite.world.chunk;

import org.diorite.material.BlockMaterialData;
import org.diorite.world.Block;
import org.diorite.world.World;

public interface Chunk
{
    int CHUNK_SIZE        = 16;
    int CHUNK_PARTS       = 16;
    int CHUNK_PART_HEIGHT = 16;
    int CHUNK_FULL_HEIGHT = 256;
    int CHUNK_BIOMES_SIZE = CHUNK_SIZE * CHUNK_SIZE;

    boolean isPopulated();

    void populate();

    void initHeightMap();

    void setBlock(int x, int y, int z, BlockMaterialData materialData);

    void setBlock(int x, int y, int z, int id, int meta);

    BlockMaterialData getBlockType(int x, int y, int z);

    BlockMaterialData getHighestBlockType(int x, int z);

    Block getBlock(int x, int y, int z);

    Block getHighestBlock(int x, int z);

    int getHighestBlockY(int x, int z);

    World getWorld();

    int getUsages();

    ChunkPos getPos();

    int getX();

    int getZ();

    void recalculateBlockCounts();

    int addUsage();

    int removeUsage();
}
