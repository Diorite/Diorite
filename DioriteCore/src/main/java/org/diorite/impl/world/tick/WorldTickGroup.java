package org.diorite.impl.world.tick;

import org.diorite.impl.world.WorldImpl;

public class WorldTickGroup implements TickGroupImpl
{
    private final WorldImpl world;

    public WorldTickGroup(final WorldImpl world)
    {
        this.world = world;
    }

    @Override
    public void doTick(final int tps)
    {
        this.world.doTick(tps);
    }
}
