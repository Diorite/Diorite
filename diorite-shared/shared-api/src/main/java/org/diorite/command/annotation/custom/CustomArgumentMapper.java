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

package org.diorite.command.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

import org.diorite.command.parser.ParsersManager;
import org.diorite.command.parser.TypeParser;

/**
 * Used to annotate method that can be used to fetch String representation of given object, or to get object representation from string. <br>
 * For more advanced usage manual registration should be used: {@link ParsersManager#createParser(TypeParser, Class, Function)} <br>
 * <pre>
 * Each custom argument should be able to perform 3 operations:
 * <ul>
 *     <li>Fetch list of all possible values and their names. (optional but auto-complete will not work without it)</li>
 *     <li>Convert String value to valid argument Object.</li>
 *     <li>Convert argument Object to String representation.</li>
 * </ul>
 * Annotation can be used over:
 *  <ul>
 *      <li>Object method that returns string representation.</li>
 *      <li>Object field of String type.</li>
 *      <li>Static method with single String argument that returns argument Object.</li>
 *      <li>Static field of {@code Map<String, ArgumentObject>} type. (it will be used for both fetching all possible values and converting string to
 * object)</li>
 *      <li>Static method without arguments that returns {@code Map<String, ArgumentObject>} type. (it will be used for both fetching all possible values and
 * converting string to object)</li>
 *      <li>Static field of {@code Collection<ArgumentObject>} type.</li>
 *      <li>Static method without arguments that returns {@code Collection<ArgumentObject>} type.</li>
 *      <li>Static method with single String argument that returns {@code Collection<ArgumentObject>} type. - collection of elements of which keys starts with
 * given string. (might be used to improve performance of tab-complete lookup for large collections or database fetched data)</li>
 * </ul>
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomArgumentMapper
{
    /**
     * Optional way to specify type of argument if it cannot be automatically recognized.
     *
     * @return type of custom argument.
     */
    Class<?> value() default DefaultType.class;

    /**
     * Custom arguments can be mapped by more than one factor, like player can be mapped by name and uuid, specifying name is optional, parser will try to fetch
     * name from method/field name.
     *
     * @return name of mapper.
     */
    String id() default "";
}
