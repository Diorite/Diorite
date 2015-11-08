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

package org.diorite.permissions.pattern;

import org.diorite.permissions.pattern.group.SpecialGroup;

/**
 * Represent more complicated permission pattern that use {@link SpecialGroup}s.
 */
public interface ExtendedPermissionPattern extends PermissionPattern
{
    /**
     * Returns array of {@link SpecialGroup}s used by this extended pattern.
     *
     * @return array of {@link SpecialGroup}s used by this extended pattern.
     */
    @SuppressWarnings("rawtypes")
    SpecialGroup[] getGroups();

    /**
     * Checks if given string can be a permission value using this pattern. <br>
     * Like "foo.5" will return true for "foo.{$-$}" pattern, but "foo.5-10" will return false as this is pattern value.
     *
     * @param str string/permission to check.
     *
     * @return if given string is valid for this pattern.
     */
    boolean isValidValue(String str);

    /**
     * Get value data of given permission for this pattern,
     * if permission isn't valid NULL value will be returned. <br>
     * Like valid value data for {$-$} is a long value.
     *
     * @param permission permissions to extract data.
     *
     * @return data of permission or null.
     */
    Object[] getValueData(String permission);

    /**
     * Get pattern data of given permission for this pattern,
     * if permission isn't valid NULL value will be returned. <br>
     * Like valid pattern data for {$-$} is array of {@link org.diorite.utils.math.LongRange}
     *
     * @param permission permissions to extract data.
     *
     * @return data of permission or null.
     */
    Object[] getPatternData(String permission);
}
