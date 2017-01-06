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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ConfigPropertyValue;
import org.diorite.config.impl.actions.AbstractPropertyAction;
import org.diorite.config.serialization.snakeyaml.YamlCollectionCreator;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AddToCollectionPropertyAction extends AbstractPropertyAction
{
    public AddToCollectionPropertyAction()
    {
        super("addToCollection", "(?:addTo|putIn)(?<property>[A-Z0-9].*)");
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        return (parameters.length == 1) || (parameters.length == 2);
    }

    @Override
    public Object perform(MethodInvoker method, ConfigPropertyValue value, Object... args)
    {
        Object rawValue = value.getRawValue();
        if (rawValue == null)
        {
            rawValue = YamlCollectionCreator.createCollection(value.getRawType(), 5);
        }
        if (rawValue instanceof Collection)
        {
            Collection<Object> collection = (Collection<Object>) rawValue;
            if (method.isVarArgs())
            {
                Object[] arg = (Object[]) args[0];
                return Collections.addAll(collection, arg);
            }
            return collection.add(args[0]);
        }
        if (rawValue instanceof Map)
        {
            Map<Object, Object> map = (Map<Object, Object>) rawValue;
            if (method.isVarArgs())
            {
                if (Entry.class.isAssignableFrom(method.getParameterTypes()[0]))
                {
                    Entry<Object, Object>[] arg = (Entry<Object, Object>[]) args[0];
                    for (Entry<Object, Object> entry : arg)
                    {
                        map.put(entry.getKey(), entry.getValue());
                    }
                    return arg.length != 0;
                }
                else
                {
                    Object[] arg = (Object[]) args[0];
                    if ((arg.length % 2) != 0)
                    {
                        throw new IllegalStateException("Expected key-value arguments in: " + method.getMethod() + " of " + value);
                    }
                    for (int i = 0; i < arg.length; i += 2)
                    {
                        map.put(arg[0], arg[1]);
                    }
                    return arg.length != 0;
                }
            }
            return map.put(args[0], args[1]);
        }
        throw new IllegalStateException("Expected collection on " + value);
    }
}
