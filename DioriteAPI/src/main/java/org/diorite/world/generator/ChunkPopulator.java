package org.diorite.world.generator;

import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

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

    /**
     * Additional method, is used before creating {@link Chunk} from {@link ChunkBuilder}
     * Data like height map, light or entities is not available yet.
     * Editing {@link ChunkBuilder} is faster than normal {@link Chunk} .
     *
     * @param chunkBuilder chunk to populate
     * @param pos x, z and world of chunk.
     */
    default void prePopulate(final ChunkBuilder chunkBuilder, final ChunkPos pos)
    {
        //
    }
}
