package org.diorite.impl.world.world;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.impl.world.chunk.ChunkManagerImpl;
import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

public class WorldImpl implements World
{
    private final String           name;
    private final UUID             uuid;
    private final ChunkManagerImpl chunkManager;

    public WorldImpl(final String name, final UUID uuid)
    {
        this.name = name;
        this.uuid = uuid;
        this.chunkManager = new ChunkManagerImpl(this);
    }

    @Override
    public ChunkManagerImpl getChunkManager()
    {
        return this.chunkManager;
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final BlockMaterialData material)
    {
        Main.debug("Block edit at: "+x+", "+y+", "+x+", from: "+ this.chunkManager.getChunkAt(ChunkPos.fromWorldPos(x, z, this)).getBlockType(Math.abs(x % Chunk.CHUNK_SIZE),y,Math.abs(z % Chunk.CHUNK_SIZE)));
        this.chunkManager.getChunkAt(ChunkPos.fromWorldPos(x, z, this)).setBlock(Math.abs(x % Chunk.CHUNK_SIZE), y, Math.abs(z % Chunk.CHUNK_SIZE), material.getId(), material.getType());
        Main.debug("Block edit at: "+x+", "+y+", "+x+", after: "+ this.chunkManager.getChunkAt(ChunkPos.fromWorldPos(x, z, this)).getBlockType(Math.abs(x % Chunk.CHUNK_SIZE),y,Math.abs(z % Chunk.CHUNK_SIZE)));
    }

    @Override
    public void setBlock(final BlockLocation location, final BlockMaterialData material)
    {
        this.setBlock(location.getX(), location.getY(), location.getZ(), material);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("uuid", this.uuid).toString();
    }
}
