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

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.config.annotations.Comment;

@org.diorite.config.annotations.Serializable
public class MetaObject
{
    String    name;
    @Comment("value of meta")
    MetaValue value;

    public MetaObject(String name, MetaValue value)
    {
        this.name = name;
        this.value = value;
    }

    @org.diorite.config.annotations.Serializable
    public static void ser(MetaObject metaObject, SerializationData data)
    {
        data.add("name", metaObject.name);
        data.add("value", metaObject.value);
    }

    @org.diorite.config.annotations.Serializable
    public static MetaObject deser(DeserializationData data)
    {
        return new MetaObject(data.getOrThrow("name", String.class), data.getOrThrow("value", MetaValue.class));
    }

    @Comment("name of meta")
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public MetaValue getValue()
    {
        return this.value;
    }

    @Comment("value of meta")
    public void setValue(MetaValue value)
    {
        this.value = value;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof MetaObject))
        {
            return false;
        }
        MetaObject that = (MetaObject) object;
        return Objects.equals(this.name, that.name) &&
               Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.name, this.value);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("name", this.name).append("value", this.value)
                                        .toString();
    }
}
