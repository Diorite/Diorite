/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import org.apache.commons.lang3.StringUtils;

import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.commons.math.LongRange;

/**
 * Ranged special group, it use {$-$} pattern. <br>
 * If we have "foo.{$-$}" pattern, we can use it in 2 ways: <br>
 * <ol>
 * <li>If player have "foo.10-20" permissions, and we check if he have "foo.15" it will return true as 15 {@literal >=} 10 and 15 {@literal <=} 20.</li>
 * <li>If player have "foo.[10-20,30-40]" permissions, and we check if he have "foo.30" it will return true as 30 {@literal >=} 30 and 30 {@literal <=} 40, it use multiple ranges.</li>
 * </ol>
 */
public class RangeGroup implements SpecialGroup<Long, LongRange[]>
{
    /**
     * Pattern value of this group.
     */
    protected static final String PAT = "{$-$}";

    /**
     * Construct new range group.
     */
    @SuppressWarnings("RedundantNoArgConstructor")
    public RangeGroup()
    {
        super();
    }

    @Override
    public Long parseValueData(String data)
    {
        if (data == null)
        {
            return null;
        }
        return DioriteMathUtils.asLong(data);
    }

    @Override
    public LongRange[] parsePatternData(String data)
    {
        if (data == null)
        {
            return null;
        }

        if (data.length() < 3) // smallest range is 3 chars, "1-2"
        {
            return null;
        }
        if ((data.charAt(0) == '[') && (data.charAt(data.length() - 1) == ']'))
        {
            if (data.length() < 5) // smallest multi-range is 5 chars, "[1-2]"
            {
                return null;
            }
            data = data.substring(1, data.length() - 1);
            String[] ranges = StringUtils.split(data, ',');
            LongRange[] result = new LongRange[ranges.length];
            for (int i = 0, rangesLength = ranges.length; i < rangesLength; i++)
            {
                String str = ranges[i];
                LongRange range = parseSingleRange(str);
                if (range == null)
                {
                    return null;
                }
                result[i] = range;
            }
            return result;
        }
        LongRange range = parseSingleRange(data);
        if (range == null)
        {
            return null;
        }
        return new LongRange[]{range};
    }

    @Override
    public boolean isMatching(LongRange[] validData, Long userData)
    {
        for (LongRange longRange : validData)
        {
            if (longRange.isIn(userData))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public GroupResult parse(String string, Long data)
    {
        int endIndex = 0;
        char[] charArray = string.toCharArray();
        if (charArray[0] == '[')
        {
            endIndex = string.indexOf(']');
            string = string.substring(1, endIndex++);
            charArray = string.toCharArray();
            String[] ranges = StringUtils.split(string, ',');
            for (String str : ranges)
            {
                GroupResult result = checkRange(str, charArray, 0, data);
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

    @Override
    public String getGroupPattern()
    {
        return PAT;
    }

    @Override
    public boolean isValidPattern(String string)
    {
        if (string.length() < 3) // smallest range is 3 chars, "1-2"
        {
            return false;
        }
        if ((string.charAt(0) == '[') && (string.charAt(string.length() - 1) == ']'))
        {
            if (string.length() < 5) // smallest multi-range is 5 chars, "[1-2]"
            {
                return false;
            }
            string = string.substring(1, string.length() - 1);
            String[] ranges = StringUtils.split(string, ',');
            for (String str : ranges)
            {
                if (! checkIfValidRange(str))
                {
                    return false;
                }
            }
            return true;
        }
        return checkIfValidRange(string);
    }

    @Override
    public boolean isValidValue(String string)
    {
        return DioriteMathUtils.asLong(string) != null;
    }

    private static LongRange parseSingleRange(String string)
    {
        int index = string.lastIndexOf('-');
        if (index == - 1)
        {
            return null;
        }
        int index2 = string.lastIndexOf('-', index - 1); // to handle negative values in ranges
        Long a;
        Long b;
        if ((index2 == 0) || (index2 == - 1))
        {
            a = DioriteMathUtils.asLong(string.substring(0, index));
            b = DioriteMathUtils.asLong(string.substring(index + 1));
        }
        else
        {
            a = DioriteMathUtils.asLong(string.substring(0, index2));
            b = DioriteMathUtils.asLong(string.substring(index2 + 1));
        }
        if ((a == null) || (b == null))
        {
            return null;
        }
        return new LongRange(a, b);
    }

    private static boolean checkIfValidRange(String string)
    {
        int index = string.lastIndexOf('-');
        if (index == - 1)
        {
            return false;
        }
        int index2 = string.lastIndexOf('-', index - 1); // to handle negative values in ranges
        if ((index2 == 0) || (index2 == - 1))
        {
            return (DioriteMathUtils.asLong(string.substring(0, index)) != null) && (DioriteMathUtils.asLong(string.substring(index + 1)) != null);
        }
        return (DioriteMathUtils.asLong(string.substring(0, index2)) != null) && (DioriteMathUtils.asLong(string.substring(index2 + 1)) != null);
    }

    private static GroupResult checkRange(String string, char[] charArray, int endIndex, long data)
    {
        Long min, max;
        {
            boolean sign = true;
            for (int charArrayLength = charArray.length; endIndex < charArrayLength; endIndex++)
            {
                char c = charArray[endIndex];
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
            min = DioriteMathUtils.asLong(string.substring(0, endIndex));
            if (min == null)
            {
                return new GroupResult(false, false, 0);
            }
        }
        if (charArray[endIndex++] != '-')
        {
            return new GroupResult(false, false, 0);
        }
        int start = endIndex;
        {
            boolean sign = true;
            for (int charArrayLength = charArray.length; endIndex < charArrayLength; endIndex++)
            {
                char c = charArray[endIndex];
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
            max = DioriteMathUtils.asLong(string.substring(start, endIndex));
            if (max == null)
            {
                return new GroupResult(false, false, 0);
            }
        }
        return new GroupResult(true, (data >= min) && (data <= max), endIndex);
    }
}
