package org.diorite.impl.world;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.diorite.impl.Main;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtOutputStream;

public final class RegionFileCache
{
    private static final int MAX_CACHE_SIZE = 256;


    private static final Map<File, Reference<RegionFile>> cache = new ConcurrentHashMap<>(25, .2f, 4);

    private RegionFileCache()
    {
    }

    public static synchronized RegionFile getRegionFile(final File basePath, final int chunkX, final int chunkZ)
    {
        final File regionDir = new File(basePath, "region");
        final File file = new File(regionDir, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mcr");

        final Reference<RegionFile> ref = cache.get(file);

        if ((ref != null) && (ref.get() != null))
        {
            return ref.get();
        }

        if (! regionDir.exists())
        {
            regionDir.mkdirs();
        }

        if (cache.size() >= MAX_CACHE_SIZE)
        {
            RegionFileCache.clear();
        }

        final RegionFile reg = new RegionFile(file);

        cache.put(file, new SoftReference<>(reg));
        return reg;
    }

    public static synchronized void clear()
    {
        for (final Reference<RegionFile> ref : cache.values())
        {
            try
            {
                final RegionFile file;
                if ((file = ref.get()) != null)
                {
                    file.close();
                }
            } catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        cache.clear();
    }

    public static int getSizeDelta(final File basePath, final int chunkX, final int chunkZ)
    {
        final RegionFile r = getRegionFile(basePath, chunkX, chunkZ);
        return r.getSizeDelta();
    }

    @SuppressWarnings("MagicNumber")
    public static NbtInputStream getChunkDataInputStream(final File basePath, final int chunkX, final int chunkZ)
    {
        final RegionFile r = getRegionFile(basePath, chunkX, chunkZ);
        return r.getChunkDataInputStream(chunkX & 31, chunkZ & 31);
    }

    @SuppressWarnings("MagicNumber")
    public static NbtOutputStream getChunkDataOutputStream(final File basePath, final int chunkX, final int chunkZ)
    {
        final RegionFile r = getRegionFile(basePath, chunkX, chunkZ);
        return r.getChunkDataOutputStream(chunkX & 31, chunkZ & 31);
    }
}
