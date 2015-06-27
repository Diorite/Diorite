package org.diorite.impl.entity.tracker;

import org.diorite.impl.world.WorldImpl;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class EntityTrackers
{
    private final TIntObjectMap<EntityTracker> trackers = new TIntObjectHashMap<>(1000, 0.25F, - 1);
    private final WorldImpl world;

    public EntityTrackers(final WorldImpl world)
    {
        this.world = world;
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }
}
