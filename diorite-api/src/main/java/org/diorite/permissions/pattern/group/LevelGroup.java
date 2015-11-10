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
public class LevelGroup implements SpecialGroup<Long, Long>
{
    /**
     * Pattern value if group is ascending.
     */
    protected static final String ASC_PAT  = "{$++}";
    /**
     * Pattern value if group is descending.
     */
    protected static final String DESC_PAT = "{$--}";

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
        boolean sign = true;
        for (final int charArrayLength = charArray.length; endIndex < charArrayLength; endIndex++)
        {
            final char c = charArray[endIndex];
            if (sign)
            {
                sign = false;
                if ((c == '+') || (c == '-'))
                {
                    continue;
                }
            }
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
    public Long parseValueData(final String data)
    {
        if (data == null)
        {
            return null;
        }
        return DioriteMathUtils.asLong(data);
    }

    @Override
    public Long parsePatternData(final String data)
    {
        if (data == null)
        {
            return null;
        }
        return DioriteMathUtils.asLong(data);
    }

    @Override
    public boolean isMatching(final Long validData, final Long userData)
    {
        return this.ascending ? (validData >= userData) : (validData <= userData);
    }

    @Override
    public String getGroupPattern()
    {
        return this.ascending ? ASC_PAT : DESC_PAT;
    }

    @Override
    public boolean isValidPattern(final String string)
    {
        return DioriteMathUtils.asLong(string) != null;
    }

    @Override
    public boolean isValidValue(final String string)
    {
        return DioriteMathUtils.asLong(string) != null;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("ascending", this.ascending).toString();
    }
}
