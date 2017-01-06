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

package org.diorite.config.serialization;

import javax.annotation.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

abstract class AbstractDeserializationData implements DeserializationData
{
    protected final Serialization serialization;
    protected final Class<?>      type;

    private final Set<String> trueValues  = new HashSet<>(1);
    private final Set<String> falseValues = new HashSet<>(1);

    AbstractDeserializationData(Class<?> type, Serialization serialization)
    {
        this.type = type;
        this.serialization = serialization;
    }

    public Class<?> getType()
    {
        return this.type;
    }

    @Override
    public Serialization getSerializationInstance()
    {
        return this.serialization;
    }

    @Override
    public void addTrueValues(String... strings)
    {
        Collections.addAll(this.trueValues, strings);
    }

    @Override
    public void addFalseValues(String... strings)
    {
        Collections.addAll(this.falseValues, strings);
    }

    @Nullable
    Boolean toBool(String str)
    {
        Boolean bool = this.serialization.toBool(str);
        if (bool != null)
        {
            return bool;
        }
        if (this.trueValues.contains(str))
        {
            return true;
        }
        if (this.falseValues.contains(str))
        {
            return false;
        }
        return null;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("type", this.type).toString();
    }
}
