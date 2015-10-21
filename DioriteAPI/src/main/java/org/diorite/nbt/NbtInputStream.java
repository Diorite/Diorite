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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent nbt input stream, used to read nbt tags.
 */
public class NbtInputStream extends DataInputStream
{
    /**
     * Construct new nbt stream for given input stream.
     *
     * @param in input stream to be used.
     */
    public NbtInputStream(final InputStream in)
    {
        super(in);
    }

    /**
     * Read nbt tag from this stream using given limiter.
     *
     * @param limiter limiter to be used.
     *
     * @return readed tag from stream.
     *
     * @throws IOException if any read operation failed.
     */
    public NbtTag readTag(final NbtLimiter limiter) throws IOException
    {
        final byte type = this.readByte();
        final NbtTagType tagType = NbtTagType.valueOf(type);
        if (tagType == null)
        {
            throw new IOException("Invalid NBT tag: Found unknown tag type " + type + ".");
        }
        return this.readTag(tagType, false, limiter);
    }

    /**
     * Read nbt tag from this stream using given limiter.
     *
     * @param type      type of tag to read.
     * @param anonymous if tag have name.
     * @param limiter   limiter to be used.
     *
     * @return readed tag from stream.
     *
     * @throws IOException if any read operation failed.
     */
    public NbtTag readTag(final NbtTagType type, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        final NbtTag tag = type.newInstance();
        tag.read(this, anonymous, limiter);
        return tag;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    /**
     * Construct new NbtInputStream for raw input stream and limiter.
     *
     * @param in      input stream to be used.
     * @param limiter limiter to be used.
     *
     * @return new NbtInputStream.
     */
    public static NbtInputStream from(final InputStream in, final NbtLimiter limiter)
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new NbtInputLimitedStream(in, limiter))));
    }

    /**
     * Construct new NbtInputStream for compressed input stream and limiter.
     *
     * @param in      input stream to be used.
     * @param limiter limiter to be used.
     *
     * @return new NbtInputStream.
     *
     * @throws IOException if any read operation failed.
     */
    public static NbtInputStream fromCompressed(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new GZIPInputStream(new NbtInputLimitedStream(in, limiter)))));
    }

    /**
     * Construct new NbtInputStream for inflated input stream and limiter.
     *
     * @param in      input stream to be used.
     * @param limiter limiter to be used.
     *
     * @return new NbtInputStream.
     */
    public static NbtInputStream fromInflated(final InputStream in, final NbtLimiter limiter)
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new InflaterInputStream(new NbtInputLimitedStream(in, limiter)))));
    }

    /**
     * Construct new NbtInputStream for deflated input stream and limiter.
     *
     * @param in      input stream to be used.
     * @param limiter limiter to be used.
     *
     * @return new NbtInputStream.
     */
    public static NbtInputStream fromDeflater(final InputStream in, final NbtLimiter limiter)
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new DeflaterInputStream(new NbtInputLimitedStream(in, limiter)))));
    }

    /**
     * Construct new NbtInputStream for raw input stream and limiter, and then read nbt tag from it.
     *
     * @param in      input stream to be used.
     * @param limiter limiter to be used.
     *
     * @return readed nbt tag from given stream.
     *
     * @throws IOException if any read operation failed.
     */
    public static NbtTag readTag(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        try (NbtInputStream nbtIS = new NbtInputStream(new NbtInputLimitedStream(in, limiter)))
        {
            return nbtIS.readTag(limiter);
        }
    }

    /**
     * Construct new NbtInputStream for compressed input stream and limiter, and then read nbt tag from it.
     *
     * @param in      input stream to be used.
     * @param limiter limiter to be used.
     *
     * @return readed nbt tag from given stream.
     *
     * @throws IOException if any read operation failed.
     */
    public static NbtTag readTagCompressed(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        try (NbtInputStream nbtIS = fromCompressed(in, limiter))
        {
            return nbtIS.readTag(limiter);
        }
    }

    /**
     * Construct new NbtInputStream for inflated input stream and limiter, and then read nbt tag from it.
     *
     * @param in      input stream to be used.
     * @param limiter limiter to be used.
     *
     * @return readed nbt tag from given stream.
     *
     * @throws IOException if any read operation failed.
     */
    public static NbtTag readTagInflated(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        try (NbtInputStream nbtIS = fromInflated(in, limiter))
        {
            return nbtIS.readTag(limiter);
        }
    }

    /**
     * Construct new NbtInputStream for deflated input stream and limiter, and then read nbt tag from it.
     *
     * @param in      input stream to be used.
     * @param limiter limiter to be used.
     *
     * @return readed nbt tag from given stream.
     *
     * @throws IOException if any read operation failed.
     */
    public static NbtTag readTagDeflater(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        try (NbtInputStream nbtIS = fromDeflater(in, limiter))
        {
            return nbtIS.readTag(limiter);
        }
    }

    /**
     * Construct new NbtInputStream for raw data file and limiter.
     *
     * @param in      data file to be used.
     * @param limiter limiter to be used.
     *
     * @return new NbtInputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtInputStream from(final File in, final NbtLimiter limiter) throws IOException
    {
        return from(new FileInputStream(in), limiter);
    }

    /**
     * Construct new NbtInputStream for compressed data file and limiter.
     *
     * @param in      data file to be used.
     * @param limiter limiter to be used.
     *
     * @return new NbtInputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtInputStream fromCompressed(final File in, final NbtLimiter limiter) throws IOException
    {
        return fromCompressed(new FileInputStream(in), limiter);
    }

    /**
     * Construct new NbtInputStream for inflated data file and limiter.
     *
     * @param in      data file to be used.
     * @param limiter limiter to be used.
     *
     * @return new NbtInputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtInputStream fromInflated(final File in, final NbtLimiter limiter) throws IOException
    {
        return fromInflated(new FileInputStream(in), limiter);
    }

    /**
     * Construct new NbtInputStream for deflated data file and limiter.
     *
     * @param in      data file to be used.
     * @param limiter limiter to be used.
     *
     * @return new NbtInputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtInputStream fromDeflater(final File in, final NbtLimiter limiter) throws IOException
    {
        return fromDeflater(new FileInputStream(in), limiter);
    }

    /**
     * Construct new NbtInputStream for raw data file and limiter, and then read nbt tag from it.
     *
     * @param in      data file to be used.
     * @param limiter limiter to be used.
     *
     * @return readed nbt tag from given stream.
     *
     * @throws IOException if any read operation failed.
     */
    public static NbtTag readTag(final File in, final NbtLimiter limiter) throws IOException
    {
        return readTag(new FileInputStream(in), limiter);
    }

    /**
     * Construct new NbtInputStream for compressed data file and limiter, and then read nbt tag from it.
     *
     * @param in      data file to be used.
     * @param limiter limiter to be used.
     *
     * @return readed nbt tag from given stream.
     *
     * @throws IOException if any read operation failed.
     */
    public static NbtTag readTagCompressed(final File in, final NbtLimiter limiter) throws IOException
    {
        return readTagCompressed(new FileInputStream(in), limiter);
    }

    /**
     * Construct new NbtInputStream for inflated data file and limiter, and then read nbt tag from it.
     *
     * @param in      data file to be used.
     * @param limiter limiter to be used.
     *
     * @return readed nbt tag from given stream.
     *
     * @throws IOException if any read operation failed.
     */
    public static NbtTag readTagInflated(final File in, final NbtLimiter limiter) throws IOException
    {
        return readTagInflated(new FileInputStream(in), limiter);
    }

    /**
     * Construct new NbtInputStream for deflated data file and limiter, and then read nbt tag from it.
     *
     * @param in      data file to be used.
     * @param limiter limiter to be used.
     *
     * @return readed nbt tag from given stream.
     *
     * @throws IOException if any read operation failed.
     */
    public static NbtTag readTagDeflater(final File in, final NbtLimiter limiter) throws IOException
    {
        return readTagDeflater(new FileInputStream(in), limiter);
    }
}