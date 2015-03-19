package org.diorite.impl.world.world.generator;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.utils.math.DioriteRandomUtils;

public class MapGeneratorImpl // temp code, TODO
{
    public void generateChunk(final ChunkImpl chunk)
    {
        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                for (int y = 0; y < 61; y++)
                {
                    if (y == 60) // some grass and dirt to faster testing (block break time)
                    {
                        chunk.setBlock(x, y, z, 2, 0);
                    }
                    else if (y < 58)
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
