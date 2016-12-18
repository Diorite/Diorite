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

package org.diorite.inject;

import javax.annotation.Nullable;

import java.lang.instrument.Instrumentation;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.diorite.inject.controller.DefaultInjectionController;

import net.bytebuddy.agent.ByteBuddyAgent;

public final class Injection
{
//    public static final String DEFAULT_ID = "default";

    private static final Instrumentation instrumentation = ByteBuddyAgent.install();
//    private static final Map<String, DiController<?, ?, ?>> controllers     = new ConcurrentHashMap<>(5);
//    private static final List<DiController<?, ?, ?>>        controllersList = new CopyOnWriteArrayList<>();

    private static final DefaultInjectionController DEFAULT = new DefaultInjectionController();

    private Injection()
    {
    }

    public static void ensureLoaded()
    {
        Logger.getLogger("diorite-injection").info("Injection library is ready to use.");
    }

    public static DefaultInjectionController getController()
    {
        return DEFAULT;
    }

    // to prevent IDEs like intellij to see that inject always return null.
    private static final Supplier<Object> NULL = () -> null;

    /**
     * Any use of that method will be removed from bytecode and replaced with valid injection call. <br/>
     * Throws error on null injection.
     *
     * @param <T>
     *         type of injected field.
     *
     * @return always null.
     */
    @SuppressWarnings("unchecked")
    public static <T> T inject()
    {
        return (T) NULL.get();
    }

    /**
     * Any use of that method will be removed from bytecode and replaced with valid injection call.
     *
     * @param <T>
     *         type of injected field.
     *
     * @return always null.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T injectNullable()
    {
        return (T) NULL.get();
    }
}
