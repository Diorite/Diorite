/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.entity.tracker;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.Tickable;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.entity.Entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntOpenHashMap;

public class EntityTrackers implements Tickable
{
    private final Int2ObjectMap<BaseTracker<?>> trackers = new Int2ObjectOpenHashMap<>(1000, 0.25F);
    /**
     * Maps entity type id and amount of entity of that type. <br>
     * Used by statisitcs and performance commands.
     */
    private final Short2IntMap                  stats    = new Short2IntOpenHashMap(50, 0.1f); // only for stats, performance commands etc.
    private final WorldImpl world;

    public EntityTrackers(final WorldImpl world)
    {
        this.world = world;
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }

    public BaseTracker<?> getTracker(final Entity entity)
    {
        return this.trackers.get(entity.getId());
    }

    public Short2IntMap getStats() // not for API use, TODO: add API for that
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
            if (CoreMain.isEnabledDebug())
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
            if (CoreMain.isEnabledDebug())
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
        final EntityTracker et = new EntityTracker(trackable);
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
            this.trackers.values().forEach((t) -> t.remove(player));
        }
        this.decrementStat(trackable);
        return true;
    }

    public void updatePlayer(final PlayerImpl player)
    {
        this.trackers.values().forEach((t) -> t.updatePlayer(player));
    }

    @Override
    public void doTick(final int tps)
    {
        //noinspection ObjectEquality
        final Collection<PlayerImpl> players = DioriteCore.getInstance().getPlayersManager().getOnlinePlayers(p -> p.getWorld() == this.world);
        this.trackers.values().forEach((t) -> t.tick(tps, players));
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
