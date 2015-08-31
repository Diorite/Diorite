package org.diorite.impl.world.io;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.math.pack.IntsToLong;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class ChunkRegionCache
{
    private final TLongObjectMap<Reference<ChunkRegion>> cache = new TLongObjectHashMap<>(50);

    private final String extension;
    private final File   regionDir;
    private final int    maxCacheSize;

    public ChunkRegionCache(final File basePath, final String extension, final int maxCacheSize)
    {
        this.extension = extension;
        this.regionDir = new File(basePath, "region");
        this.maxCacheSize = maxCacheSize;
    }

    public ChunkRegion getChunkRegion(final int regionX, final int regionZ) throws IOException
    {
        final long key = IntsToLong.pack(regionX, regionZ);
        final File file = new File(this.regionDir, "r." + regionX + "." + regionZ + this.extension);

        final Reference<ChunkRegion> ref = this.cache.get(key);

        if ((ref != null) && (ref.get() != null))
        {
            return ref.get();
        }

        if (! this.regionDir.isDirectory() && ! this.regionDir.mkdirs())
        {
            System.err.println("[WorldIO] Failed to create directory: " + this.regionDir);
        }

        if (this.cache.size() >= this.maxCacheSize)
        {
            this.clear();
        }

        final ChunkRegion reg = new ChunkRegion(file);
        this.cache.put(key, new SoftReference<>(reg));
        return reg;
    }

    public void clear() throws IOException
    {
        final TLongObjectIterator<Reference<ChunkRegion>> it = this.cache.iterator();
        while (it.hasNext())
        {
            it.advance();
            final ChunkRegion value = it.value().get();
            if (value != null)
            {
                value.close();
            }
        }
        this.cache.clear();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("extension", this.extension).append("regionDir", this.regionDir).toString();
    }
}
