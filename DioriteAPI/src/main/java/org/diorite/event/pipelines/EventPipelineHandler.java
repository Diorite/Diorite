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

import org.diorite.event.Event;

/**
 * Used by {@link EventPipeline} as type of {@link org.diorite.utils.pipeline.Pipeline}
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
