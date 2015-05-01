package org.diorite.nbt;

import java.util.Map;

public interface NbtNamedTagContainer extends NbtTagContainer
{
    <T extends NbtTag> T getTag(String name);

    <T extends NbtTag> T getTag(String name, Class<T> tagClass);

    <T extends NbtTag> T getTag(String name, T def);

    <T extends NbtTag> T getTag(String name, Class<T> tagClass, T def);

    Map<String, NbtTag> getTags();

    void removeTag(String tag);

    void addTag(NbtTag tag);

    void addTag(String path, NbtTag tag);

    boolean containsTag(String name);
}