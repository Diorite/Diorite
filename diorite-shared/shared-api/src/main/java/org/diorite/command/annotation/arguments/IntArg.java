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

/**
 * Byte/Short/Integer/Long argument settings.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntArg
{
    /**
     * Basic argument settings.
     *
     * @return basic argument settings.
     */
    Arg value() default @Arg;

    /**
     * Expected radix of value.
     *
     * @return expected radix of value.
     */
    int radix() default 10;

    /**
     * Minimal value of number, multiple values can be used along with {@link #max()} setting to create multiple valid ranges.
     *
     * @return minimal value of number.
     */
    long[] min() default Long.MIN_VALUE;

    /**
     * Maximal value of number, multiple values can be used along with {@link #min()} setting to create multiple valid ranges.
     *
     * @return maximal value of number.
     */
    long[] max() default Long.MAX_VALUE;

    /**
     * Array of invalid values.
     *
     * @return array of invalid values.
     */
    long[] invalidValues() default {};

    /**
     * Array of valid values, if empty then all values are allowed.
     *
     * @return array of valid values.
     */
    long[] validValues() default {};
}
