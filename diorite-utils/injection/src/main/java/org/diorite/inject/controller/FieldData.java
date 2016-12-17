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
import java.util.Collections;
import java.util.List;

import net.bytebuddy.description.field.FieldDescription.InDefinedShape;
import net.bytebuddy.description.type.TypeDescription;

class FieldData<T> extends MemberData<InDefinedShape> implements org.diorite.inject.data.FieldData<T, TypeDescription.ForLoadedType.Generic>
{
    private final InjectValueData<T>             value;
    private final Collection<InjectValueData<T>> collection;

    protected FieldData(DefaultInjectionController controller, TypeDescription.ForLoadedType classType, InDefinedShape member, String name, int index)
    {
        super(controller, classType, member, name, index);
        this.value = controller.createValue(0, classType, member.getType(), member, name, Collections.emptyMap(), Collections.emptyMap());
        this.collection = List.of(this.value);
    }

    @Override
    public Collection<? extends InjectValueData<?>> getInjectValues()
    {
        return this.collection;
    }

    @Override
    public InjectValueData<T> getValueData()
    {
        return this.value;
    }
}
