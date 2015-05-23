package org.diorite.impl.world.io;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.world.chunk.ChunkPos;

public class WorldFile
{
    private final File             worldDir;
    private final WorldImpl        world;
    private final ChunkManagerImpl chunks;
    private final ChunkIO          io;

    public WorldFile(final File worldDir, final WorldImpl world, final ChunkIO io)
    {
        this.worldDir = worldDir;
        this.world = world;
        this.io = io;
        this.chunks = world.getChunkManager();
    }

    public ChunkIO getChunkIO()
    {
        return this.io;
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }

    public ChunkManagerImpl getChunks()
    {
        return this.chunks;
    }

    public File getWorldDir()
    {
        return this.worldDir;
    }

    public ChunkImpl loadChunk(final ChunkPos pos)
    {
        return this.loadChunk(pos.getX(), pos.getZ());
    }

    public ChunkImpl loadChunk(final int x, final int z)
    {
        try
        {
            final NbtTagCompound chunk = this.io.getChunkData(this.worldDir, x, z);
            if (chunk == null)
            {
                return null;
            }
            return ChunkImpl.loadFromNBT(this.world, chunk);
        } catch (final RuntimeException e)
        {
            System.err.println("[WorldFile] Error on chunk (" + x + ", " + z + ") loading: " + e.getMessage() + ", " + e.toString());
            if (Main.isEnabledDebug())
            {
                e.printStackTrace();
            }
            return new ChunkImpl(new ChunkPos(x, z, this.world));
        }
    }

    public void saveChunk(final ChunkImpl chunk)
    {
        if (chunk == null)
        {
            return;
        }
        this.io.saveChunkData(this.worldDir, chunk.getX(), chunk.getZ(), chunk.writeTo(new NbtTagCompound("Level")));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worldDir", this.worldDir).append("world", this.world).toString();
    }
}
