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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.plugin.DioritePlugin;

/**
 * Simple builder class to build all types of tasks.
 *
 * @see DioriteTask
 * @see Scheduler
 */
public class TaskBuilder
{
    private final DioritePlugin dioritePlugin;
    private final Runnable      runnable;
    private boolean        async          = false;
    private boolean        isRealTime     = false;
    private boolean        safeMode       = true;
    private long           delay          = 0;
    private TaskType       type           = TaskType.SINGLE;
    private Synchronizable synchronizable = Diorite.getCore();
    private String name; // optional

    private TaskBuilder(final DioritePlugin dioritePlugin, final Runnable runnable)
    {
        Validate.notNull(dioritePlugin, "Plugin can't by null.");
        Validate.notNull(runnable, "Runnable can't be null.");
        this.dioritePlugin = dioritePlugin;
        this.runnable = runnable;
    }

    /**
     * Chnage task to asynchronous, async task are running from separate threads
     * not related to ticking threads.
     *
     * @return this same task builder for chaining method.
     */
    public TaskBuilder async()
    {
        this.async = true;
        return this;
    }

    /**
     * <b>This is default value/state of builder.</b> <br>
     * Change task type to sync, so it will be executed in one of main server threads.
     *
     * @return this same task builder for chaining method.
     *
     * @see #syncTo(Synchronizable)
     */
    public TaskBuilder sync()
    {
        this.async = false;
        return this;
    }

    /**
     * <b>server instance is default value, don't work for async tasks.</b> <br>
     * Default object is server instance, that means that task will be executed
     * before ticking worlds in main thread. <br>
     * Using other object (entity or chunk) will cause that task will be always executed in this same
     * thread as object is ticked. <br>
     * NOTE: Task store weak reference to object, if weak reference will be free, task will be canceled.
     * <br>
     * This will alse set async mode back to false if needed.
     *
     * @param synchronizable object to sync with it. (task will be executed in this same thread as object is ticked)
     *
     * @return this same task builder for chaining method.
     */
    public TaskBuilder syncTo(final Synchronizable synchronizable)
    {
        Validate.notNull(synchronizable, "Can't synchronize to null object");
        this.async = false;
        this.synchronizable = synchronizable;
        return this;
    }

    /**
     * Change name of task, you don't need set it, it isn't used by any important code. <br>
     * It may be used by some statisitc/timing systems.
     *
     * @param name new name of task.
     *
     * @return this same task builder for chaining method.
     */
    public TaskBuilder name(final String name)
    {
        this.name = name + "@" + System.identityHashCode(this.runnable);
        return this;
    }

    /**
     * Change task delay type to real-time, so it is milliseconds based. <br>
     * Using real-time delay isn't always accurate for sync tasks, if you set
     * delay to 200ms, but server is running with 2TPS, task can be only
     * executed every 500ms.
     *
     * @return this same task builder for chaining method.
     */
    public TaskBuilder realTime()
    {
        this.isRealTime = true;
        return this;
    }

    /**
     * <b>This is default value/state of builder.</b> <br>
     * Change task delay type to game-time, so it is tick based. <br>
     * PS: server lag may extend the duration of tick.
     *
     * @return this same task builder for chaining method.
     */
    public TaskBuilder gameTime()
    {
        this.isRealTime = false;
        return this;
    }

    /**
     * <b>This is default value/state of builder.</b> <br>
     * This works only for sync to object tasks. <br>
     * Safe sync task will automatically unregister when
     * sync object will be invalid, like after player
     * logout or chunk unload. <br>
     *
     * @return this same task builder for chaining method.
     *
     * @see Synchronizable#isValidSynchronizable()
     */
    public TaskBuilder safe()
    {
        this.safeMode = true;
        return this;
    }

    /**
     * This works only for sync to object tasks. <br>
     * This will turn off safe mode! <br>
     * Safe sync task will automatically unregister when
     * sync object will be invalid, like after player
     * logout or chunk unload. <br>
     *
     * @return this same task builder for chaining method.
     *
     * @see Synchronizable#isValidSynchronizable()
     */
    public TaskBuilder unsafe()
    {
        this.safeMode = false;
        return this;
    }

    /**
     * <b>0 is default value.</b>
     * Set delay of task, if task is game time then it is in ticks, <br>
     * if task is real-time then it is in milliseconds. <br>
     * Using real-time delay isn't always accurate for sync tasks, if you set
     * delay to 200ms, but server is running with 2TPS, task can be only
     * executed every 500ms.
     *
     * @param delay value of delay.
     *
     * @return this same task builder for chaining method.
     */
    public TaskBuilder delay(final long delay)
    {
        this.delay = delay;
        return this;
    }

    /**
     * <b>This is default value/state of builder.</b> <br>
     * Change task type to single, so it will be only executed once.
     *
     * @return this same task builder for chaining method.
     */
    public TaskBuilder single()
    {
        this.type = TaskType.SINGLE;
        return this;
    }

    /**
     * Change task type to repeated, so it will be executed multiple
     * times with given delay between each run. <br>
     * It's possible to add extra delay before first run, see: {@link #start(long)}
     *
     * @return this same task builder for chaining method.
     */
    public TaskBuilder repeated()
    {
        this.type = TaskType.REPEATED;
        return this;
    }

    /**
     * Finish and register task.
     *
     * @return finished and registered diorite task.
     */
    public DioriteTask start()
    {
        return this.start(0);
    }

    /**
     * Finish and register task with given delay. <br>
     * If task is of single type, this delay will be added to task delay. <br>
     * If it is repeated type, then first run of task will be delayed by this time. <br>
     * This delay works like task delay, if task dealy is real-time, then it is also real-time.
     *
     * @param startDelay delay to first run.
     *
     * @return finished and registered diorite task.
     */
    public DioriteTask start(final long startDelay)
    {
        if (this.type == TaskType.SINGLE)
        {
            this.delay += startDelay;
            return Diorite.getScheduler().runTask(this, 0);
        }
        if (this.name == null)
        {
            this.name = this.runnable.getClass().getName() + "@" + System.identityHashCode(this.runnable);
        }
        return Diorite.getScheduler().runTask(this, startDelay);
    }

    /**
     * Create new TaskBuilder with selected runnable, it can't be null.
     *
     * @param dioritePlugin plugin that want register task.
     * @param runnable      runnable to use as task.
     *
     * @return new task builder.
     *
     * @see #async(DioritePlugin, Runnable)
     * @see #sync(DioritePlugin, Runnable)
     * @see #sync(DioritePlugin, Runnable, Synchronizable)
     * @see #start()
     */
    public static TaskBuilder start(final DioritePlugin dioritePlugin, final Runnable runnable)
    {
        return new TaskBuilder(dioritePlugin, runnable);
    }

    /**
     * Simple method to create new sync task and run it. <br>
     * Equal to: <br>
     * <ol>
     * <li>{@link #start(DioritePlugin, Runnable)}</li>
     * <li>{@link #start()}</li>
     * </ol>
     *
     * @param dioritePlugin plugin that want register task.
     * @param runnable      runnable to use as task.
     *
     * @return finished and registered diorite task.
     */
    public static DioriteTask sync(final DioritePlugin dioritePlugin, final Runnable runnable)
    {
        return new TaskBuilder(dioritePlugin, runnable).start();
    }

    /**
     * Simple method to create new sync task and run it. <br>
     * Equal to: <br>
     * <ol>
     * <li>{@link #start(DioritePlugin, Runnable)}</li>
     * <li>{@link #syncTo(Synchronizable)}</li>
     * <li>{@link #start()}</li>
     * </ol>
     *
     * @param dioritePlugin  plugin that want register task.
     * @param runnable       runnable to use as task.
     * @param synchronizable object to sync with it. (task will be executed in this same thread as object is ticked as long as object exist in memory)
     *
     * @return finished and registered diorite task.
     */
    public static DioriteTask sync(final DioritePlugin dioritePlugin, final Runnable runnable, final Synchronizable synchronizable)
    {
        return new TaskBuilder(dioritePlugin, runnable).syncTo(synchronizable).start();
    }

    /**
     * Simple method to create new async task and run it. <br>
     * Equal to: <br>
     * <ol>
     * <li>{@link #start(DioritePlugin, Runnable)}</li>
     * <li>{@link #async()}</li>
     * <li>{@link #start()}</li>
     * </ol>
     *
     * @param dioritePlugin plugin that want register task.
     * @param runnable      runnable to use as task.
     *
     * @return finished and registered diorite task.
     */
    public static DioriteTask async(final DioritePlugin dioritePlugin, final Runnable runnable)
    {
        return new TaskBuilder(dioritePlugin, runnable).async().start();
    }

    /**
     * Getters
     */

    /**
     * @return plugin that wan't register this task.
     */
    public DioritePlugin getPlugin()
    {
        return this.dioritePlugin;
    }

    /**
     * @return name of task.
     */
    public String getName()
    {
        if ((this.name == null))
        {
            return this.runnable.getClass().getName() + "@" + System.identityHashCode(this.runnable);
        }
        return this.name;
    }

    /**
     * Check if task will be in safe mode. <br>
     * This works only for sync to object tasks. <br>
     * Safe sync task will automatically unregister when
     * sync object will be invalid, like after player
     * logout or chunk unload. <br>
     *
     * @return if task is in safe mode.
     *
     * @see Synchronizable#isValidSynchronizable()
     */
    public boolean isSafeMode()
    {
        return this.safeMode;
    }

    /**
     * Only for sync tasks, async task can't use synchronizable objects. <br>
     * Default object is server instance, that means that task will be executed
     * before ticking worlds in main thread. <br>
     * Using other object (entity or chunk) will cause that task will be always executed in this same
     * thread as object is ticked. <br>
     * NOTE: Task store weak reference to object, if weak reference will be free, task will be canceled.
     *
     * @return Synchronizable object, or null if task is async.
     */
    public Synchronizable getSynchronizable()
    {
        if (this.async)
        {
            return null;
        }
        return this.synchronizable;
    }

    /**
     * Asynchronous task are running in separate threads not related to
     * server ticking threads.
     *
     * @return true if task is asynchronous.
     */
    public boolean isAsync()
    {
        return this.async;
    }

    /**
     * This runnable will be executed as task.
     *
     * @return runnable to use by task.
     */
    public Runnable getRunnable()
    {
        return this.runnable;
    }

    /**
     * Real rime is in milliseconds, and game time is in ticks.
     *
     * @return true if task use real-time instead of game-time.
     */
    public boolean isRealTime()
    {
        return this.isRealTime;
    }

    /**
     * Delay can be in milliseconds if real-time is used, or in game
     * ticks, if game time is used. <br>
     * <br>
     * If task is single, this is dealy before run. <br>
     * If task is repeated, this is delay between runs.
     *
     * @return dealy of/between task.
     */
    public long getDelay()
    {
        return this.delay;
    }

    /**
     * Single task is executed only once.
     *
     * @return if task is Single.
     */
    public boolean isSingle()
    {
        return this.type == TaskType.SINGLE;
    }

    /**
     * Repeated task is executed multiple time with selected delay.
     *
     * @return if task is Repeated.
     */
    public boolean isRepeated()
    {
        return this.type == TaskType.REPEATED;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("runnable", this.runnable).append("async", this.async).append("isRealTime", this.isRealTime).append("delay", this.delay).append("synchronizable", this.synchronizable).toString();
    }

    private enum TaskType
    {
        SINGLE,
        REPEATED
    }
}
