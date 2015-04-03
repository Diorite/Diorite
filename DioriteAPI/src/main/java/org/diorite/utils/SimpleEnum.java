package org.diorite.utils;

public interface SimpleEnum<T extends SimpleEnum<T>>
{
    float SMALL_LOAD_FACTOR = .1f;

    String name();

    int getId();

    T byId(int id);

    T byName(String name);
}
