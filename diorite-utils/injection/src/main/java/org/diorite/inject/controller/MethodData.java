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

package org.diorite.inject.controller;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.bytebuddy.description.method.MethodDescription.InDefinedShape;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.method.ParameterList;
import net.bytebuddy.description.type.TypeDescription;

class MethodData extends MemberData<InDefinedShape> implements org.diorite.inject.data.MethodData<TypeDescription.ForLoadedType.Generic>
{
    private final List<org.diorite.inject.data.InjectValueData<?, TypeDescription.ForLoadedType.Generic>> values;
    private final Map<Class<? extends Annotation>, ? extends Annotation>                                  scopeAnnotations;
    private final Map<Class<? extends Annotation>, ? extends Annotation>                                  qualifierAnnotations;

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected MethodData(DefaultInjectionController controller, TypeDescription.ForLoadedType classType, InDefinedShape member, String name, int index)
    {
        super(controller, classType, member, name, index);
        Map<Class<? extends Annotation>, ? extends Annotation> rawScopeAnnotations = controller.extractRawScopeAnnotations(member);
        Map<Class<? extends Annotation>, ? extends Annotation> rawQualifierAnnotations = controller.extractRawQualifierAnnotations(member);
        this.scopeAnnotations = controller.transformAll(this.classType, name, member, rawScopeAnnotations);
        this.qualifierAnnotations = controller.transformAll(this.classType, name, member, rawQualifierAnnotations);
        ParameterList<ParameterDescription.InDefinedShape> parameters = member.getParameters();
        org.diorite.inject.data.InjectValueData<?, TypeDescription.ForLoadedType.Generic>[] values = new org.diorite.inject.data.InjectValueData[parameters.size()];
        int i = 0;
        for (ParameterDescription.InDefinedShape param : parameters)
        {
            values[i] = controller.createValue(i, classType, param.getType(), param, DefaultInjectionController.fixName(param.getType(), param.getName()),
                                               rawScopeAnnotations,
                                               rawQualifierAnnotations);
            i++;
        }
        this.values = List.of(values);
    }

    @Override
    public Map<Class<? extends Annotation>, ? extends Annotation> getDeclaredScopes()
    {
        return this.scopeAnnotations;
    }

    @Override
    public Map<Class<? extends Annotation>, ? extends Annotation> getDeclaredQualifiers()
    {
        return this.qualifierAnnotations;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> InjectValueData<T> getValueData(int index)
    {
        return (InjectValueData<T>) this.values.get(index);
    }

    @Override
    public Collection<? extends org.diorite.inject.data.InjectValueData<?, TypeDescription.ForLoadedType.Generic>> getInjectValues()
    {
        return this.values;
    }

    @Override
    public InjectValueDataIterator iterator()
    {
        return new InjectValueDataIterator(this.values.iterator());
    }

    static class InjectValueDataIterator implements Iterator<org.diorite.inject.data.InjectValueData<?, TypeDescription.ForLoadedType.Generic>>
    {
        private final Iterator<org.diorite.inject.data.InjectValueData<?, TypeDescription.ForLoadedType.Generic>> iterator;

        InjectValueDataIterator(Iterator<org.diorite.inject.data.InjectValueData<?, TypeDescription.ForLoadedType.Generic>> iterator)
        {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext()
        {
            return this.iterator.hasNext();
        }

        @Override
        public InjectValueData<?> next()
        {
            return (InjectValueData<?>) this.iterator.next();
        }

    }
}
