package org.diorite.impl.world;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.diorite.nbt.NbtOutputStream;
import org.diorite.nbt.NbtTagCompound;

/*
 ** 2011 January 5
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 **/

/*
 * 2011 February 16
 *
 * This source code is based on the work of Scaevolus (see notice above).
 * It has been slightly modified by Mojang AB to limit the maximum cache
 * size (relevant to extremely big worlds on Linux systems with limited
 * number of file handles). The region files are postfixed with ".mcr"
 * (Minecraft region file) instead of ".data" to differentiate from the
 * original McRegion files.
 *
 */

/*
 * Some changes have been made as part of the Glowstone and later Diorite project.
 */
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
        final File file = new File(regionDir, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca");

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
    public static NbtTagCompound getChunkDataInputStream(final File basePath, final int chunkX, final int chunkZ)
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
