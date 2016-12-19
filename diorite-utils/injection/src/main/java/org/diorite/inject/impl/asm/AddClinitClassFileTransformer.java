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

package org.diorite.inject.impl.asm;

import javax.annotation.Nullable;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Module;
import java.security.ProtectionDomain;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import org.diorite.inject.InjectionController;
import org.diorite.inject.impl.controller.Controller;
import org.diorite.inject.impl.utils.Constants;

public class AddClinitClassFileTransformer extends AbstractClassTransformer
{
    public AddClinitClassFileTransformer(Controller controller, Instrumentation instrumentation)
    {
        super(controller, instrumentation);
    }

    @Nullable
    @Override
    public byte[] transform(Controller controller, Instrumentation instr, Module module, @Nullable ClassLoader loader, String className,
                            @Nullable Class<?> clazz, ProtectionDomain pd, byte[] bytecode) throws IllegalClassFormatException
    {
        // skip basic java classes
        if (loader == null)
        {
            return null;
        }
        ClassReader cr = new ClassReader(bytecode);
        ClassWriter cw = new ClassWriter(0);
        ClinitClassVisitor classVisitor = new ClinitClassVisitor(cw);
        cr.accept(classVisitor, 0);
        if (! classVisitor.added)
        {
            return null;
        }
        return cw.toByteArray();
    }

    static class ClinitClassVisitor extends ClassVisitor implements Opcodes
    {
        boolean injectable   = false;
        boolean clinitExists = false;
        boolean added        = false;

        String name;

        ClinitClassVisitor(ClassWriter classWriter)
        {
            super(ASM6, classWriter);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
        {
            this.name = name;
            super.visit(version, access, name, signature, superName, interfaces);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            if (name.equals(InjectionController.STATIC_BLOCK_NAME))
            {
                this.clinitExists = true;
            }
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (this.clinitExists && this.injectable)
            {
                this.added = true;
                mv = this.appendCode(mv);
            }
            return mv;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible)
        {
            if (desc.equals(Constants.INJECTABLE_CLASS.getDescriptor()))
            {
                this.injectable = true;
            }
            return super.visitAnnotation(desc, visible);
        }

        @Override
        public void visitEnd()
        {
            if (this.injectable && ! this.clinitExists)
            {
                MethodVisitor mv = super.visitMethod(ACC_STATIC, InjectionController.STATIC_BLOCK_NAME, "()V", null, null);
                mv.visitCode();

                mv = this.appendCode(mv);

                mv.visitInsn(RETURN);
                mv.visitMaxs(2, 0);
                mv.visitEnd();
                this.added = true;
            }
            super.visitEnd();
        }

        private MethodVisitor appendCode(MethodVisitor mv)
        {
            mv.visitMethodInsn(INVOKESTATIC, Constants.INJECTION_LIBRARY.getInternalName(), "getController",
                               "()" + Constants.CONTROLLER.getDescriptor(), false);
            mv.visitLdcInsn(Type.getType("L" + this.name + ";"));
            mv.visitMethodInsn(INVOKEINTERFACE, Constants.CONTROLLER.getInternalName(), "addClassData",
                               "(Ljava/lang/Class;)V", true);
            return mv;
        }
    }
}
