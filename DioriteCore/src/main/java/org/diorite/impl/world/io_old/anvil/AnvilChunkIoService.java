package org.diorite.impl.world.io_old.anvil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io_old.ChunkIoService;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtLimiter;
import org.diorite.nbt.NbtOutputStream;
import org.diorite.nbt.NbtTagCompound;

/**
 * An implementation of the {@link ChunkIoService} which reads and writes Anvil maps,
 * an improvement on the McRegion file format.
 */
public final class AnvilChunkIoService implements ChunkIoService
{

    /**
     * The size of a region - a 32x32 group of chunks.
     */
    private static final int REGION_SIZE = 32;

    /**
     * The region file cache.
     */
    private final RegionFileCache cache;

    private final File worldFile;

    // todo: consider the session.lock file

    public AnvilChunkIoService(final File dir)
    {
        this.worldFile = dir;
        this.cache = new RegionFileCache(dir, ".mca");
    }

    @Override
    public File getWorldFile()
    {
        return this.worldFile;
    }

    @Override
    public boolean read(final ChunkImpl chunk) throws IOException
    {
        final int x = chunk.getX();
        final int z = chunk.getZ();
        final RegionFile region = this.cache.getRegionFile(x, z);
        final int regionX = x & (REGION_SIZE - 1);
        final int regionZ = z & (REGION_SIZE - 1);
        if (! region.hasChunk(regionX, regionZ))
        {
            return false;
        }
        chunk.loadFrom(((NbtTagCompound) NbtInputStream.readTag(region.getChunkDataInputStream(regionX, regionZ), NbtLimiter.getUnlimited())).getCompound("Level"));
        return true;
    }

    @Override
    public void write(final ChunkImpl chunk) throws IOException
    {
        final int x = chunk.getX();
        final int z = chunk.getZ();
        final RegionFile region = this.cache.getRegionFile(x, z);
        final int regionX = x & (REGION_SIZE - 1);
        final int regionZ = z & (REGION_SIZE - 1);

        final NbtTagCompound levelTag = chunk.writeTo(new NbtTagCompound("Level"));
        final NbtTagCompound levelOut = new NbtTagCompound();
        levelOut.addTag(levelTag);

        try (final DataOutputStream data = region.getChunkDataOutputStream(regionX, regionZ))
        {
            try (final NbtOutputStream out = NbtOutputStream.write(levelOut, data))
            {
                out.flush();
                out.close();
            }
        }
    }

    @Override
    public void unload() throws IOException
    {
        this.cache.clear();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("cache", this.cache).toString();
    }
}
