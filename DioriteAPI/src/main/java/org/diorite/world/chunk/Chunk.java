package org.diorite.world.chunk;

import org.diorite.world.World;

public interface Chunk
{
    int CHUNK_SIZE        = 16;
    int CHUNK_PARTS       = 16;
    int CHUNK_PART_HEIGHT = 16;
    int CHUNK_FULL_HEIGHT = 256;
    int CHUNK_BIOMES_SIZE = CHUNK_SIZE * CHUNK_SIZE;

    World getWorld();

    ChunkPos getPos();
}
