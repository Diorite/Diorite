package org.diorite.impl.pipelines;

import org.diorite.event.pipelines.ExceptionEvent;
import org.diorite.event.pipelines.ExceptionHandler;
import org.diorite.event.pipelines.ExceptionPipeline;
import org.diorite.utils.pipeline.BasePipeline;

public class ExceptionPipelineImpl extends BasePipeline<ExceptionHandler> implements ExceptionPipeline
{
    @Override
    public void tryCatch(final Throwable throwable)
    {
        final ExceptionEvent event = new ExceptionEvent(throwable);
        for (final ExceptionHandler handler : this)
        {
            if (handler.ignoreCancelled() && event.isCancelled())
            {
                continue;
            }
            handler.tryCatch(event);
        }
        if (! event.isCancelled())
        {
            throw new RuntimeException(event.getThrowable());
        }
    }
}
