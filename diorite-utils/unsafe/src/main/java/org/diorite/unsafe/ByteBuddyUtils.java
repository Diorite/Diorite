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

package org.diorite.unsafe;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import net.bytebuddy.description.annotation.AnnotatedCodeElement;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.ForLoadedType;

public final class ByteBuddyUtils
{
    public static final TypeDescription.ForLoadedType VOID      = new TypeDescription.ForLoadedType(Void.class);
    public static final TypeDescription.ForLoadedType BOOLEAN   = new TypeDescription.ForLoadedType(Boolean.class);
    public static final TypeDescription.ForLoadedType BYTE      = new TypeDescription.ForLoadedType(Byte.class);
    public static final TypeDescription.ForLoadedType SHORT     = new TypeDescription.ForLoadedType(Short.class);
    public static final TypeDescription.ForLoadedType CHARACTER = new TypeDescription.ForLoadedType(Character.class);
    public static final TypeDescription.ForLoadedType INTEGER   = new TypeDescription.ForLoadedType(Integer.class);
    public static final TypeDescription.ForLoadedType LONG      = new TypeDescription.ForLoadedType(Long.class);
    public static final TypeDescription.ForLoadedType FLOAT     = new TypeDescription.ForLoadedType(Float.class);
    public static final TypeDescription.ForLoadedType DOUBLE    = new TypeDescription.ForLoadedType(Double.class);

    public static final TypeDescription.ForLoadedType VOID_P    = new TypeDescription.ForLoadedType(void.class);
    public static final TypeDescription.ForLoadedType BOOLEAN_P = new TypeDescription.ForLoadedType(boolean.class);
    public static final TypeDescription.ForLoadedType BYTE_P    = new TypeDescription.ForLoadedType(byte.class);
    public static final TypeDescription.ForLoadedType SHORT_P   = new TypeDescription.ForLoadedType(short.class);
    public static final TypeDescription.ForLoadedType CHAR_P    = new TypeDescription.ForLoadedType(char.class);
    public static final TypeDescription.ForLoadedType INT_P     = new TypeDescription.ForLoadedType(int.class);
    public static final TypeDescription.ForLoadedType LONG_P    = new TypeDescription.ForLoadedType(long.class);
    public static final TypeDescription.ForLoadedType FLOAT_P   = new TypeDescription.ForLoadedType(float.class);
    public static final TypeDescription.ForLoadedType DOUBLE_P  = new TypeDescription.ForLoadedType(double.class);

    private ByteBuddyUtils()
    {
    }

    /**
     * If given type is primitive type {@link net.bytebuddy.description.type.TypeDescription#isPrimitive()} then it will return
     * wrapper type for it. Like: boolean.class {@literal ->} Boolean.class
     * If given type isn't primitive, then it will return given type.
     *
     * @param type
     *         type to get wrapper of it.
     *
     * @return non-primitive type.
     */
    public static TypeDescription getWrapperClass(TypeDescription type)
    {
        if (! type.isPrimitive())
        {
            return type;
        }
        if (type.equals(BOOLEAN_P))
        {
            return BOOLEAN;
        }
        if (type.equals(BYTE_P))
        {
            return BYTE;
        }
        if (type.equals(SHORT_P))
        {
            return SHORT;
        }
        if (type.equals(CHAR_P))
        {
            return CHARACTER;
        }
        if (type.equals(INT_P))
        {
            return INTEGER;
        }
        if (type.equals(LONG_P))
        {
            return LONG;
        }
        if (type.equals(FLOAT_P))
        {
            return FLOAT;
        }
        if (type.equals(DOUBLE_P))
        {
            return DOUBLE;
        }
        if (type.equals(VOID_P))
        {
            return VOID;
        }
        throw new Error("Unknown primitive type?"); // not possible?
    }

    public static TypeDescription getPrimitiveClass(TypeDescription type)
    {
        if (type.isPrimitive())
        {
            return type;
        }
        if (type.equals(BOOLEAN))
        {
            return BOOLEAN_P;
        }
        if (type.equals(BYTE))
        {
            return BYTE_P;
        }
        if (type.equals(SHORT))
        {
            return SHORT_P;
        }
        if (type.equals(CHARACTER))
        {
            return CHAR_P;
        }
        if (type.equals(INTEGER))
        {
            return INT_P;
        }
        if (type.equals(LONG))
        {
            return LONG_P;
        }
        if (type.equals(FLOAT))
        {
            return FLOAT_P;
        }
        if (type.equals(DOUBLE))
        {
            return DOUBLE_P;
        }
        if (type.equals(VOID))
        {
            return VOID_P;
        }
        throw new RuntimeException("Invalid primitive type?");
    }

    public static AnnotationList getAnnotationList(AnnotatedCodeElement element)
    {
        if (element instanceof TypeDescription)
        {
            return ((TypeDescription) element).getInheritedAnnotations();
        }
        else
        {
            return element.getDeclaredAnnotations();
        }
    }

    public static Annotation[] getAnnotations(AnnotatedCodeElement element, RetentionPolicy... policies)
    {
        Set<RetentionPolicy> policySet = Set.of(policies);
        AnnotationList annotationList = getAnnotationList(element);
        return getAnnotations(annotationList.visibility(policySet::contains));
    }

    public static Annotation[] getAnnotations(AnnotatedCodeElement element)
    {
        return getAnnotations(getAnnotationList(element));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Annotation[] getAnnotations(Collection<AnnotationDescription> annotations)
    {
        Collection<Annotation> col = new ArrayList<>(annotations.size());
        for (AnnotationDescription annotation : annotations)
        {
            TypeDescription annotationType = annotation.getAnnotationType();
            try
            {
                Class<?> forName = Class.forName(annotationType.getActualName());
                if (! forName.isAnnotation())
                {
                    continue;
                }
                col.add(annotation.prepare((Class) forName).load());
            }
            catch (ClassNotFoundException ignored)
            {
            }
        }
        return col.toArray(new Annotation[col.size()]);
    }

    private static final MethodHandle typeHandle;

    static
    {
        MethodHandle r;
        try
        {
            Field typeField = ForLoadedType.class.getDeclaredField("type");
            typeField.setAccessible(true);
            r = MethodHandles.lookup().unreflectGetter(typeField);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            r = null;
        }
        typeHandle = r;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> toClass(TypeDescription type)
    {
        if ((type instanceof TypeDescription.ForLoadedType) && (typeHandle != null))
        {
            try
            {
                return (Class<T>) typeHandle.invoke(type);
            }
            catch (Throwable throwable)
            {
                throwable.printStackTrace();
            }
        }
        ClassLoader classLoader = StackWalker.getInstance().getCallerClass().getClassLoader();
        try
        {
            return (Class<T>) Class.forName(type.getActualName(), false, classLoader);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
