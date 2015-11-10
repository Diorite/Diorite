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

package org.diorite.utils.pipeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Core;
import org.diorite.Diorite;
import org.diorite.event.Event;
import org.diorite.event.EventPriority;
import org.diorite.event.pipelines.EventPipeline;
import org.diorite.event.pipelines.EventPipelineHandler;
import org.diorite.event.pipelines.ExceptionEvent;
import org.diorite.event.pipelines.ExceptionHandler;
import org.diorite.event.pipelines.ExceptionPipeline;
import org.diorite.utils.timings.TimingsContainer;

public abstract class SimpleEventPipeline<T extends Event> extends BasePipeline<EventPipelineHandler<T>> implements EventPipeline<T>
{
    private final   Map<EventPipelineHandler<T>, TimingsContainer> timings           = new HashMap<>(5);
    protected final ExceptionPipeline                              exceptionPipeline = new SimpleExceptionPipeline();

    /**
     * used by priority "handlers"
     */
    protected final transient EventPipelineHandler<T> emptyElement = new EventPipelineHandler<T>()
    {
        @Override
        public void handle(final T evt, final EventPipeline<T> pipeline)
        {
            // ignored
        }

        @Override
        public boolean ignoreCancelled()
        {
            return true;
        }
    };
    protected final           Core                    core         = Diorite.getCore();

    @Override
    public Core getCore()
    {
        return this.core;
    }

    @Override
    public Map<EventPipelineHandler<T>, TimingsContainer> getTimings()
    {
        return this.timings;
    }

    private synchronized void init()
    {
        super.clear();
        this.exceptionPipeline.clear();
        for (final EventPriority ep : EventPriority.values())
        {
            if (! this.containsKey(ep.getPipelineName()))
            {
                this.addFirst(ep.getPipelineName(), this.emptyElement);
            }
        }
        this.exceptionPipeline.add("Unhandled", new EventExceptionHandler());
    }

    @Override
    public void run(final T evt)
    {
        final Iterator<Map.Entry<String, EventPipelineHandler<T>>> entries = this.entriesIterator();
        while (entries.hasNext())
        {
            final Map.Entry<String, EventPipelineHandler<T>> entry = entries.next();
            final EventPipelineHandler<T> handler = entry.getValue();
            //noinspection ObjectEquality
            if (handler == this.emptyElement)
            {
                continue;
            }
            if (handler.ignoreCancelled() && evt.isCancelled())
            {
                continue;
            }

            final long start = System.currentTimeMillis();
            try
            {
                handler.handle(evt, this);
            } catch (final Exception e)
            {
                this.exceptionPipeline.tryCatch(e);

                break;
            }

            if (! Diorite.getTimings().isCollecting())
            {
                continue;
            }

            final long end = System.currentTimeMillis() - start;
            TimingsContainer timings = this.timings.get(handler);
            if (timings == null)
            {
                timings = new TimingsContainer(entry.getKey());
                this.timings.put(handler, timings);
            }
            timings.recordTiming(end);
        }
    }

    @Override
    public ExceptionPipeline getExceptionPipeline()
    {
        return this.exceptionPipeline;
    }

    @Override
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

    private static class EventExceptionHandler implements ExceptionHandler
    {
        @Override
        public void tryCatch(final ExceptionEvent evt)
        {
            if (evt.isCancelled())
            {
                return;
            }
            System.err.println("Error when executing pipeline event (" + this.getClass().getName() + " -> " + evt.getClass().getName() + ")");
            evt.getThrowable().printStackTrace();
            evt.cancel();
        }
    }

    {
        this.reset();
    }


    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}
