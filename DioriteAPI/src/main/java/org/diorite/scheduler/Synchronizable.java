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
import org.diorite.world.Block;

/**
 * Objects implementing this interface can be used when creating task, to be always synchronized with this object. <br>
 * So if entity teleport to other world, handled by other thread, then all task synchronized to this entity will also move to other thread. <br>
 * <p>
 * Implementing this interface will not make object useful for scheduler, special handler must be created on core side that will handle all opbject of this type. <br>
 * Object supported by default: <br>
 * - {@link org.diorite.entity.Entity} <br>
 * - {@link org.diorite.world.chunk.Chunk} <br>
 * - {@link Core} (default value, task will be executed before world ticking) <br>
 * Also {@link org.diorite.world.Block} is partially supproted by scheduler, but {@link Block#getChunk()} is used.
 */
public interface Synchronizable
{
    /**
     * Method need return last used tick thrad.
     *
     * @return last used tick thread.
     */
    Thread getLastTickThread();

    /**
     * Method should check if task sync to this object should
     * be unregistred, like after player exit.
     *
     * @return true if task don't need to be unregistred.
     */
    boolean isValidSynchronizable();
}
