package org.diorite.impl.world.io;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;

public abstract class ChunkIO
{
    protected final ChunkRegionCache cache;

    public ChunkIO(final ChunkRegionCache cache)
    {
        this.cache = cache;
    }

    public abstract ChunkImpl loadChunk(int x, int z, ChunkImpl chunk);

    public abstract boolean deleteChunk(int x, int z);

    public abstract void saveChunk(ChunkImpl chunk);

    protected abstract ChunkRegion getChunkRegion(int chunkX, int chunkZ);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("cache", this.cache).toString();
    }
}
