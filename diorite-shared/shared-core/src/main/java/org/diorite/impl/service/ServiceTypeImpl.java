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

package org.diorite.impl.service;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.diorite.commons.sets.ConcurrentSet;
import org.diorite.plugin.Plugin;
import org.diorite.service.ServiceImplementation;
import org.diorite.service.ServiceType;

class ServiceTypeImpl<T> implements ServiceType<T>
{
    private final Plugin   owner;
    private final Class<T> serviceClass;
    private final Collection<ServiceImplementationImpl<T>> implementations             = new ConcurrentSet<>();
    private final Collection<ServiceImplementationImpl<T>> implementationsUnmodifiable = Collections.unmodifiableCollection(this.implementations);

    private @Nullable ServiceImplementationImpl<T> selected;

    ServiceTypeImpl(Plugin owner, Class<T> serviceClass)
    {
        this.owner = owner;
        this.serviceClass = serviceClass;
    }

    @Override
    public Plugin getOwner()
    {
        return this.owner;
    }

    @Override
    public Class<T> getServiceClass()
    {
        return this.serviceClass;
    }

    @Override
    public Collection<ServiceImplementationImpl<T>> getAvailableImplementations()
    {
        return this.implementationsUnmodifiable;
    }

    @Nullable
    @Override
    public ServiceImplementationImpl<T> getImplementation()
    {
        return this.selected;
    }

    @Override
    public void selectImplementation(ServiceImplementation<T> implementation) throws IllegalStateException
    {
        if (! (implementation instanceof ServiceImplementationImpl))
        {
            throw new IllegalStateException("Unsupported implementation type.");
        }
        if (implementation.getType() != this)
        {
            throw new IllegalArgumentException("Invalid implementation.");
        }
        this.selected = (ServiceImplementationImpl<T>) implementation;
    }

    void addImplementation(ServiceImplementationImpl<T> implementation, boolean forceActive)
    {
        for (ServiceImplementationImpl<T> impl : this.implementations)
        {
            if (impl.getService() == implementation.getService())
            {
                throw new IllegalStateException("Service implementation already registered.");
            }
        }
        this.implementations.add(implementation);
        if (forceActive || (this.selected == null))
        {
            this.selected = implementation;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ServiceTypeImpl))
        {
            return false;
        }
        ServiceTypeImpl<?> that = (ServiceTypeImpl<?>) o;
        return Objects.equals(this.serviceClass, that.serviceClass);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.serviceClass);
    }
}
