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
