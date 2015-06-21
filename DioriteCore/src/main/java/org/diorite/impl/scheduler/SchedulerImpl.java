package org.diorite.impl.scheduler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.diorite.impl.Tickable;
import org.diorite.plugin.Plugin;
import org.diorite.scheduler.DioriteTask;
import org.diorite.scheduler.DioriteWorker;
import org.diorite.scheduler.Scheduler;
import org.diorite.scheduler.Synchronizable;

public class SchedulerImpl implements Scheduler, Tickable
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
    public DioriteTask runTask(final Plugin plugin, final Runnable task) throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public DioriteTask runTask(final Plugin plugin, final Runnable task, final Synchronizable sync) throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public DioriteTask runTaskAsynchronously(final Plugin plugin, final Runnable task) throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public DioriteTask runTaskLater(final Plugin plugin, final Runnable task, final long delay) throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public DioriteTask runTaskLater(final Plugin plugin, final Runnable task, final long delay, final Synchronizable sync) throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public DioriteTask runTaskLaterAsynchronously(final Plugin plugin, final Runnable task, final long delay) throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public DioriteTask runTaskTimer(final Plugin plugin, final Runnable task, final long delay, final long period) throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public DioriteTask runTaskTimer(final Plugin plugin, final Runnable task, final long delay, final long period, final Synchronizable sync) throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public DioriteTask runTaskTimerAsynchronously(final Plugin plugin, final Runnable task, final long delay, final long period) throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public void doTick(final int tps)
    {
        // TODO
    }
}
