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

public class NbtOutputStream extends DataOutputStream
{
    public NbtOutputStream(final OutputStream out)
    {
        super(out);
    }

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

    public static NbtOutputStream write(final NbtTag tag, final OutputStream outputStream) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(outputStream);
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeCompressed(final NbtTag tag, final OutputStream outputStream) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(new GZIPOutputStream(outputStream));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeInflated(final NbtTag tag, final OutputStream outputStream) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(new InflaterOutputStream(outputStream));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeDeflated(final NbtTag tag, final OutputStream outputStream) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(outputStream));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeDeflated(final NbtTag tag, final OutputStream outputStream, final Deflater def) throws IOException
    {
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(outputStream, def));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream write(final NbtTag tag, final File file) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new FileOutputStream(file, false));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeCompressed(final NbtTag tag, final File file) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new GZIPOutputStream(new FileOutputStream(file, false)));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeInflated(final NbtTag tag, final File file) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new InflaterOutputStream(new FileOutputStream(file, false)));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeDeflated(final NbtTag tag, final File file) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, false)));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeDeflated(final NbtTag tag, final File file, final Deflater def) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, false), def));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream write(final NbtTag tag, final File file, final boolean append) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new FileOutputStream(file, append));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeCompressed(final NbtTag tag, final File file, final boolean append) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new GZIPOutputStream(new FileOutputStream(file, append)));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeInflated(final NbtTag tag, final File file, final boolean append) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new InflaterOutputStream(new FileOutputStream(file, append)));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeDeflated(final NbtTag tag, final File file, final boolean append) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, append)));
        out.write(tag);
        return out;
    }

    public static NbtOutputStream writeDeflated(final NbtTag tag, final File file, final boolean append, final Deflater def) throws IOException
    {
        file.createNewFile();
        final NbtOutputStream out = new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, append), def));
        out.write(tag);
        return out;
    }


    public static NbtOutputStream get(final OutputStream outputStream)
    {
        return new NbtOutputStream(outputStream);
    }

    public static NbtOutputStream getCompressed(final OutputStream outputStream) throws IOException
    {
        return new NbtOutputStream(new GZIPOutputStream(outputStream));
    }

    public static NbtOutputStream getInflated(final OutputStream outputStream)
    {
        return new NbtOutputStream(new InflaterOutputStream(outputStream));
    }

    public static NbtOutputStream getDeflated(final OutputStream outputStream)
    {
        return new NbtOutputStream(new DeflaterOutputStream(outputStream));
    }

    public static NbtOutputStream getDeflated(final OutputStream outputStream, final Deflater def)
    {
        return new NbtOutputStream(new DeflaterOutputStream(outputStream, def));
    }

    public static NbtOutputStream get(final File file) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new FileOutputStream(file, false));
    }

    public static NbtOutputStream getCompressed(final File file) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new GZIPOutputStream(new FileOutputStream(file, false)));
    }

    public static NbtOutputStream getInflated(final File file) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new InflaterOutputStream(new FileOutputStream(file, false)));
    }

    public static NbtOutputStream getDeflated(final File file) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, false)));
    }

    public static NbtOutputStream getDeflated(final File file, final Deflater def) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, false), def));
    }

    public static NbtOutputStream get(final File file, final boolean append) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new FileOutputStream(file, append));
    }

    public static NbtOutputStream getCompressed(final File file, final boolean append) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new GZIPOutputStream(new FileOutputStream(file, append)));
    }

    public static NbtOutputStream getInflated(final File file, final boolean append) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new InflaterOutputStream(new FileOutputStream(file, append)));
    }

    public static NbtOutputStream getDeflated(final File file, final boolean append) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, append)));
    }

    public static NbtOutputStream getDeflated(final File file, final boolean append, final Deflater def) throws IOException
    {
        file.createNewFile();
        return new NbtOutputStream(new DeflaterOutputStream(new FileOutputStream(file, append), def));
    }
}