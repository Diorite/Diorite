package org.diorite.world.chunk;

import java.util.List;

public interface ChunkManager
{
    Chunk getChunk(final ChunkPos pos);

    Chunk getChunk(int x, int z);

    boolean isChunkLoaded(int x, int z);

    boolean isChunkInUse(int x, int z);

    boolean loadChunk(int x, int z, boolean generate);

    void unloadOldChunks();

    void populateChunk(int x, int z, boolean force);

    void forcePopulation(int x, int z);

    void generateChunk(Chunk chunk, int x, int z);

    boolean forceRegeneration(int x, int z);

    List<? extends Chunk> getLoadedChunks();

    boolean save(Chunk chunk);
}
