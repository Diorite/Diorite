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

import javax.annotation.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.Validate;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.ReflectedProperty;
import org.diorite.config.Config;

public final class NestedNodesHelper
{
    private static final Map<CacheKey, ReflectedProperty<?>> propertyCache = new ConcurrentHashMap<>(200);

    private NestedNodesHelper() {}

    @SuppressWarnings("unchecked")
    @Nullable
    static Object get(Object object, LinkedList<String> path)
    {
        while (true)
        {
            if (path.isEmpty())
            {
                throw new IllegalArgumentException("Empty path given");
            }
            String current = path.removeFirst();
            try
            {
                Object val;
                if (object instanceof Config)
                {
                    val = ((Config) object).get(current);
                }
                else if (object instanceof Map)
                {
                    val = ((Map<Object, Object>) object).get(current);
                }
                else if (object instanceof List)
                {
                    val = ((List<Object>) object).get(Integer.parseInt(current));
                }
                else
                {
                    Class<?> type = object.getClass();
                    CacheKey cacheKey = new CacheKey(type, current);
                    ReflectedProperty<?> property = propertyCache.computeIfAbsent(cacheKey, k -> DioriteReflectionUtils.getReflectedProperty(current, type));
                    val = property.get(object);
                }
                if (path.isEmpty())
                {
                    return val;
                }
                Validate.notNull(val, "Value on: " + current + " in " + object + " is null");
                object = val;
            }
            catch (Exception e)
            {
                throw new IllegalStateException("Can't find property: " + current + " (" + path + ") in: " + object, e);
            }
        }
    }

    @Nullable
    public static Object get(Object object, String[] path)
    {
        if (path.length == 0)
        {
            throw new IllegalArgumentException("Empty path given");
        }
        LinkedList<String> list = new LinkedList<>();
        Collections.addAll(list, path);
        return get(object, list);
    }

    public static void set(Object object, String[] path, @Nullable Object newValue)
    {
        if (path.length == 0)
        {
            throw new IllegalArgumentException("Empty path given");
        }
        LinkedList<String> list = new LinkedList<>();
        Collections.addAll(list, path);
        set(object, list, newValue);
    }

    @Nullable
    public static Object remove(Object object, String[] path)
    {
        if (path.length == 0)
        {
            throw new IllegalArgumentException("Empty path given");
        }
        LinkedList<String> list = new LinkedList<>();
        Collections.addAll(list, path);
        return remove(object, list);
    }

    @SuppressWarnings("unchecked")
    static void set(Object object, LinkedList<String> path, @Nullable Object newValue)
    {
        String last = path.removeLast();
        Object preLast;
        if (path.isEmpty())
        {
            preLast = object;
        }
        else
        {
            preLast = get(object, path);
        }

        Validate.notNull(preLast);

        try
        {
            if (preLast instanceof Config)
            {
                ((Config) preLast).set(last, newValue);
            }
            else if (preLast instanceof Map)
            {
                ((Map<Object, Object>) preLast).put(last, newValue);
            }
            else if (preLast instanceof List)
            {
                List<Object> list = (List<Object>) preLast;
                int size = list.size();
                int index = Integer.parseInt(last);
                if (index == size)
                {
                    list.add(newValue);
                }
                else if (index > size)
                {
                    while (index > size++)
                    {
                        list.add(null);
                    }
                    list.add(newValue);
                }
                else
                {
                    list.set(index, newValue);
                }
            }
            else
            {
                Class<?> type = preLast.getClass();
                CacheKey cacheKey = new CacheKey(type, last);
                ReflectedProperty<?> property = propertyCache.computeIfAbsent(cacheKey, k -> DioriteReflectionUtils.getReflectedProperty(last, type));
                property.set(preLast, newValue);
            }
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Can't find property: " + last + " (" + path + ") in: " + preLast, e);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    static Object remove(Object object, LinkedList<String> path)
    {
        while (true)
        {
            if (path.isEmpty())
            {
                throw new IllegalArgumentException("Empty path given");
            }
            String current = path.removeFirst();
            try
            {
                Object val;
                if (object instanceof Config)
                {
                    val = ((Config) object).remove(current);
                }
                else if (object instanceof Map)
                {
                    val = ((Map<Object, Object>) object).remove(current);
                }
                else if (object instanceof List)
                {
                    val = ((List<Object>) object).remove(Integer.parseInt(current));
                }
                else
                {
                    throw new IllegalStateException("Can't remove class property!");
                }
                if (path.isEmpty())
                {
                    return val;
                }
                Validate.notNull(val, "Value on: " + current + " in " + object + " is null");
                object = val;
            }
            catch (Exception e)
            {
                throw new IllegalStateException("Can't find property: " + current + " (" + path + ") in: " + object, e);
            }
        }
    }
}
