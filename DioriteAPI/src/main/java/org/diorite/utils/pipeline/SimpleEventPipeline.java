package org.diorite.utils.pipeline;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.Server;
import org.diorite.event.Cancellable;
import org.diorite.event.Event;
import org.diorite.event.EventPriority;
import org.diorite.event.pipelines.EventPipeline;
import org.diorite.event.pipelines.EventPipelineHandler;
import org.diorite.event.pipelines.ExceptionEvent;
import org.diorite.event.pipelines.ExceptionHandler;
import org.diorite.event.pipelines.ExceptionPipeline;

public abstract class SimpleEventPipeline<T extends Event> extends BasePipeline<EventPipelineHandler<T>> implements EventPipeline<T>
{
    protected final ExceptionPipeline exceptionPipeline = new SimpleExceptionPipeline();

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
    protected final           Server                  server       = Diorite.getServer();

    @Override
    public Server getServer()
    {
        return this.server;
    }

    private synchronized void init()
    {
        super.clear();
        this.exceptionPipeline.clear();
        for (final EventPriority ep : EventPriority.values())
        {
            if (! this.containsKey(ep.getPipelineName()))
            {
                this.addLast(ep.getPipelineName(), this.emptyElement);
            }
        }
        this.exceptionPipeline.add("Unhandled", new ExceptionHandler()
        {
            @Override
            public void tryCatch(final ExceptionEvent evt)
            {
                if (evt.isCancelled())
                {
                    return;
                }
                System.err.println("Error when executing pipeline event (" + this.getClass().getName() + " -> " + evt.getClass().getName() + ")");
                //noinspection ThrowableResultOfMethodCallIgnored
                evt.getThrowable().printStackTrace();
                evt.cancel();
            }
        });
    }

    @Override
    public void run(final T evt)
    {
        final Cancellable inst = (evt instanceof Cancellable) ? (Cancellable) evt : null;
        for (final EventPipelineHandler<T> handler : this)
        {
            if (handler.ignoreCancelled() && (inst != null) && inst.isCancelled())
            {
                continue;
            }
            try
            {
                handler.handle(evt, this);
            } catch (final Exception e)
            {
                this.exceptionPipeline.tryCatch(e);

                break;
            }
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

    {
        this.reset();
    }


    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}
