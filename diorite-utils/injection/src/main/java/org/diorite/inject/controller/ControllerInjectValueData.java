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
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.diorite.inject.binder.DynamicProvider;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.Generic;

class ControllerInjectValueData<T> implements org.diorite.inject.data.InjectValueData<T, TypeDescription.ForLoadedType.Generic>
{
    private final int                                                    index;
    private final String                                                 name;
    private final TypeDescription.ForLoadedType.Generic                  type;
    private final Map<Class<? extends Annotation>, ? extends Annotation> scopes;
    private final Map<Class<? extends Annotation>, ? extends Annotation> qualifiers;
    private final boolean                                                isProvider;

    private final AtomicReference<DynamicProvider<T>> provider = new AtomicReference<>(DefaultInjectionController.getNullDynamicProvider());

    ControllerInjectValueData(int index, String name, Generic type, Map<Class<? extends Annotation>, ? extends Annotation> scopes,
                              Map<Class<? extends Annotation>, ? extends Annotation> qualifiers)
    {
        this.index = index;
        this.name = name;
        this.type = type;
        this.scopes = scopes;
        this.qualifiers = qualifiers;
        this.isProvider = type.asErasure().equals(DefaultInjectionController.PROVIDER);
    }

    @Override
    public int getIndex()
    {
        return this.index;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public boolean isProvider()
    {
        return this.isProvider;
    }

    @Override
    public TypeDescription.ForLoadedType.Generic getType()
    {
        return this.type;
    }

    @Override
    public Map<Class<? extends Annotation>, ? extends Annotation> getScopes()
    {
        return this.scopes;
    }

    @Override
    public Map<Class<? extends Annotation>, ? extends Annotation> getQualifiers()
    {
        return this.qualifiers;
    }

    @Override
    public DynamicProvider<T> getProvider()
    {
        return this.provider.get();
    }

    @Override
    public void setProvider(DynamicProvider<T> provider)
    {
        if (provider == null)
        {
            provider = DefaultInjectionController.getNullDynamicProvider();
        }
        this.provider.set(provider);
    }
}
