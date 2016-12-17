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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public interface ClassData<GENERIC>
{
    int getIndex();

    Collection<? extends FieldData<?, GENERIC>> getFields();

    Collection<? extends MethodData<GENERIC>> getMethods();

    List<? extends MemberData<GENERIC>> getMembers();

    void addBefore(String method);

    void addAfter(String method);

    Collection<String> getBefore();

    Collection<String> getAfter();

    default <T> FieldData<T, GENERIC> getField(int index)
    {
        return (FieldData<T, GENERIC>) this.getMembers().get(index);
    }

    default MethodData<GENERIC> getMethod(int index)
    {
        return (MethodData<GENERIC>) this.getMembers().get(index);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    default <T> FieldData<T, GENERIC> getField(String name)
    {
        for (FieldData<?, GENERIC> fieldData : this.getFields())
        {
            if (fieldData.getName().equals(name))
            {
                return (FieldData<T, GENERIC>) fieldData;
            }
        }
        return null;
    }

    @Nullable
    default MethodData<GENERIC> getMethod(String name)
    {
        for (MethodData<GENERIC> methodData : this.getMethods())
        {
            if (methodData.getName().equals(name))
            {
                return methodData;
            }
        }
        return null;
    }

    @Nullable
    default MemberData<?> getMember(String name)
    {
        for (MemberData<?> memberData : this.getMembers())
        {
            if (memberData.getName().equals(name))
            {
                return memberData;
            }
        }
        return null;
    }

    default Collection<? extends FieldData<?, GENERIC>> getFields(Predicate<FieldData<?, GENERIC>> predicate)
    {
        Collection<? extends FieldData<?, GENERIC>> fields = this.getFields();
        Collection<FieldData<?, GENERIC>> result = new LinkedList<>();
        for (FieldData<?, GENERIC> fieldData : fields)
        {
            if (predicate.test(fieldData))
            {
                result.add(fieldData);
            }
        }
        return result;
    }

    default Collection<? extends MethodData<GENERIC>> getMethods(Predicate<MethodData<GENERIC>> predicate)
    {
        Collection<? extends MethodData<GENERIC>> methods = this.getMethods();
        Collection<MethodData<GENERIC>> result = new LinkedList<>();
        for (MethodData<GENERIC> methodData : methods)
        {
            if (predicate.test(methodData))
            {
                result.add(methodData);
            }
        }
        return result;
    }

    default Collection<? extends MemberData<?>> getMembers(Predicate<MemberData<?>> predicate)
    {
        Collection<? extends MemberData<?>> members = this.getMembers();
        Collection<MemberData<?>> result = new LinkedList<>();
        for (MemberData<?> memberData : members)
        {
            if (predicate.test(memberData))
            {
                result.add(memberData);
            }
        }
        return result;
    }

    default Collection<? extends InjectValueData<?, GENERIC>> getInjectValues(Predicate<InjectValueData<?, GENERIC>> predicate)
    {
        List<? extends MemberData<GENERIC>> members = this.getMembers();
        Collection<InjectValueData<?, GENERIC>> result = new LinkedList<>();
        for (MemberData<GENERIC> member : members)
        {
            for (InjectValueData<?, GENERIC> valueData : member)
            {
                if (predicate.test(valueData))
                {
                    result.add(valueData);
                }
            }
        }
        return result;
    }
}
