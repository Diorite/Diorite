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

import org.diorite.command.parser.basic.tokens.BooleanTokens;
import org.diorite.commons.arrays.DioriteArrayUtils;

/**
 * Boolean argument settings.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BoolArg
{
    /**
     * Basic argument settings.
     *
     * @return basic argument settings.
     */
    Arg value() default @Arg;

    /**
     * Returns tokens used by boolean.
     *
     * @return tokens used by boolean.
     */
    Tokens tokens() default @Tokens;

    /**
     * Boolean tokens.
     */
    @Target({ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Tokens
    {
        Tokens DEFAULT = new BooleanTokens(true, DioriteArrayUtils.EMPTY_STRINGS, DioriteArrayUtils.EMPTY_STRINGS, DioriteArrayUtils.EMPTY_CHARS);

        /**
         * If true than words placed in {@link #yesWords()} and {@link #noWords()} are added to config/default values.
         *
         * @return if words are added.
         */
        boolean addToDefaults() default true;

        /**
         * Array of words that are parsed as true.
         *
         * @return array of words that are parsed as true.
         */
        String[] yesWords() default {"on", "enable", "enabled", "yes", "allow", "allowed", "y", "true", "tak", "t", "wlaczony", "prawda", "p", "1"};

        /**
         * Array of words that are parsed as false.
         *
         * @return array of words that are parsed as false.
         */
        String[] noWords() default {"off", "disable", "disabled", "no", "disallow", "disallowed", "n", "false", "f", "nie", "wylaczony", "falsz", "0"};

        /**
         * Array of char pairs where first char is replaced by second before parsing, like {'ł', 'l'}, `ł` will be replaced with `l`. <br>
         *
         * @return array of char pairs where first char is replaced by second before parsing.
         */
        char[] replaces() default {'ę', 'e', 'ó', 'o', 'ą', 'a', 'ś', 's', 'ł', 'l', 'ż', 'z', 'ź', 'z', 'ć', 'c', 'ń', 'n'};
    }
}
