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

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.config.annotations.Comment;

public class CreeperEntityData extends AbstractEntityData
{
    @Comment("also indent of comments is ignored.")
    boolean powered;

    protected CreeperEntityData(String name, int age, boolean special, boolean powered, @Nullable String displayName)
    {
        super(EntityType.CREEPER, name, age, special, displayName);
        this.powered = powered;
    }

    protected CreeperEntityData(DeserializationData data)
    {
        super(data);
        this.powered = data.getAsBoolean("powered");
    }

    @Override
    public void serialize(SerializationData data)
    {
        super.serialize(data);
        data.setFalseValue("no");
        data.setTrueValue("yes");
        data.add("powered", this.powered);
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof CreeperEntityData))
        {
            return false;
        }
        if (! super.equals(object))
        {
            return false;
        }
        CreeperEntityData that = (CreeperEntityData) object;
        return this.powered == that.powered;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), this.powered);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("powered", this.powered).toString();
    }
}
