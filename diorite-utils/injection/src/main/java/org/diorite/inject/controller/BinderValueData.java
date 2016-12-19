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

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import org.diorite.inject.Provider;
import org.diorite.inject.binder.DynamicProvider;
import org.diorite.inject.binder.qualifier.QualifierPattern;
import org.diorite.inject.data.InjectValueData;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.Generic;

abstract class BinderValueData implements Comparable<BinderValueData>
{
    private final Predicate<TypeDescription.Generic> typePredicate;
    private final Collection<QualifierPattern>       qualifiers;

    BinderValueData(Predicate<TypeDescription.Generic> typePredicate, Collection<QualifierPattern> qualifiers)
    {
        this.typePredicate = typePredicate;
        this.qualifiers = qualifiers;
    }

    public boolean isCompatible(InjectValueData<?, Generic> injectValueData)
    {
        if (! this.typePredicate.test(injectValueData.getType()))
        {
            return false;
        }
        for (QualifierPattern qualifier : this.qualifiers)
        {
            if (! qualifier.test(injectValueData))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof BinderValueData))
        {
            return false;
        }
        BinderValueData that = (BinderValueData) object;
        return Objects.equals(this.typePredicate, that.typePredicate) &&
               Objects.equals(this.qualifiers, that.qualifiers);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.typePredicate, this.qualifiers);
    }

    @Override
    public int compareTo(BinderValueData o)
    {
        int compare = Integer.compare(o.qualifiers.size(), this.qualifiers.size());
        if (compare != 0)
        {
            return compare;
        }
        compare = Integer.compare(System.identityHashCode(this), System.identityHashCode(o));
        if (compare != 0)
        {
            return compare;
        }
        return 1;
    }

    public abstract <T> DynamicProvider<T> getProvider();

    static BinderValueData simple(Predicate<Generic> typePredicate, Collection<QualifierPattern> qualifiers, Provider<?> provider)
    {
        return new BinderSimpleValueData(typePredicate, qualifiers, provider);
    }

    static BinderValueData dynamic(Predicate<Generic> typePredicate, Collection<QualifierPattern> qualifiers, DynamicProvider<?> provider)
    {
        return new BinderDynamicValueData(typePredicate, qualifiers, provider);
    }

}
