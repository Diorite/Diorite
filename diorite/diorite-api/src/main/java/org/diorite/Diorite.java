/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite;

import org.diorite.commons.function.supplier.Supplier;
import org.diorite.event.EventManager;

/**
 * Diorite server interface, with all main methods to control diorite server instance, there can be only single instance od diorite server at runtime!
 */
public interface Diorite
{
    /**
     * Default server port.
     */
    int DEFAULT_PORT = 25565;

    /**
     * Returns diorite config instance.
     *
     * @return diorite config instance.
     */
    DioriteConfig getConfig();

    /**
     * Returns event manager.
     *
     * @return event manager.
     */
    EventManager getEventManager();

    /**
     * Returns true if server is running.
     *
     * @return true if server is running.
     */
    boolean isRunning();

    /**
     * Returns true if debug is enabled.
     *
     * @return true if debug is enabled.
     */
    boolean isEnabledDebug();

    /**
     * Prints throwable to logger but only if debug is enabled.
     *
     * @param throwable
     *         throwable to print.
     */
    void debug(Throwable throwable);

    /**
     * Prints object to logger but only if debug is enabled.
     *
     * @param object
     *         object to print.
     */
    void debug(Object object);

    /**
     * Runs runnable but only if debug is enabled.
     *
     * @param runnable
     *         runnable to run.
     */
    void debugRun(Runnable runnable);

    /**
     * Runs supplier and print its result to logger but only if debug is enabled.
     *
     * @param supplier
     *         supplier to run.
     */
    void debugRun(Supplier<?> supplier);

    /**
     * Returns current minecraft version for main protocol implementation.
     *
     * @return current minecraft version for main protocol implementation.
     */
    static String getMinecraftVersion()
    {
        return "1.11.2";
    }

    /**
     * Returns version of diorite.
     *
     * @return version of diorite.
     */
    String getVersion();

    /**
     * Get diorite server instance from static content.
     *
     * @return diorite instance.
     */
    static Diorite getDiorite()
    {
        Diorite diorite = DioriteInstanceHolder.diorite;
        if (diorite == null)
        {
            throw new IllegalStateException("Instance not ready yet.");
        }
        return diorite;
    }

    /**
     * Used by diorite to set static instance throws exception if used after init.
     *
     * @param dioriteInstance
     *         instance to set.
     */
    static void setDioriteInstance(Diorite dioriteInstance)
    {
        if (DioriteInstanceHolder.diorite != null)
        {
            throw new IllegalStateException("Instance already set!");
        }
        DioriteInstanceHolder.diorite = dioriteInstance;
    }
}
