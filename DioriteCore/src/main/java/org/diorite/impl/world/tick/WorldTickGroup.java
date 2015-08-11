package org.diorite.impl.world.tick;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.world.World;

public class WorldTickGroup implements TickGroupImpl
{
    private final WeakReference<WorldImpl> world;

    public WorldTickGroup(final WorldImpl world)
    {
        this.world = new WeakReference<>(world);
    }

    @Override
    public void doTick(final int tps)
    {
        final WorldImpl impl = this.world.get();
        if (impl != null)
        {
            impl.doTick(tps);
            impl.getChunkManager().getLoadedChunks().stream().filter(ChunkImpl::isLoaded).forEach(c -> this.tickChunk(c, tps));
        }
    }

    @Override
    public boolean removeWorld(final World world)
    {
        if (world.equals(this.world.get()))
        {
            this.world.clear();
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return this.world.get() == null;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).toString();
    }
}
