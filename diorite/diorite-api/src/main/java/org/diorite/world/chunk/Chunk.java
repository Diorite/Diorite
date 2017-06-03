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

import java.util.Collection;

import org.diorite.BlockLocation;
import org.diorite.block.BlockContainer;
import org.diorite.event.chunk.ChunkAnchorRemovedEvent;
import org.diorite.scheduler.Synchronizable;
import org.diorite.world.World;

/**
 * Represents chunk, chunk is some part of world that can be loaded/saved/unloaded/generated. <br>
 * Chunks are always square but their size may be different depending on implementation.
 */
public interface Chunk extends BlockContainer, Synchronizable
{
    @Override
    default World getParent()
    {
        return this.getWorld();
    }

    /**
     * Returns world where this chunk is stored.
     *
     * @return world where this chunk is stored.
     */
    World getWorld();

    /**
     * Returns position of this chunk.
     *
     * @return position of this chunk.
     */
    ChunkPosition getPosition();

    /**
     * Returns position of chunk on X axis.
     *
     * @return position of chunk on X axis.
     */
    int getX();

    /**
     * Returns position of chunk on Z axis.
     *
     * @return position of chunk on Z axis.
     */
    int getZ();

    /**
     * Returns true if this is valid chunk instance. <br>
     * This may return false if chunk is unloaded and not tracked by server anymore, so after loading chunk again this instance will be still empty. <br>
     *
     * @return true if this is valid chunk instance.
     */
    boolean isValid();

    /**
     * Returns true if this chunk is loaded.
     *
     * @return true if this chunk is loaded.
     */
    boolean isLoaded();

    /**
     * Returns true if this chunk can be unloaded.
     *
     * @return true if this chunk can be unloaded.
     */
    boolean canBeUnloaded();

    /**
     * Save and unload this chunk if possible. (chunk anchors and events can be used to disable chunk unloading)
     *
     * @return if chunk was unloaded.
     */
    default boolean unload()
    {
        return this.unload(true);
    }

    /**
     * Unloads this chunk if possible. (chunk anchors and events can be used to disable chunk unloading)
     *
     * @param save
     *         if chunk should be saved.
     *
     * @return if chunk was unloaded.
     */
    boolean unload(boolean save);

    /**
     * Loads this chunk, if chunk is already loaded nothing will happen. <br>
     * If chunk isn't generated yet it will be generated.
     */
    default void load()
    {
        this.load(true);
    }

    /**
     * Loads this chunk, if chunk is already loaded nothing will happen. <br>
     *
     * @param generate
     *         if chunk should be loaded.
     */
    void load(boolean generate);

    /**
     * Adds anchor to this chunk to prevent unloading.
     *
     * @param anchor
     *         anchor to add.
     *
     * @return true if anchor was added, false if anchor was already added or can't be added to this chunk.
     */
    boolean addAnchor(ChunkAnchor anchor);

    /**
     * Removes anchor from this chunk, anchors are used to prevent chunk unloading. <br>
     * Removing anchor will call {@link ChunkAnchorRemovedEvent} that can be cancelled.
     *
     * @param anchor
     *         anchor to remove.
     *
     * @return if anchor was successful removed.
     */
    boolean removeAnchor(ChunkAnchor anchor);

    /**
     * Returns collection of all anchors added to this chunk.
     *
     * @return collection of all anchors added to this chunk.
     */
    Collection<? extends ChunkAnchor> getAnchors();

    /**
     * Returns true if this chunk is empty, and there is nothing in it.
     *
     * @return true if this chunk is empty, and there is nothing in it.
     */
    boolean isEmpty();

    /**
     * Returns block location of minimal point on chunk, so location win minimal x/y/z coordinates on this chunk.
     *
     * @return block location of minimal point on chunk.
     */
    default BlockLocation getMinimalPoint()
    {
        return this.getRelativeOrigin();
    }

    /**
     * Returns block location of maximal point on chunk, so location win maximal x/y/z coordinates on this chunk.
     *
     * @return block location of maximal point on chunk.
     */
    BlockLocation getMaximalPoint();
}
