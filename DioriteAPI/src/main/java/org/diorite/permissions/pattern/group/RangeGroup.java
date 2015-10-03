package org.diorite.permissions.pattern.group;

import org.apache.commons.lang3.StringUtils;

import org.diorite.utils.math.DioriteMathUtils;

/**
 * Ranged special group, it use {$-$} pattern. <br>
 * If we have "foo.{$-$}" pattern, we can use it in 2 ways: <br>
 * <ol>
 * <li>If player have "foo.10-20" permissions, and we check if he have "foo.15" it will return true as 15 {@literal >=} 10 and 15 {@literal <=} 20.</li>
 * <li>If player have "foo.[10-20,30-40]" permissions, and we check if he have "foo.30" it will return true as 30 {@literal >=} 30 and 30 {@literal <=} 40, it use multiple ranges.</li>
 * </ol>
 */
public class RangeGroup implements SpecialNumberGroup
{
    /**
     * Construct new range group.
     */
    @SuppressWarnings("RedundantNoArgConstructor")
    public RangeGroup()
    {
        super();
    }

    @Override
    public GroupResult parse(String string, final Long data)
    {
        int endIndex = 0;
        char[] charArray = string.toCharArray();
        if (charArray[0] == '[')
        {
            endIndex = string.indexOf(']');
            string = string.substring(1, endIndex++);
            charArray = string.toCharArray();
            final String[] ranges = StringUtils.split(string, ',');
            for (final String str : ranges)
            {
                final GroupResult result = checkRange(str, charArray, 0, data);
                if (! result.isValid())
                {
                    return result;
                }
                if (result.isMatching())
                {
                    return new GroupResult(true, true, endIndex);
                }
            }
            return new GroupResult(true, false, endIndex);
        }
        else
        {
            return checkRange(string, charArray, endIndex, data);
        }
    }

    private static GroupResult checkRange(final String string, final char[] charArray, int endIndex, final long data)
    {
        final Long min, max;
        {
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
            min = DioriteMathUtils.asLong(string.substring(0, endIndex));
            if (min == null)// should never be true, only if someone use number that don't fit into long.
            {
                return new GroupResult(false, false, 0);
            }
        }
        if (charArray[endIndex++] != '-')
        {
            return new GroupResult(false, false, 0);
        }
        final int start = endIndex;
        {
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
            max = DioriteMathUtils.asLong(string.substring(start, endIndex));
            if (max == null)// should never be true, only if someone use number that don't fit into long.
            {
                return new GroupResult(false, false, 0);
            }
        }
        return new GroupResult(true, (data >= min) && (data <= max), endIndex);
    }
}
