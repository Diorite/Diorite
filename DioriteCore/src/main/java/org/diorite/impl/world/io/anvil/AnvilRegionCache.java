package org.diorite.impl.world.io.anvil;

import java.io.File;

import org.diorite.impl.world.io.ChunkRegion;
import org.diorite.impl.world.io.ChunkRegionCache;

class AnvilRegionCache extends ChunkRegionCache
{
    public static final int DEFAULT_MAX_CACHE_SIZE = 255;

    AnvilRegionCache(final File basePath, final String extension, final int maxCacheSize)
    {
        super(basePath, extension, maxCacheSize);
    }

    AnvilRegionCache(final File basePath)
    {
        super(basePath, ".mca", DEFAULT_MAX_CACHE_SIZE);
    }

    @Override
    public ChunkRegion createNewRegion(final File file, final int regionX, final int regionZ)
    {
        return new AnvilRegion(file, regionX, regionZ);
    }
}
