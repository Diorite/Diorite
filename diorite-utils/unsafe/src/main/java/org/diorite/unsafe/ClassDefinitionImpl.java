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

import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;

class ClassDefinitionImpl implements ClassDefinition
{
    boolean isValid = false;
    private           byte[]           bytecode;
    private final     String           name;
    private final     Module           module;
    private final     ClassLoader      classLoader;
    private final     ProtectionDomain protectionDomain;
    @Nullable private ClassReader      classReader;

    ClassDefinitionImpl(byte[] bytecode, String name, Module module, ClassLoader classLoader, ProtectionDomain protectionDomain)
    {
        this.bytecode = bytecode;
        this.name = name;
        this.module = module;
        this.classLoader = classLoader;
        this.protectionDomain = protectionDomain;
    }

    @Override
    public byte[] getBytecode()
    {
        return this.bytecode;
    }

    @Override
    public Module getModule()
    {
        return this.module;
    }

    @Nullable
    @Override
    public ClassLoader getClassLoader()
    {
        return this.classLoader;
    }

    @Override
    public ProtectionDomain getProtectedDomain()
    {
        return this.protectionDomain;
    }

    @Override
    public void setBytecode(byte[] bytecode)
    {
        if (! this.isValid)
        {
            throw new IllegalStateException("Can't affect bytecode now!");
        }
        this.bytecode = bytecode;
        if (this.classReader != null)
        {
            this.classReader = new ClassReader(this.bytecode);
        }
    }

    @Override
    public ClassReader getClassReader()
    {
        if (this.classReader == null)
        {
            this.classReader = new ClassReader(this.bytecode);
        }
        return this.classReader;
    }

    @Nullable
    @Override
    public String getName()
    {
        return this.name;
    }
}
