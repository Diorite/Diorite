package org.diorite.impl.world.io;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.nbt.NbtTagCompound;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

public abstract class ChunkIO<C extends Chunk>
{
    protected final File  worldDir;
    protected World world;

    public ChunkIO(final File worldDir)
    {
        this.worldDir = worldDir;
    }

    public ChunkIO(final File worldDir, final World world)
    {
        this.worldDir = worldDir;
        this.world = world;
        worldDir.mkdirs();
    }

    public void setWorld(final World world)
    {
        this.world = world;
    }

    public File getWorldDir()
    {
        return this.worldDir;
    }

    public abstract C loadChunk(ChunkPos pos);

    public abstract C loadChunk(int x, int z);

    public abstract void saveChunk(C chunk);

    public abstract NbtTagCompound getChunkData(final int chunkX, final int chunkZ);

    public abstract void saveChunkData(final int chunkX, final int chunkZ, final NbtTagCompound data);

    public abstract void saveChunks(final Collection<? extends C> chunksToSave);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worldDir", this.worldDir).toString();
    }
}
