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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.plugin.DioritePlugin;
import org.diorite.scheduler.DioriteWorker;

public class DioriteWorkerImpl implements DioriteWorker
{
    private final DioriteAsyncTask dioriteAsyncTask;
    private final Thread           thread;

    public DioriteWorkerImpl(final DioriteAsyncTask dioriteAsyncTask, final Thread thread)
    {
        this.dioriteAsyncTask = dioriteAsyncTask;
        this.thread = thread;
    }

    @Override
    public Thread getThread()
    {
        return this.thread;
    }

    @Override
    public int getTaskId()
    {
        return this.dioriteAsyncTask.getTaskId();
    }

    @Override
    public DioritePlugin getOwner()
    {
        return this.dioriteAsyncTask.getOwner();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("dioriteAsyncTask", this.dioriteAsyncTask.getTaskId()).append("thread", this.thread).toString();
    }
}
