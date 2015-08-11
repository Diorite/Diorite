package org.diorite.world.chunk;

import org.diorite.material.BlockMaterialData;
import org.diorite.scheduler.Synchronizable;
import org.diorite.world.Biome;
import org.diorite.world.Block;
import org.diorite.world.World;

public interface Chunk extends Synchronizable
{
    int CHUNK_SIZE        = 16;
    int CHUNK_PARTS       = 16;
    int CHUNK_PART_HEIGHT = 16;
    int CHUNK_FULL_HEIGHT = 256;
    int CHUNK_BIOMES_SIZE = CHUNK_SIZE * CHUNK_SIZE;

    Biome getBiome(int x, int y, int z) // y is ignored, added for future possible changes.
    ;

    boolean isLoaded();

    boolean load();

    boolean load(boolean generate);

    boolean unload();

    boolean unload(boolean save);

    boolean unload(boolean save, boolean safe);

    boolean isPopulated();

    boolean populate();

    void initHeightMap();

//    BlockMaterialData setBlock(int x, int y, int z, BlockMaterialData materialData);
//
//    BlockMaterialData setBlock(int x, int y, int z, int id, int meta);

    BlockMaterialData getBlockType(int x, int y, int z);

    BlockMaterialData getHighestBlockType(int x, int z);

    Block getBlock(int x, int y, int z);

    Block getHighestBlock(int x, int z);

    int getHighestBlockY(int x, int z);

    World getWorld();

    ChunkPos getPos();

    int getX();

    int getZ();

//    void recalculateBlockCounts();
}
