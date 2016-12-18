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

package org.diorite.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.diorite.inject.asm.QualifierAndScopeImplementationGenerator;

public final class Qualifiers
{
    private static final Map<Class<? extends Annotation>, Function<Map<String, ?>, ? extends Annotation>>       constructors = new ConcurrentHashMap<>(20);
    private static final Map<Class<? extends Annotation>, Function<? extends Annotation, ? extends Annotation>> unproxy      = new ConcurrentHashMap<>(20);

    private Qualifiers()
    {
    }

    /**
     * Register new annotation constructor function.
     *
     * @param type
     *         type of annotation, must be annotated with {@link Qualifier} or {@link Scope}
     * @param constructor
     *         constructor function.
     * @param <T>
     *         type of annotation
     */
    public static <T extends Annotation> void register(Class<T> type, Function<Map<String, ?>, T> constructor, Function<T, T> unproxy)
    {
        if (! type.isAnnotation())
        {
            throw new IllegalStateException("Given class isn't annotation class! Found " + type + " instead!");
        }
        if (! (type.isAnnotationPresent(Qualifier.class) || type.isAnnotationPresent(Scope.class)))
        {
            throw new IllegalStateException("Given annotation isn't annotated with 'org.diorite.inject.Qualifier' or 'org.diorite.inject.Scope' annotation!");
        }
        constructors.put(type, constructor);
        Qualifiers.unproxy.put(type, unproxy);
    }

    private static Function<? extends Annotation, ? extends Annotation> getUnproxyFunction(Class<? extends Annotation> type)
    {
        Function<? extends Annotation, ? extends Annotation> constructor = unproxy.get(type);
        if (constructor != null)
        {
            return constructor;
        }
        Class<? extends Annotation> transform = QualifierAndScopeImplementationGenerator.transform(type);
        if (transform == null)
        {
            throw new IllegalArgumentException("Given class isn't valid qualifier annotation: " + type);
        }
        forceInit(transform);
        return unproxy.get(type);
    }

    private static Function<Map<String, ?>, ? extends Annotation> getConstructor(Class<? extends Annotation> type)
    {
        Function<Map<String, ?>, ? extends Annotation> constructor = constructors.get(type);
        if (constructor != null)
        {
            return constructor;
        }
        Class<? extends Annotation> transform = QualifierAndScopeImplementationGenerator.transform(type);
        if (transform == null)
        {
            throw new IllegalArgumentException("Given class isn't valid qualifier annotation: " + type);
        }
        forceInit(transform);
        return constructors.get(type);
    }

    private static <T> Class<T> forceInit(Class<T> clazz)
    {
        try
        {
            Class.forName(clazz.getName(), true, clazz.getClassLoader());
        }
        catch (ClassNotFoundException e)
        {
            throw new AssertionError(e);  // Can't happen
        }
        return clazz;
    }

    /**
     * Convert java proxy annotation to specialized implementation, used to speedup equals/hashcode/toString methods.
     *
     * @param annotation
     *         annotation to unproxy.
     * @param <T>
     *         type of annotation.
     *
     * @return specialized annotation implementation instance.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends Annotation> T unproxy(T annotation)
    {
        Function unproxyFunction = getUnproxyFunction(annotation.annotationType());
        return (T) unproxyFunction.apply(annotation);
    }

    /**
     * Create annotation instance with given values.
     *
     * @param type
     *         type of annotation, must be annotated with {@link Qualifier} or {@link Scope}
     * @param data
     *         annotation values.
     * @param <T>
     *         type of annotation.
     *
     * @return annotation instance with given values.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T of(Class<T> type, Map<String, ?> data)
    {
        Function<Map<String, ?>, ? extends Annotation> constructor = getConstructor(type);
        return (T) constructor.apply(data);
    }

    /**
     * Create annotation instance with given values.
     *
     * @param type
     *         type of annotation, must be annotated with {@link Qualifier} or {@link Scope}
     * @param value
     *         value of annotation.
     * @param <T>
     *         type of annotation.
     *
     * @return annotation instance with given values.
     */
    public static <T extends Annotation> T of(Class<T> type, Object value)
    {
        Method best = null;
        for (Method method : type.getDeclaredMethods())
        {
            String methodName = method.getName();
            Object def = method.getDefaultValue();
            if (methodName.equals("value") && (def == null))
            {
                return of(type, Map.of("value", value));
            }
            if (def == null)
            {
                best = method;
            }
        }
        if (best != null)
        {
            return of(type, Map.of(best.getName(), value));
        }
        return of(type, Map.of("value", value));
    }

    /**
     * Create annotation instance with given values.
     *
     * @param type
     *         type of annotation, must be annotated with {@link Qualifier} or {@link Scope}
     * @param name
     *         name of annotation value to use.
     * @param value
     *         value of annotation.
     * @param <T>
     *         type of annotation.
     *
     * @return annotation instance with given values.
     */
    public static <T extends Annotation> T of(Class<T> type, String name, Object value)
    {
        return of(type, Map.of(name, value));
    }

    /**
     * Create annotation instance with default values.
     *
     * @param type
     *         type of annotation, must be annotated with {@link Qualifier} or {@link Scope}
     * @param <T>
     *         type of annotation.
     *
     * @return annotation instance with given values.
     */
    public static <T extends Annotation> T of(Class<T> type)
    {
        return of(type, Map.of());
    }
}
