package diorite.impl.map.world.generator;

import diorite.impl.map.chunk.ChunkImpl;
import diorite.utils.DioriteMathUtils;

public class MapGeneratorImpl // temp code, TODO
{
    public void generateChunk(ChunkImpl chunk)
    {
        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                for (int y = 0; y < 69; y++)
                {
                    chunk.setBlock(x, y, z, 1, DioriteMathUtils.getRandInt(3,4));
                }
            }
        }
        chunk.recalculateBlockCounts();
    }
}
