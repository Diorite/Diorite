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

    public NbtTag readTag() throws IOException
    {
        final byte type = this.readByte();
        final NbtTagType tagType = NbtTagType.valueOf(type);
        if (tagType == null)
        {
            throw new IOException("Invalid NBT tag: Found unknown tag type " + type + ".");
        }
        return this.readTag(tagType, false);
    }

    public NbtTag readTag(final NbtTagType type, final boolean anonymous) throws IOException
    {
        final NbtTag tag = type.newInstance();
        tag.read(this, anonymous);
        return tag;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    public static NbtInputStream from(final InputStream in)
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(in)));
    }

    public static NbtInputStream fromCompressed(final InputStream in) throws IOException
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new GZIPInputStream(in))));
    }

    public static NbtInputStream fromInflated(final InputStream in)
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new InflaterInputStream(in))));
    }

    public static NbtInputStream fromDeflater(final InputStream in)
    {
        return new NbtInputStream(new DataInputStream(new BufferedInputStream(new DeflaterInputStream(in))));
    }

    public static NbtTag readTag(final InputStream in) throws IOException
    {
        try (NbtInputStream nbtIS = new NbtInputStream(in))
        {
            return nbtIS.readTag();
        }
    }

    public static NbtTag readTagCompressed(final InputStream in) throws IOException
    {
        try (NbtInputStream nbtIS = fromCompressed(in))
        {
            return nbtIS.readTag();
        }
    }

    public static NbtTag readTagInflated(final InputStream in) throws IOException
    {
        try (NbtInputStream nbtIS = fromInflated(in))
        {
            return nbtIS.readTag();
        }
    }

    public static NbtTag readTagDeflater(final InputStream in) throws IOException
    {
        try (NbtInputStream nbtIS = fromDeflater(in))
        {
            return nbtIS.readTag();
        }
    }

    public static NbtInputStream from(final File in) throws IOException
    {
        return from(new FileInputStream(in));
    }

    public static NbtInputStream fromCompressed(final File in) throws IOException
    {
        return fromCompressed(new FileInputStream(in));
    }

    public static NbtInputStream fromInflated(final File in) throws IOException
    {
        return fromInflated(new FileInputStream(in));
    }

    public static NbtInputStream fromDeflater(final File in) throws IOException
    {
        return fromDeflater(new FileInputStream(in));
    }

    public static NbtTag readTag(final File in) throws IOException
    {
        return readTag(new FileInputStream(in));
    }

    public static NbtTag readTagCompressed(final File in) throws IOException
    {
        return readTagCompressed(new FileInputStream(in));
    }

    public static NbtTag readTagInflated(final File in) throws IOException
    {
        return readTagInflated(new FileInputStream(in));
    }

    public static NbtTag readTagDeflater(final File in) throws IOException
    {
        return readTagDeflater(new FileInputStream(in));
    }
}