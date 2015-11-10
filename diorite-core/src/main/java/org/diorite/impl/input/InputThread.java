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

package org.diorite.impl.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class InputThread
{
    private final Queue<InputAction> actions = new ConcurrentLinkedQueue<>();
    private final ForkJoinPool pool;

    public InputThread(final ForkJoinPool pool)
    {
        this.pool = pool;
    }

    public void add(final InputAction action)
    {
        if ((action == null) || ! action.getType().checkAction(action))
        {
            return;
        }
        this.pool.submit(() -> action.getType().doAction(action));
    }

    public int getActionsSize()
    {
        return this.actions.size();
    }

    public static InputThread start(final int poolSize)
    {
        return new InputThread(new ForkJoinPool(poolSize, InputWorkerThread::new, null, false));
    }


    public static class InputWorkerThread extends ForkJoinWorkerThread
    {
        static final AtomicInteger i = new AtomicInteger();

        public InputWorkerThread(final ForkJoinPool pool)
        {
            super(pool);
            this.setName("{Diorite|Input-" + i.getAndIncrement() + "}");
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("actions", this.actions).append("pool", this.pool).toString();
    }
}
