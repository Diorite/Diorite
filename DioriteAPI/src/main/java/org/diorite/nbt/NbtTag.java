package org.diorite.nbt;

import java.io.IOException;
import java.nio.charset.Charset;

public interface NbtTag
{
    public static final Charset STRING_CHARSET = Charset.forName("UTF-8");

    public String getName();

    public void setName(String name);

    default public byte[] getNameBytes()
    {
        return this.getName().getBytes(STRING_CHARSET);
    }

    public NbtTagContainer getParent();

    public void setParent(NbtTagContainer parent);

    default public byte getTagID()
    {
        return this.getTagType().getTypeID();
    }

    public NbtTagType getTagType();

    public void write(NbtOutputStream outputStream, boolean anonymous) throws IOException;

    public void read(NbtInputStream inputStream, boolean anonymous) throws IOException;
}