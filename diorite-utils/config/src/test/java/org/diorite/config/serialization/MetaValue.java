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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.commons.math.DioriteMathUtils;

@org.diorite.config.annotations.StringSerializable
public class MetaValue
{
    String value;
    int    power;

    public MetaValue(String value, int power)
    {
        this.value = value;
        this.power = power;
    }

    @org.diorite.config.annotations.StringSerializable
    public static MetaValue deserialize(String str)
    {
        String[] split = StringUtils.split(str, ':');
        return new MetaValue(split[0], DioriteMathUtils.asInt(split[1], - 1));
    }

    @org.diorite.config.annotations.StringSerializable
    public String serializeToString()
    {
        return this.value + ":" + this.power;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof MetaValue))
        {
            return false;
        }
        MetaValue metaValue = (MetaValue) object;
        return this.power == metaValue.power &&
               Objects.equals(this.value, metaValue.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.value, this.power);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("value", this.value).append("power", this.power)
                                        .append("serializeToString", this.serializeToString()).toString();
    }
}
