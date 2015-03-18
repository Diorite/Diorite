package org.diorite.nbt;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class NbtInputStream extends DataInputStream
{
    public NbtInputStream(final InputStream in)
    {
        super(in);
    }

    public NbtAbstractTag<?> readTag() throws IOException
    {
        final byte type = this.readByte();
        final NbtTagType nbtTagType = NbtTagType.valueOf(type);
        if (nbtTagType == null)
        {
            throw new TagNotFoundException("Invalid NBT tag: Found unknown tag type " + type + ".");
        }
        if (nbtTagType == NbtTagType.END)
        {
            return null;
        }
        return this.readTag(nbtTagType, true);
    }

    public NbtAbstractTag<?> readTag(final NbtTagType type, final boolean hasName) throws IOException
    {
        return type.newInstance().read(this, hasName);
    }

    public static NbtInputStream fromCompressed(final InputStream in) throws IOException
    {
        return new NbtInputStream(new BufferedInputStream(new GZIPInputStream(in)));
    }

    public static NbtInputStream fromUnknown(final InputStream in) throws IOException
    {
        final GZIPInputStream zip;
        try
        {
            //noinspection resource,IOResourceOpenedButNotSafelyClosed
            zip = new GZIPInputStream(in);
        } catch (final ZipException e)
        {
            return new NbtInputStream(in);
        }
        return new NbtInputStream(new BufferedInputStream(zip));
    }

    public static NbtAbstractTag<?> readTag(final InputStream in) throws IOException
    {
        try (NbtInputStream nbtIS = new NbtInputStream(in))
        {
            return nbtIS.readTag();
        }
    }

    public static NbtAbstractTag<?> readTagFromCompressed(final InputStream in) throws IOException
    {
        try (NbtInputStream nbtIS = fromCompressed(in))
        {
            return nbtIS.readTag();
        }
    }

    public static NbtAbstractTag<?> readTagFromUnknown(final InputStream in) throws IOException
    {
        try (NbtInputStream nbtIS = fromUnknown(in))
        {
            return nbtIS.readTag();
        }
    }
}