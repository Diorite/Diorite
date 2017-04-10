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

import org.diorite.command.parser.basic.tokens.MapTokens;

/**
 * Map argument settings.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MapArg
{
    /**
     * Basic argument settings.
     *
     * @return basic argument settings.
     */
    Arg value() default @Arg;

    /**
     * Marks a map argument as vararg. <br>
     * If map is vararg then it doesn't require valid map syntax, each element can be a separate argument, like a:1 b:2 c:3 instead of a:1,b:2,c:3.
     *
     * @return if argument is vararg.
     */
    boolean vararg() default false;

    /**
     * Marks a size of map argument. <br>
     * Fixed size map doesn't require valid map syntax, each element can be a separate argument, like a:1 b:2 c:3 instead of a:1,b:2,c:3.
     *
     * @return expected size of map, -1 if disabled.
     */
    int size() default - 1;

    /**
     * Minimal size of map, multiple values can be used along with {@link #max()} setting to create multiple valid ranges.
     *
     * @return minimal size of map.
     */
    int[] min() default Integer.MIN_VALUE;

    /**
     * Maximal size of map, multiple values can be used along with {@link #min()} setting to create multiple valid ranges.
     *
     * @return maximal size of map.
     */
    int[] max() default Integer.MAX_VALUE;

    /**
     * Returns tokens used by map.
     *
     * @return tokens used by map.
     */
    Tokens tokens() default @Tokens;

    /**
     * Map tokens.
     */
    @Target({ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Tokens
    {
        Tokens DEFAULT = new MapTokens(new char[]{'{', '[', '(', '<'},
                                       new char[]{'}', ']', ')', '>'},
                                       new char[]{','},
                                       new char[]{':', '='}
        );

        /**
         * Map start tokens. <br>
         * Order of {@link #start()} and {@link #end()} tokens must match, only tokens on this same index can be used to mark map in argument.
         *
         * @return start map tokens.
         */
        char[] start() default {'{', '[', '(', '<'};

        /**
         * Map start tokens. <br>
         * Order of {@link #start()} and {@link #end()} tokens must match, only tokens on this same index can be used to mark map in argument.
         *
         * @return end map tokens.
         */
        char[] end() default {'}', ']', ')', '>'};

        /**
         * Tokens used to separate map entries.
         *
         * @return map entries separator tokens.
         */
        char[] separator() default ',';

        /**
         * Tokens used to separate key from value in each entry.
         *
         * @return entry key-value separator tokens.
         */
        char[] entrySeparator() default {':', '='};
    }
}
