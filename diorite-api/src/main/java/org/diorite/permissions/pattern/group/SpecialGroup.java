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

/**
 * Base abstract class for special permission pattern groups, like permissions that can use number ranges. {@link RangeGroup}
 *
 * @param <R> type of input data when checking string.
 * @param <T> type of input from {@link org.diorite.permissions.pattern.ExtendedPermissionPattern}
 */
public interface SpecialGroup<R, T>
{
    /**
     * This method will parse given string, check if it is valid, and check if given data is in range, so hasPermission should return true. <br>
     * Given string ALWAYS must start with pattern data to parse, but may contains additional chars at the end,
     * like when parsing "foo.15.bar" with data 10 for pattern "foo.{$++}.bar", given string will be "15.bar", and it will return that
     * string is valid, (as it contains valid number where excepted), and it is matching LevelGroup 10 {@literal <=} 15,
     * and end index of 2 as pattern data is only one 2 digit number, 2 chars.
     *
     * @param string given string to parse.
     * @param data   data to check.
     *
     * @return {@link GroupResult} results contains if string was valid, if data was matching, and end index of pattern data to get unparsed part of given string.
     */
    GroupResult parse(final String string, final R data);

    /**
     * This method will convert given string to valid group data, like in pattern "foo.{$-$}.bar" for "foo.56.bar" in {@link RangeGroup}
     * this method will parse "56" string to Long value. <br>
     * Or return null if it is not possible.
     *
     * @param data data to parse in string from permission.
     *
     * @return parsed data or null.
     */
    R parseValueData(final String data);

    /**
     * This method will convert given string to valid group pattern data, like in pattern "foo.{$-$}.bar" for "foo.13-24.bar" in {@link RangeGroup}
     * this method will parse "13-24" string to 1 element array of {@link org.diorite.utils.math.LongRange}. <br>
     * Or return null if it is not possible.
     *
     * @param data data to parse in string from permission.
     *
     * @return parsed data or null.
     */
    T parsePatternData(final String data);

    /**
     * This method will check if given value is valid for this group,
     * may be used by implementation of permission system.
     *
     * @param validData valid data for this pattern.
     * @param userData  data to check.
     *
     * @return true is data is valid.
     */
    boolean isMatching(final T validData, final R userData);

    /**
     * Returns pattern value for this group, like "{$++}". <br>
     * This will return always this same value for this same type of special group.
     *
     * @return pattern value for this group.
     */
    String getGroupPattern();

    /**
     * This method will parse given string, check if it is valid, and check if given data is in range, so hasPermission should return true. <br>
     * Given string ALWAYS must start with pattern data to parse, but may contains additional chars at the end,
     * like when parsing "foo.15.bar" with data 10 for pattern "foo.{$++}.bar", given string will be "15.bar", and it will return that
     * string is valid, (as it contains valid number where excepted), and it is matching LevelGroup 10 {@literal <=} 15,
     * and end index of 2 as pattern data is only one 2 digit number, 2 chars.
     *
     * @param string given string to parse.
     * @param data   data to check.
     *
     * @return {@link GroupResult} results contains if string was valid, if data was matching, and end index of pattern data to get unparsed part of given string.
     */
    default GroupResult parse(final String string, final String data)
    {
        final R parsedData = this.parseValueData(data);
        if (parsedData == null)
        {
            return new GroupResult(false, false, 0);
        }
        return this.parse(string, parsedData);
    }

    /**
     * Check if given string can be a pattern value of special group, like "5-10" for "{$-$}"
     *
     * @param string string to check.
     *
     * @return true if given string can be a pattern value of this group.
     */
    boolean isValidPattern(final String string);

    /**
     * Check if given string can be a value of special group, like "5" for "{$-$}"
     *
     * @param string string to check.
     *
     * @return true if given string can be value of this group.
     */
    boolean isValidValue(final String string);
}
