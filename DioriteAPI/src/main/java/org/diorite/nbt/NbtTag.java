package org.diorite.nbt;

import java.io.IOException;
import java.nio.charset.Charset;

public interface NbtTag
{
    Charset STRING_CHARSET = Charset.forName("UTF-8");

    String getName();

    void setName(String name);

    default byte[] getNameBytes()
    {
        return this.getName().getBytes(STRING_CHARSET);
    }

    NbtTagContainer getParent();

    void setParent(NbtTagContainer parent);

    default byte getTagID()
    {
        return this.getTagType().getTypeID();
    }

    NbtTagType getTagType();

    void write(NbtOutputStream outputStream, boolean anonymous) throws IOException;

    void read(NbtInputStream inputStream, boolean anonymous) throws IOException;
}