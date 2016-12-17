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

package org.diorite.inject.data;

import javax.annotation.Nullable;
import javax.inject.Provider;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.diorite.inject.binder.DynamicProvider;
import org.diorite.inject.binder.qualifier.QualifierData;

public interface InjectValueData<T, GENERIC> extends QualifierData
{
    int getIndex();

    String getName();

    boolean isProvider();

    GENERIC getType();

    Map<Class<? extends Annotation>, ? extends Annotation> getScopes();

    Map<Class<? extends Annotation>, ? extends Annotation> getQualifiers();

    DynamicProvider<T> getProvider();

    void setProvider(@Nullable DynamicProvider<T> provider);

    @Nullable
    @SuppressWarnings({"unchecked", "rawtypes"})
    default T tryToGet(Object object)
    {
        if (this.isProvider())
        {
            Provider provider = () -> this.getProvider().tryToGet(object, this);
            return (T) provider;
        }
        return this.getProvider().tryToGet(object, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    default <A extends Annotation> A getScope(Class<A> type)
    {
        return (A) this.getScopes().get(type);
    }

    @SuppressWarnings("unchecked")
    @Override
    default <A extends Annotation> A getQualifier(Class<A> type)
    {
        return (A) this.getQualifiers().get(type);
    }
}
