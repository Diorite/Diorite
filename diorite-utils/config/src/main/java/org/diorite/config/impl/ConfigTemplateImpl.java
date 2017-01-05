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

package org.diorite.config.impl;

import javax.annotation.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.commons.lang3.tuple.Pair;

import org.diorite.commons.DioriteUtils;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ActionMatcherResult;
import org.diorite.config.Config;
import org.diorite.config.ConfigPropertyAction;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.MethodSignature;
import org.diorite.config.Property;
import org.diorite.config.impl.actions.ActionsRegistry;
import org.diorite.config.serialization.comments.DocumentComments;

public class ConfigTemplateImpl<T extends Config> implements ConfigTemplate<T>
{
    private final Class<T>                     type;
    private       String                       name;
    private       CharsetEncoder               charsetEncoder;
    private       CharsetDecoder               charsetDecoder;
    private final ConfigImplementationProvider implementationProvider;

    private final List<String>                               order             = new ArrayList<>(10);
    private final Map<String, ConfigPropertyTemplateImpl<?>> mutableProperties = new ConcurrentSkipListMap<>(Comparator.comparingInt(this.order::indexOf));
    private final Map<String, ConfigPropertyTemplate<?>>     properties        = Collections.unmodifiableMap(this.mutableProperties);

    private final Map<ConfigPropertyAction, ConfigPropertyTemplate<?>> mutableActions = new ConcurrentHashMap<>(15);
    private final Map<ConfigPropertyAction, ConfigPropertyTemplate<?>> actions        = Collections.unmodifiableMap(this.mutableActions);

    private final Map<MethodSignature, ConfigPropertyAction> actionsDispatcher = new ConcurrentHashMap<>(10);

    private final DocumentComments comments = DocumentComments.create();

    public ConfigTemplateImpl(Class<T> type, ConfigImplementationProvider provider)
    {
        this.type = type;
        this.implementationProvider = provider;
        this.name = type.getSimpleName();
        this.charsetEncoder = StandardCharsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        this.charsetDecoder = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        this.setupActions();
    }

    public Map<MethodSignature, ConfigPropertyAction> getActionsDispatcher()
    {
        return this.actionsDispatcher;
    }

    private void scanInterface(@Nullable Class<?> type, LinkedList<MethodInvoker> methods)
    {
        if ((type == null) || (type == Config.class))
        {
            return;
        }
        for (Method method : type.getDeclaredMethods())
        {
            MethodInvoker methodInvoker = new MethodInvoker(method);
            if (methodInvoker.isPrivate() && (methodInvoker.getParameterCount() == 0) && (methodInvoker.getReturnType() != void.class) &&
                methodInvoker.isAnnotationPresent(Property.class))
            {
                methods.add(methodInvoker);
                continue;
            }
            if (methodInvoker.isPublic())
            {
                methods.add(methodInvoker);
            }
        }
        for (Class<?> subType : type.getInterfaces())
        {
            this.scanInterface(subType, methods);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private synchronized void setupActions()
    {
        LinkedList<MethodInvoker> methods = new LinkedList<>();
        this.scanInterface(this.type, methods);


        for (MethodInvoker methodInvoker : methods)
        {
            ConfigPropertyTemplateImpl templateImpl;
            Class<?> returnType = methodInvoker.getReturnType();
            String name;
            Method method = methodInvoker.getMethod();
            if (methodInvoker.isPrivate())
            {
                Property annotation = methodInvoker.getAnnotation(Property.class);
                if (annotation.name().isEmpty())
                {
                    name = methodInvoker.getName();
                }
                else
                {
                    name = annotation.name();
                }
                if (this.mutableProperties.containsKey(name))
                {
                    throw new RuntimeException("Duplicated property found in " + this.type + ": " + name + ", " + method);
                }
                methodInvoker.ensureAccessible();
                templateImpl = new ConfigPropertyTemplateImpl(returnType, methodInvoker.getGenericReturnType(), name, cfg -> methodInvoker.invoke(cfg));
            }
            else
            {
                Pair<ConfigPropertyAction, ActionMatcherResult> resultPair = ActionsRegistry.findMethod(method);
                if (resultPair == null)
                {
                    continue;
                }
                ConfigPropertyAction propertyAction = resultPair.getLeft();
                if (! propertyAction.getActionName().equals("get") && methodInvoker.isDefault())
                {
                    throw new RuntimeException("Unexpected default implementation of: " + method);
                }
                name = resultPair.getRight().getPropertyName();

                {
                    ConfigPropertyTemplateImpl<?> oldTemplate = this.mutableProperties.get(name);
                    if (oldTemplate != null)
                    {
                        templateImpl = oldTemplate;
                    }
                    else
                    {
                        templateImpl = new ConfigPropertyTemplateImpl(returnType, methodInvoker.getGenericReturnType(), name, cfg -> null);
                    }

                    if (propertyAction.getActionName().equals("get") && methodInvoker.isDefault())
                    {
                        methodInvoker.ensureAccessible();
                        try
                        {
                            MethodHandle methodHandle =
                                    DioriteReflectionUtils.createLookup(method.getDeclaringClass(), - 1).unreflectSpecial(method, method.getDeclaringClass());
                            templateImpl.setDefaultValueSupplier(cfg ->
                                                                 {
                                                                     try
                                                                     {
                                                                         return methodHandle.invoke(cfg);
                                                                     }
                                                                     catch (Throwable t)
                                                                     {
                                                                         throw DioriteUtils.sneakyThrow(t);
                                                                     }
                                                                 });
                        }
                        catch (Exception e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
                this.mutableActions.put(propertyAction, templateImpl);
                this.actionsDispatcher.put(new MethodSignature(method), propertyAction);
            }
            this.order.add(name);
            this.mutableProperties.put(name, templateImpl);
        }
    }

    @Override
    public Class<T> getConfigType()
    {
        return this.type;
    }

    @Override
    public DocumentComments getComments()
    {
        return this.comments;
    }

    @Override
    public Map<String, ? extends ConfigPropertyTemplate<?>> getProperties()
    {
        return this.properties;
    }

    @Override
    public Map<? extends ConfigPropertyAction, ? extends ConfigPropertyTemplate<?>> getActionsMap()
    {
        return this.actions;
    }

    @Override
    @Nullable
    public ConfigPropertyTemplate<?> getTemplateFor(String property)
    {
        return this.properties.get(property);
    }

    @Override
    @Nullable
    public ConfigPropertyTemplate<?> getTemplateFor(ConfigPropertyAction action)
    {
        return this.actions.get(action);
    }

    @Override
    public ConfigPropertyAction getActionFor(MethodSignature method)
    {
        return this.actionsDispatcher.get(method);
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public CharsetEncoder getDefaultEncoder()
    {
        return this.charsetEncoder;
    }

    @Override
    public void setDefaultEncoder(CharsetEncoder encoder)
    {
        this.charsetEncoder = encoder;
    }

    @Override
    public CharsetDecoder getDefaultDecoder()
    {
        return this.charsetDecoder;
    }

    @Override
    public void setDefaultDecoder(CharsetDecoder decoder)
    {
        this.charsetDecoder = decoder;
    }

    @Override
    public T create()
    {
        return this.implementationProvider.createImplementation(this.type, this);
    }
}