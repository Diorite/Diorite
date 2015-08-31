package org.diorite.impl.world.io_old.anvil;

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
 * Some changes have been made as part of the Glowstone and Diorite project.
 */

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A simple cache and wrapper for efficiently accessing multiple RegionFiles
 * simultaneously.
 */
public class RegionFileCache
{
    private static final int MAX_CACHE_SIZE = 256;

    private final Map<File, Reference<RegionFile>> cache = new HashMap<>(50);

    private final String extension;
    private final File   regionDir;

    public RegionFileCache(final File basePath, final String extension)
    {
        this.extension = extension;
        this.regionDir = new File(basePath, "region");
    }

    public RegionFile getRegionFile(final int chunkX, final int chunkZ) throws IOException
    {
        final File file = new File(this.regionDir, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + this.extension);

        final Reference<RegionFile> ref = this.cache.get(file);

        if ((ref != null) && (ref.get() != null))
        {
            return ref.get();
        }

        if (! this.regionDir.isDirectory() && ! this.regionDir.mkdirs())
        {
            System.err.println("[WorldIO] Failed to create directory: " + this.regionDir);
        }

        if (this.cache.size() >= MAX_CACHE_SIZE)
        {
            this.clear();
        }

        final RegionFile reg = new RegionFile(file);
        this.cache.put(file, new SoftReference<>(reg));
        return reg;
    }

    public void clear() throws IOException
    {
        for (final Reference<RegionFile> ref : this.cache.values())
        {
            final RegionFile value = ref.get();
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
