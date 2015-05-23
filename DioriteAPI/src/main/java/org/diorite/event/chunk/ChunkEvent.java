package org.diorite.event.chunk;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.event.Event;
import org.diorite.world.World;
import org.diorite.world.chunk.ChunkPos;

/**
 * Base class for chunk related events.
 */
public class ChunkEvent extends Event
{
    protected final ChunkPos chunkPos;

    /**
     * Construct new world event.
     *
     * @param chunkPos world related to event, can't be null, and must contains world.
     */
    public ChunkEvent(final ChunkPos chunkPos)
    {
        Validate.notNull(chunkPos, "chunk position can't be null.");
        Validate.notNull(chunkPos.getWorld(), "world of chunk position can't be null.");
        this.chunkPos = chunkPos;
    }

    /**
     * @return world related to event.
     *
     * @see ChunkPos#getWorld()
     */
    public World getWorld()
    {
        return this.chunkPos.getWorld();
    }

    /**
     * @return chunk position related to event.
     */
    public ChunkPos getChunkPos()
    {
        return this.chunkPos;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkEvent))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final ChunkEvent that = (ChunkEvent) o;

        return this.chunkPos.equals(that.chunkPos);

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.chunkPos.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunkPos", this.chunkPos).toString();
    }
}
