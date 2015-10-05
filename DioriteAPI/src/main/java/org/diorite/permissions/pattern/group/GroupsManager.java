package org.diorite.permissions.pattern.group;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for all special groups, plugins can add or edit special permission groups here.
 */
public final class GroupsManager
{
    private static final Map<String, SpecialGroup<?>> groups = new HashMap<>(5, .25F);

    private GroupsManager()
    {
    }

    static
    {
        register(new LevelGroup(true), "{$++}");
        register(new LevelGroup(false), "{$--}");
        register(new RangeGroup(), "{$-$}");
    }

    /**
     * Register new or replace existing special group for given patterns. <br>
     * like {$-$} for {@link RangeGroup}.
     *
     * @param group    special group to register.
     * @param patterns patterns for this group.
     */
    public static void register(final SpecialGroup<?> group, final String... patterns)
    {
        for (final String pattern : patterns)
        {
            groups.put(pattern, group);
        }
    }

    /**
     * Get special group by pattern.
     *
     * @param pat pattern of group, like {$-$} for {@link RangeGroup}.
     *
     * @return special group or null if there is no group for given pattern.
     */
    public static SpecialGroup<?> get(final String pat)
    {
        return groups.get(pat);
    }
}
