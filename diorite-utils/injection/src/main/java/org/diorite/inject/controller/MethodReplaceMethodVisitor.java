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

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.GeneratorAdapter;

import org.diorite.inject.utils.Constants;

final class MethodReplaceMethodVisitor extends GeneratorAdapter implements Opcodes
{
    private static final String CLASS_NAME = Constants.DI_LIBRARY.getInternalName();

    MethodReplaceMethodVisitor(MethodVisitor mv, int access, String name, String desc)
    {
        super(mv, access, name, desc);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
    {
        if ((opcode == INVOKESTATIC) && owner.equals(CLASS_NAME) && name.equals("inject") && desc.equals("()Ljava/lang/Object;"))
        {


//            lineNumber = InvokerGenerator.printMethods(mv, classData.getType().getInternalName(), fieldData.getBefore(), lineNumber);
//            lineNumber = AsmUtils.printLineNumber(mv, lineNumber);
//
//            mv.visitVarInsn(ALOAD, 0);
//            mv.visitVarInsn(ALOAD, 0);
//            AsmUtils.storeInt(mv, classData.getIndex());
//            AsmUtils.storeInt(mv, fieldData.getIndex());
//            mv.visitMethodInsn(INVOKESTATIC, InvokerGenerator.INJECTOR_CLASS, InvokerGenerator.INJECTOR_FIELD, InvokerGenerator.INJECTOR_FIELD_DESC, false);
//            TypeDescription fieldType = fieldData.getMember().getType().asErasure();
//            mv.visitTypeInsn(CHECKCAST, fieldType.getInternalName()); // skip cast check?
//            mv.visitFieldInsn(PUTFIELD, classData.getType().getInternalName(), fieldData.getMember().getName(), fieldType.getDescriptor());
//
//            InvokerGenerator.printMethods(mv, classData.getType().getInternalName(), fieldData.getAfter(), lineNumber);

            // not relaying the original instruction to super effectively removes the original
            // instruction, instead we're producing a different instruction:
            super.visitMethodInsn(Opcodes.INVOKESTATIC, "whatever/package/JSONReplacement",
                                  "jsonToString", "(Lorg/json/JSONObject;)Ljava/lang/String;", false);
        }
        else
        {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

    // all other, not overridden visit methods reproduce the original instructions
}