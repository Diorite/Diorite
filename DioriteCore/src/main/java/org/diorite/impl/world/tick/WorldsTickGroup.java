package org.diorite.impl.world.tick;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.utils.collections.WeakCollection;
import org.diorite.world.World;

public class WorldsTickGroup implements TickGroupImpl
{
    private final WeakCollection<WorldImpl> worlds;

    public WorldsTickGroup(final Collection<WorldImpl> worlds)
    {
        this.worlds = WeakCollection.usingHashSet(worlds.size());
        this.worlds.addAll(worlds);
    }

    public WeakCollection<WorldImpl> getWorlds()
    {
        return this.worlds;
    }

    @Override
    public void doTick(final int tps)
    {
        this.worlds.forEach(w -> {
            w.doTick(tps);
            w.getChunkManager().getLoadedChunks().stream().filter(ChunkImpl::isLoaded).forEach(c -> this.tickChunk(c, tps));
        });
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worlds", this.worlds).toString();
    }

    @Override
    public boolean removeWorld(final World world)
    {
        return this.worlds.remove(world);
    }

    @Override
    public boolean isEmpty()
    {
        return this.worlds.isEmpty();
    }
}
