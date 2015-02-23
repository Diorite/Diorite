package com.mojang.authlib;

import java.util.HashMap;
import java.util.Map;

public enum UserType
{
    LEGACY("legacy"),
    MOJANG("mojang");

    private static final Map<String, UserType> BY_NAME;
    private final        String                name;

    private UserType(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
    static
    {
        BY_NAME = new HashMap<>(UserType.values().length);
        for (final UserType type : values())
        {
            BY_NAME.put(type.name, type);
        }
    }

    public static UserType byName(final String name)
    {
        return BY_NAME.get(name.toLowerCase());
    }
}
