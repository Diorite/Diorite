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

package org.diorite.inject.controller;

import javax.inject.Qualifier;
import javax.inject.Scope;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.instrument.ClassFileTransformer;
import java.util.function.Predicate;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import org.diorite.inject.InjectionLibrary;
import org.diorite.inject.data.InjectValueData;
import org.diorite.inject.utils.Constants;
import org.diorite.unsafe.AsmUtils;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodDescription.InDefinedShape;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.ForLoadedType;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.Implementation.Context;
import net.bytebuddy.pool.TypePool;

public class InvokerGenerator implements ClassFileTransformer, Opcodes
{
    public static final String   INJECTOR_CLASS       = Constants.INJECTOR.getInternalName();
    public static final String   INJECTOR_FIELD       = "injectField";
    public static final String   INJECTOR_FIELD_DESC  = "(Ljava/lang/Object;II)Ljava/lang/Object;";
    public static final String   INJECTOR_METHOD      = "injectMethod";
    public static final String   INJECTOR_METHOD_DESC = "(Ljava/lang/Object;III)Ljava/lang/Object;";
    public static final String   GENERATED_PREFIX     = InjectionLibrary.class.getPackage().getName() + ".generated.invokers";
    public static final Object[] STACK                = {};
    public static final int      HASHCODE_MULTI       = 127;

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> Class<? extends T> transform(Class<T> clazz)
    {
        if (clazz == null)
        {
            return null;
        }
        if (! clazz.isAnnotation() || ! (clazz.isAnnotationPresent(Qualifier.class) || clazz.isAnnotationPresent(Scope.class)))
        {
            return null;
        }
        try
        {
            String name = GENERATED_PREFIX + "." + clazz.getName();
            Unloaded<Object> make = new ByteBuddy(ClassFileVersion.JAVA_V9).subclass(Object.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
                                                                           .implement(Serializable.class, clazz).name(name)
                                                                           .visit(new AnnotationImplementationVisitor(new ForLoadedType(clazz))).make();
            Loaded<Object> load = make.load(ClassLoader.getSystemClassLoader(), Default.INJECTION);
            return (Class<? extends T>) load.getLoaded();
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e);
        }
    }

    private static final class AnnotationImplementationVisitor implements AsmVisitorWrapper
    {
        private final TypeDescription clazz;

        private AnnotationImplementationVisitor(TypeDescription clazz)
        {
            this.clazz = clazz;
        }

        @Override
        public int mergeWriter(int flags)
        {
            return flags | ClassWriter.COMPUTE_MAXS;
        }

        @Override
        public int mergeReader(int flags)
        {
            return flags;
        }

        @Override
        public ClassVisitor wrap(TypeDescription typeDescription, ClassVisitor cv, Context context, TypePool typePool, int i, int i1)
        {
//            public void visit(int version, int modifiers, String name, String signature, String superName, String[] interfaces) {
            cv.visit(ClassFileVersion.JAVA_V9.getMinorMajorVersion(), typeDescription.getModifiers(), typeDescription.getInternalName(), null,
                     typeDescription.getSuperClass().asErasure().getInternalName(), typeDescription.getInterfaces().asErasures().toInternalNames());
            TypeDescription clazz = this.clazz;
            String internalName = clazz.getInternalName();
            String descriptor = clazz.getDescriptor();
            MethodList<InDefinedShape> declaredMethods = clazz.getDeclaredMethods();
            int methodsSize = declaredMethods.size();
            String implName = GENERATED_PREFIX + "." + clazz.getName();
            String internalImplName = GENERATED_PREFIX.replace('.', '/') + "/" + internalName;
            String descriptorImplName = "L" + GENERATED_PREFIX.replace('.', '/') + "/" + internalName + ";";

            FieldVisitor fv;
            MethodVisitor mv;
            AnnotationVisitor av0;

            cv.visitEnd();
            return cv;
        }
    }

//    public static void test(ClassData classData, byte[] bytecode)
//    {
//        ClassReader cr = new ClassReader(bytecode);
//        ClassNode node = new ClassNode(Opcodes.ASM6);
//        cr.accept(node, 0);
//
//        classData.getMethods()
//        List<MethodNode> methods = node.methods;
//        for (MethodNode method : methods)
//        {
//            if (method.desc)
//        }
//    }

    public static AbstractInsnNode[] generateFieldInjection(ClassData classData, FieldData<?> fieldData, MethodNode mv, boolean printMethods, int lineNumber,
                                                            int arg)
    {
        MethodNode tempNode = new MethodNode();
        AbstractInsnNode[] result = new AbstractInsnNode[2];
        FieldDescription.InDefinedShape member = fieldData.getMember();
        TypeDescription fieldType = member.getType().asErasure();
        boolean isStatic = member.isStatic();

        if (printMethods)
        {
            lineNumber = printMethods(tempNode, classData.getType().getInternalName(), fieldData.getBefore(), isStatic, lineNumber);
        }
        lineNumber = AsmUtils.printLineNumber(tempNode, lineNumber);

        if (isStatic)
        {
            tempNode.visitInsn(ACONST_NULL);
        }
        else
        {
            tempNode.visitVarInsn(ALOAD, 0);
            tempNode.visitVarInsn(ALOAD, 0);
        }

        AsmUtils.storeInt(tempNode, classData.getIndex());
        AsmUtils.storeInt(tempNode, fieldData.getIndex());
        tempNode.visitMethodInsn(INVOKESTATIC, INJECTOR_CLASS, INJECTOR_FIELD, INJECTOR_FIELD_DESC, false);
//        mv.visitTypeInsn(CHECKCAST, fieldType.getInternalName()); // skip cast check?

        if (arg > 0)
        {
            tempNode.visitVarInsn(AsmUtils.getStoreCode(fieldType), arg);
        }
        else if (arg != Integer.MIN_VALUE)
        {
            if (isStatic)
            {
                tempNode.visitFieldInsn(PUTSTATIC, classData.getType().getInternalName(), member.getName(), fieldType.getDescriptor());
            }
            else
            {
                tempNode.visitFieldInsn(PUTFIELD, classData.getType().getInternalName(), member.getName(), fieldType.getDescriptor());
            }
        }
        if (printMethods)
        {
            printMethods(tempNode, classData.getType().getInternalName(), fieldData.getAfter(), isStatic, lineNumber);
        }
        InsnList instructions = tempNode.instructions;
        result[0] = instructions.getFirst();
        result[1] = instructions.getLast();
        mv.instructions.add(instructions);
        return result;
    }

    public static AbstractInsnNode[] generateFieldInjection(ClassData classData, FieldData<?> fieldData, MethodNode mv, boolean printMethods, int lineNumber)
    {
        return generateFieldInjection(classData, fieldData, mv, printMethods, lineNumber, - 1);
    }

    public static int printMethods(MethodNode mv, String clazz, Iterable<String> methods, Predicate<String> isStatic, int lineNumber)
    {
        for (String method : methods)
        {
            lineNumber = printMethod(mv, clazz, method, isStatic.test(method), lineNumber);
        }
        return lineNumber;
    }

    public static int printMethods(MethodNode mv, String clazz, Iterable<String> methods, boolean isStatic, int lineNumber)
    {
        for (String method : methods)
        {
            lineNumber = printMethod(mv, clazz, method, isStatic, lineNumber);
        }
        return lineNumber;
    }

    public static int printMethod(MethodNode mv, String clazz, String method, boolean isStatic, int lineNumber)
    {
        lineNumber = AsmUtils.printLineNumber(mv, lineNumber);
        if (isStatic)
        {
            mv.visitMethodInsn(INVOKESTATIC, clazz, method, "()V", false);
        }
        else
        {
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, clazz, method, "()V", false);
        }
        return lineNumber;
    }

    public static void generateMethodInjection(ClassData classData, MethodData methodData, MethodNode mv, boolean printMethods, int lineNumber)
    {
        MethodDescription.InDefinedShape member = methodData.getMember();
        boolean isStatic = member.isStatic();

        if (printMethods)
        {
            lineNumber = printMethods(mv, classData.getType().getInternalName(), methodData.getBefore(), isStatic, lineNumber);
        }
        lineNumber = AsmUtils.printLineNumber(mv, lineNumber);

        if (! isStatic)
        {
            mv.visitVarInsn(ALOAD, 0);
        }

        for (InjectValueData<?, Generic> valueData : methodData.getInjectValues())
        {
            if (isStatic)
            {
                mv.visitInsn(ACONST_NULL);
            }
            else
            {
                mv.visitVarInsn(ALOAD, 0);
            }
            AsmUtils.storeInt(mv, classData.getIndex());
            AsmUtils.storeInt(mv, methodData.getIndex());
            AsmUtils.storeInt(mv, valueData.getIndex());
            lineNumber = AsmUtils.printLineNumber(mv, lineNumber);
            mv.visitMethodInsn(INVOKESTATIC, INJECTOR_CLASS, INJECTOR_METHOD, INJECTOR_METHOD_DESC, false);
            TypeDescription paramType = valueData.getType().asErasure();
            mv.visitTypeInsn(CHECKCAST, paramType.getInternalName()); // skip cast check?
        }

        lineNumber = AsmUtils.printLineNumber(mv, lineNumber);

        if (isStatic)
        {
            mv.visitMethodInsn(INVOKESTATIC, classData.getType().getInternalName(), member.getName(), member.getDescriptor(), false);
        }
        else
        {
            mv.visitMethodInsn(INVOKESPECIAL, classData.getType().getInternalName(), member.getName(), member.getDescriptor(), false);
        }

        if (printMethods)
        {
            printMethods(mv, classData.getType().getInternalName(), methodData.getAfter(), isStatic, lineNumber);
        }
    }
}
