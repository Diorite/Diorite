package org.diorite.impl.world.io;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.chunk.ChunkPos;

public abstract class WorldFile<W extends World, CM extends ChunkManager, IO extends ChunkIO, C extends Chunk>
{
    protected final File worldDir;
    protected final W    world;
    protected final CM   chunks;
    protected final IO   io;

    public WorldFile(final File worldDir, final W world, final CM chunks, final IO io)
    {
        this.worldDir = worldDir;
        this.world = world;
        this.chunks = chunks;
        this.io = io;
    }

    public File getWorldDir()
    {
        return this.worldDir;
    }

    public W getWorld()
    {
        return this.world;
    }

    public CM getChunks()
    {
        return this.chunks;
    }

    public IO getIo()
    {
        return this.io;
    }

    public abstract C loadChunk(ChunkPos pos);

    public abstract C loadChunk(int x, int z);

    public abstract void saveChunk(C chunk);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worldDir", this.worldDir).append("world", this.world).append("chunks", this.chunks).append("io", this.io).toString();
    }
}
