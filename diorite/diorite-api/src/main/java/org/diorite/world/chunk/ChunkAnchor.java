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

import org.diorite.plugin.Plugin;

/**
 * Special keys used by chunks to keep them loaded as long as anchor is present and active.
 */
public interface ChunkAnchor
{
    /**
     * Returns plugin that owns this anchor.
     *
     * @return plugin that owns this anchor.
     */
    Plugin getPlugin();

    /**
     * Returns if this anchor is active.
     *
     * @return if this anchor is active.
     */
    boolean isActive();

    /**
     * Change active state of this anchor.
     *
     * @param active
     *         if anchor should be active.
     */
    void setActive(boolean active);

    /**
     * Clears list of affected chunks.
     */
    void clearChunks();

    /**
     * Returns true if given chunk is locked by this anchor.
     *
     * @param chunk
     *         chunk to check.
     *
     * @return true if given chunk is locked by this anchor.
     */
    default boolean containsChunk(Chunk chunk)
    {
        return this.containsChunk(chunk.getPosition());
    }

    /**
     * Returns true if given chunk is locked by this anchor.
     *
     * @param chunk
     *         chunk position to check.
     *
     * @return true if given chunk is locked by this anchor.
     */
    boolean containsChunk(ChunkPosition chunk);

    /**
     * Returns (read-only) collection of chunk positions affected by this anchor.
     *
     * @return (read-only) collection of chunk positions affected by this anchor.
     */
    Collection<ChunkPosition> getAffectedChunks();
}
