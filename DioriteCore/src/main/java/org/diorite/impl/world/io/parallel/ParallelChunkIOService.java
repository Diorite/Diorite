package org.diorite.impl.world.io.parallel;

import org.diorite.impl.world.io.ChunkIOService;

public interface ParallelChunkIOService extends ChunkIOService
{
    int getMaxThreads();

    void setMaxThreads(int threads);
}
