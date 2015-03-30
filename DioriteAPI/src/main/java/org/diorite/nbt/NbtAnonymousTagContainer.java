package org.diorite.nbt;

import java.util.List;

public interface NbtAnonymousTagContainer extends NbtTagContainer
{
    public void addTag(NbtTag tag);

    public List<NbtTag> getTags();

    public <T extends NbtTag> List<T> getTags(Class<T> tagClass);

    public void setTag(int i, NbtTag tag);
}