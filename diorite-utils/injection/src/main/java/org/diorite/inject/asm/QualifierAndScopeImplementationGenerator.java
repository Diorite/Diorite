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

package org.diorite.inject.asm;

import javax.inject.Qualifier;
import javax.inject.Scope;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.instrument.ClassFileTransformer;
import java.util.Iterator;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import org.diorite.inject.Qualifiers;
import org.diorite.inject.utils.Constants;
import org.diorite.unsafe.ByteBuddyUtils;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.method.MethodDescription.InDefinedShape;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.ForLoadedType;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.Implementation.Context;
import net.bytebuddy.pool.TypePool;

public class QualifierAndScopeImplementationGenerator implements ClassFileTransformer, Opcodes
{
    public static final String   GENERATED_PREFIX = "org.diorite.di.generated.annotations";
    public static final Object[] STACK            = {};
    public static final int      HASHCODE_MULTI   = 127;
//
//    public QualifierAndScopeImplementationGenerator(DIController controller, Instrumentation instrumentation)
//    {
//        super(controller, instrumentation);
//    }
//
//    @Override
//    public byte[] transform(DIController controller, Instrumentation instr, Module module, ClassLoader loader, String className, Class<?> clazz,
//                            ProtectionDomain pd, byte[] bytes) throws IllegalClassFormatException
//    {
//        if (clazz == null)
//        {
//            return null;
//        }
//        ClassReader classReader = new ClassReader(bytes);
//        if ((classReader.getInterfaces().length != 1) || ! classReader.getInterfaces()[0].equals("java/lang/annotation/Annotation"))
//        {
//            return null;
//        }
//        try
//        {
//            TypePool pool = TypePool.Default.ofClassPath();
//            TypeDescription description = pool.describe(className.replace('/', '.')).resolve();
//            AnnotationList annotations = description.getInheritedAnnotations();
//            if (! description.isAnnotation() || ! (annotations.isAnnotationPresent(Qualifier.class) || annotations.isAnnotationPresent(Scope.class)))
//            {
//                return null;
//            }
//            String name = GENERATED_PREFIX + "." + description.getName();
//            Unloaded<Object> make = new ByteBuddy().subclass(Object.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
//                                                   .implement(pool.describe(Serializable.class.getName()).resolve(), description).name(name)
//                                                   .visit(new AnnotationImplementationVisitor(description)).make();
//            Loaded<Object> load = make.load(ClassLoader.getSystemClassLoader(), Default.INJECTION);
//            return null;
//        }
//        catch (Throwable e)
//        {
//            e.printStackTrace();
//            return null;
//        }
//    }

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

            {
                fv = cv.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "serialVersionUID", "J", null, 0L);
                fv.visitEnd();
            }
            if (methodsSize == 0)
            {
                {
                    fv = cv.visitField(ACC_PRIVATE + ACC_FINAL, "$_hashcode", "I", null, null);
                    fv.visitEnd();
                }
                {
                    fv = cv.visitField(ACC_PRIVATE + ACC_FINAL, "$_toString", "Ljava/lang/String;", null, null);
                    fv.visitEnd();
                }
                {
                    mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
                    mv.visitCode();
                    Label l0 = new Label();
                    mv.visitLabel(l0);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                    Label l1 = new Label();
                    mv.visitLabel(l1);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitInsn(ICONST_0);
                    mv.visitFieldInsn(PUTFIELD, internalImplName, "$_hashcode", "I");
                    Label l2 = new Label();
                    mv.visitLabel(l2);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                    mv.visitInsn(DUP);
                    mv.visitIntInsn(BIPUSH, 50);
                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(I)V", false);
                    mv.visitIntInsn(BIPUSH, '@');
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(C)Ljava/lang/StringBuilder;", false);
                    mv.visitLdcInsn(Type.getType(descriptor));
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitLdcInsn("()");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                    mv.visitFieldInsn(PUTFIELD, internalImplName, "$_toString", "Ljava/lang/String;");
                    Label l3 = new Label();
                    mv.visitLabel(l3);
                    mv.visitInsn(RETURN);
                    Label l4 = new Label();
                    mv.visitLabel(l4);
                    mv.visitLocalVariable("this", descriptorImplName, null, l0, l4, 0);
                    mv.visitMaxs(4, 1);
                    mv.visitEnd();
                }
                {
                    mv = cv.visitMethod(ACC_PUBLIC, "hashCode", "()I", null, null);
                    mv.visitCode();
                    Label l0 = new Label();
                    mv.visitLabel(l0);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, internalImplName, "$_hashcode", "I");
                    mv.visitInsn(IRETURN);
                    Label l1 = new Label();
                    mv.visitLabel(l1);
                    mv.visitLocalVariable("this", descriptorImplName, null, l0, l1, 0);
                    mv.visitMaxs(1, 1);
                    mv.visitEnd();
                }
                {
                    mv = cv.visitMethod(ACC_PUBLIC, "equals", "(Ljava/lang/Object;)Z", null, null);
                    mv.visitCode();
                    Label l0 = new Label();
                    mv.visitLabel(l0);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitTypeInsn(INSTANCEOF, internalName);
                    mv.visitInsn(IRETURN);
                    Label l1 = new Label();
                    mv.visitLabel(l1);
                    mv.visitLocalVariable("this", descriptorImplName, null, l0, l1, 0);
                    mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l0, l1, 1);
                    mv.visitMaxs(1, 2);
                    mv.visitEnd();
                }
                {
                    mv = cv.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
                    mv.visitCode();
                    Label l0 = new Label();
                    mv.visitLabel(l0);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, internalImplName, "$_toString", "Ljava/lang/String;");
                    mv.visitInsn(ARETURN);
                    Label l1 = new Label();
                    mv.visitLabel(l1);
                    mv.visitLocalVariable("this", descriptorImplName, null, l0, l1, 0);
                    mv.visitMaxs(1, 1);
                    mv.visitEnd();
                }
                {
                    mv = cv.visitMethod(ACC_PUBLIC, "annotationType", "()Ljava/lang/Class;", "()Ljava/lang/Class<+Ljava/lang/annotation/Annotation;>;",
                                        null);
                    mv.visitCode();
                    Label l0 = new Label();
                    mv.visitLabel(l0);
                    mv.visitLdcInsn(Type.getType(descriptor));
                    mv.visitInsn(ARETURN);
                    Label l1 = new Label();
                    mv.visitLabel(l1);
                    mv.visitLocalVariable("this", descriptorImplName, null, l0, l1, 0);
                    mv.visitMaxs(1, 1);
                    mv.visitEnd();
                }
                {
                    mv = cv.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
                    mv.visitCode();
                    Label l0 = new Label();
                    mv.visitLabel(l0);
                    mv.visitTypeInsn(NEW, internalImplName);
                    mv.visitInsn(DUP);
                    mv.visitMethodInsn(INVOKESPECIAL, internalImplName, "<init>", "()V", false);
                    mv.visitVarInsn(ASTORE, 0);
                    Label l1 = new Label();
                    mv.visitLabel(l1);
                    mv.visitTypeInsn(NEW, Constants.INSTANCE_SUPPLIER.getInternalName());
                    mv.visitInsn(DUP);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKESPECIAL, Constants.INSTANCE_SUPPLIER.getInternalName(), "<init>", "(Ljava/lang/Object;)V", false);
                    mv.visitVarInsn(ASTORE, 1);
                    Label l2 = new Label();
                    mv.visitLabel(l2);
                    mv.visitLdcInsn(Type.getType(descriptor));
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKESTATIC, Constants.QUALIFIERS.getInternalName(), "register",
                                       "(Ljava/lang/Class;Ljava/util/function/Function;Ljava/util/function/Function;)V", false);
                    Label l3 = new Label();
                    mv.visitLabel(l3);
                    mv.visitInsn(RETURN);
                    mv.visitLocalVariable("inst", descriptorImplName, null, l1, l3, 0);
                    mv.visitLocalVariable("supplier", Constants.INSTANCE_SUPPLIER.getDescriptor(), null, l2, l3, 1);
                    mv.visitMaxs(3, 2);
                    mv.visitEnd();
                }
                cv.visitEnd();
                return cv;
            }
            {
                fv = cv.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "DEFAULTS", "Ljava/util/Map;",
                                   "Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;", null);
                fv.visitEnd();
            }
            for (InDefinedShape method : declaredMethods)
            {
                fv = cv.visitField(ACC_PRIVATE + ACC_FINAL, method.getName(), method.getReturnType().asErasure().getDescriptor(), null, null);
                fv.visitEnd();
            }
            {
                fv = cv.visitField(ACC_PRIVATE + ACC_FINAL, "$_hashcode", "I", null, null);
                fv.visitEnd();
            }
            {
                fv = cv.visitField(ACC_PRIVATE + ACC_FINAL, "$_toString", "Ljava/lang/String;", null, null);
                fv.visitEnd();
            }
            {
                mv = cv.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/util/Map;)V", "(Ljava/util/Map<Ljava/lang/String;*>;)V", null);
                mv.visitCode();
                Label l0 = new Label();
                Label l1 = new Label();
                Label l2 = new Label();
                mv.visitTryCatchBlock(l0, l1, l2, "java/lang/ClassCastException");
                Label l3 = new Label();
                mv.visitLabel(l3);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                mv.visitLabel(l0);

                for (InDefinedShape method : declaredMethods)
                {
                    String methodName = method.getName();
                    TypeDescription returnType = method.getReturnType().asErasure();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitLdcInsn(methodName);
                    mv.visitMethodInsn(INVOKESTATIC, internalImplName, "$_get", "(Ljava/util/Map;Ljava/lang/String;)Ljava/lang/Object;", false);
                    if (returnType.isPrimitive())
                    {
                        String wrapperInternal = ByteBuddyUtils.getWrapperClass(returnType).getInternalName();
                        mv.visitTypeInsn(CHECKCAST, wrapperInternal);
                        mv.visitMethodInsn(INVOKEVIRTUAL, wrapperInternal, returnType.getName() + "Value", "()" + returnType.getDescriptor(), false);
                    }
                    else
                    {
                        mv.visitTypeInsn(CHECKCAST, returnType.getInternalName());
                    }
                    mv.visitFieldInsn(PUTFIELD, internalImplName, methodName, returnType.getDescriptor());
                }

                mv.visitLabel(l1);
                Label l9 = new Label();
                mv.visitJumpInsn(GOTO, l9);
                mv.visitLabel(l2);
                mv.visitFrame(F_FULL, 2, new Object[]{internalImplName, "java/util/Map"}, 1,
                              new Object[]{"java/lang/ClassCastException"});
                mv.visitVarInsn(ASTORE, 2);
                Label l10 = new Label();
                mv.visitLabel(l10);
                mv.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
                mv.visitInsn(DUP);
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitLdcInsn("Wrong argument type: ");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitVarInsn(ALOAD, 1);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitVarInsn(ALOAD, 2);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;Ljava/lang/Throwable;)V", false);
                mv.visitInsn(ATHROW);
                mv.visitLabel(l9);
                mv.visitFrame(F_SAME, 0, null, 0, null);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, internalImplName, "$_initHashCode", "()I", false);
                mv.visitFieldInsn(PUTFIELD, internalImplName, "$_hashcode", "I");
                Label l11 = new Label();
                mv.visitLabel(l11);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, internalImplName, "$_initToString", "()Ljava/lang/String;", false);
                mv.visitFieldInsn(PUTFIELD, internalImplName, "$_toString", "Ljava/lang/String;");
                Label l12 = new Label();
                mv.visitLabel(l12);
                mv.visitInsn(RETURN);
                Label l13 = new Label();
                mv.visitLabel(l13);
                mv.visitLocalVariable("e", "Ljava/lang/ClassCastException;", null, l10, l9, 2);
                mv.visitLocalVariable("this", descriptorImplName, null, l3, l13, 0);
                mv.visitLocalVariable("data", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/String;*>;", l3, l13, 1);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }
            {
                mv = cv.visitMethod(ACC_PRIVATE + ACC_STATIC, "$_get", "(Ljava/util/Map;Ljava/lang/String;)Ljava/lang/Object;",
                                    "<T:Ljava/lang/Object;>(Ljava/util/Map<Ljava/lang/String;*>;Ljava/lang/String;)TT;", null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitVarInsn(ALOAD, 1);
                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
                mv.visitVarInsn(ASTORE, 2);
                Label l1 = new Label();
                mv.visitLabel(l1);
                mv.visitVarInsn(ALOAD, 2);
                Label l2 = new Label();
                mv.visitJumpInsn(IFNONNULL, l2);
                Label l3 = new Label();
                mv.visitLabel(l3);
                mv.visitFieldInsn(GETSTATIC, internalImplName, "DEFAULTS", "Ljava/util/Map;");
                mv.visitVarInsn(ALOAD, 1);
                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
                mv.visitVarInsn(ASTORE, 2);
                Label l4 = new Label();
                mv.visitLabel(l4);
                mv.visitVarInsn(ALOAD, 2);
                mv.visitJumpInsn(IFNONNULL, l2);
                Label l5 = new Label();
                mv.visitLabel(l5);
                mv.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
                mv.visitInsn(DUP);
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitLdcInsn("Missing required annotation value: ");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitVarInsn(ALOAD, 1);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
                mv.visitInsn(ATHROW);
                mv.visitLabel(l2);
                mv.visitFrame(F_APPEND, 1, new Object[]{"java/lang/Object"}, 0, null);
                mv.visitVarInsn(ALOAD, 2);
                mv.visitInsn(ARETURN);
                Label l6 = new Label();
                mv.visitLabel(l6);
                mv.visitLocalVariable("data", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/String;*>;", l0, l6, 0);
                mv.visitLocalVariable("field", "Ljava/lang/String;", null, l0, l6, 1);
                mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l1, l6, 2);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }

            for (InDefinedShape method : declaredMethods)
            {
                String methodName = method.getName();
                TypeDescription returnType = method.getReturnType().asErasure();
                String returnTypeDescriptor = returnType.getDescriptor();
                mv = cv.visitMethod(ACC_PUBLIC, methodName, "()" + returnTypeDescriptor, null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, internalImplName, methodName, returnTypeDescriptor);
                if (returnType.isArray()) // array must be cloned.
                {
                    mv.visitMethodInsn(INVOKEVIRTUAL, returnTypeDescriptor, "clone", "()Ljava/lang/Object;", false);
                    mv.visitTypeInsn(CHECKCAST, returnTypeDescriptor);
                }
                if (returnType.isPrimitive())
                {
                    if (returnType.getName().equals(float.class.getName()))
                    {
                        mv.visitInsn(FRETURN);
                    }
                    else if (returnType.getName().equals(double.class.getName()))
                    {
                        mv.visitInsn(DRETURN);
                    }
                    else if (returnType.getName().equals(long.class.getName()))
                    {
                        mv.visitInsn(LRETURN);
                    }
                    else
                    {
                        mv.visitInsn(IRETURN);
                    }
                }
                else
                {
                    mv.visitInsn(ARETURN);
                }
                Label l1 = new Label();
                mv.visitLabel(l1);
                mv.visitLocalVariable("this", descriptorImplName, null, l0, l1, 0);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }


            {
                mv = cv.visitMethod(ACC_PUBLIC, "<init>", "(" + descriptor + ")V", null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                Label l1 = new Label();
                mv.visitLabel(l1);

                for (InDefinedShape method : declaredMethods)
                {
                    String methodName = method.getName();
                    TypeDescription returnType = method.getReturnType().asErasure();
                    String returnTypeDescriptor = returnType.getDescriptor();

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEINTERFACE, internalName, methodName, "()" + returnTypeDescriptor, true);
                    mv.visitFieldInsn(PUTFIELD, internalImplName, methodName, returnTypeDescriptor);
                }
                mv.visitVarInsn(ALOAD, 0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, internalImplName, "$_initHashCode", "()I", false);
                mv.visitFieldInsn(PUTFIELD, internalImplName, "$_hashcode", "I");
                Label l8 = new Label();
                mv.visitLabel(l8);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, internalImplName, "$_initToString", "()Ljava/lang/String;", false);
                mv.visitFieldInsn(PUTFIELD, internalImplName, "$_toString", "Ljava/lang/String;");
                Label l9 = new Label();
                mv.visitLabel(l9);
                mv.visitInsn(RETURN);
                Label l10 = new Label();
                mv.visitLabel(l10);
                mv.visitLocalVariable("this", descriptorImplName, null, l0, l10, 0);
                mv.visitLocalVariable("data", descriptor, null, l0, l10, 1);
                mv.visitMaxs(3, 2);
                mv.visitEnd();
            }
            {
                mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "unproxy", "(" + descriptor + ")" + descriptorImplName, null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitTypeInsn(INSTANCEOF, internalImplName);
                Label l1 = new Label();
                mv.visitJumpInsn(IFEQ, l1);
                Label l2 = new Label();
                mv.visitLabel(l2);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitTypeInsn(CHECKCAST, internalImplName);
                mv.visitInsn(ARETURN);
                mv.visitLabel(l1);
                mv.visitFrame(F_SAME, 0, null, 0, null);
                mv.visitTypeInsn(NEW, internalImplName);
                mv.visitInsn(DUP);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, internalImplName, "<init>", "(" + descriptor + ")V", false);
                mv.visitInsn(ARETURN);
                Label l3 = new Label();
                mv.visitLabel(l3);
                mv.visitLocalVariable("ann", descriptor, null, l0, l3, 0);
                mv.visitMaxs(3, 1);
                mv.visitEnd();
            }

            {
                mv = cv.visitMethod(ACC_PRIVATE, "$_initHashCode", "()I", null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitInsn(ICONST_0);
                mv.visitVarInsn(ISTORE, 1);
                Label l1 = new Label();
                for (InDefinedShape method : declaredMethods)
                {
                    String methodName = method.getName();
                    TypeDescription returnType = method.getReturnType().asErasure();
                    String returnTypeDescriptor = returnType.getDescriptor();
                    Label labelX = new Label();
                    mv.visitLabel(labelX);
                    mv.visitVarInsn(ILOAD, 1);
                    mv.visitIntInsn(BIPUSH, HASHCODE_MULTI);
                    mv.visitLdcInsn(methodName);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false);
                    mv.visitInsn(IMUL);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, internalImplName, methodName, returnTypeDescriptor);
                    if (returnType.isPrimitive())
                    {
                        TypeDescription wrapperClass = ByteBuddyUtils.getWrapperClass(returnType);
                        mv.visitMethodInsn(INVOKESTATIC, wrapperClass.getInternalName(), "valueOf",
                                           "(" + returnTypeDescriptor + ")" + wrapperClass.getDescriptor(), false);
                        mv.visitMethodInsn(INVOKEVIRTUAL, wrapperClass.getInternalName(), "hashCode", "()I", false);
                    }
                    else if (returnType.isArray())
                    {
                        if (returnType.getComponentType().isPrimitive())
                        {
                            mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "hashCode", "(" + returnTypeDescriptor + ")I", false);
                        }
                        else
                        {
                            mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "hashCode", "([Ljava/lang/Object;)I", false);
                        }
                    }
                    else
                    {
                        mv.visitMethodInsn(INVOKEVIRTUAL, returnType.getInternalName(), "hashCode", "()I", false);
                    }
                    mv.visitInsn(IXOR);
                    mv.visitInsn(IADD);
                    mv.visitVarInsn(ISTORE, 1);
                }
                mv.visitVarInsn(ILOAD, 1);
                mv.visitInsn(IRETURN);
                Label l7 = new Label();
                mv.visitLabel(l7);
                mv.visitLocalVariable("this", descriptorImplName, null, l0, l7, 0);
                mv.visitLocalVariable("sum", "I", null, l1, l7, 1);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }

            {
                mv = cv.visitMethod(ACC_PUBLIC, "hashCode", "()I", null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, internalImplName, "$_hashcode", "I");
                mv.visitInsn(IRETURN);
                Label l1 = new Label();
                mv.visitLabel(l1);
                mv.visitLocalVariable("this", descriptorImplName, null, l0, l1, 0);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }

            {
                mv = cv.visitMethod(ACC_PUBLIC, "equals", "(Ljava/lang/Object;)Z", null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitVarInsn(ALOAD, 1);
                mv.visitTypeInsn(INSTANCEOF, internalName);
                Label l1 = new Label();
                mv.visitJumpInsn(IFNE, l1);
                Label l2 = new Label();
                mv.visitLabel(l2);
                mv.visitInsn(ICONST_0);
                mv.visitInsn(IRETURN);
                mv.visitLabel(l1);
                mv.visitFrame(F_SAME, 0, null, 0, null);
                mv.visitVarInsn(ALOAD, 1);
                mv.visitTypeInsn(CHECKCAST, internalName);
                mv.visitVarInsn(ASTORE, 2);
                Label l3 = new Label();
                mv.visitLabel(l3);

                Label l4 = new Label();
                for (InDefinedShape method : declaredMethods)
                {
                    String methodName = method.getName();
                    TypeDescription returnType = method.getReturnType().asErasure();
                    String returnTypeDescriptor = returnType.getDescriptor();
                    boolean isPrimitive = returnType.isPrimitive();
                    boolean isFloat =
                            isPrimitive && (returnType.getName().equals(float.class.getName()) || returnType.getName().equals(double.class.getName()));
                    TypeDescription wrapperClass = ByteBuddyUtils.getWrapperClass(returnType);
                    String wrapperClassInternalName = wrapperClass.getInternalName();

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, internalImplName, methodName, returnTypeDescriptor);
                    if (isFloat)
                    {
                        mv.visitMethodInsn(INVOKESTATIC, wrapperClassInternalName, "valueOf",
                                           "(" + returnTypeDescriptor + ")" + wrapperClass.getDescriptor(), false);
                    }
                    mv.visitVarInsn(ALOAD, 2);
                    mv.visitMethodInsn(INVOKEINTERFACE, internalName, methodName, "()" + returnTypeDescriptor, true);
                    if (isPrimitive && ! isFloat)
                    {
                        if (returnType.getName().equals(long.class.getName()))
                        {
                            mv.visitInsn(LCMP);
                            mv.visitJumpInsn(IFNE, l4);
                        }
                        else
                        {
                            mv.visitJumpInsn(IF_ICMPNE, l4);
                        }
                    }
                    else
                    {
                        if (isFloat)
                        {
                            mv.visitMethodInsn(INVOKESTATIC, wrapperClassInternalName, "valueOf",
                                               "(" + returnTypeDescriptor + ")" + wrapperClass.getDescriptor(), false);
                        }
                        if (returnType.isArray())
                        {
                            TypeDescription componentType = returnType.getComponentType();
                            if (componentType.isPrimitive())
                            {
                                mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "equals",
                                                   "(" + returnType.getDescriptor() + "" + returnType.getDescriptor() + ")Z", false);
                            }
                            else
                            {
                                mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "equals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", false);
                            }
                        }
                        else
                        {
                            mv.visitMethodInsn(INVOKEVIRTUAL, wrapperClassInternalName, "equals", "(Ljava/lang/Object;)Z", false);
                        }
                        mv.visitJumpInsn(IFEQ, l4);
                    }
                }


                mv.visitInsn(ICONST_1);
                Label l6 = new Label();
                mv.visitJumpInsn(GOTO, l6);
                mv.visitLabel(l4);
                mv.visitFrame(F_APPEND, 1, new Object[]{internalName}, 0, null);
                mv.visitInsn(ICONST_0);
                mv.visitLabel(l6);
                mv.visitFrame(F_SAME1, 0, null, 1, new Object[]{INTEGER});
                mv.visitInsn(IRETURN);
                Label l7 = new Label();
                mv.visitLabel(l7);
                mv.visitLocalVariable("this", descriptorImplName, null, l0, l7, 0);
                mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l0, l7, 1);
                mv.visitLocalVariable("other", descriptor, null, l3, l7, 2);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }

            {
                mv = cv.visitMethod(ACC_PRIVATE, "$_initToString", "()Ljava/lang/String;", null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitIntInsn(SIPUSH, methodsSize * 30);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(I)V", false);
                mv.visitIntInsn(BIPUSH, '@');
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(C)Ljava/lang/StringBuilder;", false);
                mv.visitLdcInsn(Type.getType(descriptor));
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitIntInsn(BIPUSH, '(');
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(C)Ljava/lang/StringBuilder;", false);

                for (Iterator<InDefinedShape> iterator = declaredMethods.iterator(); iterator.hasNext(); )
                {
                    InDefinedShape method = iterator.next();
                    String methodName = method.getName();
                    TypeDescription returnType = method.getReturnType().asErasure();
                    String returnTypeDescriptor = returnType.getDescriptor();

                    mv.visitLdcInsn(methodName);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitIntInsn(BIPUSH, '=');
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(C)Ljava/lang/StringBuilder;", false);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, internalImplName, methodName, returnTypeDescriptor);
                    if (returnType.isArray())
                    {
                        TypeDescription componentType = returnType.getComponentType();
                        if (componentType.isPrimitive())
                        {
                            mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "toString", "(" + returnTypeDescriptor + ")Ljava/lang/String;", false);
                        }
                        else
                        {
                            mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "toString", "([Ljava/lang/Object;)Ljava/lang/String;", false);
                        }
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    }
                    else if (returnType.isPrimitive())
                    {
                        String type = returnType.getName();
                        if (type.equals(double.class.getName()) || type.equals(long.class.getName()) || type.equals(float.class.getName()) ||
                            type.equals(boolean.class.getName()) || type.equals(char.class.getName()))
                        {
                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(" + returnTypeDescriptor + ")Ljava/lang/StringBuilder;",
                                               false);
                        }
                        else
                        {
                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
                        }
                    }
                    else
                    {

                        if (! returnType.equals(ForLoadedType.STRING))
                        {
                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;", false);
                        }
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
                                           false);
                    }
                    if (iterator.hasNext())
                    {
                        mv.visitLdcInsn(", ");
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
                                           false);
                    }
                }
                mv.visitIntInsn(BIPUSH, ')');
                Label l3 = new Label();
                mv.visitLabel(l3);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(C)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                Label l4 = new Label();
                mv.visitLabel(l4);
                mv.visitInsn(ARETURN);
                Label l5 = new Label();
                mv.visitLabel(l5);
                mv.visitLocalVariable("this", descriptorImplName, null, l0, l5, 0);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }

            {
                mv = cv.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, internalImplName, "$_toString", "Ljava/lang/String;");
                mv.visitInsn(ARETURN);
                Label l1 = new Label();
                mv.visitLabel(l1);
                mv.visitLocalVariable("this", descriptorImplName, null, l0, l1, 0);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }
            {
                mv = cv.visitMethod(ACC_PUBLIC, "annotationType", "()Ljava/lang/Class;", "()Ljava/lang/Class<+Ljava/lang/annotation/Annotation;>;",
                                    null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitLdcInsn(Type.getType(descriptor));
                mv.visitInsn(ARETURN);
                Label l1 = new Label();
                mv.visitLabel(l1);
                mv.visitLocalVariable("this", descriptorImplName, null, l0, l1, 0);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }
            {
                mv = cv.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitLdcInsn(Type.getType(descriptor));
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethods", "()[Ljava/lang/reflect/Method;", false);
                mv.visitVarInsn(ASTORE, 0);
                Label l1 = new Label();
                mv.visitLabel(l1);
                mv.visitTypeInsn(NEW, "java/util/HashMap");
                mv.visitInsn(DUP);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitInsn(ARRAYLENGTH);
                mv.visitMethodInsn(INVOKESPECIAL, "java/util/HashMap", "<init>", "(I)V", false);
                mv.visitFieldInsn(PUTSTATIC, internalImplName, "DEFAULTS", "Ljava/util/Map;");
                Label l2 = new Label();
                mv.visitLabel(l2);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitVarInsn(ASTORE, 1);
                mv.visitVarInsn(ALOAD, 1);
                mv.visitInsn(ARRAYLENGTH);
                mv.visitVarInsn(ISTORE, 2);
                mv.visitInsn(ICONST_0);
                mv.visitVarInsn(ISTORE, 3);
                Label l3 = new Label();
                mv.visitLabel(l3);
                mv.visitFrame(F_FULL, 4, new Object[]{"[Ljava/lang/reflect/Method;", "[Ljava/lang/reflect/Method;", INTEGER, INTEGER}, 0, STACK);
                mv.visitVarInsn(ILOAD, 3);
                mv.visitVarInsn(ILOAD, 2);
                Label l4 = new Label();
                mv.visitJumpInsn(IF_ICMPGE, l4);
                mv.visitVarInsn(ALOAD, 1);
                mv.visitVarInsn(ILOAD, 3);
                mv.visitInsn(AALOAD);
                mv.visitVarInsn(ASTORE, 4);
                Label l5 = new Label();
                mv.visitLabel(l5);
                mv.visitVarInsn(ALOAD, 4);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Method", "getDefaultValue", "()Ljava/lang/Object;", false);
                mv.visitVarInsn(ASTORE, 5);
                Label l6 = new Label();
                mv.visitLabel(l6);
                mv.visitVarInsn(ALOAD, 5);
                Label l7 = new Label();
                mv.visitJumpInsn(IFNULL, l7);
                Label l8 = new Label();
                mv.visitLabel(l8);
                mv.visitFieldInsn(GETSTATIC, internalImplName, "DEFAULTS", "Ljava/util/Map;");
                mv.visitVarInsn(ALOAD, 4);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Method", "getName", "()Ljava/lang/String;", false);
                mv.visitVarInsn(ALOAD, 5);
                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
                mv.visitInsn(POP);
                mv.visitLabel(l7);
                mv.visitFrame(F_SAME, 0, null, 0, null);
                mv.visitIincInsn(3, 1);
                mv.visitJumpInsn(GOTO, l3);
                mv.visitLabel(l4);
                mv.visitFrame(F_CHOP, 3, null, 0, null);
                mv.visitLdcInsn(Type.getType(descriptor));
                mv.visitInvokeDynamicInsn("apply", "()Ljava/util/function/Function;",
                                          new Handle(H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                                                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;" +
                                                     "Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)" +
                                                     "Ljava/lang/invoke/CallSite;"),
                                          Type.getType("(Ljava/lang/Object;)Ljava/lang/Object;"),
                                          new Handle(H_NEWINVOKESPECIAL, internalImplName, "<init>", "(Ljava/util/Map;)V"),
                                          Type.getType("(Ljava/util/Map;)" + descriptor));
                mv.visitInvokeDynamicInsn("apply", "()Ljava/util/function/Function;",
                                          new Handle(H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                                                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;" +
                                                     "Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)" +
                                                     "Ljava/lang/invoke/CallSite;"),
                                          Type.getType("(Ljava/lang/Object;)Ljava/lang/Object;"),
                                          new Handle(H_INVOKESTATIC, internalImplName, "unproxy", "(" + descriptor + ")" + descriptorImplName),
                                          Type.getType("(" + descriptor + ")" + descriptor));
                mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(Qualifiers.class), "register",
                                   "(Ljava/lang/Class;Ljava/util/function/Function;Ljava/util/function/Function;)V", false);
                Label l9 = new Label();
                mv.visitLabel(l9);
                mv.visitInsn(RETURN);
                mv.visitLocalVariable("defaultValue", "Ljava/lang/Object;", null, l6, l7, 5);
                mv.visitLocalVariable("method", "Ljava/lang/reflect/Method;", null, l5, l7, 4);
                mv.visitLocalVariable("methods", "[Ljava/lang/reflect/Method;", null, l1, l9, 0);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
            }
            cv.visitEnd();
            return cv;
        }
    }
}
