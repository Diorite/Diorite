/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.commons.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.diorite.commons.arrays.DioriteArrayUtils;

final class DynamicEnumType<T extends DynamicEnum<T>>
{
    private static final Map<Class<?>, DynamicEnumType<?>> types = new ConcurrentHashMap<>(20);

    final Map<String, T> elementsMap = Collections.synchronizedMap(new HashMap<>(10, 0.1f));
    private final Class<T> type;
    T[] values;
    volatile int counter;

    DynamicEnumType(Class<T> type)
    {
        this.type = type;
        this.values = DioriteArrayUtils.newObjectArray(type, 0);
    }

    synchronized void addElement(T element, String name)
    {
        element.name = name;
        element.ordinal = this.counter++;
        this.addElement(element);
    }

    synchronized void addElement(T element)
    {
        int oldLength = this.values.length;
        if (element.ordinal != oldLength)
        {
            throw new IllegalStateException("Expected enum element of id: " + oldLength + " but got: " + element.ordinal);
        }
        T[] newValues = DioriteArrayUtils.newObjectArray(this.type, oldLength + 1);
        System.arraycopy(this.values, 0, newValues, 0, oldLength);
        newValues[oldLength] = element;
        element.ordinal = oldLength;
        this.values = newValues;
        this.elementsMap.put(element.name, element);
    }

    @SuppressWarnings("unchecked")
    static <T extends DynamicEnum<T>> DynamicEnumType<T> getDynamicEnumType(Class<T> type)
    {
        DynamicEnumType<T> enumType = (DynamicEnumType<T>) types.get(type);
        if (enumType != null)
        {
            return enumType;
        }
        enumType = new DynamicEnumType<>(type);
        types.putIfAbsent(type, enumType);
        return enumType;
    }
}
