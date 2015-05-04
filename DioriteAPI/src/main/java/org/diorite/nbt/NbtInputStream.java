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

public class NbtInputStream extends DataInputStream
{
    public NbtInputStream(final InputStream in)
    {
        super(in);
    }

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

    public static NbtInputStream from(final InputStream in, final NbtLimiter limiter)
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new NbtInputLimitedStream(in, limiter))));
    }

    public static NbtInputStream fromCompressed(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new GZIPInputStream(new NbtInputLimitedStream(in, limiter)))));
    }

    public static NbtInputStream fromInflated(final InputStream in, final NbtLimiter limiter)
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new InflaterInputStream(new NbtInputLimitedStream(in, limiter)))));
    }

    public static NbtInputStream fromDeflater(final InputStream in, final NbtLimiter limiter)
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new DeflaterInputStream(new NbtInputLimitedStream(in, limiter)))));
    }

    public static NbtTag readTag(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        try (NbtInputStream nbtIS = new NbtInputStream(new NbtInputLimitedStream(in, limiter)))
        {
            return nbtIS.readTag(limiter);
        }
    }

    public static NbtTag readTagCompressed(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        try (NbtInputStream nbtIS = fromCompressed(in, limiter))
        {
            return nbtIS.readTag(limiter);
        }
    }

    public static NbtTag readTagInflated(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        try (NbtInputStream nbtIS = fromInflated(in, limiter))
        {
            return nbtIS.readTag(limiter);
        }
    }

    public static NbtTag readTagDeflater(final InputStream in, final NbtLimiter limiter) throws IOException
    {
        try (NbtInputStream nbtIS = fromDeflater(in, limiter))
        {
            return nbtIS.readTag(limiter);
        }
    }

    public static NbtInputStream from(final File in, final NbtLimiter limiter) throws IOException
    {
        return from(new FileInputStream(in), limiter);
    }

    public static NbtInputStream fromCompressed(final File in, final NbtLimiter limiter) throws IOException
    {
        return fromCompressed(new FileInputStream(in), limiter);
    }

    public static NbtInputStream fromInflated(final File in, final NbtLimiter limiter) throws IOException
    {
        return fromInflated(new FileInputStream(in), limiter);
    }

    public static NbtInputStream fromDeflater(final File in, final NbtLimiter limiter) throws IOException
    {
        return fromDeflater(new FileInputStream(in), limiter);
    }

    public static NbtTag readTag(final File in, final NbtLimiter limiter) throws IOException
    {
        return readTag(new FileInputStream(in), limiter);
    }

    public static NbtTag readTagCompressed(final File in, final NbtLimiter limiter) throws IOException
    {
        return readTagCompressed(new FileInputStream(in), limiter);
    }

    public static NbtTag readTagInflated(final File in, final NbtLimiter limiter) throws IOException
    {
        return readTagInflated(new FileInputStream(in), limiter);
    }

    public static NbtTag readTagDeflater(final File in, final NbtLimiter limiter) throws IOException
    {
        return readTagDeflater(new FileInputStream(in), limiter);
    }
}