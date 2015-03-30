package org.diorite.nbt;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
}