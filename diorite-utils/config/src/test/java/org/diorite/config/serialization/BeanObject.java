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

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.config.annotations.Comment;

public class BeanObject
{
    private int               intProperty;
    private String            stringProperty;
    private List<String>      list;
    private List<String>      list2;
    @Comment("Weird map of int arrays:")
    private Map<int[], int[]> intMap;

    public int getIntProperty()
    {
        return this.intProperty;
    }

    public void setIntProperty(int intProperty)
    {
        this.intProperty = intProperty;
    }

    public String getStringProperty()
    {
        return this.stringProperty;
    }

    public void setStringProperty(String stringProperty)
    {
        this.stringProperty = stringProperty;
    }

    public Map<int[], int[]> getIntMap()
    {
        return this.intMap;
    }

    public void setIntMap(Map<int[], int[]> intMap)
    {
        this.intMap = intMap;
    }

    public List<String> getList()
    {
        return this.list;
    }

    public void setList(List<String> list)
    {
        this.list = list;
    }

    public List<String> getList2()
    {
        return this.list2;
    }

    public void setList2(List<String> list2)
    {
        this.list2 = list2;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof BeanObject))
        {
            return false;
        }
        BeanObject that = (BeanObject) object;
        return (this.intProperty == that.intProperty) &&
               Objects.equals(this.stringProperty, that.stringProperty) && Objects.deepEquals(this.intMap.keySet().toArray(), that.intMap.keySet().toArray())
               && Objects.deepEquals(this.intMap.values().toArray(), that.intMap.values().toArray());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.intProperty, this.stringProperty, this.intMap);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("intProperty", this.intProperty)
                                        .append("stringProperty", this.stringProperty).append("intMap", this.intMap).toString();
    }
}
