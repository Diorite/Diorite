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

import javax.annotation.Nonnull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ConfigPropertyValue;
import org.diorite.config.impl.actions.AbstractPropertyAction;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RemoveFromCollectionIfPropertyAction extends AbstractPropertyAction
{
    public RemoveFromCollectionIfPropertyAction()
    {
        super("removeFromCollectionIf", "removeFrom(?<property>[A-Z0-9].*?)If", "remove(?<property>[A-Z0-9].*?)If");
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        if (parameters.length != 1)
        {
            return false;
        }
        if (method.isVarArgs())
        {
            return false;
        }
        Class<?> parameter = parameters[0];
        if (! Predicate.class.isAssignableFrom(parameter))
        {
            return false;
        }
        Class<?> returnType = method.getReturnType();
        return (returnType == void.class) || (returnType == boolean.class);
    }

    @Nonnull
    @Override
    public Boolean perform(MethodInvoker method, ConfigPropertyValue value, Object... args)
    {
        Object rawValue = value.getRawValue();
        if (rawValue == null)
        {
            return false;
        }

        Predicate<Object> predicate = (Predicate<Object>) args[0];
        if (rawValue instanceof Collection)
        {
            Collection<Object> collection = (Collection<Object>) rawValue;
            return collection.removeIf(predicate);
        }
        if (rawValue instanceof Map)
        {
            Map<Object, Object> map = (Map<Object, Object>) rawValue;
            boolean any = false;
            for (Iterator<Entry<Object, Object>> iterator = map.entrySet().iterator(); iterator.hasNext(); )
            {
                Entry<Object, Object> entry = iterator.next();
                if (predicate.test(entry))
                {
                    iterator.remove();
                    any = true;
                }
            }
            return any;
        }
        throw new IllegalStateException("Expected collection on " + value);
    }
}
