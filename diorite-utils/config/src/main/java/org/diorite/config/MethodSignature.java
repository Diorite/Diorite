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

package org.diorite.config;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MethodSignature
{
    private final String     name;
    private final Class<?>[] arguments;
    private final Class<?>   returnType;

    private final int hashCode;

    public MethodSignature(String name, Class<?>[] arguments, Class<?> returnType)
    {
        this.name = name;
        this.arguments = arguments;
        this.returnType = returnType;

        this.hashCode = Objects.hash(this.name, this.returnType) + Arrays.hashCode(this.arguments);
    }

    public MethodSignature(Method method)
    {
        this.name = method.getName();
        this.arguments = method.getParameterTypes();
        this.returnType = method.getReturnType();

        this.hashCode = Objects.hash(this.name, this.returnType) + Arrays.hashCode(this.arguments);
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof MethodSignature))
        {
            return false;
        }
        MethodSignature that = (MethodSignature) object;
        return Objects.equals(this.name, that.name) &&
               Arrays.equals(this.arguments, that.arguments) &&
               Objects.equals(this.returnType, that.returnType);
    }

    @Override
    public int hashCode()
    {
        return this.hashCode;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("name", this.name).append("returnType", this.returnType)
                                        .append("arguments", this.arguments).toString();
    }
}
