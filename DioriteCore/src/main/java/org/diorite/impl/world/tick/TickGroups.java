package org.diorite.impl.world.tick;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.Tickable;
import org.diorite.utils.SpammyError;
import org.diorite.utils.collections.sets.ConcurrentSet;

public class TickGroups implements Tickable
{
    private static final Object key = new Object(); // key to spammy messages

    protected final DioriteCore core;
    private Collection<TickGroupImpl> groups = new ConcurrentSet<>(10);

    public TickGroups(final DioriteCore core)
    {
        this.core = core;
    }

    public Collection<TickGroupImpl> getGroups()
    {
        return this.groups;
    }

    public void setGroups(final Collection<TickGroupImpl> groups)
    {
        this.groups = groups;
    }

    @Override
    public synchronized void doTick(final int tps)
    {
        if (this.groups.isEmpty())
        {
            if (! CoreMain.isClient())
            {
                SpammyError.err("There is no tick groups, server don't have anything to do. Do you have any worlds?", 10, key);
            }
            return;
        }
        if (this.groups.size() == 1)
        {
            /**
             * TODO count time of execution and split if needed.
             */
            this.groups.iterator().next().doTick(tps);
            return;
        }
        final AtomicInteger i = new AtomicInteger(0);
        final ForkJoinPool pool = new ForkJoinPool(this.groups.size(), p -> new NamedForkJoinWorkerThread(p, i.getAndIncrement()), (t, e) -> {
            // TODO: maybe add some pretty error priting
            System.err.println("Error in tick thread: " + t.getName());
            e.printStackTrace();
        }, false);

        /**
         * TODO count time of execution for all groups.
         * if any group is creating lags, try split it. (should not count single-time lags?)
         * if two grups can be join, try join them.
         */
        final CountDownLatch latch = new CountDownLatch(this.groups.size());
        for (final Iterator<TickGroupImpl> it = this.groups.iterator(); it.hasNext(); )
        {
            final TickGroupImpl tickGroup = it.next();
            if (tickGroup.isEmpty())
            {
                it.remove();
                latch.countDown();
                continue;
            }
            pool.submit(() -> {
                try
                {
                    tickGroup.doTick(tps);
                    this.core.runScheduler(false);
                    this.core.runSync();
                } finally
                {
                    latch.countDown();
                }
            });
        }
        try
        {
            latch.await();
        } catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("groups", this.groups).toString();
    }

    private static class NamedForkJoinWorkerThread extends ForkJoinWorkerThread
    {
        private NamedForkJoinWorkerThread(final ForkJoinPool p, final int i)
        {
            super(p);
            this.setName("[WorldTick@" + i + "]");
        }
    }
}
