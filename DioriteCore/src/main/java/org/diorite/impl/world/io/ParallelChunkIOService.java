package org.diorite.impl.world.io;

public interface ParallelChunkIOService extends ChunkIOService
{
    int getMaxThreads();

    void setMaxThreads(int threads);
}
