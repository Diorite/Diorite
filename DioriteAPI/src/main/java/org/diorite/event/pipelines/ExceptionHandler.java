package org.diorite.event.pipelines;

/**
 * Interface used by {@link ExceptionPipeline} to handle exceptions.
 */
@FunctionalInterface
public interface ExceptionHandler
{
    /**
     * Try catch an exception (cancel event)
     *
     * @param evt exception to catch.
     */
    void tryCatch(final ExceptionEvent evt);

    /**
     * If this will return true,
     * then exception pipeline will not invoke {@link #tryCatch(ExceptionEvent)}
     * method if event is cancelled.
     *
     * @return if handler should ignore cancelled/handled exceptions.
     */
    default boolean ignoreCancelled()
    {
        return false;
    }
}
