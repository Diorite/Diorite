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

package org.diorite.inject.binder.qualifier;

import javax.annotation.Nullable;

import java.lang.annotation.Annotation;

/**
 * Represent something that can store qualifiers.
 */
public interface QualifierData
{
    /**
     * Returns scope annotation instance of given type, or null if this QualifierData does not store this type of scope.
     *
     * @param type
     *         type of scope annotation.
     * @param <T>
     *         type of scope.
     *
     * @return scope annotation instance or null.
     */
    @Nullable
    <T extends Annotation> T getScope(Class<T> type);

    /**
     * Returns qualifier annotation instance of given type, or null if this QualifierData does not store this type of qualifier.
     *
     * @param type
     *         type of qualifier annotation.
     * @param <T>
     *         type of qualifier.
     *
     * @return qualifier annotation instance or null.
     */
    @Nullable
    <T extends Annotation> T getQualifier(Class<T> type);

    /**
     * Returns scope annotation instance of given type, or throws error if this QualifierData does not store this type of scope.
     *
     * @param type
     *         type of scope annotation.
     * @param <T>
     *         type of scope.
     *
     * @return scope annotation instance or error.
     *
     * @throws IllegalArgumentException
     *         if this QualifierData does not store this type of scope.
     */
    default <T extends Annotation> T getScopeNotNull(Class<T> type)
    {
        T scope = this.getScope(type);
        if (scope == null)
        {
            throw new IllegalArgumentException("Unknown scope " + type);
        }
        return scope;
    }

    /**
     * Returns qualifier annotation instance of given type, or throws error if this QualifierData does not store this type of qualifier.
     *
     * @param type
     *         type of qualifier annotation.
     * @param <T>
     *         type of qualifier.
     *
     * @return qualifier annotation instance or error.
     *
     * @throws IllegalArgumentException
     *         if this QualifierData does not store this type of qualifier.
     */
    default <T extends Annotation> T getQualifierNotNull(Class<T> type)
    {
        T qualifier = this.getQualifier(type);
        if (qualifier == null)
        {
            throw new IllegalArgumentException("Unknown qualifier " + type);
        }
        return qualifier;
    }
}
