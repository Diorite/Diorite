/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import javax.annotation.Nullable;

/**
 * Objects implementing this interface can be used when creating task, to be always synchronized with this object. <br>
 * So if entity teleport to other world, handled by other thread, then all task synchronized to this entity will also move to other thread. <br>
 * <br>
 * Implementing this interface will not make object useful for scheduler, special handler must be created on core side that will handle all objects of this
 * type.
 */
public interface Synchronizable
{
    /**
     * Returns last used tick thread.
     *
     * @return last used tick thread.
     */
    @Nullable
    Thread getLastTickThread();

    /**
     * Returns if task synced to this object should be unregistered, like after player exit.
     *
     * @return true if task don't need to be unregistered.
     */
    boolean isValidSynchronizable();
}
