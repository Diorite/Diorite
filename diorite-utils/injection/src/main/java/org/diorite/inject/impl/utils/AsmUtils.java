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

package org.diorite.inject.impl.utils;

import javax.annotation.Nullable;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

import net.bytebuddy.description.annotation.AnnotatedCodeElement;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.ForLoadedType;

@SuppressWarnings("Duplicates")
public final class AsmUtils implements Opcodes
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

    @Nullable
    static final MethodHandle typeHandle;

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

    private AsmUtils()
    {
    }

    public static void printBytecodeSource(ClassWriter classWriter, OutputStream outputStream)
    {
        ClassReader cr = new ClassReader(classWriter.toByteArray());
        cr.accept(new TraceClassVisitor(new PrintWriter(outputStream)), 0);
    }

    public static boolean isPutField(int code)
    {
        switch (code)
        {
            case PUTFIELD:
            case PUTSTATIC:
                return true;
            default:
                return false;
        }
    }

    public static boolean isInvokeCode(int code)
    {
        switch (code)
        {
            case INVOKEDYNAMIC:
            case INVOKEINTERFACE:
            case INVOKESPECIAL:
            case INVOKESTATIC:
            case INVOKEVIRTUAL:
                return true;
            default:
                return false;
        }
    }

    public static boolean isLoadCode(int code)
    {
        switch (code)
        {
            case ALOAD:
            case ILOAD:
            case LLOAD:
            case FLOAD:
            case DLOAD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isStoreCode(int code)
    {
        switch (code)
        {
            case ASTORE:
            case ISTORE:
            case LSTORE:
            case FSTORE:
            case DSTORE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isReturnCode(int code)
    {
        switch (code)
        {
            case RETURN:
            case ARETURN:
            case IRETURN:
            case LRETURN:
            case FRETURN:
            case DRETURN:
                return true;
            default:
                return false;
        }
    }

    public static int getReturnCode(TypeDescription fieldType)
    {
        if (fieldType.isPrimitive())
        {
            if (BOOLEAN_P.equals(fieldType) || BYTE_P.equals(fieldType) || CHAR_P.equals(fieldType) ||
                SHORT_P.equals(fieldType) || INT_P.equals(fieldType))
            {
                return IRETURN;
            }
            if (LONG_P.equals(fieldType))
            {
                return LRETURN;
            }
            if (FLOAT_P.equals(fieldType))
            {
                return FRETURN;
            }
            if (DOUBLE_P.equals(fieldType))
            {
                return DRETURN;
            }
            else
            {
                throw new IllegalStateException("Unknown store method");
            }
        }
        else
        {
            return ARETURN;
        }
    }

    public static int getStoreCode(TypeDescription fieldType)
    {
        if (fieldType.isPrimitive())
        {
            if (BOOLEAN_P.equals(fieldType) || BYTE_P.equals(fieldType) || CHAR_P.equals(fieldType) ||
                SHORT_P.equals(fieldType) || INT_P.equals(fieldType))
            {
                return ISTORE;
            }
            if (LONG_P.equals(fieldType))
            {
                return LSTORE;
            }
            if (FLOAT_P.equals(fieldType))
            {
                return FSTORE;
            }
            if (DOUBLE_P.equals(fieldType))
            {
                return DSTORE;
            }
            else
            {
                throw new IllegalStateException("Unknown store method");
            }
        }
        else
        {
            return ASTORE;
        }
    }

    public static int getLoadCode(TypeDescription fieldType)
    {
        if (fieldType.isPrimitive())
        {
            if (BOOLEAN_P.equals(fieldType) || BYTE_P.equals(fieldType) || CHAR_P.equals(fieldType) ||
                SHORT_P.equals(fieldType) || INT_P.equals(fieldType))
            {
                return ILOAD;
            }
            if (LONG_P.equals(fieldType))
            {
                return LLOAD;
            }
            if (FLOAT_P.equals(fieldType))
            {
                return FLOAD;
            }
            if (DOUBLE_P.equals(fieldType))
            {
                return DLOAD;
            }
            else
            {
                throw new IllegalStateException("Unknown load method");
            }
        }
        else
        {
            return ALOAD;
        }
    }

    public static int printLineNumber(MethodVisitor mv, int lineNumber)
    {
        if (lineNumber == - 1)
        {
            return - 1;
        }
        Label label = new Label();
        mv.visitLabel(label);
        mv.visitLineNumber(lineNumber, label);
        return lineNumber + 1;
    }

    public static void storeInt(MethodVisitor mv, int i)
    {
        switch (i)
        {
            case 0:
                mv.visitInsn(ICONST_0);
                break;
            case 1:
                mv.visitInsn(ICONST_1);
                break;
            case 2:
                mv.visitInsn(ICONST_2);
                break;
            case 3:
                mv.visitInsn(ICONST_3);
                break;
            case 4:
                mv.visitInsn(ICONST_4);
                break;
            default:
                mv.visitIntInsn(BIPUSH, i);
                break;
        }

    }

    /**
     * If given type is primitive type {@link TypeDescription#isPrimitive()} then it will return
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
