package org.diorite.utils;

public interface SimpleEnum<T extends SimpleEnum<T>>
{
    String name();

    int getId();

    T byId(int id);

    T byName(String name);
}
