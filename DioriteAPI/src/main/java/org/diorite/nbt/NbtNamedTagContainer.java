package org.diorite.nbt;

import java.util.Map;

public interface NbtNamedTagContainer extends NbtTagContainer
{
    public <T extends NbtTag> T getTag(String name);

    public <T extends NbtTag> T getTag(String name, Class<T> tagClass);

    @SuppressWarnings("unchecked")
    <T extends NbtTag> T getTag(String name, T def);

    @SuppressWarnings("unchecked")
    <T extends NbtTag> T getTag(String name, Class<T> tagClass, T def);

    public Map<String, NbtTag> getTags();

    public void removeTag(String tag);

    public void addTag(NbtTag tag);

    void addTag(String path, NbtTag tag);

    boolean containsTag(String name);
}