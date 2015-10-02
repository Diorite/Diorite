package org.diorite.permissions.pattern.group;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.utils.math.DioriteMathUtils;

/**
 * Level special group, it use {$++} and {$--} patterns. <br>
 * If we have "foo.{$++}" or "foo.{$--}" pattern: <br>
 * <ol>
 * <li>{$++} is ascending type of level group, if player have permission "foo.5" and plugin will check for "foo.2" it will return true, as 2 {@literal <=} 5.<br>
 * So bigger permission level means more permissions, so it is ascending type.</li>
 * <li>{$--} is descending type of level group, if player have permission "foo.5" and plugin will check for "foo.7" it will return true, as 7 {@literal >=} 5.<br>
 * So bigger permission level means less permissions, so it is descending type.</li>
 * </ol>
 */
public class LevelGroup implements SpecialNumberGroup
{
    protected final boolean ascending; // {$++} if true, for pattern "foo.{$++}" permission "foo.3" will return true for player with "foo.5", as 3 <= 5.

    /**
     * Construct new level group with given type.
     *
     * @param ascending type of group.
     */
    public LevelGroup(final boolean ascending)
    {
        this.ascending = ascending;
    }

    /**
     * Returns if this group represent {$++} (true), or {$--} (false).
     *
     * @return if this group represent {$++} (true), or {$--} (false).
     */
    public boolean isAscending()
    {
        return this.ascending;
    }

    @Override
    public GroupResult parse(final String string, final Long data)
    {
        int endIndex = 0;
        final char[] charArray = string.toCharArray();
        for (final int charArrayLength = charArray.length; endIndex < charArrayLength; endIndex++)
        {
            final char c = charArray[endIndex];
            if ((c < '0') || (c > '9'))
            {
                break;
            }
        }
        if (endIndex == 0)
        {
            return new GroupResult(false, false, 0);
        }
        final Long i = DioriteMathUtils.asLong(string.substring(0, endIndex));
        if (i == null)// should never be true, only if someone use number that don't fit into long.
        {
            return new GroupResult(false, false, 0);
        }
        return new GroupResult(true, this.ascending ? (i <= data) : (i >= data), endIndex);
    }

    @Override
    public Long parseData(final String data)
    {
        return null;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("ascending", this.ascending).toString();
    }
}
