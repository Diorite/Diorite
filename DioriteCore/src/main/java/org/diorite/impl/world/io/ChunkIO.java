package org.diorite.impl.world.io;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.io.mca.McaRegionProvider;
import org.diorite.nbt.NbtTagCompound;

public class ChunkIO
{
    public ChunkIO(final RegionProvider provider)
    {
        this.provider = provider;
    }

    private final RegionProvider provider;

    public RegionProvider getProvider()
    {
        return this.provider;
    }

    public NbtTagCompound getChunkData(final File basePath, final int chunkX, final int chunkZ)
    {
        final ChunkProvider r = this.provider.getChunkProvider(basePath, chunkX, chunkZ);
        return r.getChunkData(chunkX, chunkZ);
    }

    public void saveChunkData(final File basePath, final int chunkX, final int chunkZ, final NbtTagCompound data)
    {
        final ChunkProvider r = this.provider.getChunkProvider(basePath, chunkX, chunkZ);
        r.saveChunkData(chunkX, chunkZ, data);
    }

    public static ChunkIO getDefault()
    {
        return new ChunkIO(new McaRegionProvider());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("provider", this.provider).toString();
    }
}
