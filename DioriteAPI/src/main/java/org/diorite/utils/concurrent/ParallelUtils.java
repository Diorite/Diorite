package org.diorite.utils.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class ParallelUtils
{
    private static final Runtime runtime = Runtime.getRuntime();

    private ParallelUtils()
    {
    }

    public static int getCores()
    {
        return runtime.availableProcessors();
    }

    public static void realParallelStream(final Runnable streamAction, final int parallelism)
    {
        realParallelStream(streamAction, parallelism, true);
    }

    public static void realParallelStream(final Runnable streamAction)
    {
        realParallelStream(streamAction, runtime.availableProcessors(), true);
    }

    public static void realParallelStream(final Runnable streamAction, final int parallelism, final boolean await)
    {
        final ForkJoinPool pool = new ForkJoinPool(parallelism);
        if (await)
        {
            pool.invoke(createSimpleTask(streamAction));
        }
        else
        {
            pool.submit(streamAction);
        }
    }

    public static void realParallelStream(final Runnable streamAction, final boolean await)
    {
        realParallelStream(streamAction, runtime.availableProcessors(), await);
    }

    public static <T> T realParallelStream(final Supplier<T> streamAction, final int parallelism)
    {
        final ForkJoinPool pool = new ForkJoinPool(parallelism);
        return pool.invoke(createTask(streamAction));
    }

    public static <T> T realParallelStream(final Supplier<T> streamAction)
    {
        return realParallelStream(streamAction, runtime.availableProcessors());
    }

    public static void realParallelStream(final Runnable streamAction, final int parallelism, final String name)
    {
        realParallelStream(streamAction, parallelism, true, name);
    }

    public static void realParallelStream(final Runnable streamAction, final String name)
    {
        realParallelStream(streamAction, runtime.availableProcessors(), true, name);
    }

    public static void realParallelStream(final Runnable streamAction, final int parallelism, final boolean await, final String name)
    {
        final ForkJoinPool pool = new ForkJoinPool(parallelism, new NamedForkJoinWorkerFactory(name), null, false);
        if (await)
        {
            pool.invoke(createSimpleTask(streamAction));
        }
        else
        {
            pool.submit(streamAction);
        }
    }

    public static void realParallelStream(final Runnable streamAction, final boolean await, final String name)
    {
        realParallelStream(streamAction, runtime.availableProcessors(), await, name);
    }

    public static <T> T realParallelStream(final Supplier<T> streamAction, final int parallelism, final String name)
    {
        final ForkJoinPool pool = new ForkJoinPool(parallelism, new NamedForkJoinWorkerFactory(name), null, false);
        return pool.invoke(createTask(streamAction));
    }

    public static <T> T realParallelStream(final Supplier<T> streamAction, final String name)
    {
        return realParallelStream(streamAction, runtime.availableProcessors(), name);
    }


    private static ForkJoinTask<Void> createSimpleTask(final Runnable runnable)
    {
        return new ForkJoinTask<Void>()
        {
            @Override
            public Void getRawResult()
            {
                return null;
            }

            @Override
            protected void setRawResult(final Void value)
            {
            }

            @Override
            protected boolean exec()
            {
                runnable.run();
                return true;
            }
        };
    }

    private static ForkJoinTask<Void> createSimpleTask(final Supplier<Boolean> runnable)
    {
        return new ForkJoinTask<Void>()
        {
            @Override
            public Void getRawResult()
            {
                return null;
            }

            @Override
            protected void setRawResult(final Void value)
            {
            }

            @Override
            protected boolean exec()
            {
                return runnable.get();
            }
        };
    }

    private static <T> ForkJoinTask<T> createTask(final Supplier<T> runnable)
    {
        return new ForkJoinTask<T>()
        {
            private T result;

            @Override
            public T getRawResult()
            {
                return this.result;
            }

            @Override
            protected void setRawResult(final T value)
            {
                this.result = value;
            }

            @Override
            protected boolean exec()
            {
                this.result = runnable.get();
                return true;
            }
        };
    }

    private static class NamedForkJoinWorkerThread extends ForkJoinWorkerThread
    {

        private NamedForkJoinWorkerThread(final ForkJoinPool pool, final String name)
        {
            super(pool);
            this.setName(name);
        }
    }

    private static class NamedForkJoinWorkerFactory implements ForkJoinWorkerThreadFactory
    {
        private final String baseName;
        private final AtomicInteger i = new AtomicInteger(0);

        private NamedForkJoinWorkerFactory(final String baseName)
        {
            this.baseName = baseName;
        }

        @Override
        public final ForkJoinWorkerThread newThread(final ForkJoinPool pool)
        {
            return new NamedForkJoinWorkerThread(pool, this.baseName + "-" + this.i.getAndIncrement());
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("baseName", this.baseName).append("i", this.i).toString();
        }
    }
}
