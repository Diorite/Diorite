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

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import net.bytebuddy.description.type.TypeDescription;

class ControllerClassData implements org.diorite.inject.impl.data.ClassData<TypeDescription.ForLoadedType.Generic>
{
    private       int                                index;
    private final TypeDescription                    type;
    private final List<ControllerMemberData<?>>      members;
    private final Collection<ControllerFieldData<?>> fields;
    private final Collection<ControllerMethodData>   methods;
    @Nullable
    private Collection<String> before = null;
    @Nullable
    private Collection<String> after  = null;

    @SuppressWarnings({"rawtypes", "unchecked"})
    ControllerClassData(TypeDescription type, ControllerMemberData... members)
    {
        this.type = type;
        this.members = List.of(members);
        Collection<ControllerFieldData<?>> fields = new ArrayList<>(members.length);
        Collection<ControllerMethodData> methods = new ArrayList<>(members.length);
        for (org.diorite.inject.impl.data.MemberData member : members)
        {
            if (member instanceof org.diorite.inject.impl.data.MethodData)
            {
                methods.add(((ControllerMethodData) member));
            }
            else
            {
                fields.add(((ControllerFieldData<?>) member));
            }
        }
        this.fields = List.of(fields.toArray(new ControllerFieldData[fields.size()]));
        this.methods = List.of(methods.toArray(new ControllerMethodData[methods.size()]));
    }

    public TypeDescription getType()
    {
        return this.type;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    @Override
    public String getName()
    {
        return this.type.getCanonicalName();
    }

    @Override
    public int getIndex()
    {
        return this.index;
    }

    @Override
    public Collection<ControllerFieldData<?>> getFields()
    {
        return this.fields;
    }

    @Override
    public Collection<ControllerMethodData> getMethods()
    {
        return this.methods;
    }

    @Override
    public List<ControllerMemberData<?>> getMembers()
    {
        return this.members;
    }

    @Override
    public void addBefore(String method)
    {
        if (this.before == null)
        {
            this.before = new HashSet<>(1);
        }
        this.before.add(method);
    }

    @Override
    public void addAfter(String method)
    {
        if (this.after == null)
        {
            this.after = new HashSet<>(1);
        }
        this.after.add(method);
    }

    @Override
    public Collection<String> getBefore()
    {
        if (this.before == null)
        {
            return Collections.emptySet();
        }
        return this.before;
    }

    @Override
    public Collection<String> getAfter()
    {
        if (this.after == null)
        {
            return Collections.emptySet();
        }
        return this.after;
    }
}
