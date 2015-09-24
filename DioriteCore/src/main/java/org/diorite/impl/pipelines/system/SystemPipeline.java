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
