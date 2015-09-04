package org.diorite.impl.world.io.requests;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.io.ChunkIO;

public abstract class Request<OUT> implements Comparable<Request<?>>
{
    protected final Object lock = new Object();

    private volatile OUT     result;
    private volatile boolean finished;
    private final    int     priority;

    protected Request(final int priority)
    {
        this.priority = priority;
    }

    public void setResult(final OUT result)
    {
        this.result = result;
        this.finished = true;
        synchronized (this.lock)
        {
            this.lock.notifyAll();
        }
    }

    public abstract void run(ChunkIO io);

    public abstract int getX();
    public abstract int getZ();

    public OUT await()
    {
        if (this.finished)
        {
            return this.result;
        }
        while (! this.finished)
        {
            try
            {
                synchronized (this.lock)
                {
                    this.lock.wait();
                }
            } catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        return this.result;
    }

    public OUT get()
    {
        if (! this.finished)
        {
            throw new IllegalStateException("Request wasn't yet processed.");
        }
        return this.result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof Request))
        {
            return false;
        }

        final Request<?> request = (Request<?>) o;

        return this.priority == request.priority;
    }

    @Override
    public int hashCode()
    {
        return this.priority;
    }

    @Override
    public int compareTo(final Request<?> o)
    {
        return Integer.compare(o.priority, this.priority);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("result", this.result).append("finished", this.finished).toString();
    }
}
