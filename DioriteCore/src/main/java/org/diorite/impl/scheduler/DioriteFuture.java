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

package org.diorite.impl.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.plugin.DioritePlugin;
import org.diorite.scheduler.Synchronizable;

public class DioriteFuture<T> extends DioriteTaskImpl implements Future<T>
{
    private final Callable<T> callable;
    private       T           value;
    private Exception exception = null;

    DioriteFuture(final Callable<T> callable, final DioritePlugin dioritePlugin, final Synchronizable sync, final int id)
    {
        super(callable.getClass().getName() + "@" + System.identityHashCode(callable), dioritePlugin, null, sync, false, id, STATE_SINGLE);
        this.callable = callable;
    }

    @Override
    public synchronized boolean cancel(final boolean mayInterruptIfRunning)
    {
        if (this.getPeriod() != STATE_SINGLE)
        {
            return false;
        }
        this.setPeriod(STATE_CANCEL);
        return true;
    }

    @Override
    public boolean isCancelled()
    {
        return this.getPeriod() == STATE_CANCEL;
    }

    @Override
    public boolean isDone()
    {
        final long period = this.getPeriod();
        return (period != STATE_SINGLE) && (period != STATE_FUTURE);
    }

    @Override
    public T get() throws CancellationException, InterruptedException, ExecutionException
    {
        try
        {
            return this.get(0, TimeUnit.MILLISECONDS);
        } catch (final TimeoutException e)
        {
            throw new Error(e);
        }
    }

    @Override
    public synchronized T get(long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
    {
        timeout = unit.toMillis(timeout);
        long period = this.getPeriod();
        long timestamp = (timeout > 0) ? System.currentTimeMillis() : 0;
        while (true)
        {
            if ((period == STATE_SINGLE) || (period == STATE_FUTURE))
            {
                this.wait(timeout);
                period = this.getPeriod();
                if ((period == - STATE_SINGLE) || (period == STATE_FUTURE))
                {
                    if (timeout == 0)
                    {
                        continue;
                    }
                    timeout += timestamp - (timestamp = System.currentTimeMillis());
                    if (timeout > 0)
                    {
                        continue;
                    }
                    throw new TimeoutException();
                }
            }
            if (period == - STATE_CANCEL)
            {
                throw new CancellationException();
            }
            if (period == STATE_FUTURE_DONE)
            {
                if (this.exception == null)
                {
                    return this.value;
                }
                throw new ExecutionException(this.exception);
            }
            throw new IllegalStateException("Expected from -1 to -4, but got " + period);
        }
    }

    @Override
    public void run()
    {
        synchronized (this)
        {
            if (this.getPeriod() == STATE_CANCEL)
            {
                return;
            }
            this.setPeriod(STATE_FUTURE);
        }
        try
        {
            this.value = this.callable.call();
        } catch (final Exception e)
        {
            this.exception = e;
        } finally
        {
            synchronized (this)
            {
                this.setPeriod(STATE_FUTURE_DONE);
                this.notifyAll();
            }
        }
    }

    @Override
    public synchronized boolean forceCancel()
    {
        if (this.getPeriod() != STATE_SINGLE)
        {
            return false;
        }
        this.setPeriod(STATE_CANCEL);
        this.notifyAll();
        return true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("callable", this.callable).append("value", this.value).append("exception", this.exception).toString();
    }
}
