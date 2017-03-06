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

package org.diorite.world.chunk;

import org.diorite.world.World;

/**
 * Represents chunk, chunk is some part of world that can be loaded/saved/unloaded/generated. <br>
 * Chunks are always square but their size may be different depending on implementation.
 */
public interface Chunk
{
    /**
     * Returns world where this chunk is stored.
     *
     * @return world where this chunk is stored.
     */
    World getWorld();

    /**
     * Returns true if this is valid chunk instance. <br>
     * This may return false if chunk is unloaded and not tracked by server anymore, so after loading chunk again this instance will be still empty.
     *
     * @return true if this is valid chunk instance.
     */
    boolean isValid();

    /**
     * Returns true if this chunk is empty, and there is nothing in it.
     *
     * @return true if this chunk is empty, and there is nothing in it.
     */
    boolean isEmpty();

    /**
     * Number of blocks in X axis.
     *
     * @return size in X axis.
     */
    int getSizeX();

    /**
     * Number of blocks in Z axis.
     *
     * @return size in Z axis.
     */
    int getSizeZ();

}
