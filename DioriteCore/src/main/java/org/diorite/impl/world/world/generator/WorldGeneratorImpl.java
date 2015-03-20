package org.diorite.impl.world.world.generator;

import org.diorite.impl.Main;
import org.diorite.impl.world.chunk.ChunkImpl;

public class WorldGeneratorImpl // temp code, TODO
{
    public void generateChunk(final ChunkImpl chunk)
    {
        Main.debug("Generating: "+chunk.getPos());
        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                chunk.setBlock(x, 0, z, 3, 0);
//                for (int y = 0; y < 4; y++)
//                {
//                    if (y == 3) // some grass and dirt to faster testing (block break time)
//                    {
//                        chunk.setBlock(x, y, z, 2, 0);
//                    }
//                    else if (y < 1)
//                    {
//                        chunk.setBlock(x, y, z, 1, DioriteRandomUtils.getRandInt(3, 4));
//                    }
//                    else
//                    {
//                        chunk.setBlock(x, y, z, 3, 0);
//                    }
//                }
            }
        }
        chunk.recalculateBlockCounts();
    }
}
