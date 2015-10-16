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

package org.diorite.event;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.event.pipelines.EventPipeline;

/**
 * Class for all events, all events needs to be registered here. <br>
 * It don't use {@link org.diorite.utils.SimpleEnum} because it use generic types.
 *
 * @param <T> type of event.
 * @param <E> type of pipeline for this event.
 */
public class EventType<T extends Event, E extends EventPipeline<T>>
{
    private static final Map<Class<? extends Event>, EventType<?, ?>> byEventClass = new HashMap<>(3);

    private final Class<T> eventClass;
    private final Class<E> pipelineClass;
    private       E        pipeline;

    /**
     * Construct new event type, not registred event is usless.
     *
     * @param eventClass    class of used event.
     * @param pipelineClass class of used pipeline.
     * @param pipeline      event pipeline.
     *
     * @see #register(EventType)
     */
    public EventType(final Class<T> eventClass, final Class<E> pipelineClass, final E pipeline)
    {
        this.eventClass = eventClass;
        this.pipelineClass = pipelineClass;
        this.pipeline = pipeline;
    }

    /**
     * @return event class used by this event.
     */
    public Class<T> getEventClass()
    {
        return this.eventClass;
    }

    /**
     * @return expected pipeline class.
     */
    public Class<E> getPipelineClass()
    {
        return this.pipelineClass;
    }

    /**
     * Every even have own pipeline, may return null if used before event init.
     *
     * @return used pipeline.
     */
    public E getPipeline()
    {
        return this.pipeline;
    }

    /**
     * Allow to change event pipeline used be event.
     *
     * @param pipeline new pipeline for event.
     */
    public void setPipeline(final E pipeline)
    {
        if ((pipeline != null) && ! this.pipelineClass.isAssignableFrom(pipeline.getClass()))
        {
            throw new RuntimeException("This event need pipeline of " + this.pipelineClass.getName() + " type, not: " + pipeline.getClass());
        }
        this.pipeline = pipeline;
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
    public static <T extends Event, E extends EventPipeline<T>> EventType<T, E> register(final Class<T> eventClass, final Class<E> pipelineClass, final E pipeline) throws IllegalArgumentException
    {
        return register(new EventType<>(eventClass, pipelineClass, pipeline));
    }

    /**
     * Register new event type.
     *
     * @param type event type to register.
     * @param <T>  type of event.
     * @param <E>  type of pipeline for this event.
     *
     * @return given event type.
     *
     * @throws IllegalArgumentException if event class is already registered.
     */
    public static <T extends Event, E extends EventPipeline<T>> EventType<T, E> register(final EventType<T, E> type) throws IllegalArgumentException
    {
        if (byEventClass.containsKey(type.getEventClass()))
        {
            throw new IllegalArgumentException("Event already registered: " + type + ", existing: " + byEventClass.get(type.getEventClass()));
        }
        byEventClass.put(type.getEventClass(), type);
        return type;
    }

    /**
     * Gets event type by given event class. <br>
     *
     * @param clazz event class to find event type for it.
     * @param <T>   type of event.
     * @param <E>   type of pipeline for this event.
     *
     * @return event type for that event or null.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Event, E extends EventPipeline<T>> EventType<T, E> getByEvent(final Class<T> clazz)
    {
        return (EventType<T, E>) byEventClass.get(clazz);
    }

    /**
     * Gets event type by given event instance. <br>
     * It just pass class of given object to {@link #getByEvent(Class)}
     * and return this same value.
     *
     * @param evt event instance to find event type for it.
     * @param <T> type of event.
     * @param <E> type of pipeline for this event.
     *
     * @return event type for that event or null.
     *
     * @see #getByEvent(Class)
     */
    @SuppressWarnings("unchecked")
    public static <T extends Event, E extends EventPipeline<T>> EventType<T, E> getByEvent(final T evt)
    {
        return (EventType<T, E>) byEventClass.get(evt.getClass());
    }

    /**
     * Method will find event type of this event and pass given event to its pipeline. <br>
     * If it can't find event type or pipeline is null, method will return false.
     *
     * @param event event to call.
     *
     * @return true if pipeline was found and event was passed to it.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static boolean callEvent(final Event event)
    {
        final EventType pipe = getByEvent(event);
        if ((pipe == null) || (pipe.pipeline == null))
        {
            return false;
        }
        pipe.pipeline.run(event);
        return true;
    }

    /**
     * @return all event types in array.
     */
    public static EventType<?, ?>[] values()
    {
        return byEventClass.values().toArray(new EventType<?, ?>[byEventClass.size()]);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("eventClass", this.eventClass).append("pipelineClass", this.pipelineClass).append("pipeline", (this.pipeline == null) ? null : this.pipeline.toNamesCollection()).toString();
    }
}
