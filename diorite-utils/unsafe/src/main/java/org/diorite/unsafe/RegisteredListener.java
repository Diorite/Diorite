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

import javax.annotation.Nullable;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Module;
import java.security.ProtectionDomain;
import java.util.function.Predicate;

class RegisteredListener implements ClassFileTransformer
{
    private final ListenerKey                    listenerKey;
    private final Instrumentation                instrumentation;
    private final ClassTransformerListener       listener;
    private final Predicate<ClassDefinitionImpl> predicate;

    RegisteredListener(ListenerKey listenerKey, Instrumentation instrumentation, ClassTransformerListener listener, Predicate<ClassDefinitionImpl> predicate)
    {
        this.listenerKey = listenerKey;
        this.instrumentation = instrumentation;
        this.listener = listener;
        this.predicate = predicate;
    }

    public ListenerKey getListenerKey()
    {
        return this.listenerKey;
    }

    public ClassTransformerListener getListener()
    {
        return this.listener;
    }

    @Nullable
    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException
    {
        ClassDefinitionImpl classDefinition = new ClassDefinitionImpl(classfileBuffer, className, module, loader, protectionDomain);
        classDefinition.isValid = true;
        if (! this.predicate.test(classDefinition))
        {
            return null;
        }
        if (classBeingRedefined == null)
        {
            this.listener.onClassDefinition(this.instrumentation, classDefinition);
        }
        else
        {
            this.listener.onClassRetransform(this.instrumentation, classBeingRedefined, classDefinition);
        }
        classDefinition.isValid = false;
        byte[] bytecode = classDefinition.getBytecode();
        //noinspection ArrayEquality
        if (bytecode == classfileBuffer)
        {
            return null;
        }
        return bytecode;
    }

}
