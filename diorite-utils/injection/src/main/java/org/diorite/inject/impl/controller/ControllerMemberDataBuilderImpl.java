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

package org.diorite.inject.impl.controller;

import java.lang.annotation.Annotation;
import java.util.Map;

import net.bytebuddy.description.annotation.AnnotationSource;
import net.bytebuddy.description.type.TypeDescription;

class ControllerMemberDataBuilderImpl<T extends AnnotationSource> implements org.diorite.inject.AnnotatedMemberData<T, TypeDescription>
{
    private final TypeDescription                                        classType;
    private final String                                                 name;
    private final T                                                      member;
    private final Map<Class<? extends Annotation>, ? extends Annotation> rawAnnotations;

    ControllerMemberDataBuilderImpl(TypeDescription classType, String name, T member, Map<Class<? extends Annotation>, ? extends Annotation> rawAnnotations)
    {
        this.classType = classType;
        this.name = name;
        this.member = member;
        this.rawAnnotations = rawAnnotations;
    }

    @Override
    public TypeDescription getClassType()
    {
        return this.classType;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public T getMember()
    {
        return this.member;
    }

    @Override
    public Map<Class<? extends Annotation>, ? extends Annotation> getRawAnnotations()
    {
        return this.rawAnnotations;
    }
}
