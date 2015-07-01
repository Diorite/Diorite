package org.diorite.impl.entity.tracker;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.Tickable;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.world.WorldImpl;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class EntityTrackers implements Tickable
{
    @SuppressWarnings("MagicNumber")
    private final TIntObjectMap<BaseTracker<?>> trackers = new TIntObjectHashMap<>(1000, 0.25F, - 1);
    private final WorldImpl world;

    public EntityTrackers(final WorldImpl world)
    {
        this.world = world;
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }

    public void addTracked(final PlayerImpl trackable)
    {
        this.trackers.put(trackable.getId(), new PlayerTracker(trackable));
        this.updatePlayer(trackable);
    }

    public void addTracked(final EntityImpl trackable)
    {
        this.trackers.put(trackable.getId(), new EntityTracker(trackable));
    }

    public boolean removeTracked(final EntityImpl trackable)
    {
        final BaseTracker<?> tracker = this.trackers.remove(trackable.getId());
        if (tracker == null)
        {
            return false;
        }
        tracker.despawn();
        if (trackable instanceof PlayerImpl)
        {
            final PlayerImpl player = (PlayerImpl) trackable;
            this.trackers.forEachValue(t -> {
                t.remove(player);
                return true;
            });
        }
        return true;
    }

    public void updatePlayer(final PlayerImpl player)
    {
        this.trackers.forEachValue(t -> {
            t.updatePlayer(player);
            return true;
        });
    }

    @Override
    public void doTick(final int tps)
    {
        //noinspection ObjectEquality
        final Collection<PlayerImpl> players = ServerImpl.getInstance().getPlayersManager().getOnlinePlayers(p -> p.getWorld() == this.world);
        this.trackers.forEachValue(t -> {
            t.tick(tps, players);
            return true;
        });
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("trackers", this.trackers).append("world", this.world).toString();
    }

    public void spawn(final PlayerImpl player)
    {
        final BaseTracker<?> tracker = this.trackers.get(player.getId());
        if (tracker != null)
        {
            tracker.spawn();
        }
    }
}
