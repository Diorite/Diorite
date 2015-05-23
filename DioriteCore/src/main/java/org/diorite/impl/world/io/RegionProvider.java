package org.diorite.impl.world.io;

import java.io.File;

public interface RegionProvider
{
    ChunkProvider getChunkProvider(File basePath, int chunkX, int chunkZ);

    void clear();
}
