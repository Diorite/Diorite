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

package org.diorite.utils.validator;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.diorite.utils.validator.SimpleValidator.ValidatorEntry;
import org.diorite.utils.validator.string.StringAllowedCharsValidator;
import org.diorite.utils.validator.string.StringCustomValidator;
import org.diorite.utils.validator.string.StringDisallowedCharsValidator;
import org.diorite.utils.validator.string.StringLengthValidator;
import org.diorite.utils.validator.string.StringRegExValidator;

/**
 * Utility class for creating ValidatorEntires for Strings
 */
public final class StringValidators
{
    private StringValidators()
    {
    }

    /**
     * Create new custom String validator that don't throw exception on invalid parameter.
     *
     * @param predicate predicate to be used as validator.
     *
     * @return created validator.
     */
    public static Validator<String> custom(final Predicate<String> predicate)
    {
        return custom(predicate, null);
    }

    /**
     * Create new custom String validator that throws given exception on invalid parameter.
     *
     * @param predicate predicate to be used as validator.
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     * @param <T>       type of Predicate.
     *
     * @return created validator.
     */
    public static <T extends Predicate<String>> Validator<String> custom(final T predicate, final BiFunction<String, StringCustomValidator<T>, ? extends IllegalArgumentException> exception)
    {
        return new ValidatorEntry<>(StringCustomValidator.create(predicate), exception);
    }

    /**
     * Create min/max length string validator that don't throw exception on invalid parameter.
     *
     * @param min minimum length of string.
     * @param max maximum length of string.
     *
     * @return created validator.
     */
    public static Validator<String> length(final int min, final int max)
    {
        return length(min, max, null);
    }

    /**
     * Create min/max length string validator that throws given exception on invalid parameter.
     *
     * @param min       minimum length of string.
     * @param max       maximum length of string.
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     *
     * @return created validator.
     */
    public static Validator<String> length(final int min, final int max, final BiFunction<String, StringLengthValidator, ? extends IllegalArgumentException> exception)
    {
        return new ValidatorEntry<>(StringLengthValidator.create(min, max), exception);
    }

    /**
     * Create new String regex validator that don't throw exception on invalid parameter.
     *
     * @param pattern rexex pattern to be used as validator.
     *
     * @return created validator.
     */
    public static Validator<String> regEx(final Pattern pattern)
    {
        return regEx(pattern, null);
    }

    /**
     * Create new String regex validator that throws given exception on invalid parameter.
     *
     * @param pattern   rexex pattern to be used as validator.
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     *
     * @return created validator.
     */
    public static Validator<String> regEx(final Pattern pattern, final BiFunction<String, StringRegExValidator, ? extends IllegalArgumentException> exception)
    {
        return new ValidatorEntry<>(StringRegExValidator.create(pattern), exception);
    }

    /**
     * Create new String allowed chars validator that don't throw exception on invalid parameter.
     *
     * @param chars array of allowed chars.
     *
     * @return created validator.
     */
    public static Validator<String> allowedChars(final char... chars)
    {
        return allowedChars(chars, null);
    }

    /**
     * Create new String allowed chars validator that throws given exception on invalid parameter.
     *
     * @param chars     array of allowed chars.
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     *
     * @return created validator.
     */
    public static Validator<String> allowedChars(final char[] chars, final BiFunction<String, StringAllowedCharsValidator, ? extends IllegalArgumentException> exception)
    {
        return new ValidatorEntry<>(StringAllowedCharsValidator.create(chars), exception);
    }

    /**
     * Create new String allowed chars validator that throws given exception on invalid parameter.
     *
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     * @param chars     array of allowed chars.
     *
     * @return created validator.
     */
    public static Validator<String> allowedChars(final BiFunction<String, StringAllowedCharsValidator, ? extends IllegalArgumentException> exception, final char... chars)
    {
        return allowedChars(chars, exception);
    }

    /**
     * Create new String allowed chars validator that don't throw exception on invalid parameter.
     *
     * @param chars array of strings contains ranges of disallowed chars.
     *
     * @return created validator.
     */
    public static Validator<String> allowedChars(final String... chars)
    {
        return allowedChars(chars, null);
    }

    /**
     * Create new String allowed chars validator that throws given exception on invalid parameter.
     *
     * @param chars     array of strings contains ranges of disallowed chars.
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     *
     * @return created validator.
     */
    public static Validator<String> allowedChars(final String[] chars, final BiFunction<String, StringAllowedCharsValidator, ? extends IllegalArgumentException> exception)
    {
        return new ValidatorEntry<>(StringAllowedCharsValidator.create(joinRanges(chars)), exception);
    }

    /**
     * Create new String allowed chars validator that throws given exception on invalid parameter.
     *
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     * @param chars     array of strings contains ranges of disallowed chars.
     *
     * @return created validator.
     */
    public static Validator<String> allowedChars(final BiFunction<String, StringAllowedCharsValidator, ? extends IllegalArgumentException> exception, final String... chars)
    {
        return allowedChars(chars, exception);
    }

    /**
     * Create new String disallowed chars validator that don't throw exception on invalid parameter.
     *
     * @param chars array of disallowed chars.
     *
     * @return created validator.
     */
    public static Validator<String> disallowedChars(final char... chars)
    {
        return disallowedChars(chars, null);
    }

    /**
     * Create new String disallowed chars validator that throws given exception on invalid parameter.
     *
     * @param chars     array of disallowed chars.
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     *
     * @return created validator.
     */
    public static Validator<String> disallowedChars(final char[] chars, final BiFunction<String, StringDisallowedCharsValidator, ? extends IllegalArgumentException> exception)
    {
        return new ValidatorEntry<>(StringDisallowedCharsValidator.create(chars), exception);
    }

    /**
     * Create new String disallowed chars validator that throws given exception on invalid parameter.
     *
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     * @param chars     array of disallowed chars.
     *
     * @return created validator.
     */
    public static Validator<String> disallowedChars(final BiFunction<String, StringDisallowedCharsValidator, ? extends IllegalArgumentException> exception, final char... chars)
    {
        return disallowedChars(chars, exception);
    }

    /**
     * Create new String disallowed chars validator that don't throw exception on invalid parameter.
     *
     * @param chars array of strings contains ranges of disallowed chars.
     *
     * @return created validator.
     */
    public static Validator<String> disallowedChars(final String... chars)
    {
        return disallowedChars(chars, null);
    }

    /**
     * Create new String disallowed chars validator that throws given exception on invalid parameter.
     *
     * @param chars     array of strings contains ranges of disallowed chars.
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     *
     * @return created validator.
     */
    public static Validator<String> disallowedChars(final String[] chars, final BiFunction<String, StringDisallowedCharsValidator, ? extends IllegalArgumentException> exception)
    {
        return new ValidatorEntry<>(StringDisallowedCharsValidator.create(joinRanges(chars)), exception);
    }

    /**
     * Create new String disallowed chars validator that throws given exception on invalid parameter.
     *
     * @param exception function that create exception from validated object and used validator. If function will return null, validator will not throw any exception.
     * @param chars     array of strings contains ranges of disallowed chars.
     *
     * @return created validator.
     */
    public static Validator<String> disallowedChars(final BiFunction<String, StringDisallowedCharsValidator, ? extends IllegalArgumentException> exception, final String... chars)
    {
        return disallowedChars(chars, exception);
    }

    private static char[] joinRanges(final String... ranges)
    {
        final Collection<Character> charSet = new HashSet<>((ranges.length * 20) + 1);
        for (final String range : ranges)
        {
            if (range.length() != 3)
            {
                if (range.length() == 1)
                {
                    charSet.add(range.charAt(0));
                    continue;
                }
                throw new IllegalArgumentException("Range string isn't 3 chars long: " + range);
            }
            char a = range.charAt(0);
            final char b = range.charAt(2);
            while (a <= b)
            {
                charSet.add(a++);
            }
        }
        final char[] chars = new char[charSet.size()];
        int i = 0;
        for (final Character character : charSet)
        {
            chars[i++] = character;
        }
        return chars;
    }
}
