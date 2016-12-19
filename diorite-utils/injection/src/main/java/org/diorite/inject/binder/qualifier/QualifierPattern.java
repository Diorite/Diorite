/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.inject.binder.qualifier;

/**
 * Qualifier pattern filter/predicate for {@link QualifierData}.
 */
public interface QualifierPattern
{
    /**
     * Pattern that always matches and returns true.
     */
    QualifierPattern EMPTY = d -> true;

    /**
     * Check if given {@link QualifierData} matches this pattern.
     *
     * @param data
     *         to check.
     *
     * @return true if given data matches this pattern.
     */
    boolean test(QualifierData data);

    /**
     * Join multiple {@link QualifierPattern} into one.
     *
     * @param patterns
     *         patterns to join.
     *
     * @return pattern that can be used instead of given array.
     */
    static QualifierPattern create(QualifierPattern... patterns)
    {
        if (patterns.length == 0)
        {
            return QualifierPattern.EMPTY;
        }
        if (patterns.length == 1)
        {
            return patterns[0];
        }
        return data ->
        {
            for (QualifierPattern pattern : patterns)
            {
                if (! pattern.test(data))
                {
                    return false;
                }
            }
            return true;
        };
    }
}
