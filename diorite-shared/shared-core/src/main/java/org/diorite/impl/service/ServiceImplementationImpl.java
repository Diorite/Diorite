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

import java.util.Objects;

import org.diorite.plugin.Plugin;
import org.diorite.service.ServiceImplementation;
import org.diorite.service.ServiceType;

class ServiceImplementationImpl<T> implements ServiceImplementation<T>
{
    private final Plugin             plugin;
    private final ServiceTypeImpl<T> type;
    private final T                  service;

    ServiceImplementationImpl(Plugin plugin, ServiceTypeImpl<T> type, T service)
    {
        this.plugin = plugin;
        this.type = type;
        this.service = service;
    }

    @Override
    public ServiceType<T> getType()
    {
        return this.type;
    }

    @Override
    public T getService()
    {
        return this.service;
    }

    @Override
    public Plugin getProvidingPlugin()
    {
        return this.plugin;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ServiceImplementationImpl))
        {
            return false;
        }
        ServiceImplementationImpl<?> that = (ServiceImplementationImpl<?>) o;
        return Objects.equals(this.plugin, that.plugin) &&
               Objects.equals(this.type, that.type) &&
               Objects.equals(this.service, that.service);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.plugin, this.type, this.service);
    }
}
