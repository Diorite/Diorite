package org.diorite.impl.scheduler;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.plugin.PluginMainClass;
import org.diorite.scheduler.DioriteWorker;

public class DioriteWorkerImpl implements DioriteWorker
{
    private final DioriteAsyncTask dioriteAsyncTask;
    private final Thread           thread;

    public DioriteWorkerImpl(final DioriteAsyncTask dioriteAsyncTask, final Thread thread)
    {
        this.dioriteAsyncTask = dioriteAsyncTask;
        this.thread = thread;
    }

    @Override
    public Thread getThread()
    {
        return this.thread;
    }

    @Override
    public int getTaskId()
    {
        return this.dioriteAsyncTask.getTaskId();
    }

    @Override
    public PluginMainClass getOwner()
    {
        return this.dioriteAsyncTask.getOwner();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("dioriteAsyncTask", this.dioriteAsyncTask.getTaskId()).append("thread", this.thread).toString();
    }
}
