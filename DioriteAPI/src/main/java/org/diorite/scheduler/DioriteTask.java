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

package org.diorite.scheduler;

import org.diorite.Core;
import org.diorite.Diorite;
import org.diorite.plugin.DioritePlugin;

/**
 * Represents a task being executed by the scheduler.
 */
public interface DioriteTask
{
    /**
     * @return Unique Task id number.
     */
    int getTaskId();

    /**
     * @return The Plugin that owns the task.
     */
    DioritePlugin getOwner();

    /**
     * @return true if the task isn't run by any of main threads.
     */
    boolean isAsync();

    /**
     * Check if this task is synchronized with given object. <br>
     * Task is synchronized with given object if it is running in this same thread as object is ticking.
     *
     * @param obj object to check.
     *
     * @return true if task is synchronized with given object.
     */
    boolean isSynchronizedTo(Synchronizable obj);

    /**
     * Check if this task is synchronized with given object type. <br>
     * Task is synchronized with given object type if it is synchronized with object of that type.
     *
     * @param obj object to check.
     *
     * @return true if task is synchronized with given object type.
     */
    boolean isSynchronizedTo(Class<? extends Synchronizable> obj);

    /**
     * Check if task is synchronized with {@link Core}.
     *
     * @return true if task is synchronized with {@link Core}.
     *
     * @see #isSynchronizedTo(Synchronizable)
     */
    default boolean isSynchronizdWithServer()
    {
        return this.isSynchronizedTo(Diorite.getCore());
    }

    Synchronizable getSynchronizable();

    String getTaskName();

    /**
     * Will attempt to cancel this task.
     */
    void cancel();
}
