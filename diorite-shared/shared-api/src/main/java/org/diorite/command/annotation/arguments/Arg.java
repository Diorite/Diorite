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

import org.intellij.lang.annotations.Language;

/**
 * Represents argument parameters.
 */
@SuppressWarnings("rawtypes")
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Arg
{
    /**
     * Select mappers for this argument, some custom argument types might support multiple mappers, like player by name or player by uuid. <br>
     * By specifying more than one mapper id user can use both, like use both uuid or player name.
     *
     * @return mappers to use.
     */
    String[] mappers() default {};

    /**
     * If value is empty method parameter name will be used. <br>
     * Only used when {@link #named()} or {@link #flag()} is true.
     *
     * @return name and aliases of argument.
     */
    String[] value() default {};

    /**
     * Set to true to mark this argument as a named argument. <br>
     * Named arguments can be used in any order, but must contains name and separator before each value, like: player=GotoFinal
     *
     * @return if argument was marked as a named argument.
     */
    boolean named() default false;

    /**
     * Set to true to mark this argument as a flag argument. <br>
     * Flags are used for simple arguments that can be used in any place of command line. <br>
     * Examples: <br>
     * /command -abc args args args - first diorite will look for 3 separate boolean flags: `a`, `b` and `c` and set them to true, if diorite can't find that
     * flags then it will look for `abc` flag, if it exist and it is boolean type, then it will be set to true, if type is different than boolean then first
     * argument after flag will ba parsed as flag value.
     * /command --abc args args args - diorite will only look for `abc` flag.
     *
     * @return if argument was marked as a flag argument.
     */
    boolean flag() default false;

    /**
     * TODO: not sure if implement. (might be too resource consuming or too hard to handle)
     * Main argument can be repeated to invoke command multiple times with different main arguments by using array instead of singe argument (/tell
     * [playerA,playerB] message), each command can have only one main argument, if main argument isn't selected, diorite will select a argument based on type
     * and priority. (most of the time first Player argument is selected.)
     *
     * @return true if argument was marked as a main argument.
     */
    boolean main() default false;

    /**
     * Separator used to split name from value.
     *
     * @return separator used to split name from value.
     */
    char[] nameSeparator() default {'=', ':'};

    /**
     * Default value of argument as groovy expression.
     *
     * @return default value of argument as groovy expression.
     */
    @Language("groovy")
    String def() default "";
}
