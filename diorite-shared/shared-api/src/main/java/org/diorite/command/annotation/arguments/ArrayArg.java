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

import org.diorite.command.parser.basic.tokens.ArrayTokens;

/**
 * Array argument settings.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArrayArg
{
    /**
     * Basic argument settings.
     *
     * @return basic argument settings.
     */
    Arg value() default @Arg;

    /**
     * Marks a array/collection argument as vararg. <br>
     * If array is vararg then it doesn't require valid array syntax, each element can be a separate argument, like 1 2 3 instead of 1,2,3.
     *
     * @return if argument is vararg.
     */
    boolean vararg() default false;

    /**
     * Marks a size of array/collection argument. <br>
     * Fixed size array/collection doesn't require valid array syntax, each element can be a separate argument, like 1 2 3 instead of 1,2,3.
     *
     * @return expected size of array, -1 if disabled.
     */
    int size() default - 1;

    /**
     * Minimal size of array/collection, multiple values can be used along with {@link #max()} setting to create multiple valid ranges.
     *
     * @return minimal size of array/collection.
     */
    int[] min() default Integer.MIN_VALUE;

    /**
     * Maximal size of array/collection, multiple values can be used along with {@link #min()} setting to create multiple valid ranges.
     *
     * @return maximal size of array/collection.
     */
    int[] max() default Integer.MAX_VALUE;

    /**
     * Returns tokens used by array.
     *
     * @return tokens used by array.
     */
    Tokens tokens() default @Tokens;

    /**
     * Array tokens.
     */
    @Target({ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Tokens
    {
        Tokens DEFAULT =
                new ArrayTokens(new char[]{'[', '{', '(', '<'},
                                new char[]{']', '}', ')', '>'},
                                new char[]{','}
                );

        /**
         * Array start tokens. <br>
         * Order of {@link #start()} and {@link #end()} tokens must match, only tokens on this same index can be used to mark array in argument.
         *
         * @return
         */
        char[] start() default {'[', '{', '(', '<'};

        /**
         * Array start tokens. <br>
         * Order of {@link #start()} and {@link #end()} tokens must match, only tokens on this same index can be used to mark array in argument.
         *
         * @return
         */
        char[] end() default {']', '}', ')', '>'};

        /**
         * Tokens used to separate array/collection entries.
         *
         * @return array/collection entries separator tokens.
         */
        char[] separator() default ',';
    }
}
