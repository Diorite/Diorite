package org.diorite.impl.scheduler;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.Diorite;
import org.diorite.plugin.Plugin;
import org.diorite.scheduler.DioriteTask;
import org.diorite.scheduler.Synchronizable;

public class DioriteTaskImpl implements DioriteTask
{
    private final int                           taskId;
    private final Plugin                        owner;
    private final boolean                       async;
    private final boolean                       isRealTime;
    private final long                          delay;
    private final long                          period;
    private final WeakReference<Synchronizable> synchronizable;

    public DioriteTaskImpl(final int taskId, final Plugin owner, final boolean async, final boolean isRealTime, final long delay, final long period, final WeakReference<Synchronizable> synchronizable)
    {
        this.taskId = taskId;
        this.owner = owner;
        this.async = async;
        this.isRealTime = isRealTime;
        this.delay = delay;
        this.period = period;
        this.synchronizable = synchronizable;
    }

    @Override
    public int getTaskId()
    {
        return this.taskId;
    }

    @Override
    public Plugin getOwner()
    {
        return this.owner;
    }

    @Override
    public boolean isAsync()
    {
        return this.async;
    }

    public boolean isRealTime()
    {
        return this.isRealTime;
    }

    public long getDelay()
    {
        return this.delay;
    }

    public long getPeriod()
    {
        return this.period;
    }

    public WeakReference<Synchronizable> getWeakSynchronizable()
    {
        return this.synchronizable;
    }

    public Synchronizable getSynchronizable()
    {
        return this.checkReference();
    }

    @Override
    public boolean isSynchronizedTo(final Synchronizable obj)
    {
        return obj.equals(this.checkReference());
    }

    @Override
    public boolean isSynchronizedTo(final Class<? extends Synchronizable> clazz)
    {
        final Synchronizable sync = this.checkReference();
        return (sync != null) && clazz.isInstance(sync);
    }

    private Synchronizable checkReference()
    {
        final Synchronizable sync = this.synchronizable.get();
        if (sync == null)
        {
            Main.debug("Task: " + this.taskId + " cancelled due to empty weak reference.");
            this.cancel();
        }
        return sync;
    }

    @Override
    public void cancel()
    {
        Diorite.getScheduler().cancelTask(this.taskId);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("taskId", this.taskId).append("owner", this.owner).append("async", this.async).append("isRealTime", this.isRealTime).append("delay", this.delay).append("period", this.period).append("synchronizable", this.synchronizable).toString();
    }
}
