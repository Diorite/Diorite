/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.world.io;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.math.endian.BigEndianUtils;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public abstract class ChunkRegionCache
{
    protected final TLongObjectMap<Reference<ChunkRegion>> cache = new TLongObjectHashMap<>(100);

    protected final String extension;
    protected final File   regionDir;
    protected final int    maxCacheSize;

    public ChunkRegionCache(final File basePath, final String extension, final int maxCacheSize)
    {
        this.extension = extension;
        this.regionDir = new File(basePath, "region");
        this.maxCacheSize = maxCacheSize;
    }

    public File getRegionDir()
    {
        return this.regionDir;
    }

    public ChunkRegion getChunkRegion(final int regionX, final int regionZ)
    {
        final long key = BigEndianUtils.toLong(regionX, regionZ);
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

        final ChunkRegion reg = this.createNewRegion(file, regionX, regionZ);
        synchronized (this.cache)
        {
            this.cache.put(key, new SoftReference<>(reg));
        }
        return reg;
    }

    public abstract ChunkRegion createNewRegion(final File file, final int regionX, final int regionZ);

    public synchronized void clear()
    {
        synchronized (this.cache)
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
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("extension", this.extension).append("regionDir", this.regionDir).toString();
    }
}
