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

import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Module;
import java.security.ProtectionDomain;

import org.diorite.inject.InjectionController;
import org.diorite.inject.asm.AbstractClassTransformer;
import org.diorite.unsafe.AsmUtils;

public final class TransformerOfInjectedClass extends AbstractClassTransformer<InjectionController<?, ?, ?>>
{
    public TransformerOfInjectedClass(InjectionController<?, ?, ?> controller, Instrumentation instrumentation)
    {
        super(controller, instrumentation);
    }

    @Override
    public byte[] transform(InjectionController<?, ?, ?> controller, Instrumentation instr, Module module, ClassLoader loader, String className, Class<?> clazz,
                            ProtectionDomain pd, byte[] bytes) throws IllegalClassFormatException
    {
        if (clazz == null)
        {
            return null;
        }
        ControllerClassData classData = (ControllerClassData) controller.getClassData(clazz);
        if (classData == null)
        {
            return null;
        }
        try
        {
            Transformer classTransformer = new Transformer(bytes, classData);
//            AsmUtils.printBytecodeSource(classTransformer.getWriter(), System.out);
            classTransformer.run();
            AsmUtils.printBytecodeSource(classTransformer.getWriter(), System.out);
            return classTransformer.getWriter().toByteArray();
        } catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
