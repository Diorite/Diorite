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

package org.diorite.inject.annotations;

import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.lang.annotation.Annotation;

import org.junit.Assert;

public final class AnnotationUtils
{
    private static final StackWalker walker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);

    private AnnotationUtils()
    {
    }

    public static <T extends Annotation> T getAnnInstance(Class<T> type, int levels)
    {
        String methodName = walker.walk(s -> s.map(StackFrame::getMethodName).skip(1 + levels).findFirst()).orElse(null);
        Class<?> clazz = walker.walk(s -> s.map(StackFrame::getDeclaringClass).skip(1 + levels).findFirst()).orElse(null);
        return getAnnInstance(type, methodName, clazz);
    }

    public static <T extends Annotation> T getAnnInstance(Class<T> type)
    {
        String methodName = walker.walk(s -> s.map(StackFrame::getMethodName).skip(1).findFirst()).orElse(null);
        Class<?> clazz = walker.walk(s -> s.map(StackFrame::getDeclaringClass).skip(1).findFirst()).orElse(null);
        return getAnnInstance(type, methodName, clazz);
    }

    public static <T extends Annotation> void assertEquals(T expected, T actual)
    {
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected.annotationType(), actual.annotationType());
        Assert.assertEquals(expected.hashCode(), actual.hashCode());
    }

    public static <T extends Annotation> void assertNotEquals(T expected, T actual)
    {
        Assert.assertNotEquals(expected, actual);
        Assert.assertEquals(expected.annotationType(), actual.annotationType());
        Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
    }

    private static <T extends Annotation> T getAnnInstance(Class<T> type, String method, Class<?> clazz)
    {
        try
        {
            return clazz.getDeclaredMethod(method).getAnnotation(type);
        }
        catch (Exception e)
        {
            throw new InternalError(e);
        }
    }
}
