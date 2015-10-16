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

package org.diorite.event.pipelines;

import java.util.Map;
import java.util.NoSuchElementException;

import org.diorite.Core;
import org.diorite.event.Event;
import org.diorite.event.EventPriority;
import org.diorite.utils.pipeline.BasePipeline;
import org.diorite.utils.pipeline.Pipeline;
import org.diorite.utils.timings.TimingsContainer;

/**
 * Event pipeline, it pipeline that use special {@link EventPipelineHandler} as values,
 * and contains empty elements based on {@link EventPriority} that can be used as
 * relative elements when using methods like {@link #addBefore(EventPriority, String, EventPipelineHandler)}
 * (Simple event priority system)
 *
 * @param <T> type of event.
 */
public interface EventPipeline<T extends Event> extends Pipeline<EventPipelineHandler<T>>
{
    /**
     * @return server instance.
     */
    Core getCore();

    /**
     * Run event pipeline on given data.
     *
     * @param evt data to process.
     */
    void run(T evt);

    /**
     * Exception pipeline is used to handle exceptions thrown by event handlers.
     *
     * @return exception pipeline.
     */
    ExceptionPipeline getExceptionPipeline();

    /**
     * Reset pipeline, clear all elements and re-add default diorite handlers.
     */
    void reset();

    /**
     * @return stored timings data
     */
    Map<EventPipelineHandler<T>, TimingsContainer> getTimings();

    /**
     * Method will look for event priority pipeline element from {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <p>
     * Method is iterating from head to tail of pipeline.
     * <p>
     * If there is no element with given name, method will throw error.
     * {@link #addBeforeIfContains(EventPriority, String, EventPipelineHandler)}
     *
     * @param before priority of event.
     * @param name   name of new element.
     * @param value  event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addBeforeFromHead(EventPriority, String, EventPipelineHandler)
     * @see #addBeforeIfContains(EventPriority, String, EventPipelineHandler)
     * @see #addBeforeIfContainsFromHead(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addBefore(final EventPriority before, final String name, final EventPipelineHandler<T> value) throws NoSuchElementException
    {
        return this.addBefore(before.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <p>
     * Method is iterating from head to tail of pipeline.
     * <p>
     * If there is no element with given name, method will throw error.
     * {@link #addBeforeIfContains(EventPriority, String, EventPipelineHandler)}
     *
     * @param before priority of event.
     * @param name   name of new element.
     * @param value  event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addBefore(EventPriority, String, EventPipelineHandler)
     * @see #addBeforeIfContains(EventPriority, String, EventPipelineHandler)
     * @see #addBeforeIfContainsFromHead(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addBeforeFromHead(final EventPriority before, final String name, final EventPipelineHandler<T> value) throws NoSuchElementException
    {
        return this.addBeforeFromHead(before.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <p>
     * Method is iterating from tail to head of pipeline.
     * <p>
     * If there is no element with given name, method will throw error.
     * {@link #addBeforeIfContainsFromTail(EventPriority, String, EventPipelineHandler)}
     *
     * @param before priority of event.
     * @param name   name of new element.
     * @param value  event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addBeforeIfContainsFromTail(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addBeforeFromTail(final EventPriority before, final String name, final EventPipelineHandler<T> value) throws NoSuchElementException
    {
        return this.addBeforeFromTail(before.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code after} param,
     * and add new element with given name and value AFTER it.
     * <p>
     * Method is iterating from head to tail of pipeline.
     * <p>
     * If there is no element with given name, method will throw error.
     * {@link #addAfterIfContains(EventPriority, String, EventPipelineHandler)}
     *
     * @param after priority of event.
     * @param name  name of new element.
     * @param value event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addAfterFromHead(EventPriority, String, EventPipelineHandler)
     * @see #addAfterIfContains(EventPriority, String, EventPipelineHandler)
     * @see #addAfterIfContainsFromHead(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addAfter(final EventPriority after, final String name, final EventPipelineHandler<T> value) throws NoSuchElementException
    {
        return this.addAfter(after.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code after} param,
     * and add new element with given name and value AFTER it.
     * <p>
     * Method is iterating from head to tail of pipeline.
     * <p>
     * If there is no element with given name, method will throw error.
     * {@link #addAfterIfContains(EventPriority, String, EventPipelineHandler)}
     *
     * @param after priority of event.
     * @param name  name of new element.
     * @param value event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addAfter(EventPriority, String, EventPipelineHandler)
     * @see #addAfterIfContains(EventPriority, String, EventPipelineHandler)
     * @see #addAfterIfContainsFromHead(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addAfterFromHead(final EventPriority after, final String name, final EventPipelineHandler<T> value) throws NoSuchElementException
    {
        return this.addAfterFromHead(after.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code after} param,
     * and add new element with given name and value AFTER it.
     * <p>
     * Method is iterating from tail to head of pipeline.
     * <p>
     * If there is no element with given name, method will throw error.
     * {@link #addAfterIfContainsFromTail(EventPriority, String, EventPipelineHandler)}
     *
     * @param after priority of event.
     * @param name  name of new element.
     * @param value event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addAfterIfContainsFromTail(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addAfterFromTail(final EventPriority after, final String name, final EventPipelineHandler<T> value) throws NoSuchElementException
    {
        return this.addAfterFromTail(after.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <p>
     * Method is iterating from head to tail of pipeline.
     * <p>
     * If there is no element with given name, method will do nothing.
     * {@link #addBefore(EventPriority, String, EventPipelineHandler)}
     *
     * @param before priority of event.
     * @param name   name of new element.
     * @param value  event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addBefore(EventPriority, String, EventPipelineHandler)
     * @see #addBeforeFromHead(EventPriority, String, EventPipelineHandler)
     * @see #addBeforeIfContainsFromHead(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addBeforeIfContains(final EventPriority before, final String name, final EventPipelineHandler<T> value)
    {
        return this.addBeforeIfContains(before.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <p>
     * Method is iterating from head to tail of pipeline.
     * <p>
     * If there is no element with given name, method will do nothing.
     * {@link #addBefore(EventPriority, String, EventPipelineHandler)}
     *
     * @param before priority of event.
     * @param name   name of new element.
     * @param value  event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addBefore(EventPriority, String, EventPipelineHandler)
     * @see #addBeforeFromHead(EventPriority, String, EventPipelineHandler)
     * @see #addBeforeIfContains(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addBeforeIfContainsFromHead(final EventPriority before, final String name, final EventPipelineHandler<T> value)
    {
        return this.addBeforeIfContainsFromHead(before.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <p>
     * Method is iterating from tail to head of pipeline.
     * <p>
     * If there is no element with given name, method will do nothing.
     * {@link #addBeforeFromTail(EventPriority, String, EventPipelineHandler)}
     *
     * @param before priority of event.
     * @param name   name of new element.
     * @param value  event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addBeforeIfContains(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addBeforeIfContainsFromTail(final EventPriority before, final String name, final EventPipelineHandler<T> value)
    {
        return this.addBeforeIfContainsFromTail(before.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code after} param,
     * and add new element with given name and value AFTER it.
     * <p>
     * Method is iterating from head to tail of pipeline.
     * <p>
     * If there is no element with given name, method will do nothing.
     * {@link #addAfter(EventPriority, String, EventPipelineHandler)}
     *
     * @param after priority of event.
     * @param name  name of new element.
     * @param value event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addAfter(EventPriority, String, EventPipelineHandler)
     * @see #addAfterFromHead(EventPriority, String, EventPipelineHandler)
     * @see #addAfterIfContainsFromHead(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addAfterIfContains(final EventPriority after, final String name, final EventPipelineHandler<T> value)
    {
        return this.addAfterIfContains(after.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code after} param,
     * and add new element with given name and value AFTER it.
     * <p>
     * Method is iterating from head to tail of pipeline.
     * <p>
     * If there is no element with given name, method will do nothing.
     * {@link #addAfter(EventPriority, String, EventPipelineHandler)}
     *
     * @param after priority of event.
     * @param name  name of new element.
     * @param value event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addAfter(EventPriority, String, EventPipelineHandler)
     * @see #addAfterFromHead(EventPriority, String, EventPipelineHandler)
     * @see #addAfterIfContains(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addAfterIfContainsFromHead(final EventPriority after, final String name, final EventPipelineHandler<T> value)
    {
        return this.addAfterIfContainsFromHead(after.getPipelineName(), name, value);
    }

    /**
     * Method will look for event priority pipeline element from {@code after} param,
     * and add new element with given name and value AFTER it.
     * <p>
     * Method is iterating from tail to head of pipeline.
     * <p>
     * If there is no element with given name, method will do nothing.
     * {@link #addAfterFromTail(EventPriority, String, EventPipelineHandler)}
     *
     * @param after priority of event.
     * @param name  name of new element.
     * @param value event handler.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #addAfterFromTail(EventPriority, String, EventPipelineHandler)
     */
    default BasePipeline<EventPipelineHandler<T>> addAfterIfContainsFromTail(final EventPriority after, final String name, final EventPipelineHandler<T> value)
    {
        return this.addAfterIfContainsFromTail(after.getPipelineName(), name, value);
    }
}
