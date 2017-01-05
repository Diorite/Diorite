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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ConfigPropertyValue;
import org.diorite.config.impl.actions.AbstractPropertyAction;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RemoveFromCollectionPropertyAction extends AbstractPropertyAction
{
    public RemoveFromCollectionPropertyAction()
    {
        super("removeFromCollection", "removeFrom(?<property>[A-Z0-9].*)");
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        return parameters.length == 1;
    }

    @Override
    public Object perform(MethodInvoker method, ConfigPropertyValue value, Object... args)
    {
        Object rawValue = value.getRawValue();
        if (rawValue == null)
        {
            if (Collection.class.isAssignableFrom(value.getProperty().getRawType()))
            {
                return false;
            }
            return null;
        }
        if (rawValue instanceof Collection)
        {
            Collection<Object> collection = (Collection<Object>) rawValue;
            if (method.isVarArgs())
            {
                Object[] arg = (Object[]) args[0];
                return collection.removeAll(Arrays.asList(arg));
            }
            return collection.remove(args[0]);
        }
        if (rawValue instanceof Map)
        {
            Map<Object, Object> map = (Map<Object, Object>) rawValue;
            if (method.isVarArgs())
            {
                Object[] arg = (Object[]) args[0];
                if (arg.length == 0)
                {
                    return Collections.emptyList();
                }
                List<Object> removed = new ArrayList<>(arg.length);
                for (Object anArg : arg)
                {
                    removed.add(map.remove(anArg));
                }
                return removed;
            }
            return map.remove(args[0]);
        }
        throw new IllegalStateException("Expected collection on " + value);
    }
}
