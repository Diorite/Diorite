package org.diorite.impl.world;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtOutputStream;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagType;
import org.diorite.world.chunk.ChunkPos;

public class WorldFileImpl
{
    private final File             worldDir;
    private final WorldImpl        world;
    private final ChunkManagerImpl chunks;

    public WorldFileImpl(final File worldDir, final WorldImpl world)
    {
        this.worldDir = worldDir;
        this.world = world;
        this.chunks = world.getChunkManager();
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
        try (NbtInputStream is = RegionFileCache.getChunkDataInputStream(this.worldDir, x, z))
        {
            if (is == null)
            {
                return null;
            }
            Main.debug("NBT size: "+is.available());
            final NbtTagCompound nbt = (NbtTagCompound) is.readTag(NbtTagType.COMPOUND, false);
            return ChunkImpl.loadFromNBT(this.world, nbt);
        } catch (final IOException e)
        {
            throw new RuntimeException("can't load chunk on: [" + x + ", " + z + "]", e);
        }
    }

    public void saveChunk(final ChunkImpl chunk)
    {
        if (chunk == null)
        {
            return;
        }
        try (NbtOutputStream is = RegionFileCache.getChunkDataOutputStream(this.worldDir, chunk.getX(), chunk.getZ()))
        {
            final NbtTagCompound data = new NbtTagCompound("Level");
            chunk.writeTo(data);
            is.write(data);
        } catch (final IOException e)
        {
            throw new RuntimeException("can't save chunk on: [" + chunk.getX() + ", " + chunk.getZ() + "]", e);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worldDir", this.worldDir).append("world", this.world).toString();
    }
}
