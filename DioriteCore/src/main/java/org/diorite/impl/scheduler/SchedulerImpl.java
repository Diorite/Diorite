package org.diorite.impl.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.plugin.PluginMainClass;
import org.diorite.scheduler.DioriteTask;
import org.diorite.scheduler.DioriteWorker;
import org.diorite.scheduler.Scheduler;
import org.diorite.scheduler.Synchronizable;
import org.diorite.scheduler.TaskBuilder;

@SuppressWarnings("ObjectEquality")
public class SchedulerImpl extends Scheduler
{
    // TODO: This is only test code, improve it later.
    private final GameTimeScheduler gts = new GameTimeScheduler();
    private final RealTimeScheduler rts = new RealTimeScheduler();


    @Override
    protected DioriteTask runTask(final TaskBuilder builder, final long startDelay) throws IllegalArgumentException
    {
        if (builder.isRealTime())
        {
            return this.rts.runTask(builder, startDelay);
        }
        return this.gts.runTask(builder, startDelay);
    }

    @Override
    public <T> Future<T> callSyncMethod(final PluginMainClass pluginMainClass, final Callable<T> task)
    {
        return this.gts.callSyncMethod(pluginMainClass, task);
    }

    @Override
    public <T> Future<T> callSyncMethod(final PluginMainClass pluginMainClass, final Callable<T> task, final Synchronizable sync)
    {
        return this.gts.callSyncMethod(pluginMainClass, task, sync);
    }

    @Override
    public void cancelTask(final int taskId)
    {
        this.gts.cancelTask(taskId);
        this.rts.cancelTask(taskId);
    }

    @Override
    public void cancelTasks(final PluginMainClass pluginMainClass)
    {
        this.gts.cancelTasks(pluginMainClass);
        this.rts.cancelTasks(pluginMainClass);
    }

    @Override
    public void cancelAllTasks()
    {
        this.gts.cancelAllTasks();
        this.rts.cancelAllTasks();
    }

    @Override
    public boolean isCurrentlyRunning(final int taskId)
    {
        return this.gts.isCurrentlyRunning(taskId) || this.rts.isCurrentlyRunning(taskId);
    }

    @Override
    public boolean isQueued(final int taskId)
    {
        return this.gts.isQueued(taskId) || this.rts.isQueued(taskId);
    }

    @Override
    public List<? extends DioriteWorker> getActiveWorkers()
    {
        final List<DioriteWorker> workers = new ArrayList<>(this.gts.getActiveWorkers());
        workers.addAll(this.rts.getActiveWorkers());
        return workers;
    }

    @Override
    public List<? extends DioriteTask> getPendingTasks()
    {
        final List<DioriteTask> workers = new ArrayList<>(this.gts.getPendingTasks());
        workers.addAll(this.rts.getPendingTasks());
        return workers;
    }

    protected DioriteTask handle(final DioriteTaskImpl task, final long delay)
    {
        throw new UnsupportedOperationException("This method should be never used directly");
    }

    public void tick(final long currentTick, final boolean withAsync)
    {
        this.gts.tick(currentTick, withAsync);
        this.rts.tick(currentTick, withAsync);
    }

    @SuppressWarnings("ClassHasNoToStringMethod")
    private abstract static class BaseScheduler extends Scheduler
    {
        protected final    AtomicInteger                               ids     = new AtomicInteger(1);
        protected volatile DioriteTaskImpl                             head    = new DioriteTaskImpl("{HEAD}");
        protected final    AtomicReference<DioriteTaskImpl>            tail    = new AtomicReference<>(this.head);
        protected final    PriorityQueue<DioriteTaskImpl>              pending = new PriorityQueue<>(10, (o1, o2) -> (int) (o1.getNextRun() - o2.getNextRun()));
        protected final    List<DioriteTaskImpl>                       temp    = new ArrayList<>(100);
        protected final    ConcurrentHashMap<Integer, DioriteTaskImpl> runners = new ConcurrentHashMap<>(200);

        protected abstract DioriteTask handle(final DioriteTaskImpl task, final long delay);

        protected abstract void tick(final long currentTick, final boolean withAsync);

        protected int nextId()
        {
            return this.ids.incrementAndGet();
        }

        @Override
        protected DioriteTask runTask(final TaskBuilder builder, final long startDelay) throws IllegalArgumentException
        {
            final PluginMainClass pluginMainClass = builder.getPlugin();
            final Runnable task = builder.getRunnable();
            validate(pluginMainClass, task);
            if (builder.isSingle() && (startDelay != 0))
            {
                throw new IllegalArgumentException("Single task can't have additional delay.");
            }
            final boolean isSingle = builder.isSingle();
            long delay = isSingle ? builder.getDelay() : startDelay;
            long period = isSingle ? - 1 : builder.getDelay();
            if (delay < 0)
            {
                delay = 0;
            }
            if (period == 0)
            {
                period = 1;
            }
            else if (period < - 1)
            {
                period = - 1;
            }
            if (builder.isAsync())
            {
                return this.handle(new DioriteAsyncTask(builder.getName(), this.runners, pluginMainClass, task, this.nextId(), period), delay);

            }
            return this.handle(new DioriteTaskImpl(builder.getName(), pluginMainClass, task, builder.getSynchronizable(), builder.isSafeMode(), this.nextId(), period), delay);
        }

        @Override
        public <T> Future<T> callSyncMethod(final PluginMainClass pluginMainClass, final Callable<T> task)
        {
            return this.callSyncMethod(pluginMainClass, task, Diorite.getServer());
        }

        @Override
        public <T> Future<T> callSyncMethod(final PluginMainClass pluginMainClass, final Callable<T> task, final Synchronizable sync)
        {
            Validate.notNull(sync, "Can't synchronize to null object");
            validate(pluginMainClass, task);
            final DioriteFuture<T> future = new DioriteFuture<>(task, pluginMainClass, sync, this.nextId());
            this.handle(future, 0);
            return future;
        }

        @Override
        public void cancelTask(final int taskId)
        {
            if (taskId <= 0)
            {
                return;
            }
            DioriteTaskImpl task = this.runners.get(taskId);
            if (task != null)
            {
                task.forceCancel();
            }
            task = new DioriteTaskImpl(new Runnable()
            {
                @Override
                public void run()
                {
                    if (! check(BaseScheduler.this.temp))
                    {
                        check(BaseScheduler.this.pending);
                    }
                }

                private boolean check(final Iterable<DioriteTaskImpl> collection)
                {
                    final Iterator<DioriteTaskImpl> tasks = collection.iterator();
                    while (tasks.hasNext())
                    {
                        final DioriteTaskImpl task = tasks.next();
                        if (task.getTaskId() == taskId)
                        {
                            task.forceCancel();
                            tasks.remove();
                            if (! task.isAsync())
                            {
                                BaseScheduler.this.runners.remove(taskId);
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
            this.handle(task, 0);
            for (DioriteTaskImpl taskPending = this.head.getNext(); taskPending != null; taskPending = taskPending.getNext())
            {
                if (taskPending == task)
                {
                    return;
                }
                if (taskPending.getTaskId() == taskId)
                {
                    taskPending.forceCancel();
                }
            }
        }

        @Override
        public void cancelTasks(final PluginMainClass pluginMainClass)
        {
            Validate.notNull(pluginMainClass, "Cannot cancel tasks of null plugin");
            final DioriteTaskImpl task = new DioriteTaskImpl(new Runnable()
            {
                @Override
                public void run()
                {
                    check(BaseScheduler.this.pending);
                    check(BaseScheduler.this.temp);
                }

                void check(final Iterable<DioriteTaskImpl> collection)
                {
                    final Iterator<DioriteTaskImpl> tasks = collection.iterator();
                    while (tasks.hasNext())
                    {
                        final DioriteTaskImpl task = tasks.next();
                        if (task.getOwner().equals(pluginMainClass))
                        {
                            task.forceCancel();
                            tasks.remove();
                            if (! task.isAsync())
                            {
                                BaseScheduler.this.runners.remove(task.getTaskId());
                            }
                        }
                    }
                }
            });
            this.handle(task, 0);
            for (DioriteTaskImpl taskPending = this.head.getNext(); taskPending != null; taskPending = taskPending.getNext())
            {
                if (taskPending == task)
                {
                    return;
                }
                if ((taskPending.getTaskId() != - 1) && taskPending.getOwner().equals(pluginMainClass))
                {
                    taskPending.forceCancel();
                }
            }
            this.runners.values().stream().filter(runner -> runner.getOwner().equals(pluginMainClass)).forEach(DioriteTaskImpl::forceCancel);
        }

        @Override
        public void cancelAllTasks()
        {
            final DioriteTaskImpl task = new DioriteTaskImpl(() -> {
                final Iterator<DioriteTaskImpl> it = BaseScheduler.this.runners.values().iterator();
                while (it.hasNext())
                {
                    final DioriteTaskImpl task1 = it.next();
                    task1.forceCancel();
                    if (! task1.isAsync())
                    {
                        it.remove();
                    }
                }
                BaseScheduler.this.pending.clear();
                BaseScheduler.this.temp.clear();
            });
            this.handle(task, 0);
            for (DioriteTaskImpl taskPending = this.head.getNext(); taskPending != null; taskPending = taskPending.getNext())
            {
                if (taskPending == task)
                {
                    break;
                }
                taskPending.forceCancel();
            }
            this.runners.values().forEach(DioriteTaskImpl::forceCancel);
        }

        @Override
        public boolean isCurrentlyRunning(final int taskId)
        {
            final DioriteTaskImpl task = this.runners.get(taskId);
            if ((task == null) || ! task.isAsync())
            {
                return false;
            }
            final DioriteAsyncTask asyncTask = (DioriteAsyncTask) task;
            synchronized (asyncTask.getWorkers())
            {
                return asyncTask.getWorkers().isEmpty();
            }
        }

        @Override
        public boolean isQueued(final int taskId)
        {
            if (taskId <= 0)
            {
                return false;
            }
            for (DioriteTaskImpl task = this.head.getNext(); task != null; task = task.getNext())
            {
                if (task.getTaskId() == taskId)
                {
                    return task.getPeriod() >= - 1; // The task will run
                }
            }
            final DioriteTaskImpl task = this.runners.get(taskId);
            return (task != null) && (task.getPeriod() >= - 1);
        }

        @Override
        public List<DioriteWorker> getActiveWorkers()
        {
            final List<DioriteWorker> workers = new ArrayList<>(50);
            for (final DioriteTaskImpl taskObj : this.runners.values())
            {
                if (! taskObj.isAsync())
                {
                    continue;
                }
                final DioriteAsyncTask task = (DioriteAsyncTask) taskObj;
                synchronized (task.getWorkers())
                {
                    workers.addAll(task.getWorkers());
                }
            }
            return workers;
        }

        @Override
        public List<? extends DioriteTask> getPendingTasks()
        {
            final Collection<DioriteTaskImpl> truePending = new ArrayList<>(200);
            for (DioriteTaskImpl task = this.head.getNext(); task != null; task = task.getNext())
            {
                if (task.getTaskId() != DioriteTaskImpl.STATE_CANCEL)
                {
                    truePending.add(task);
                }
            }
            final List<DioriteTaskImpl> pending = this.runners.values().stream().filter(task -> task.getPeriod() >= - 1).collect(Collectors.toList());
            truePending.stream().filter(task -> (task.getPeriod() >= - 1) && ! pending.contains(task)).forEach(pending::add);
            return pending;
        }

        protected void parsePending()
        {
            DioriteTaskImpl head = this.head;
            DioriteTaskImpl task = head.getNext();
            DioriteTaskImpl lastTask = head;
            for (; task != null; task = (lastTask = task).getNext())
            {
                if (task.getTaskId() == DioriteTaskImpl.STATE_CANCEL)
                {
                    task.run();
                }
                else if (task.getPeriod() >= - 1)
                {
                    this.pending.add(task);
                    this.runners.put(task.getTaskId(), task);
                }
            }
            // We split this because of the way things are ordered for all of the async calls in SchedulerImpl
            // (it prevents race-conditions)
            for (task = head; task != lastTask; task = head)
            {
                head = task.getNext();
                task.setNext(null);
            }
            this.head = lastTask;
        }

        protected void addTask(final DioriteTaskImpl task)
        {
            final AtomicReference<DioriteTaskImpl> tail = this.tail;
            DioriteTaskImpl tailTask = tail.get();
            while (! tail.compareAndSet(tailTask, task))
            {
                tailTask = tail.get();
            }
            tailTask.setNext(task);
        }

        protected boolean isReady(final long time, final boolean withAsync)
        {
            if (! this.pending.isEmpty())
            {
                final DioriteTaskImpl task = this.pending.peek();
                final boolean async = task.isAsync();
                if (task.getNextRun() <= time)
                {
                    if (async && withAsync) // async task are executed on server tick, don't execute them when ticking other parts of server.
                    {
                        return true;
                    }
                    final Synchronizable sync = task.getSynchronizable();
                    return ((sync != null) && (sync.getLastTickThread() == Thread.currentThread()));
                }
            }
            return false;
        }
    }

    private class GameTimeScheduler extends BaseScheduler
    {
        protected volatile long     currentTick = - 1;
        private final      Executor executor    = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("{DioriteTask-%1$d}").build());

        @Override
        public void tick(final long currentTick, final boolean withAsync)
        {
            this.currentTick = currentTick;
            final List<DioriteTaskImpl> temp = this.temp;
            this.parsePending();
            while (this.isReady(currentTick, withAsync))
            {
                final DioriteTaskImpl task = this.pending.remove();
                if (task.getPeriod() < - 1)
                {
                    if (! task.isAsync())
                    {
                        this.runners.remove(task.getTaskId(), task);
                    }
                    this.parsePending();
                    continue;
                }
                if (! task.isAsync())
                {
                    try
                    {
                        task.run();
                    } catch (final Throwable throwable)
                    {
                        // TODO: add better logging
                        System.err.println(String.format("Task #%s for %s generated an exception", task.getTaskId(), task.getOwner().getName()));
                        throwable.printStackTrace();
                    }
                    this.parsePending();
                }
                else
                {
                    this.executor.execute(task);
                }
                final long period = task.getPeriod();
                if (period > 0)
                {
                    task.setNextRun(currentTick + period);
                    temp.add(task);
                }
                else if (! task.isAsync())
                {
                    this.runners.remove(task.getTaskId());
                }
            }
            this.pending.addAll(temp);
            temp.clear();
        }

        @Override
        protected DioriteTask handle(final DioriteTaskImpl task, final long delay)
        {
            task.setNextRun(this.currentTick + delay);
            this.addTask(task);
            return task;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("ids", this.ids).append("currentTick", this.currentTick).toString();
        }
    }

    private class RealTimeScheduler extends BaseScheduler
    {
        protected volatile long                     time     = System.currentTimeMillis();
        protected final    ScheduledExecutorService executor = Executors.newScheduledThreadPool(2, new ThreadFactoryBuilder().setNameFormat("{DioriteTaskRT-%1$d}").build());

        @Override
        public void tick(final long curTick, final boolean withAsync)
        {
            final long time = System.currentTimeMillis();
            this.time = time;
            final List<DioriteTaskImpl> temp = this.temp;
            this.parsePending();
            while (this.isReady(time, withAsync))
            {
                final DioriteTaskImpl task = this.pending.remove();
                if (task.getPeriod() < - 1)
                {
                    if (! task.isAsync())
                    {
                        this.runners.remove(task.getTaskId(), task);
                    }
                    this.parsePending();
                    continue;
                }
                if (! task.isAsync())
                {
                    try
                    {
                        task.run();
                    } catch (final Throwable throwable)
                    {
                        // TODO: add better logging
                        System.err.println(String.format("Task #%s for %s generated an exception", task.getTaskId(), task.getOwner().getName()));
                        throwable.printStackTrace();
                    }
                    this.parsePending();
                }
                // don't execuite async task, it's handled by other code.
                final long period = task.getPeriod();
                if (period > 0)
                {
                    task.setNextRun(time + period);
                    temp.add(task);
                }
                else if (! task.isAsync())
                {
                    this.runners.remove(task.getTaskId());
                }
            }
            this.pending.addAll(temp);
            temp.clear();
        }

        @Override
        protected DioriteTask handle(final DioriteTaskImpl task, final long delay)
        {
            if (task instanceof DioriteAsyncTask)
            {
                final DioriteAsyncTask aTask = (DioriteAsyncTask) task;
                if (aTask.getPeriod() == DioriteTaskImpl.STATE_SINGLE)
                {
                    this.executor.schedule(aTask, delay, TimeUnit.MILLISECONDS);
                }
                else
                {
                    this.executor.scheduleAtFixedRate(() -> {
                        aTask.run();
                        final long period = task.getPeriod();
                        if (period > 0)
                        {
                            aTask.setNextRun(System.currentTimeMillis() + period);
                        }
                    }, delay, aTask.getPeriod(), TimeUnit.MILLISECONDS);
                }
            }
            task.setNextRun(this.time + delay);
            this.addTask(task);
            return task;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("ids", this.ids).append("time", this.time).toString();
        }
    }

    private static void validate(final PluginMainClass pluginMainClass, final Object task)
    {
        Validate.notNull(pluginMainClass, "Plugin cannot be null");
        Validate.notNull(task, "Task cannot be null");
        // TODO: check if plugin is enabled.
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("gts", this.gts).append("rts", this.rts).toString();
    }
}
