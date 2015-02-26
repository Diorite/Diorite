package diorite.nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

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
}