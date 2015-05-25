package org.diorite.impl.world.io.mca;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class McaRegionProvider
{
    private static final int                                    MAX_CACHE_SIZE = 256;
    private final        Map<File, Reference<McaChunkProvider>> cache          = new ConcurrentHashMap<>(25, .2f, 4);

    public McaChunkProvider getChunkProvider(final File basePath, final int chunkX, final int chunkZ)
    {
        final File regionDir = new File(basePath, "region");
        final File file = new File(regionDir, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca");

        final Reference<McaChunkProvider> ref = this.cache.get(file);

        if ((ref != null) && (ref.get() != null))
        {
            return ref.get();
        }

        if (! regionDir.exists())
        {
            regionDir.mkdirs();
        }

        if (this.cache.size() >= MAX_CACHE_SIZE)
        {
            this.clear();
        }

        final McaChunkProvider reg = new McaChunkProvider(file, chunkX, chunkZ);
        this.cache.put(file, new SoftReference<>(reg));
        return reg;
    }

    public synchronized void clear()
    {
        for (final Entry<File, Reference<McaChunkProvider>> entry : this.cache.entrySet())
        {
            try
            {
                final Reference<McaChunkProvider> ref = entry.getValue();
                final McaChunkProvider file;
                if ((((file = ref.get())) != null))
                {
                    file.close();
                }
            } catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        this.cache.clear();
    }


    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("cache", this.cache).toString();
    }
}
