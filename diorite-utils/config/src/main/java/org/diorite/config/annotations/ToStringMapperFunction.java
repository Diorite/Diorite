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

package org.diorite.config.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.intellij.lang.annotations.Language;

/**
 * Used with {@link Mapped} to mark list properties in config interfaces that are saved as maps. <br>
 * {@link Mapped} Annotation needs to be placed on property get method, and <br>
 * {@link ToStringMapperFunction} on private mapping function, function that take list element and returns String - key of value in map. <br>
 *
 * {@link ToStringMapperFunction} can be also used for map values to annotate method that is used to change map key objects to strings. <br>
 * {@link ToKeyMapperFunction} should be then used to annotate method that change strings to map keys.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToStringMapperFunction
{
    /**
     * Returns groovy script that will be used to map object to string.
     *
     * @return groovy script that will be used to map object to string.
     */
    @Language(value = "groovy", prefix = "String toString(def x, Config config) {", suffix = "}")
    String value() default "";

    /**
     * Returns property name that use this mapping function. <br>
     * Optional if annotation is over property with value filled with groovy mapper.
     *
     * @return property name that use this mapping function.
     */
    String property() default "";
}
