package org.diorite.impl.world.io.anvil;

import java.io.File;

import org.diorite.impl.world.io.ChunkRegion;
import org.diorite.impl.world.io.ChunkRegionCache;

class AnvilRegionCache extends ChunkRegionCache
{
    AnvilRegionCache(final File basePath, final String extension, final int maxCacheSize)
    {
        super(basePath, extension, maxCacheSize);
    }

    @Override
    public ChunkRegion createNewRegion(final File file, final int regionX, final int regionZ)
    {
        return new AnvilRegion(file, regionX, regionZ);
    }
}
