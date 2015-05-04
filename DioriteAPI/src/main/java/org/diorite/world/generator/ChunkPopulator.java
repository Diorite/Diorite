package org.diorite.world.generator;

import org.diorite.world.chunk.Chunk;

/**
 * Used by generator to place all structures, like trees, ores, villages etc..
 */
@FunctionalInterface
public interface ChunkPopulator
{
    /**
     * Main populate method, used after generating and creating {@link Chunk}.
     *
     * @param chunk {@link Chunk} to populate
     */
    void populate(Chunk chunk);
}
