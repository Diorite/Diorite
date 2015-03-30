package org.diorite.world.chunk;

public interface ChunkManager
{
    void unloadAll();

    void saveAll();

    void unload(Chunk chunk);

    Chunk getChunkAt(ChunkPos pos);

    Chunk getChunkAt(int x, int z);

    Chunk getChunkAt(ChunkPos pos, boolean generate);

    Chunk getChunkAt(int x, int z, boolean generate);
}
