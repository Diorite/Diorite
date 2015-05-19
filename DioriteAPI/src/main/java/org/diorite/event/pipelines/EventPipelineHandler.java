package org.diorite.event.pipelines;

import org.diorite.event.Event;

/**
 * Used by {@link org.diorite.event.pipelines.EventPipeline} as type of {@link org.diorite.utils.pipeline.Pipeline}
 *
 * @param <T> type of event data to process.
 */
@FunctionalInterface
public interface EventPipelineHandler<T extends Event>
{
    /**
     * Main method, invoked for every element of pipeline.
     *
     * @param evt      object with event data.
     * @param pipeline pipeline reference.
     */
    void handle(T evt, EventPipeline<T> pipeline);

    /**
     * If this will return true,
     * then event pipeline will not invoke {@link #handle(Event, EventPipeline)}
     * method if event is cancelled.
     *
     * @return if handler should ignore cancelled events.
     */
    default boolean ignoreCancelled()
    {
        return false;
    }
}
