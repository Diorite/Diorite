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

package org.diorite.event;

import javax.annotation.Nullable;

import java.util.Collection;

import org.diorite.plugin.Plugin;

/**
 * Special manager for controlling listeners.
 */
public interface EventManager
{
    /**
     * Publish given event to all listeners.
     *
     * @param event
     *         event to call.
     */
    void callEvent(Event event);

    /**
     * Register new listener.
     *
     * @param plugin
     *         owner of listener.
     * @param listener
     *         to register.
     */
    void registerListener(Plugin plugin, Listener listener);

    /**
     * Unregister given event listener from given listener.
     *
     * @param listener
     *         listener of event.
     * @param eventType
     *         event type to unregister.
     */
    void unregisterListener(Listener listener, Class<? extends Event> eventType);

    /**
     * Register previously unregistered event listener from given listener.
     *
     * @param listener
     *         listener of event.
     * @param eventType
     *         event type to register.
     */
    void reRegisterListener(Listener listener, Class<? extends Event> eventType);

    /**
     * Unregister given listener.
     *
     * @param listener
     *         listener to unregister.
     */
    void unregisterListener(Listener listener);

    @Nullable
    Plugin getListenerOwner(Listener listener);

    /**
     * Unregister all listeners of given type.
     *
     * @param type
     *         class of listeners to unregister.
     *
     * @return unregistered listeners.
     */
    Collection<? extends Listener> unregisterAllListeners(Class<? extends Listener> type);

    /**
     * Unregister all listeners from given plugin.
     *
     * @param plugin
     *         plugin of listeners to unregister.
     *
     * @return unregistered listeners.
     */
    Collection<? extends Listener> unregisterAllListeners(Plugin plugin);
}
