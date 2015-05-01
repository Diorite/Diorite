package org.diorite.nbt;

import java.util.List;

public interface NbtAnonymousTagContainer extends NbtTagContainer
{
    void addTag(NbtTag tag);

    List<NbtTag> getTags();

    <T extends NbtTag> List<T> getTags(Class<T> tagClass);

    void setTag(int i, NbtTag tag);
}