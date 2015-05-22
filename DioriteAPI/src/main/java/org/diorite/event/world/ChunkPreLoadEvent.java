package org.diorite.event.world;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.World;
import org.diorite.world.chunk.ChunkPos;

/**
 * When map want load next chunk.
 */
public class ChunkPreLoadEvent extends WorldEvent
{
    private final ChunkPos chunkPos;

    /**
     * Construct new chunk pre load event.
     *
     * @param world    related world to tihs chunk.
     * @param chunkPos pos of this chunk.
     */
    public ChunkPreLoadEvent(final World world, final ChunkPos chunkPos)
    {
        super(world);
        this.chunkPos = chunkPos;
    }

    /**
     * @return chunk position of related chunk.
     */
    public ChunkPos getChunkPos()
    {
        return this.chunkPos;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunkPos", this.chunkPos).toString();
    }
}
