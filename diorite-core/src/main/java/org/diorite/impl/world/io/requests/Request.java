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

package org.diorite.impl.world.io.requests;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.io.ChunkIO;

public abstract class Request<OUT> implements Comparable<Request<?>>
{
    protected final Object lock = new Object();

    private volatile OUT                         result;
    private volatile boolean                     finished;
    private final    int                         priority;
    private          Set<Consumer<Request<OUT>>> onEnd;

    protected Request(final int priority)
    {
        this.priority = priority;
    }

    public void setResult(final OUT result)
    {
        this.result = result;
        this.finished = true;
        if (this.onEnd != null)
        {
            this.onEnd.forEach(r -> r.accept(this));
        }
        synchronized (this.lock)
        {
            this.lock.notifyAll();
        }
    }

    public synchronized void addOnEnd(final Consumer<Request<OUT>> onEnd)
    {
        if (this.onEnd == null)
        {
            this.onEnd = new HashSet<>(3);
        }
        this.onEnd.add(onEnd);
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
