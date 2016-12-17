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

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Module;
import java.security.ProtectionDomain;
import java.util.Objects;

import org.objectweb.asm.Opcodes;

import org.diorite.inject.InjectionController;

public abstract class AbstractClassTransformer<T extends InjectionController<?, ?, ?>> implements ClassFileTransformer, Opcodes
{
    private final T               controller;
    private final Instrumentation instrumentation;

    public AbstractClassTransformer(T controller, Instrumentation instrumentation)
    {
        this.controller = controller;
        this.instrumentation = instrumentation;
    }

    public abstract byte[] transform(T controller, Instrumentation instr, Module module, ClassLoader loader, String className, Class<?> clazz,
                                     ProtectionDomain pd, byte[] bytes) throws IllegalClassFormatException;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> clazz, ProtectionDomain pd, byte[] bytes) throws IllegalClassFormatException
    {
        return this.transform(this.controller, this.instrumentation, clazz.getModule(), loader, className, clazz, pd, bytes);
    }

    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> clazz, ProtectionDomain pd,
                            byte[] bytes) throws IllegalClassFormatException
    {
        return this.transform(this.controller, this.instrumentation, module, loader, className, clazz, pd, bytes);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof AbstractClassTransformer))
        {
            return false;
        }
        AbstractClassTransformer<?> that = (AbstractClassTransformer<?>) o;
        return Objects.equals(this.controller, that.controller) &&
               Objects.equals(this.instrumentation, that.instrumentation);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.controller, this.instrumentation);
    }
}
