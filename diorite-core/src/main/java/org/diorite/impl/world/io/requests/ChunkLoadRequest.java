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

package org.diorite.impl.world.io.requests;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkIO;

public class ChunkLoadRequest extends Request<ChunkImpl>
{
    private final ChunkImpl unloadedChunk;
    private final int       x;
    private final int       z;

    public ChunkLoadRequest(final int priority, final ChunkImpl unloadedChunk, final int x, final int z)
    {
        super(priority);
        this.unloadedChunk = unloadedChunk;
        this.x = x;
        this.z = z;
    }

    @Override
    public void run(final ChunkIO io)
    {
        if (! this.unloadedChunk.isLoaded())
        {
            this.setResult(io.loadChunk(this.x, this.z, this.unloadedChunk));
        }
        else
        {
            this.setResult(this.unloadedChunk);
        }
    }

    public ChunkImpl getChunk()
    {
        return this.unloadedChunk;
    }

    @Override
    public int getX()
    {
        return this.x;
    }

    @Override
    public int getZ()
    {
        return this.z;
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkLoadRequest))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final ChunkLoadRequest that = (ChunkLoadRequest) o;

        return (this.x == that.x) && (this.z == that.z);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.x;
        result = (31 * result) + this.z;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).toString();
    }
}
