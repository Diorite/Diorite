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
                chunk.setBlock(x, 1, z, 1, DioriteMathUtils.getRandInt(0, 6));
            }
        }
        chunk.recalculateBlockCounts();
    }
}
