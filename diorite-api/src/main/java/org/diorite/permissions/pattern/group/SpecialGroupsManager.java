/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.permissions.pattern.group;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for all special groups, plugins can add or edit special permission groups here.
 */
public final class SpecialGroupsManager
{
    private static final Map<String, SpecialGroup<?, ?>> groups = new HashMap<>(5, .25F);

    private SpecialGroupsManager()
    {
    }

    static
    {
        register(new LevelGroup(true));
        register(new LevelGroup(false));
        register(new RangeGroup());
    }

    /**
     * Register new or replace existing special group. <br>
     * Like {$-$} for {@link RangeGroup}.
     *
     * @param group special group to register.
     */
    public static void register(final SpecialGroup<?, ?> group)
    {
        groups.put(group.getGroupPattern(), group);
    }

    /**
     * Get special group by pattern.
     *
     * @param pat pattern of group, like {$-$} for {@link RangeGroup}.
     *
     * @return special group or null if there is no group for given pattern.
     */
    public static SpecialGroup<?, ?> get(final String pat)
    {
        return groups.get(pat);
    }
}
