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

package org.diorite.service;

import javax.annotation.Nullable;

import java.util.Collection;

import org.diorite.commons.objects.Nameable;
import org.diorite.plugin.Plugin;

/**
 * Represents service type, each type may contains multiple implementations, but only one is active.
 */
public interface ServiceType<T> extends Nameable
{
    /**
     * Returns service type owner plugin.
     *
     * @return service type owner plugin.
     */
    Plugin getOwner();

    /**
     * Returns service class that must be implemented by providers.
     *
     * @return service class that must be implemented by providers.
     */
    Class<T> getServiceClass();

    /**
     * Returns collection of available implementations.
     *
     * @return collection of available implementations.
     */
    Collection<? extends ServiceImplementation<T>> getAvailableImplementations();

    /**
     * Returns selected implementation of service.
     *
     * @return selected implementation of service.
     */
    @Nullable
    ServiceImplementation<T> getImplementation();

    /**
     * Selects implementation to use, it must be registered in manager or {@link IllegalStateException} will be thrown.
     *
     * @param implementation
     *         implementation to select.
     *
     * @throws IllegalStateException
     *         if implementation is not registered.
     */
    void selectImplementation(ServiceImplementation<T> implementation) throws IllegalStateException;

    /**
     * Returns name of service.
     *
     * @return name of service.
     */
    @Override
    default String getName()
    {
        return this.getServiceClass().getSimpleName();
    }
}
