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

package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows define string style of selected field.
 * Defining style may speed-up yaml generation, but it
 * may also cause problems if style isn't compatyble with used data.
 * <br>
 * Ignored by fields that don't know how to use it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgStringStyle
{
    /**
     * @return {@link org.diorite.cfg.annotations.CfgStringStyle.StringStyle} for this field.
     */
    StringStyle value();

    /**
     * enum with all possible styles.
     */
    enum StringStyle
    {
        /**
         * Default style, template will automagically choose best option.
         * (it must scan all elements, so it will be slower)
         */
        DEFAULT,
        /**
         * Always use double quotes in strings, will cause problems with
         * multi-lined strings.
         * <br>
         * node: "value of node"
         */
        ALWAYS_QUOTED,
        /**
         * Always use single quotes in strings, will cause problems with
         * multi-lined strings.
         * <br>
         * node: 'value of node'
         */
        ALWAYS_SINGLE_QUOTED,
        /**
         * Always use multi-lined style for strings, don't cause any problems.
         * <br>
         * <pre>node: |2-
         *   Some multi line
         *   string ;)</pre>
         */
        ALWAYS_MULTI_LINE
    }
}
