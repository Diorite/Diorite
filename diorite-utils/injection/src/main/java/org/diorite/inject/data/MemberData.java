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

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Predicate;

public interface MemberData<GENERIC> extends Iterable<InjectValueData<?, GENERIC>>
{
    String getName();

    int getIndex();

    <T> InjectValueData<?, GENERIC> getValueData(String name);

    <T> InjectValueData<?, GENERIC> getValueData(int index);

    Collection<? extends InjectValueData<?, GENERIC>> getInjectValues();

    void addBefore(String method);

    void addAfter(String method);

    Collection<String> getBefore();

    Collection<String> getAfter();

    Map<Class<? extends Annotation>, ? extends Annotation> getDeclaredScopes();

    Map<Class<? extends Annotation>, ? extends Annotation> getDeclaredQualifiers();

    default Collection<? extends InjectValueData<?, GENERIC>> getInjectValues(Predicate<InjectValueData<?, GENERIC>> predicate)
    {
        Collection<InjectValueData<?, GENERIC>> result = new LinkedList<>();
        for (InjectValueData<?, GENERIC> valueData : this.getInjectValues())
        {
            if (predicate.test(valueData))
            {
                result.add(valueData);
            }
        }
        return result;
    }

    default <A extends Annotation> A getDeclaredScope(Class<A> type)
    {
        return (A) this.getDeclaredScopes().get(type);
    }

    default <A extends Annotation> A getDeclaredQualifier(Class<A> type)
    {
        return (A) this.getDeclaredQualifiers().get(type);
    }
}
