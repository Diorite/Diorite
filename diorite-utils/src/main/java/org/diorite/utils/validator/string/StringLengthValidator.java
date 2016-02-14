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

/**
 * Validator interface for String length check.
 */
public interface StringLengthValidator extends StringCustomValidator<StringLengthValidator>
{
    /**
     * Returns min length of string, -1 if no limit.
     *
     * @return min length of string, -1 if no limit.
     */
    int getMinLength();

    /**
     * Returns max length of string, -1 if no limit.
     *
     * @return max length of string, -1 if no limit.
     */
    int getMaxLength();

    /**
     * Create new String length validator with given min/max length, use -1 to ignore.
     *
     * @param min minimum length of string.
     * @param max maximim length of string.
     *
     * @return new string length validator.
     */
    static StringLengthValidator create(final int min, final int max)
    {
        return new StringLengthValidatorImpl(min, max);
    }
}
