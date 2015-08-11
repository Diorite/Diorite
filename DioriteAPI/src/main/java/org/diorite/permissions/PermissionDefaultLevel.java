package org.diorite.permissions;

import java.util.regex.Pattern;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

public enum PermissionDefaultLevel
{
    TRUE("true")
            {
                @Override
                public boolean getValue(final boolean op)
                {
                    return true;
                }
            },
    FALSE("false")
            {
                @Override
                public boolean getValue(final boolean op)
                {
                    return false;
                }
            },
    OP("op", "isop", "operator", "isoperator", "admin", "isadmin")
            {
                @Override
                public boolean getValue(final boolean op)
                {
                    return op;
                }
            },
    NOT_OP("!op", "notop", "!operator", "notoperator", "!admin", "notadmin")
            {
                @Override
                public boolean getValue(final boolean op)
                {
                    return !op;
                }
            };

    private static final Pattern INVALID_CHARS = Pattern.compile("[^a-zA-Z!]");
    private final String[] names;
    private static final CaseInsensitiveMap<PermissionDefaultLevel> lookup = new CaseInsensitiveMap<>(15, .1f);

    PermissionDefaultLevel(final String... names)
    {
        this.names = names;
    }

    public abstract boolean getValue(boolean op);

    public static PermissionDefaultLevel getByName(final CharSequence name)
    {
        return lookup.get(INVALID_CHARS.matcher(name).replaceAll(""));
    }

    public String toString()
    {
        return this.names[0];
    }

    static
    {
        for (final PermissionDefaultLevel value : values())
        {
            for (final String name : value.names)
            {
                lookup.put(name, value);
            }
        }
    }
}
