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

import org.diorite.plugin.Plugin;

/**
 * Used to manage all registered services, service type does not need to be registered before implementation, but implementation is not accessible before type
 * is registered.
 */
public interface ServiceManager
{
    /**
     * Returns collection of known service types.
     *
     * @return collection of known service types.
     */
    Collection<? extends ServiceType<?>> getKnownTypes();

    /**
     * Register new service type.
     *
     * @param plugin
     *         plugin providing service type.
     * @param type
     *         type of service to register.
     * @param <T>
     *         type of service to register.
     *
     * @return created service type instance.
     */
    <T> ServiceType<T> registerServiceType(Plugin plugin, Class<T> type);

    /**
     * Returns service type object.
     *
     * @param type
     *         type of service.
     * @param <T>
     *         type of service.
     *
     * @return service type object.
     */
    @Nullable
    <T> ServiceType<T> getServiceType(Class<T> type);

    /**
     * Register new service implementation.
     *
     * @param plugin
     *         plugin owning this implementation.
     * @param service
     *         implemented type.
     * @param serviceImplementation
     *         implementation of service.
     * @param forceActive
     *         if true this implementation will be set as active even if other plugin already implemented this service.
     * @param <T>
     *         service type.
     *
     * @return implementation details if available. (details are available only if service type is already registered)
     */
    @Nullable
    <T> ServiceImplementation<T> provideService(Plugin plugin, Class<T> service, T serviceImplementation, boolean forceActive);

    /**
     * Returns service implementation if any.
     *
     * @param serviceType
     *         type of service.
     * @param <T>
     *         type of service.
     *
     * @return implementation of service.
     */
    @Nullable
    <T> T get(Class<T> serviceType);

    /**
     * Returns implementation details for given service and implementation, returns null if service isn't registered.
     *
     * @param serviceType
     *         type of service.
     * @param serviceImplementation
     *         implementation.
     * @param <T>
     *         type of service.
     *
     * @return implementation details if service is registered.
     */
    @Nullable
    <T> ServiceImplementation<T> getImplementationDetails(Class<T> serviceType, T serviceImplementation);
}
