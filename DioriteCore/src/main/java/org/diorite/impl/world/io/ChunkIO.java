package org.diorite.impl.world.io;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;

public abstract class ChunkIO
{
    protected final ChunkRegionCache cache;

    public ChunkIO(final File basePath, final String extension, final int maxCacheSize)
    {
        this.cache = new ChunkRegionCache(basePath, extension, maxCacheSize);
    }

    public ChunkIO(final ChunkRegionCache cache)
    {
        this.cache = cache;
    }

    public abstract ChunkImpl loadChunk(int x, int z);

    public abstract void deleteChunk(int x, int z);

    public abstract void saveChunk(ChunkImpl chunk);

    protected abstract ChunkRegion getChunkRegion(final int chunkX, final int chunkZ);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("cache", this.cache).toString();
    }
}
