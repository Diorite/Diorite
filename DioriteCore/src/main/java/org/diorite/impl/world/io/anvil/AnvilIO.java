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
        region.saveChunk(this.getLocalFromRegion(chunk.getX()), this.getLocalFromRegion(chunk.getZ()), chunk.writeTo(new NbtTagCompound("Level"))); // TODO, not sure about nbt tag
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
