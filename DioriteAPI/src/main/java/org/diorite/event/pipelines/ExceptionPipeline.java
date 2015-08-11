package org.diorite.event.pipelines;

import org.diorite.utils.pipeline.Pipeline;

/**
 * Pipeline for exception handling events.
 */
public interface ExceptionPipeline extends Pipeline<ExceptionHandler>
{
    /**
     * Try catch an exception using handlers.
     *
     * @param throwable exception to catch.
     */
    void tryCatch(Throwable throwable);
}
