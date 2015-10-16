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

package org.diorite.event.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.chunk.Chunk;

public class ChunkUnloadEvent extends ChunkEvent
{
    protected final Chunk   chunk;
    protected       boolean safe;

    /**
     * Construct new chunk pre load event.
     *
     * @param chunk chunk to unload.
     * @param safe  if it should use safe save. (check for uses of chunk etc...)
     */
    public ChunkUnloadEvent(final Chunk chunk, final boolean safe)
    {
        super(chunk.getPos());
        this.chunk = chunk;
        this.safe = safe;
    }

    /**
     * Returns chunk that is unloading in this event.
     *
     * @return chunk to unload.
     */
    public Chunk getChunk()
    {
        return this.chunk;
    }

    /**
     * @return if it should use safe save. (check for uses of chunk etc...)
     */
    public boolean isSafe()
    {
        return this.safe;
    }

    /**
     * Change safe mode.
     *
     * @param safe if it should use safe save. (check for uses of chunk etc...)
     */
    public void setSafe(final boolean safe)
    {
        this.safe = safe;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunk", this.chunk).toString();
    }
}
