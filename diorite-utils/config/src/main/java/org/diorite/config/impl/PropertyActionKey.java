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

package org.diorite.config.impl;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.config.ConfigPropertyAction;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.MethodSignature;

class PropertyActionKey implements ConfigPropertyActionInstance
{
    private final ConfigPropertyAction propertyAction;
    private final MethodSignature      methodSignature;

    private final int hashCode;

    PropertyActionKey(ConfigPropertyAction propertyAction, MethodSignature methodSignature)
    {
        this.propertyAction = propertyAction;
        this.methodSignature = methodSignature;

        this.hashCode = Objects.hash(this.propertyAction, this.methodSignature);
    }

    @Override
    public ConfigPropertyAction getPropertyAction()
    {
        return this.propertyAction;
    }

    @Override
    public MethodSignature getMethodSignature()
    {
        return this.methodSignature;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof PropertyActionKey))
        {
            return false;
        }
        PropertyActionKey that = (PropertyActionKey) object;
        return Objects.equals(this.propertyAction, that.propertyAction) && Objects.equals(this.methodSignature, that.methodSignature);
    }

    @Override
    public int hashCode()
    {
        return this.hashCode;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("propertyAction", this.propertyAction)
                                        .append("methodSignature", this.methodSignature).toString();
    }
}
