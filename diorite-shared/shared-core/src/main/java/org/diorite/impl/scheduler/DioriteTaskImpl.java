/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import javax.annotation.Nullable;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.SharedAPI;
import org.diorite.plugin.Plugin;
import org.diorite.scheduler.DioriteTask;
import org.diorite.scheduler.Synchronizable;

public class DioriteTaskImpl implements DioriteTask, Runnable
{
    protected static final int STATE_SINGLE      = - 1;
    protected static final int STATE_CANCEL      = - 2;
    protected static final int STATE_FUTURE      = - 3;
    protected static final int STATE_FUTURE_DONE = - 4;

    private volatile @Nullable DioriteTaskImpl next = null;
    private volatile long                          period;
    private          long                          nextRun;
    private final    String                        name;
    private final    Runnable                      task;
    private final    Plugin                        plugin;
    private final    boolean                       safeMode;
    private final    WeakReference<Synchronizable> synchronizable;
    private final    int                           id;

    DioriteTaskImpl(String name, Plugin internalPlugin)
    {
        this(name, internalPlugin, null, null, false, - 1, STATE_SINGLE);
    }

    DioriteTaskImpl(String name, Plugin internalPlugin, Runnable task)
    {
        this(name, internalPlugin, task, null, false, - 1, STATE_SINGLE);
    }

    DioriteTaskImpl(Plugin internalPlugin, Runnable task)
    {
        this(task.getClass().getName() + "@" + System.identityHashCode(task), internalPlugin, task, null, false, - 1, STATE_SINGLE);
    }

    DioriteTaskImpl(String name, Plugin plugin, @Nullable Runnable task, @Nullable Synchronizable synchronizable, boolean safeMode, int id, long period)
    {
        this.name = name;
        this.plugin = plugin;
        this.task = (task == null) ? () -> {} : task;
        this.safeMode = safeMode;
        this.synchronizable = new WeakReference<>(synchronizable);
        this.id = id;
        this.period = period;
    }

    @Override
    public final int getTaskId()
    {
        return this.id;
    }

    @Override
    public final Plugin getOwner()
    {
        return this.plugin;
    }

    @Override
    public boolean isAsync()
    {
        return false;
    }

    @Override
    public boolean isSynchronizedTo(Synchronizable obj)
    {
        return ! this.isAsync() && obj.equals(this.checkReference());
    }

    @Override
    public boolean isSynchronizedTo(Class<? extends Synchronizable> clazz)
    {
        if (this.isAsync())
        {
            return false;
        }
        Synchronizable sync = this.checkReference();
        return (sync != null) && clazz.isInstance(sync);
    }

    @Override
    @Nullable
    public Synchronizable getSynchronizable()
    {
        return this.checkReference();
    }

    @Nullable
    private Synchronizable checkReference()
    {
        Synchronizable sync = this.synchronizable.get();
        if (this.safeMode)
        {
            if (sync == null)
            {
                this.cancel();
                return null;
            }
            if (! sync.isValidSynchronizable())
            {
                this.synchronizable.clear();
                this.cancel();
                return null;
            }
        }
        return sync;
    }

    @Override
    public void run()
    {
        this.task.run();
    }

    @Override
    public String getTaskName()
    {
        return this.name;
    }

    public long getPeriod()
    {
        return this.period;
    }

    void setPeriod(long period)
    {
        this.period = period;
    }

    public long getNextRun()
    {
        return this.nextRun;
    }

    void setNextRun(long nextRun)
    {
        this.nextRun = nextRun;
    }

    @Nullable
    DioriteTaskImpl getNext()
    {
        return this.next;
    }

    void setNext(@Nullable DioriteTaskImpl next)
    {
        this.next = next;
    }

    public Runnable getTask()
    {
        return this.task;
    }

    @Override
    public void cancel()
    {
        SharedAPI.getSharedAPI().getScheduler().cancelTask(this.id);
    }

    public boolean forceCancel()
    {
        this.period = STATE_CANCEL;
        return true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("period", this.period)
                                                                          .append("nextRun", this.nextRun).append("task", this.task)
                                                                          .append("plugin", this.plugin).append("synchronizable", this.synchronizable)
                                                                          .append("id", this.id).toString();
    }
}

