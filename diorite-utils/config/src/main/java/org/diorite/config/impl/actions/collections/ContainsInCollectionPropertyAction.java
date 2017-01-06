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

package org.diorite.config.impl.actions.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ConfigPropertyValue;
import org.diorite.config.impl.actions.AbstractPropertyAction;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ContainsInCollectionPropertyAction extends AbstractPropertyAction
{
    public ContainsInCollectionPropertyAction()
    {
        super("containsInCollection", "(?:contains(?:Key|)(?:In|)|isIn)(?<property>[A-Z0-9].*)", "(?:contains(?:In|)|isIn)(?<property>[A-Z0-9].*)",
              "(?:contains|isIn)(?<property>[A-Z0-9].*)");
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        if (parameters.length != 1)
        {
            return false;
        }
        Class<?> returnType = method.getReturnType();
        return (returnType == boolean.class);
    }

    @Override
    public Object perform(MethodInvoker method, ConfigPropertyValue value, Object... args)
    {
        Object rawValue = value.getRawValue();
        if (rawValue == null)
        {
            return false;
        }
        if (rawValue instanceof Collection)
        {
            Collection<Object> collection = (Collection<Object>) rawValue;
            if (method.isVarArgs())
            {
                Object[] arg = (Object[]) args[0];
                return collection.containsAll(Arrays.asList(arg));
            }
            return collection.contains(args[0]);
        }
        if (rawValue instanceof Map)
        {
            Map<Object, Object> map = (Map<Object, Object>) rawValue;
            if (method.isVarArgs())
            {
                Object[] arg = (Object[]) args[0];
                if (arg.length == 0)
                {
                    return false;
                }
                for (Object o : arg)
                {
                    if (! map.containsKey(o))
                    {
                        return false;
                    }
                }
                return true;
            }
            return map.containsKey(args[0]);
        }
        throw new IllegalStateException("Expected collection on " + value);
    }
}
