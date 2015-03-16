package org.diorite.impl.map.world;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.map.chunk.ChunkManagerImpl;
import org.diorite.map.World;

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

    public ChunkManagerImpl getChunkManager()
    {
        return this.chunkManager;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("uuid", this.uuid).toString();
    }
}
