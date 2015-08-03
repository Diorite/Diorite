package org.diorite.impl.entity.tracker;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.Tickable;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.world.WorldImpl;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TShortIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TShortIntHashMap;

public class EntityTrackers implements Tickable
{
    @SuppressWarnings("MagicNumber")
    private final TIntObjectMap<BaseTracker<?>> trackers = new TIntObjectHashMap<>(1000, 0.25F, - 1);
    /**
     * Maps entity type id and amount of entity of that type. <br>
     * Used by statisitcs and performance commands.
     */
    @SuppressWarnings("MagicNumber")
    private final TShortIntMap                  stats    = new TShortIntHashMap(50, 0.1f, (short) 0, 0); // only for stats, performance commands etc.
    private final WorldImpl world;

    public EntityTrackers(final WorldImpl world)
    {
        this.world = world;
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }

    public TShortIntMap getStats() // not for API use, TODO: add API for that
    {
        return this.stats;
    }

    private void incrementStat(final EntityImpl entity)
    {
        try
        {
            final short type = (short) entity.getType().getMinecraftId();
            this.stats.put(type, this.stats.get(type) + 1);
        } catch (final Throwable ignored) // for sure that it will never cause any problems
        {
            if (Main.isEnabledDebug())
            {
                ignored.printStackTrace();
            }
        }
    }

    private void decrementStat(final EntityImpl entity)
    {
        try
        {
            final short type = (short) entity.getType().getMinecraftId();
            this.stats.put(type, this.stats.get(type) - 1);
        } catch (final Throwable ignored) // for sure that it will never cause any problems
        {
            if (Main.isEnabledDebug())
            {
                ignored.printStackTrace();
            }
        }
    }

    public PlayerTracker addTracked(final PlayerImpl trackable)
    {
        final PlayerTracker pt = new PlayerTracker(trackable);
        this.trackers.put(trackable.getId(), pt);
        this.updatePlayer(trackable);
        this.incrementStat(trackable);
        return pt;
    }

    public int size()
    {
        return this.trackers.size();
    }

    public EntityTracker addTracked(final EntityImpl trackable)
    {
        final EntityTracker et= new EntityTracker(trackable);
        this.trackers.put(trackable.getId(), et);
        this.incrementStat(trackable);
        return et;
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
        this.decrementStat(trackable);
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
        final Collection<PlayerImpl> players = DioriteCore.getInstance().getPlayersManager().getOnlinePlayers(p -> p.getWorld() == this.world);
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
