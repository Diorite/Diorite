package org.diorite;

import org.diorite.event.Event;
import org.diorite.event.EventType;
import org.diorite.event.pipelines.EventPipeline;

/**
 * Interface used to manage all event pipelines, other pipelines, plugins and more.
 */
public interface ServerManager
{
    /**
     * Method will find event type of this event and pass given event to its pipeline. <br>
     * If it can't find event type or pipeline is null, method will return false.
     *
     * @param event event to call.
     *
     * @return true if pipeline was found and event was passed to it.
     */
    default boolean callEvent(final Event event)
    {
        return EventType.callEvent(event);
    }

    /**
     * Construct new event type and register it.
     *
     * @param eventClass    class of used event.
     * @param pipelineClass class of used pipeline.
     * @param pipeline      event pipeline.
     * @param <T>           type of event.
     * @param <E>           type of pipeline for this event.
     *
     * @return created and registered event type.
     *
     * @throws IllegalArgumentException if event class is already registered.
     */
    default <T extends Event, E extends EventPipeline<T>> EventType<T, E> register(Class<T> eventClass, Class<E> pipelineClass, E pipeline) throws IllegalArgumentException
    {
        return EventType.register(eventClass, pipelineClass, pipeline);
    }
}
