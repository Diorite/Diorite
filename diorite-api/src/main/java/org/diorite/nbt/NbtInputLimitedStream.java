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

package org.diorite.nbt;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents nbt input stream that use {@link NbtLimiter} to limit readed data to prevent exploits.
 */
public class NbtInputLimitedStream extends FilterInputStream
{
    private final NbtLimiter limiter;

    /**
     * Construct new limited stream using given input stream and limiter.
     *
     * @param in      input stream to be useed.
     * @param limiter limiter of this input stream.
     */
    public NbtInputLimitedStream(final InputStream in, final NbtLimiter limiter)
    {
        super(in);
        this.limiter = limiter;
    }

    @Override
    public int read() throws IOException
    {
        this.limiter.countBytes(1);
        return super.read();
    }

    @Override
    public int read(final byte[] b) throws IOException
    {
        this.limiter.countBytes(b.length);
        return super.read(b);
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException
    {
        this.limiter.countBytes(len);
        return super.read(b, off, len);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("limiter", this.limiter).toString();
    }
}
