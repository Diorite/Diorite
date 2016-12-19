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

package org.diorite.inject.impl.data;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public interface FieldData<T, GENERIC> extends MemberData<GENERIC>
{
    InjectValueData<T, GENERIC> getValueData();

    @Override
    default <X> InjectValueData<?, GENERIC> getValueData(String name)
    {
        InjectValueData<T, GENERIC> valueData = this.getValueData();
        if (valueData.getName().equals(name))
        {
            return valueData;
        }
        return null;
    }

    @Override
    default <X> InjectValueData<T, GENERIC> getValueData(int index)
    {
        if (index != 1)
        {
            throw new IndexOutOfBoundsException();
        }
        return this.getValueData();
    }

    @Override
    default Collection<? extends InjectValueData<?, GENERIC>> getInjectValues(Predicate<InjectValueData<?, GENERIC>> predicate)
    {
        if (predicate.test(this.getValueData()))
        {
            return this.getInjectValues();
        }
        else
        {
            return Collections.emptyList();
        }
    }

    @Override
    default String getName()
    {
        return this.getValueData().getName();
    }

    @Override
    default Map<Class<? extends Annotation>, ? extends Annotation> getDeclaredScopes()
    {
        return this.getValueData().getScopes();
    }

    @Override
    default Map<Class<? extends Annotation>, ? extends Annotation> getDeclaredQualifiers()
    {
        return this.getValueData().getQualifiers();
    }

    @Override
    default Iterator<InjectValueData<?, GENERIC>> iterator()
    {
        Set<InjectValueData<?, GENERIC>> singleton = Collections.singleton(this.getValueData());
        return singleton.iterator();
    }
}
