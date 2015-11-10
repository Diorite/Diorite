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

package org.diorite.impl.world.io.anvil;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkIO;
import org.diorite.impl.world.io.ChunkRegion;
import org.diorite.nbt.NbtTagCompound;

public class AnvilIO extends ChunkIO
{
    public static final int REGION_SIZE = 32;

    protected AnvilIO(final File basePath, final String extension, final int maxCacheSize)
    {
        super(new AnvilRegionCache(basePath, extension, maxCacheSize));
    }

    protected AnvilIO(final File basePath)
    {
        super(new AnvilRegionCache(basePath));
    }

    public File getWorldDataFolder()
    {
        return this.cache.getRegionDir();
    }

    @Override
    public ChunkImpl loadChunk(final int x, final int z, final ChunkImpl chunk)
    {
        final ChunkRegion region = this.getChunkRegion(x, z);
        return region.loadChunk(this.getLocalFromRegion(x), this.getLocalFromRegion(z), chunk);
    }

    @Override
    public boolean deleteChunk(final int x, final int z)
    {
        final ChunkRegion region = this.getChunkRegion(x, z);
        return region.deleteChunk(x, z);
    }

    @Override
    public void saveChunk(final ChunkImpl chunk)
    {
        final ChunkRegion region = this.getChunkRegion(chunk.getX(), chunk.getZ());
        NbtTagCompound lvl = new NbtTagCompound("Level");
        final NbtTagCompound top = new NbtTagCompound();
        lvl = chunk.writeTo(lvl);
        if (lvl == null)
        {
            return;
        }
        top.addTag(lvl);
        region.saveChunk(this.getLocalFromRegion(chunk.getX()), this.getLocalFromRegion(chunk.getZ()), top); // TODO, not sure about nbt tag
    }

    @Override
    protected ChunkRegion getChunkRegion(final int chunkX, final int chunkZ)
    {
        final int regionX = chunkX >> 5;
        final int regionZ = chunkZ >> 5;
        return this.cache.getChunkRegion(regionX, regionZ);
    }

    protected int getLocalFromRegion(final int cord)
    {
        return cord & (REGION_SIZE - 1);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}
