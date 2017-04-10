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

package org.diorite.command.annotation.arguments;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.diorite.command.parser.basic.tokens.StringTokens;

/**
 * String argument settings.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrArg
{
    /**
     * Basic argument settings.
     *
     * @return basic argument settings.
     */
    Arg value() default @Arg;

    /**
     * Marks a string argument as vararg. <br>
     * If string is vararg then all strings will be joined using a space as delimiter.
     *
     * @return if argument is vararg.
     */
    boolean vararg() default false;

    /**
     * Marks a argument as raw string. <br>
     * Raw strings are used to make sure that argument is made of single word, they are parsed without any special tokens.
     *
     * @return true if string is marked as raw.
     */
    boolean raw() default false;

    /**
     * Marks a size of string argument, all strings will be joined using a space as delimiter.
     */
    int size() default - 1;

    /**
     * Minimal length of string, multiple values can be used along with {@link #max()} setting to create multiple valid ranges.
     *
     * @return minimal length of string.
     */
    int[] min() default Integer.MIN_VALUE;

    /**
     * Maximal length of string, multiple values can be used along with {@link #min()} setting to create multiple valid ranges.
     *
     * @return maximal length of string.
     */
    int[] max() default Integer.MAX_VALUE;

    /**
     * Array of invalid chars.
     *
     * @return array of invalid chars.
     */
    char[] invalidChars() default {};

    /**
     * Array of valid chars, if empty then all chars are allowed.
     *
     * @return array of valid chars.
     */
    char[] validChar() default {};

    /**
     * If array isn't empty, then string will be validated using the regex api, and at least one pattern must match.
     *
     * @return array fo regex patterns.
     */
    String[] validatePattern() default {};

    /**
     * Returns tokens used by string.
     *
     * @return tokens used by string.
     */
    Tokens tokens() default @Tokens;

    /**
     * String tokens.
     */
    @Target({ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Tokens
    {
        Tokens DEFAULT =
                new StringTokens(new char[]{'\'', '"', '`'},
                                 new char[]{'\'', '"', '`'}
                );

        /**
         * String start tokens. <br>
         * Order of {@link #start()} and {@link #end()} tokens must match, only tokens on this same index can be used to mark string in argument.
         *
         * @return string start tokens.
         */
        char[] start() default {'\'', '"', '`'};

        /**
         * String end tokens. <br>
         * Order of {@link #start()} and {@link #end()} tokens must match, only tokens on this same index can be used to mark string in argument.
         *
         * @return string end tokens.
         */
        char[] end() default {'\'', '"', '`'};
    }
}
