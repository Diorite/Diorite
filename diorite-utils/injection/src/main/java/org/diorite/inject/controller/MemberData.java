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

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;

abstract class MemberData<B extends ByteCodeElement & NamedElement> implements org.diorite.inject.data.MemberData<TypeDescription.ForLoadedType.Generic>
{
    protected final TypeDescription.ForLoadedType classType;
    protected final B                             member;
    protected final String                        name;
    protected final int                           index;
    @Nullable protected Collection<String> before = null;
    @Nullable protected Collection<String> after  = null;

    protected MemberData(DefaultInjectionController controller, TypeDescription.ForLoadedType classType, B member, String name, int index)
    {
        this.classType = classType;
        this.member = member;
        this.name = name;
        this.index = index;
    }

    public TypeDescription.ForLoadedType getClassType()
    {
        return this.classType;
    }

    public B getMember()
    {
        return this.member;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public int getIndex()
    {
        return this.index;
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
