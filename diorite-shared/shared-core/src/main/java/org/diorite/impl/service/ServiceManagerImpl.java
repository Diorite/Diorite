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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import org.diorite.plugin.Plugin;
import org.diorite.service.ServiceImplementation;
import org.diorite.service.ServiceManager;
import org.diorite.service.ServiceType;

public class ServiceManagerImpl implements ServiceManager
{
    private final Map<Class<?>, ServiceTypeImpl<?>>            knownTypes = Collections.synchronizedMap(new HashMap<>(20));
    private final Multimap<Class<?>, DelayedImplementation<?>> delayed    =
            Multimaps.synchronizedMultimap(Multimaps.newListMultimap(new HashMap<>(5), LinkedList::new));

    @Override
    public Collection<ServiceTypeImpl<?>> getKnownTypes()
    {
        return Collections.unmodifiableCollection(this.knownTypes.values());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public <T> ServiceType<T> registerServiceType(Plugin plugin, Class<T> type)
    {
        ServiceTypeImpl<T> tServiceType = new ServiceTypeImpl<>(plugin, type);
        this.knownTypes.put(type, tServiceType);
        for (DelayedImplementation implementation : this.delayed.removeAll(type))
        {
            this.provideService(implementation.plugin, implementation.service, implementation.serviceImplementation, implementation.forceActive);
        }
        return tServiceType;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> ServiceTypeImpl<T> getServiceType(Class<T> type)
    {
        return (ServiceTypeImpl<T>) this.knownTypes.get(type);
    }

    @Override
    @Nullable
    public <T> ServiceImplementation<T> provideService(Plugin plugin, Class<T> service, T serviceImplementation, boolean forceActive)
    {
        ServiceTypeImpl<T> serviceType = this.getServiceType(service);
        if (serviceType != null)
        {
            ServiceImplementationImpl<T> implementation = new ServiceImplementationImpl<>(plugin, serviceType, serviceImplementation);
            serviceType.addImplementation(implementation, forceActive);
            return implementation;
        }
        this.delayed.put(service, new DelayedImplementation<>(plugin, service, serviceImplementation, forceActive));
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T get(Class<T> serviceType)
    {
        ServiceTypeImpl<?> type = this.knownTypes.get(serviceType);
        if (type != null)
        {
            ServiceImplementationImpl<?> implementation = type.getImplementation();
            if (implementation != null)
            {
                return (T) implementation.getService();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> ServiceImplementation<T> getImplementationDetails(Class<T> serviceType, T serviceImplementation)
    {
        for (Entry<Class<?>, ServiceTypeImpl<?>> entry : this.knownTypes.entrySet())
        {
            if (! entry.getKey().isInstance(serviceImplementation))
            {
                continue;
            }
            for (ServiceImplementationImpl<?> implementation : entry.getValue().getAvailableImplementations())
            {
                if (implementation.getService() == serviceImplementation)
                {
                    return (ServiceImplementation<T>) implementation;
                }
            }
        }
        return null;
    }
}
