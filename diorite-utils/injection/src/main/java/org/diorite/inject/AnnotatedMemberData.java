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

package org.diorite.inject;

import javax.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Represent member of class that may use annotations.
 *
 * @param <M>
 *         type of member data descriptor.
 * @param <C>
 *         type of class data descriptor.
 */
public interface AnnotatedMemberData<M, C>
{
    /**
     * Returns class type descriptor.
     *
     * @return class type descriptor.
     */
    C getClassType();

    /**
     * Returns member descriptor.
     *
     * @return member descriptor.
     */
    M getMember();

    /**
     * Returns name of member.
     *
     * @return name of member.
     */
    String getName();

    /**
     * Returns all annotations of this member.
     *
     * @return all annotations of this member.
     */
    Map<Class<? extends Annotation>, ? extends Annotation> getRawAnnotations();

    /**
     * Returns annotation of given type from this member.
     *
     * @param type
     *         class of annotation.
     * @param <T>
     *         type of annotation.
     *
     * @return annotation instance or null if member does not contain given annotation.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    default <T extends Annotation> T getAnnotation(Class<T> type)
    {
        return (T) this.getRawAnnotations().get(type);
    }
}
