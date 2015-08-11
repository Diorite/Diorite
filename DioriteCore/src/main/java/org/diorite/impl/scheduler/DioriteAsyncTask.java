package org.diorite.impl.scheduler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.plugin.DioritePlugin;
import org.diorite.scheduler.DioriteWorker;

@SuppressWarnings("ObjectEquality")
public class DioriteAsyncTask extends DioriteTaskImpl
{
    private final LinkedList<DioriteWorker> workers = new LinkedList<>();
    private final Map<Integer, DioriteTaskImpl> runners;

    DioriteAsyncTask(final String name, final Map<Integer, DioriteTaskImpl> runners, final DioritePlugin dioritePlugin, final Runnable task, final int id, final long delay)
    {
        super(name, dioritePlugin, task, null, false, id, delay);
        this.runners = runners;
    }

    @Override
    public boolean isAsync()
    {
        return true;
    }

    @Override
    public void run()
    {
        final Thread thread = Thread.currentThread();
        synchronized (this.workers)
        {
            if (this.getPeriod() == STATE_CANCEL)
            {
                return;
            }
            this.workers.add(new DioriteWorkerImpl(this, thread));
        }
        Throwable thrown = null;
        try
        {
            super.run();
        } catch (final Throwable t)
        {
            thrown = t;
            throw new RuntimeException("Plugin " + this.getOwner().getName() + " generated an exception while executing task " + this.getTaskId(), thrown);
        } finally
        {
            synchronized (this.workers)
            {
                try
                {
                    final Iterator<DioriteWorker> workers = this.workers.iterator();
                    boolean removed = false;
                    while (workers.hasNext())
                    {
                        if (workers.next().getThread() == thread)
                        {
                            workers.remove();
                            removed = true;
                            break;
                        }
                    }
                    if (! removed)
                    {
                        //noinspection ThrowFromFinallyBlock
                        throw new IllegalStateException("Unable to remove worker " + thread.getName() + " on task " + this.getTaskId() + " for " + this.getOwner().getName(), thrown);
                    }
                } finally
                {
                    if ((this.getPeriod() < 0) && this.workers.isEmpty())
                    {
                        this.runners.remove(this.getTaskId());
                    }
                }
            }
        }
    }

    LinkedList<DioriteWorker> getWorkers()
    {
        return this.workers;
    }

    @Override
    public boolean forceCancel()
    {
        synchronized (this.workers)
        {
            // Synchronizing here prevents race condition for a completing task
            this.setPeriod(STATE_CANCEL);
            if (this.workers.isEmpty())
            {
                this.runners.remove(this.getTaskId());
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("workers", this.workers).append("runners", this.runners).toString();
    }

}
