package org.diorite.scheduler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.diorite.plugin.Plugin;

public interface Scheduler
{
    // TODO: chage ticks to something better, TPS will be dynamic, so tick aren't good chooice here.

    /**
     * Calls a method on the main thread and returns a Future object. This
     * task will be executed by the main server thread.
     * <ul>
     * <li>Note: The Future.get() methods must NOT be called from the main
     * thread.
     * <li>Note2: There is at least an average of 10ms latency until the
     * isDone() method returns true.
     * </ul>
     *
     * @param <T>    The callable's return type.
     * @param plugin Plugin that owns the task.
     * @param task   Task to be executed.
     *
     * @return Future Future object related to the task
     */
    <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task);

    /**
     * Calls a method on the main thread and returns a Future object. This
     * task will be executed by one of main server tick thread that will
     * be ticking given object. <br>
     * Using server instance as synchronizable object, will work like
     * {@link #callSyncMethod(Plugin, Callable)}
     * <ul>
     * <li>Note: The Future.get() methods must NOT be called from the main
     * thread.
     * <li>Note2: There is at least an average of 10ms latency until the
     * isDone() method returns true.
     * </ul>
     *
     * @param <T>    The callable's return type.
     * @param plugin Plugin that owns the task.
     * @param task   Task to be executed.
     * @param sync   object to synchronize with it.
     *
     * @return Future Future object related to the task
     */
    <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Synchronizable sync);

    /**
     * Removes task from scheduler.
     *
     * @param taskId Id number of task to be removed
     */
    void cancelTask(int taskId);

    /**
     * Removes all tasks associated with a particular plugin from the
     * scheduler.
     *
     * @param plugin Owner of tasks to be removed
     */
    void cancelTasks(Plugin plugin);

    /**
     * Removes all tasks from the scheduler.
     */
    void cancelAllTasks();

    /**
     * Check if the task currently running.
     * <p>
     * A repeating task might not be running currently, but will be running in
     * the future. A task that has finished, and does not repeat, will not be
     * running ever again.
     * <p>
     * Explicitly, a task is running if there exists a thread for it, and that
     * thread is alive.
     *
     * @param taskId The task to check.
     *               <p>
     *
     * @return If the task is currently running.
     */
    boolean isCurrentlyRunning(int taskId);

    /**
     * Check if the task queued to be run later.
     * <p>
     * If a repeating task is currently running, it might not be queued now
     * but could be in the future. A task that is not queued, and not running,
     * will not be queued again.
     *
     * @param taskId The task to check.
     *               <p>
     *
     * @return If the task is queued to be run.
     */
    boolean isQueued(int taskId);

    /**
     * Returns a list of all active workers.
     * <p>
     * This list contains asynch tasks that are being executed by separate
     * threads.
     *
     * @return Active workers
     */
    List<DioriteWorker> getActiveWorkers();

    /**
     * Returns a list of all pending tasks. The ordering of the tasks is not
     * related to their order of execution.
     *
     * @return Active workers
     */
    List<DioriteTask> getPendingTasks();

    /**
     * Returns a task that will run on the next server tick.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     *
     * @return a DioriteTask that contains the id number
     *
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    DioriteTask runTask(Plugin plugin, Runnable task) throws IllegalArgumentException;

    /**
     * Returns a task that will run on the next server tick in this same thread as given object. <br>
     * Using server instance as synchronizable object, will work like
     * {@link #runTask(Plugin, Runnable)}
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param sync   object to synchronize with it.
     *
     * @return a DioriteTask that contains the id number
     *
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    DioriteTask runTask(Plugin plugin, Runnable task, Synchronizable sync) throws IllegalArgumentException;

    /**
     * Returns a task that will run asynchronously.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     *
     * @return a DioriteTask that contains the id number
     *
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    DioriteTask runTaskAsynchronously(Plugin plugin, Runnable task) throws IllegalArgumentException;

    /**
     * Returns a task that will run after the specified number of server
     * ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task
     *
     * @return a DioriteTask that contains the id number
     *
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    DioriteTask runTaskLater(Plugin plugin, Runnable task, long delay) throws IllegalArgumentException;

    /**
     * Returns a task that will run after the specified number of server
     * ticks. <br>
     * It will be run in this same thread as given object. <br>
     * Using server instance as synchronizable object, will work like
     * {@link #runTaskLater(Plugin, Runnable, long)}
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task
     * @param sync   object to synchronize with it.
     *
     * @return a DioriteTask that contains the id number
     *
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    DioriteTask runTaskLater(Plugin plugin, Runnable task, long delay, Synchronizable sync) throws IllegalArgumentException;

    /**
     * Returns a task that will run asynchronously after the specified number
     * of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task
     *
     * @return a DioriteTask that contains the id number
     *
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    DioriteTask runTaskLaterAsynchronously(Plugin plugin, Runnable task, long delay) throws IllegalArgumentException;

    /**
     * Returns a task that will repeatedly run until cancelled, starting after
     * the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task
     * @param period the ticks to wait between runs
     *
     * @return a DioriteTask that contains the id number
     *
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    DioriteTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period) throws IllegalArgumentException;

    /**
     * Returns a task that will repeatedly run until cancelled, starting after
     * the specified number of server ticks.<br>
     * It will be run in this same thread as given object. <br>
     * Using server instance as synchronizable object, will work like
     * {@link #runTaskTimer(Plugin, Runnable, long, long)}
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @param sync   object to synchronize with it.
     *
     * @return a DioriteTask that contains the id number
     *
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    DioriteTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period, Synchronizable sync) throws IllegalArgumentException;

    /**
     * Returns a task that will repeatedly run asynchronously until cancelled,
     * starting after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task for the first
     *               time
     * @param period the ticks to wait between runs
     *
     * @return a DioriteTask that contains the id number
     *
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    DioriteTask runTaskTimerAsynchronously(Plugin plugin, Runnable task, long delay, long period) throws IllegalArgumentException;
}
