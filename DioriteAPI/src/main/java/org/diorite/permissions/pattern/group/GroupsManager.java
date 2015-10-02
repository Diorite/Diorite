package org.diorite.permissions.pattern.group;

import java.util.HashMap;
import java.util.Map;

public class GroupsManager
{
    private static Map<String, SpecialGroup<?>> groups = new HashMap<>(5, .25F);

    static
    {
        register(new LevelGroup(true), "{$++}");
        register(new LevelGroup(false), "{$--}");
        register(new RangeGroup(), "{$-$}");
    }

    public static void register(final SpecialGroup<?> group, final String... patterns)
    {
        for (final String pattern : patterns)
        {
            groups.put(pattern, group);
        }
    }

    public SpecialGroup<?> get(final String pat)
    {
        return groups.get(pat);
    }
}
