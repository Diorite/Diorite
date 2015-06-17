package org.diorite.world;

import org.diorite.world.chunk.Chunk;

public interface TickGroup
{
    void doTick(int tps);

    void tickChunk(Chunk chunk, int tps);
}
