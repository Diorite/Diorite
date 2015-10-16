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

/**
 * When map want populate some chunk.
 */
public class ChunkPopulateEvent extends ChunkEvent
{
    protected final Chunk   chunk;
    protected final boolean force;

    /**
     * Construct new chunk populate event.
     *
     * @param chunk chunk to populate, can't by null.
     * @param force if chunk should be force-populated
     */
    public ChunkPopulateEvent(final Chunk chunk, final boolean force)
    {
        super(chunk.getPos());
        this.chunk = chunk;
        this.force = force;
    }

    /**
     * @return true if chunk should be force-populated.
     */
    public boolean isForce()
    {
        return this.force;
    }

    /**
     * @return chunk to populate.
     */
    public Chunk getChunk()
    {
        return this.chunk;
    }

    /**
     * @return true if chunk is already populated.
     *
     * @see Chunk#isPopulated()
     */
    public boolean isPopulated()
    {
        return this.chunk.isPopulated();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkPopulateEvent))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final ChunkPopulateEvent that = (ChunkPopulateEvent) o;

        return this.chunk.equals(that.chunk);

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.chunk.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunk", this.chunk).toString();
    }
}
