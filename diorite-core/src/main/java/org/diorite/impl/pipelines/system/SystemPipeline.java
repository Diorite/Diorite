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

package org.diorite.impl.pipelines.system;

import org.diorite.impl.DioriteCore;
import org.diorite.event.pipelines.ExceptionEvent;
import org.diorite.event.pipelines.ExceptionHandler;
import org.diorite.event.pipelines.ExceptionPipeline;
import org.diorite.utils.pipeline.BasePipeline;
import org.diorite.utils.pipeline.SimpleExceptionPipeline;

@SuppressWarnings("ClassHasNoToStringMethod")
public abstract class SystemPipeline<T> extends BasePipeline<SystemPipelineHandler<T>>
{
    protected final ExceptionPipeline exceptionPipeline = new SimpleExceptionPipeline();

    private synchronized void init()
    {
        super.clear();
        this.exceptionPipeline.clear();
        this.exceptionPipeline.add("Unhandled", new SystemExceptionHandler());
    }

    public void run(final DioriteCore core, final T data)
    {
        for (final SystemPipelineHandler<T> handler : this)
        {
            try
            {
                handler.handle(core, this, data);
            } catch (final Exception e)
            {
                this.exceptionPipeline.tryCatch(e);

                break;
            }
        }
    }

    public ExceptionPipeline getExceptionPipeline()
    {
        return this.exceptionPipeline;
    }

    public synchronized void reset()
    {
        this.init();
        this.reset_();
    }

    public abstract void reset_();

    @Override
    public synchronized void clear()
    {
        this.init();
    }

    private static class SystemExceptionHandler implements ExceptionHandler
    {
        @Override
        public void tryCatch(final ExceptionEvent evt)
        {
            if (evt.isCancelled())
            {
                return;
            }
            System.err.println("Error when executing core-level pipeline (" + this.getClass().getName() + " -> " + evt.getClass().getName() + ")");
            evt.getThrowable().printStackTrace();
            evt.cancel();
        }
    }

    {
        this.reset();
    }

}
