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

package org.diorite.config.impl.proxy;

import javax.annotation.Nullable;

import java.lang.reflect.Proxy;

import org.diorite.commons.arrays.DioriteArrayUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.impl.ConfigImplementationProvider;
import org.diorite.config.impl.ConfigTemplateImpl;

public class ProxyImplementationProvider implements ConfigImplementationProvider
{
    private static final ProxyImplementationProvider INSTANCE = new ProxyImplementationProvider();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Config> T createImplementation(Class<T> clazz, ConfigTemplate<T> template)
    {
        if (! clazz.isInterface())
        {
            throw new IllegalArgumentException("Class must be a interface!");
        }
        Class<?>[] interfaces = clazz.getInterfaces();
        interfaces = DioriteArrayUtils.prepend(interfaces, clazz);
        ConfigInvocationHandler configInvocationHandler = new ConfigInvocationHandler(template);
        Config proxyInstance = (Config) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, configInvocationHandler);
        configInvocationHandler.prepare(proxyInstance);
        return (T) proxyInstance;
    }

    public static ProxyImplementationProvider getInstance()
    {
        return INSTANCE;
    }

    @Nullable
    private static ConfigTemplateImpl<ConfigNode> nodeTemplate;

    static ConfigNode createNodeInstance()
    {
        if (nodeTemplate == null)
        {
            nodeTemplate = new ConfigTemplateImpl<>(ConfigNode.class, INSTANCE);
        }
        return INSTANCE.createImplementation(ConfigNode.class, nodeTemplate);
    }
}
