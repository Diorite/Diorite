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

package org.diorite.world;

import javax.annotation.Nullable;

import org.diorite.BlockLocation;
import org.diorite.block.BlockContainer;
import org.diorite.commons.objects.Nameable;
import org.diorite.world.chunk.ChunkContainer;

/**
 * Represents minecraft world, if world isn't loaded then all methods that need to fetch data from it will result in exception being thrown.
 */
public interface World extends BlockContainer, ChunkContainer, Nameable
{
    @Nullable
    @Override
    default BlockContainer getParent()
    {
        return null;
    }

    @Override
    default BlockLocation getRelativeOrigin() throws IllegalStateException
    {
        throw new IllegalStateException("World is root block container!");
    }

    /**
     * Returns name of world. Name of world is unique, but different worlds may use this same name over time (after removing it and regenerating), if you need
     * something more unique, use UUID of world.
     *
     * @return name of world.
     */
    @Override
    String getName();

    /**
     * Returns true if world is loaded.
     *
     * @return true if world is loaded.
     */
    boolean isLoaded();

    /**
     * Returns true if this is valid world instance. <br>
     * This may return false if world is unloaded and not tracked by server anymore, so after loading world again this instance will be still empty.
     *
     * @return true if this is valid world instance.
     */
    boolean isValid();
}
