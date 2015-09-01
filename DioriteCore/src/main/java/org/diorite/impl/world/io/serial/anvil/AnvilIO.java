package org.diorite.impl.world.io.serial.anvil;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkIO;
import org.diorite.impl.world.io.ChunkRegion;
import org.diorite.nbt.NbtTagCompound;

public class AnvilIO extends ChunkIO
{
    public static final int DEFAULT_REGION_SIZE = 32;

    private final int regionSize;

    AnvilIO(final File basePath, final String extension, final int maxCacheSize, final int regionSize)
    {
        super(basePath, extension, maxCacheSize);
        this.regionSize = regionSize;
    }

    public int getRegionSize()
    {
        return this.regionSize;
    }

    @Override
    public ChunkImpl loadChunk(final int x, final int z)
    {
        final ChunkRegion region = this.getChunkRegion(x, z);
        return region.loadChunk(this.getLocalFromRegion(region.getX()), this.getLocalFromRegion(region.getZ()));
    }

    @Override
    public void deleteChunk(final int x, final int z)
    {
        final ChunkRegion region = this.getChunkRegion(x, z);
        region.deleteChunk(x, z);
    }

    @Override
    public void saveChunk(final ChunkImpl chunk)
    {
        final ChunkRegion region = this.getChunkRegion(chunk.getX(), chunk.getZ());
        region.saveChunk(this.getLocalFromRegion(region.getX()), this.getLocalFromRegion(region.getZ()), chunk.writeTo(new NbtTagCompound("Level"))); // TODO, not sure about nbt tag
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
        return cord & (this.regionSize - 1);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("regionSize", this.regionSize).toString();
    }
}
