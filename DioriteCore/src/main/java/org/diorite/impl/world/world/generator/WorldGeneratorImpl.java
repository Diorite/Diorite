package org.diorite.impl.world.world.generator;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.world.chunk.Chunk;

public class WorldGeneratorImpl // temp code, TODO
{
    public void generateChunk(final ChunkImpl chunk)
    {
        for (int x = 0; x < Chunk.CHUNK_SIZE; x++)
        {
            for (int z = 0; z < Chunk.CHUNK_SIZE; z++)
            {
                for (int y = 0; y < 4; y++)
                {
                    if (y == 3) // some grass and dirt to faster testing (block break time)
                    {
                        chunk.setBlock(x, y, z, 2, 0);
                    }
                    else if (y < 1)
                    {
                        chunk.setBlock(x, y, z, 1, DioriteRandomUtils.getRandInt(3, 4));
                    }
                    else
                    {
                        chunk.setBlock(x, y, z, 3, 0);
                    }
                }
            }
        }
        chunk.recalculateBlockCounts();
    }
}
