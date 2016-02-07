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

package org.diorite.utils.validator.string;

import java.util.Arrays;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class StringAllowedCharsValidatorImpl implements StringAllowedCharsValidator
{
    private final char[] chars;

    StringAllowedCharsValidatorImpl(final char[] chars)
    {
        Validate.notNull(chars, "Chars can't be null.");
        this.chars = chars.clone();
        Arrays.sort(this.chars);
    }

    @Override
    public char[] getChars()
    {
        return this.chars.clone();
    }

    @Override
    public boolean validate(final String s)
    {
        if (s == null)
        {
            return false;
        }
        for (final char c : s.toCharArray())
        {
            if (Arrays.binarySearch(this.chars, c) < 0)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof StringAllowedCharsValidator))
        {
            return false;
        }

        final StringAllowedCharsValidator that = (StringAllowedCharsValidator) o;
        return Arrays.equals(this.chars, that.getChars());
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(this.chars);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chars", this.chars).toString();
    }
}
