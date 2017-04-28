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

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;

/**
 * Represents class that isn't yet loaded but it's transformed.
 */
public interface ClassDefinition
{
    /**
     * Returns raw bytecode of class. <br>
     * The content of this array must not be modified.
     *
     * @return raw bytecode of class.
     */
    byte[] getBytecode();

    /**
     * Returns module of this class.
     *
     * @return module of this class.
     */
    Module getModule();

    /**
     * Returns class loader of this class.
     *
     * @return class loader of this class.
     */
    @Nullable
    ClassLoader getClassLoader();

    /**
     * Returns protected domain of this class.
     *
     * @return protected domain of this class.
     */
    ProtectionDomain getProtectedDomain();

    /**
     * Apply new bytecode for this class. <br>
     * Method can be only used in {@link ClassTransformerListener#onClassDefinition(Instrumentation, ClassDefinition)}
     *
     * @param bytecode
     *         new bytecode of class.
     *
     * @throws IllegalStateException
     *         if used  after class is defined.
     */
    void setBytecode(byte[] bytecode);

    /**
     * Returns class reader for this class.
     *
     * @return class reader for this class.
     */
    ClassReader getClassReader();

    /**
     * Returns the name of the class, might be null if class is anonymous.
     *
     * @return class name.
     */
    @Nullable
    String getName();

    /**
     * Returns the internal name of the class (see
     * {@link Type#getInternalName() getInternalName}).
     *
     * @return the internal class name.
     */
    default String getInternalName()
    {
        return this.getClassReader().getClassName();
    }

    /**
     * Returns the internal of name of the super class (see{@link Type#getInternalName() getInternalName}). For interfaces, the super class is {@link Object}.
     *
     * @return the internal name of super class, or <tt>null</tt> for{@link Object} class.
     */
    default String getSuperClassInternalName()
    {
        return this.getClassReader().getSuperName();
    }

    /**
     * Returns the internal names of the class's interfaces (see {@link Type#getInternalName() getInternalName}).
     *
     * @return the array of internal names for all implemented interfaces or <tt>null</tt>.
     */
    default String[] getInterfacesInternalNames()
    {
        return this.getClassReader().getInterfaces();
    }
}
