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

import java.io.OutputStream;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

import net.bytebuddy.description.type.TypeDescription;

public final class AsmUtils implements Opcodes
{
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
            if (ByteBuddyUtils.BOOLEAN_P.equals(fieldType) || ByteBuddyUtils.BYTE_P.equals(fieldType) || ByteBuddyUtils.CHAR_P.equals(fieldType) ||
                ByteBuddyUtils.SHORT_P.equals(fieldType) || ByteBuddyUtils.INT_P.equals(fieldType))
            {
                return IRETURN;
            }
            if (ByteBuddyUtils.LONG_P.equals(fieldType))
            {
                return LRETURN;
            }
            if (ByteBuddyUtils.FLOAT_P.equals(fieldType))
            {
                return FRETURN;
            }
            if (ByteBuddyUtils.DOUBLE_P.equals(fieldType))
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
            if (ByteBuddyUtils.BOOLEAN_P.equals(fieldType) || ByteBuddyUtils.BYTE_P.equals(fieldType) || ByteBuddyUtils.CHAR_P.equals(fieldType) ||
                ByteBuddyUtils.SHORT_P.equals(fieldType) || ByteBuddyUtils.INT_P.equals(fieldType))
            {
                return ISTORE;
            }
            if (ByteBuddyUtils.LONG_P.equals(fieldType))
            {
                return LSTORE;
            }
            if (ByteBuddyUtils.FLOAT_P.equals(fieldType))
            {
                return FSTORE;
            }
            if (ByteBuddyUtils.DOUBLE_P.equals(fieldType))
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
            if (ByteBuddyUtils.BOOLEAN_P.equals(fieldType) || ByteBuddyUtils.BYTE_P.equals(fieldType) || ByteBuddyUtils.CHAR_P.equals(fieldType) ||
                ByteBuddyUtils.SHORT_P.equals(fieldType) || ByteBuddyUtils.INT_P.equals(fieldType))
            {
                return ILOAD;
            }
            if (ByteBuddyUtils.LONG_P.equals(fieldType))
            {
                return LLOAD;
            }
            if (ByteBuddyUtils.FLOAT_P.equals(fieldType))
            {
                return FLOAD;
            }
            if (ByteBuddyUtils.DOUBLE_P.equals(fieldType))
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
}
