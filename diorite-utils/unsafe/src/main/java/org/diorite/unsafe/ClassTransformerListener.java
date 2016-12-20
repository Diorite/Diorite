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

import java.lang.instrument.Instrumentation;

/**
 * Class load listener interface, allows for interacting with classes that aren't yet loaded. <br/>
 * It is based on java instrumentation, so all rules from instrumentations applies here too.
 */
public interface ClassTransformerListener
{
    /**
     * Invoked when instrumentation {@link java.lang.instrument.ClassFileTransformer} will find class matching this listener pattern. <br/>
     * Invoked only once, when class is defined.
     *
     * @param instrumentation
     *         instrumentation instance.
     * @param classDefinition
     *         class definition object, contains class bytecode and few utility methods.
     */
    default void onClassDefinition(Instrumentation instrumentation, ClassDefinition classDefinition)
    {
    }

    /**
     * Invoked when instrumentation {@link java.lang.instrument.ClassFileTransformer} will find class matching this listener pattern.<br/>
     * Invoked on each retransform.
     *
     * @param instrumentation
     *         instrumentation instance.
     * @param clazz
     *         class being redefined.
     * @param classDefinition
     *         class definition object, contains class bytecode and few utility methods.
     */
    default void onClassRetransform(Instrumentation instrumentation, Class<?> clazz, ClassDefinition classDefinition)
    {
    }
}
