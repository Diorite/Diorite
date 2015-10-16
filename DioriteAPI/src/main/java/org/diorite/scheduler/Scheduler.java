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

package org.diorite.scheduler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.diorite.plugin.DioritePlugin;

/**
 * Class for managing tasks and calling sync method. <br>
 * To create and register new task use {@link TaskBuilder} <br>
 * Class is abstract (instead of interface) to keep few methods protected.
 *
 * @see TaskBuilder
 */
public abstract class Scheduler
{
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
     * @param <T>           The callable's return type.
     * @param dioritePlugin Plugin that owns the task.
     * @param task          Task to be executed.
     *
     * @return Future Future object related to the task
     */
    public abstract <T> Future<T> callSyncMethod(DioritePlugin dioritePlugin, Callable<T> task);

    /**
     * Calls a method on the main thread and returns a Future object. This
     * task will be executed by one of main server tick thread that will
     * be ticking given object. <br>
     * Using server instance as synchronizable object, will work like
     * {@link #callSyncMethod(DioritePlugin, Callable)}
     * <ul>
     * <li>Note: The Future.get() methods must NOT be called from the main
     * thread.
     * <li>Note2: There is at least an average of 10ms latency until the
     * isDone() method returns true.
     * </ul>
     *
     * @param <T>           The callable's return type.
     * @param dioritePlugin Plugin that owns the task.
     * @param task          Task to be executed.
     * @param sync          object to synchronize with it.
     *
     * @return Future Future object related to the task
     */
    public abstract <T> Future<T> callSyncMethod(DioritePlugin dioritePlugin, Callable<T> task, Synchronizable sync);

    /**
     * Removes task from scheduler.
     *
     * @param taskId Id number of task to be removed
     */
    public abstract void cancelTask(int taskId);

    /**
     * Removes all tasks associated with a particular plugin from the
     * scheduler.
     *
     * @param dioritePlugin Owner of tasks to be removed
     */
    public abstract void cancelTasks(DioritePlugin dioritePlugin);

    /**
     * Removes all tasks from the scheduler.
     */
    public abstract void cancelAllTasks();

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
    public abstract boolean isCurrentlyRunning(int taskId);

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
    public abstract boolean isQueued(int taskId);

    /**
     * Returns a list of all active workers.
     * <p>
     * This list contains asynch tasks that are being executed by separate
     * threads.
     *
     * @return Active workers
     */
    public abstract List<? extends DioriteWorker> getActiveWorkers();

    /**
     * Returns a list of all pending tasks. The ordering of the tasks is not
     * related to their order of execution.
     *
     * @return Active workers
     */
    public abstract List<? extends DioriteTask> getPendingTasks();

    /**
     * Create task from given builder.
     *
     * @param builder    builder of task.
     * @param startDelay additional start delay before first run of repeating task.
     *
     * @return started task.
     *
     * @throws IllegalArgumentException if task is isngle type and startDelay isn't 0.
     */
    protected abstract DioriteTask runTask(TaskBuilder builder, long startDelay) throws IllegalArgumentException;
}
