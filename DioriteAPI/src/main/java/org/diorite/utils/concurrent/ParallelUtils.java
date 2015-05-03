package org.diorite.utils.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Supplier;

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
}
