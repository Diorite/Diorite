package org.diorite.impl.scheduler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.diorite.Server;
import org.diorite.plugin.Plugin;
import org.diorite.scheduler.DioriteTask;
import org.diorite.scheduler.DioriteWorker;
import org.diorite.scheduler.Scheduler;
import org.diorite.scheduler.Synchronizable;
import org.diorite.scheduler.TaskBuilder;

public class SchedulerImpl extends Scheduler
{
    // TODO: implement,
    // such code, much wow.
    @Override
    public <T> Future<T> callSyncMethod(final Plugin plugin, final Callable<T> task)
    {
        return null;
    }

    @Override
    public <T> Future<T> callSyncMethod(final Plugin plugin, final Callable<T> task, final Synchronizable sync)
    {
        return null;
    }

    @Override
    public void cancelTask(final int taskId)
    {

    }

    @Override
    public void cancelTasks(final Plugin plugin)
    {

    }

    @Override
    public void cancelAllTasks()
    {

    }

    @Override
    public boolean isCurrentlyRunning(final int taskId)
    {
        return false;
    }

    @Override
    public boolean isQueued(final int taskId)
    {
        return false;
    }

    @Override
    public List<DioriteWorker> getActiveWorkers()
    {
        return null;
    }

    @Override
    public List<DioriteTask> getPendingTasks()
    {
        return null;
    }

    @Override
    protected DioriteTask runTask(final TaskBuilder builder, final long startDelay) throws IllegalArgumentException
    {
        if (builder.isSingle() && (startDelay != 0))
        {
            throw new IllegalArgumentException("Single task can't have additional delay.");
        }

        return null;
    }

    public void runTasks(final Class<? extends Synchronizable> type)
    {
        if (type == null)
        {
            this.runTasks(Server.class);
            return;
        }
        // TODO
    }
}
