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

/**
 * Represent results of parsing {@link SpecialGroup}
 */
public class GroupResult
{
    private final boolean valid; // if given string was parsed without problems, like no letters in number data.
    private final boolean matching; // if given string is in valid range, if hasPermission should return true.
    private final int     endIndex; // index of last char used for pattern.

    /**
     * Construct new GroupResult.
     *
     * @param valid    if given string was parsed without problems, like no letters in number data.
     * @param matching if given string and data is in valid range, so if hasPermission should return true.
     * @param endIndex index of last char used for pattern. Used to get unparsed part of given string.
     */
    public GroupResult(final boolean valid, final boolean matching, final int endIndex)
    {
        this.valid = valid;
        this.matching = matching;
        this.endIndex = endIndex;
    }

    /**
     * Returns true if given string was parsed without problems, like no letters in number data.
     *
     * @return true if given string was parsed without problems, like no letters in number data.
     */
    public boolean isValid()
    {
        return this.valid;
    }

    /**
     * Returns true if given string and data is in valid range, so if hasPermission should return true.
     *
     * @return if given string and data is in valid range, so if hasPermission should return true.
     */
    public boolean isMatching()
    {
        return this.matching;
    }

    /**
     * Returns index of last char used for pattern. Used to get unparsed part of given string.
     *
     * @return index of last char used for pattern. Used to get unparsed part of given string.
     */
    public int getEndIndex()
    {
        return this.endIndex;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("valid", this.valid).append("matching", this.matching).append("endIndex", this.endIndex).toString();
    }
}
