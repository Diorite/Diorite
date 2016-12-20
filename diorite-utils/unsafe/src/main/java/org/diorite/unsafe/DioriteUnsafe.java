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

import java.lang.annotation.Annotation;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.bytebuddy.agent.ByteBuddyAgent;

public final class DioriteUnsafe
{
    private DioriteUnsafe()
    {
    }

    private static final Instrumentation instrumentation;
    private static final Collection<RegisteredListener> listeners = Collections.synchronizedCollection(new ArrayList<>(2));

    static
    {
        try
        {
            instrumentation = ByteBuddyAgent.install();
        }
        catch (Exception e)
        {
            throw new InternalError("Can't get instrumentation instance!");
        }
    }

    /**
     * Returns instrumentation instance.
     *
     * @return instrumentation instance.
     */
    public static Instrumentation getInstrumentation()
    {
        return instrumentation;
    }

    /**
     * Register class transformer listener for given annotation type.
     *
     * @param annotation
     *         annotation type.
     * @param listener
     *         listener implementation.
     */
    public static void registerListenerForAnnotation(Class<? extends Annotation> annotation, ClassTransformerListener listener)
    {
        registerListener(new ListenerKeyAnnotation(annotation), listener);
    }

    /**
     * Register class transformer listener for given superclass, applies for all classes implementing or extending given class.
     *
     * @param supertype
     *         canonical name of super type.
     * @param listener
     *         listener implementation.
     */
    public static void registerListenerForSuperclass(String supertype, ClassTransformerListener listener)
    {
        registerListener(new ListenerKeyClass(supertype), listener);
    }

    /**
     * Register class transformer listener for given superclass, applies for all classes implementing or extending given class.
     *
     * @param supertype
     *         super type class.
     * @param listener
     *         listener implementation.
     */
    public static void registerListenerForSuperclass(Class<?> supertype, ClassTransformerListener listener)
    {
        registerListenerForSuperclass(supertype.getCanonicalName(), listener);
    }

    /**
     * Unregister listeners that are listening for classes with given annotation.
     *
     * @param annotation
     *         listeners to unregister.
     *
     * @return true if any listener was found and unregistered.
     */
    public static boolean unregisterListenersByAnnotation(Class<? extends Annotation> annotation)
    {
        return unregisterListeners(new ListenerKeyAnnotation(annotation));
    }

    /**
     * Unregister listeners that are listening for classes with given super class.
     *
     * @param superclass
     *         listeners to unregister.
     *
     * @return true if any listener was found and unregistered.
     */
    public static boolean unregisterListenersBySuperclass(Class<?> superclass)
    {
        return unregisterListenersBySuperclass(superclass.getCanonicalName());
    }

    /**
     * Unregister listeners that are listening for classes with given super class.
     *
     * @param superclass
     *         listeners to unregister.
     *
     * @return true if any listener was found and unregistered.
     */
    public static boolean unregisterListenersBySuperclass(String superclass)
    {
        return unregisterListeners(new ListenerKeyClass(superclass));
    }

    /**
     * Unregister given listener.
     *
     * @param listener
     *         lister to unregister.
     *
     * @return true if listener was found and unregistered.
     */
    public static boolean unregisterListeners(ClassTransformerListener listener)
    {
        return listeners.removeIf(
                registeredListener ->
                {
                    if (registeredListener.getListener().equals(listener))
                    {
                        instrumentation.removeTransformer(registeredListener);
                        return true;
                    }
                    return false;
                });
    }

    private static void registerListener(ListenerKey key, ClassTransformerListener listener)
    {
        RegisteredListener registeredListener = new RegisteredListener(key, instrumentation, listener, key.createPredicate());
        instrumentation.addTransformer(registeredListener, true);
        listeners.add(registeredListener);
    }

    private static boolean unregisterListeners(ListenerKey keyListener)
    {
        return listeners.removeIf(
                listener ->
                {
                    if (listener.getListenerKey().equals(keyListener))
                    {
                        instrumentation.removeTransformer(listener);
                        return true;
                    }
                    return false;
                });
    }
}
