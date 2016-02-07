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
 * Allows define collection type of selected field.
 * Any type other than unknown will disable build-in type checks,
 * so yaml generation will be faster, but may fail if you don't use valid type.
 * <br>
 * If collection contais other collections (or maps or arrays) of primitive types, you can
 * select primitive type.
 * <br>
 * Ignored by fields that don't know how to use it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgCollectionType
{
    /**
     * @return {@link org.diorite.cfg.annotations.CfgCollectionType.CollectionType} for this field.
     */
    CollectionType value();

    /**
     * enum with all possible types.
     */
    enum CollectionType
    {
        /**
         * Default value, unknown type.
         */
        UNKNOWN,
        /**
         * Custom objects.
         */
        OBJECTS,
        /**
         * Strings, or other nested collections/maps/arrays contains strings.
         */
        STRINGS,
        /**
         * Primitives, or other nested collections/maps/arrays contains primitives.
         */
        PRIMITIVES,
        /**
         * Strings and/or primitives, or other nested collections/maps/arrays contains strings and/or primitives.
         */
        STRINGS_AND_PRIMITIVES
    }
}
