/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.core.world.io.anvil;

import javax.annotation.Nullable;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.core.world.chunk.ChunkImpl;
import org.diorite.core.world.io.ChunkIO;
import org.diorite.core.world.io.ChunkRegion;
import org.diorite.nbt.NbtTagCompound;

public class AnvilIO extends ChunkIO
{
    public static final int REGION_SIZE = 32;

    protected AnvilIO(File basePath, String extension, int maxCacheSize)
    {
        super(new AnvilRegionCache(basePath, extension, maxCacheSize));
    }

    protected AnvilIO(File basePath)
    {
        super(new AnvilRegionCache(basePath));
    }

    public File getWorldDataFolder()
    {
        return this.cache.getRegionDir();
    }

    @Override
    @Nullable
    public ChunkImpl loadChunk(int x, int z, ChunkImpl chunk)
    {
        ChunkRegion region = this.getChunkRegion(x, z);
        return region.loadChunk(this.getLocalFromRegion(x), this.getLocalFromRegion(z), chunk);
    }

    @Override
    public boolean deleteChunk(int x, int z)
    {
        ChunkRegion region = this.getChunkRegion(x, z);
        return region.deleteChunk(x, z);
    }

    @Override
    public void saveChunk(ChunkImpl chunk)
    {
        ChunkRegion region = this.getChunkRegion(chunk.getX(), chunk.getZ());
        NbtTagCompound lvl = new NbtTagCompound("Level");
        NbtTagCompound top = new NbtTagCompound();
        lvl = chunk.writeTo(lvl);
        if (lvl == null)
        {
            return;
        }
        top.addTag(lvl);
        region.saveChunk(this.getLocalFromRegion(chunk.getX()), this.getLocalFromRegion(chunk.getZ()), top); // TODO, not sure about nbt tag
    }

    @Override
    protected ChunkRegion getChunkRegion(int chunkX, int chunkZ)
    {
        int regionX = chunkX >> 5;
        int regionZ = chunkZ >> 5;
        return this.cache.getChunkRegion(regionX, regionZ);
    }

    protected int getLocalFromRegion(int cord)
    {
        return cord & (REGION_SIZE - 1);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}
