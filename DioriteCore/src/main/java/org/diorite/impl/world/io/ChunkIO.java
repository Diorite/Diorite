package org.diorite.impl.world.io;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.io.mca.McaChunkIO;
import org.diorite.impl.world.io.mca.McaRegionProvider;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.world.chunk.Chunk;

@SuppressWarnings("rawtypes")
public abstract class ChunkIO
{
    public ChunkIO(final RegionProvider provider)
    {
        this.provider = provider;
    }

    protected final RegionProvider provider;

    public RegionProvider getProvider()
    {
        return this.provider;
    }

    /**
     * Should save this collection of chunks in best possible way.
     *
     * @param chunks chunks to save.
     */
    public abstract void saveChunks(Collection<Chunk> chunks, final WorldFile wf);

    /**
     * Should save this chunks in best possible way.
     *
     * @param chunk chunk to save.
     */
    public abstract void saveChunk(Chunk chunk, final WorldFile wf);

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
        return new McaChunkIO(new McaRegionProvider());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("provider", this.provider).toString();
    }
}
