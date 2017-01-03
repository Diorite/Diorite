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

package org.diorite.config.impl.actions;

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ConfigPropertyValue;

public class GetPropertyAction extends AbstractPropertyAction
{
    protected GetPropertyAction()
    {
        super("get", "get(?<property>[A-Z0-9].*)");
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        return (method.getReturnType() != void.class) && (parameters.length == 0);
    }

    @Override
    public Object perform(MethodInvoker method, ConfigPropertyValue<?> value, Object... args)
    {
        Object rawValue = value.getRawValue();
        Class<?> returnType = method.getReturnType();
        if (returnType.isPrimitive() && (rawValue == null))
        {
            if (returnType == boolean.class)
            {
                return false;
            }
            if (returnType == byte.class)
            {
                return (byte) 0;
            }
            if (returnType == short.class)
            {
                return (short) 0;
            }
            if (returnType == char.class)
            {
                return '\0';
            }
            if (returnType == int.class)
            {
                return 0;
            }
            if (returnType == long.class)
            {
                return 0L;
            }
            if (returnType == float.class)
            {
                return 0.0F;
            }
            if (returnType == double.class)
            {
                return 0.0;
            }
            throw new AssertionError("Unknown primitive: " + returnType);
        }
        return rawValue;
    }
}
