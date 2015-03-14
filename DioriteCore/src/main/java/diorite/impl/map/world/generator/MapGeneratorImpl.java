package diorite.impl.map.world.generator;

import diorite.impl.map.chunk.ChunkImpl;
import diorite.utils.math.DioriteRandomUtils;

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
                    chunk.setBlock(x, y, z, 1, DioriteRandomUtils.getRandInt(3, 4));
                }
            }
        }
        chunk.recalculateBlockCounts();
    }
}
