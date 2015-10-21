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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent nbt output stream, used to save/write nbt tags.
 */
public class NbtOutputStream extends DataOutputStream
{
    /**
     * Construct new nbt output stream for given stream.
     *
     * @param out output stream to be used.
     */
    public NbtOutputStream(final OutputStream out)
    {
        super(out);
    }

    /**
     * Write given nbt tag to this stream.
     *
     * @param tag tag to write.
     *
     * @throws IOException if any write operation failed.
     */
    public void write(final NbtTag tag) throws IOException
    {
        this.writeByte(tag.getTagID());
        tag.write(this, false);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    /**
     * Create new nbt output stream for given stream, and write nbt tag to it.
     *
     * @param tag          nbt tag to write.
     * @param outputStream output stream to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream write(final NbtTag tag, final OutputStream outputStream) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(outputStream);
        out.write(tag);
        return out;
    }

    /**
     * Create new compressed nbt output stream for given stream, and write nbt tag to it.
     *
     * @param tag          nbt tag to write.
     * @param outputStream output stream to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeCompressed(final NbtTag tag, final OutputStream outputStream) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(new GZIPOutputStream(outputStream));
        out.write(tag);
        return out;
    }

    /**
     * Create new inflated nbt output stream for given stream, and write nbt tag to it.
     *
     * @param tag          nbt tag to write.
     * @param outputStream output stream to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeInflated(final NbtTag tag, final OutputStream outputStream) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(new InflaterOutputStream(outputStream));
        out.write(tag);
        return out;
    }

    /**
     * Create new deflated nbt output stream for given stream, and write nbt tag to it.
     *
     * @param tag          nbt tag to write.
     * @param outputStream output stream to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeDeflated(final NbtTag tag, final OutputStream outputStream) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(outputStream));
        out.write(tag);
        return out;
    }

    /**
     * Create new compressed nbt output stream for given stream, and write nbt tag to it.
     *
     * @param tag          nbt tag to write.
     * @param outputStream output stream to be used.
     * @param def          deflater to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeDeflated(final NbtTag tag, final OutputStream outputStream, final Deflater def) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(outputStream, def));
        out.write(tag);
        return out;
    }

    /**
     * Create new nbt output stream for given file, and write nbt tag to it.
     *
     * @param tag  nbt tag to write.
     * @param file data file to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream write(final NbtTag tag, final File file) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new FileOutputStream(file, false));
        out.write(tag);
        return out;
    }

    /**
     * Create new compressed nbt output stream for given file, and write nbt tag to it.
     *
     * @param tag  nbt tag to write.
     * @param file data file to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeCompressed(final NbtTag tag, final File file) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new GZIPOutputStream(new FileOutputStream(file, false)));
        out.write(tag);
        return out;
    }

    /**
     * Create new inflated output stream for given file, and write nbt tag to it.
     *
     * @param tag  nbt tag to write.
     * @param file data file to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeInflated(final NbtTag tag, final File file) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new InflaterOutputStream(new FileOutputStream(file, false)));
        out.write(tag);
        return out;
    }

    /**
     * Create new deflated nbt output stream for given file, and write nbt tag to it.
     *
     * @param tag  nbt tag to write.
     * @param file data file to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeDeflated(final NbtTag tag, final File file) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, false)));
        out.write(tag);
        return out;
    }

    /**
     * Create new nbt output stream for given file, and write nbt tag to it.
     *
     * @param tag  nbt tag to write.
     * @param file data file to be used.
     * @param def  deflater to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeDeflated(final NbtTag tag, final File file, final Deflater def) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, false), def));
        out.write(tag);
        return out;
    }

    /**
     * Create new nbt output stream for given file, and write nbt tag to it.
     *
     * @param tag    nbt tag to write.
     * @param file   data file to be used.
     * @param append if new data should be appended to existing one.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream write(final NbtTag tag, final File file, final boolean append) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new FileOutputStream(file, append));
        out.write(tag);
        return out;
    }

    /**
     * Create new compressed nbt output stream for given file, and write nbt tag to it.
     *
     * @param tag    nbt tag to write.
     * @param file   data file to be used.
     * @param append if new data should be appended to existing one.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeCompressed(final NbtTag tag, final File file, final boolean append) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new GZIPOutputStream(new FileOutputStream(file, append)));
        out.write(tag);
        return out;
    }

    /**
     * Create new inflated output stream for given file, and write nbt tag to it.
     *
     * @param tag    nbt tag to write.
     * @param file   data file to be used.
     * @param append if new data should be appended to existing one.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeInflated(final NbtTag tag, final File file, final boolean append) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new InflaterOutputStream(new FileOutputStream(file, append)));
        out.write(tag);
        return out;
    }

    /**
     * Create new deflated nbt output stream for given file, and write nbt tag to it.
     *
     * @param tag    nbt tag to write.
     * @param file   data file to be used.
     * @param append if new data should be appended to existing one.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeDeflated(final NbtTag tag, final File file, final boolean append) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, append)));
        out.write(tag);
        return out;
    }

    /**
     * Create new nbt output stream for given file, and write nbt tag to it.
     *
     * @param tag    nbt tag to write.
     * @param file   data file to be used.
     * @param append if new data should be appended to existing one.
     * @param def    deflater to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream writeDeflated(final NbtTag tag, final File file, final boolean append, final Deflater def) throws IOException
    {
        createFile(file);
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, append), def));
        out.write(tag);
        return out;
    }

    /**
     * Create new nbt output stream for given stream.
     *
     * @param outputStream stream to be used.
     *
     * @return created NbtOutputStream.
     */
    public static NbtOutputStream get(final OutputStream outputStream)
    {
        return new NbtOutputStream(outputStream);
    }

    /**
     * Create new compressed nbt output stream for given stream.
     *
     * @param outputStream stream to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any write operation failed.
     */
    public static NbtOutputStream getCompressed(final OutputStream outputStream) throws IOException
    {
        return new NbtOutputStream(new GZIPOutputStream(outputStream));
    }

    /**
     * Create new inflated nbt output stream for given stream.
     *
     * @param outputStream stream to be used.
     *
     * @return created NbtOutputStream.
     */
    public static NbtOutputStream getInflated(final OutputStream outputStream)
    {
        return new NbtOutputStream(new InflaterOutputStream(outputStream));
    }

    /**
     * Create new deflated nbt output stream for given stream.
     *
     * @param outputStream stream to be used.
     *
     * @return created NbtOutputStream.
     */
    public static NbtOutputStream getDeflated(final OutputStream outputStream)
    {
        return new NbtOutputStream(new DeflaterOutputStream(outputStream));
    }

    /**
     * Create new deflated nbt output stream for given stream.
     *
     * @param outputStream stream to be used.
     * @param def          deflated to be used.
     *
     * @return created NbtOutputStream.
     */
    public static NbtOutputStream getDeflated(final OutputStream outputStream, final Deflater def)
    {
        return new NbtOutputStream(new DeflaterOutputStream(outputStream, def));
    }

    /**
     * Create new nbt output stream for given file.
     *
     * @param file file to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream get(final File file) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new FileOutputStream(file, false));
    }

    /**
     * Create new compressed nbt output stream for given file.
     *
     * @param file file to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream getCompressed(final File file) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new GZIPOutputStream(new FileOutputStream(file, false)));
    }

    /**
     * Create new inflated nbt output stream for given file.
     *
     * @param file file to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream getInflated(final File file) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new InflaterOutputStream(new FileOutputStream(file, false)));
    }

    /**
     * Create new deflated nbt output stream for given file.
     *
     * @param file file to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream getDeflated(final File file) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, false)));
    }

    /**
     * Create new deflated nbt output stream for given file.
     *
     * @param file file to be used.
     * @param def  deflater to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream getDeflated(final File file, final Deflater def) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, false), def));
    }

    /**
     * Create new nbt output stream for given file.
     *
     * @param file   file to be used.
     * @param append if new data should be appended to existing one.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream get(final File file, final boolean append) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new FileOutputStream(file, append));
    }

    /**
     * Create new compressed nbt output stream for given file.
     *
     * @param file   file to be used.
     * @param append if new data should be appended to existing one.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream getCompressed(final File file, final boolean append) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new GZIPOutputStream(new FileOutputStream(file, append)));
    }

    /**
     * Create new inflated nbt output stream for given file.
     *
     * @param file   file to be used.
     * @param append if new data should be appended to existing one.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream getInflated(final File file, final boolean append) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new InflaterOutputStream(new FileOutputStream(file, append)));
    }

    /**
     * Create new deflated nbt output stream for given file.
     *
     * @param file   file to be used.
     * @param append if new data should be appended to existing one.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream getDeflated(final File file, final boolean append) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, append)));
    }

    /**
     * Create new deflated nbt output stream for given file.
     *
     * @param file   file to be used.
     * @param append if new data should be appended to existing one.
     * @param def    deflater to be used.
     *
     * @return created NbtOutputStream.
     *
     * @throws IOException if any file operation failed.
     */
    public static NbtOutputStream getDeflated(final File file, final boolean append, final Deflater def) throws IOException
    {
        createFile(file);
        return new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, append), def));
    }

    private static void createFile(final File file) throws IOException
    {
        if (file.exists())
        {
            return;
        }
        file.getAbsoluteFile().getParentFile().mkdir();
        file.createNewFile();
    }
}