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
import java.lang.reflect.Type;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.commons.lang3.tuple.Pair;

import org.diorite.commons.DioriteUtils;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ActionMatcherResult;
import org.diorite.config.Config;
import org.diorite.config.ConfigPropertyAction;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.MethodSignature;
import org.diorite.config.Property;
import org.diorite.config.annotations.HelperMethod;
import org.diorite.config.annotations.ToKeyMapperFunction;
import org.diorite.config.annotations.ToStringMapperFunction;
import org.diorite.config.impl.actions.ActionsRegistry;
import org.diorite.config.serialization.Serialization;
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

    private final Map<ConfigPropertyActionInstance, ConfigPropertyTemplate<?>> mutableActions = new ConcurrentHashMap<>(15);
    private final Map<ConfigPropertyActionInstance, ConfigPropertyTemplate<?>> actions        = Collections.unmodifiableMap(this.mutableActions);

    private final Map<MethodSignature, ConfigPropertyActionInstance> actionsDispatcher = new ConcurrentHashMap<>(10);

    private final DocumentComments comments;

    public ConfigTemplateImpl(Class<T> type, ConfigImplementationProvider provider)
    {
        this.type = type;
        this.comments = Serialization.getInstance().getCommentsManager().getComments(type);
        this.implementationProvider = provider;
        this.name = type.getSimpleName();
        this.charsetEncoder = StandardCharsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        this.charsetDecoder = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        this.setupActions();
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
            if (methodInvoker.isAnnotationPresent(ToKeyMapperFunction.class) || methodInvoker.isAnnotationPresent(ToStringMapperFunction.class))
            {
                methods.add(methodInvoker);
            }
        }
        for (Class<?> subType : type.getInterfaces())
        {
            this.scanInterface(subType, methods);
        }
    }

    private void scanStringMapper(MethodInvoker methodInvoker, Map<String, MethodInvoker> toStringMappers)
    {
        if ((methodInvoker.getParameterCount() != 1) || (methodInvoker.getReturnType() != String.class))
        {
            throw new IllegalStateException("This isn't valid to string mapper function, valid function must have signature matching: 'String " +
                                            "anyName(KeyType)'");
        }
        String property = methodInvoker.getAnnotation(ToStringMapperFunction.class).property();
        if (toStringMappers.put(property, methodInvoker) != null)
        {
            throw new IllegalStateException("Duplicated " + ToStringMapperFunction.class.getSimpleName() + " for '" + property + "' in: " + this.type);
        }
    }

    private void scanKeyMapper(MethodInvoker methodInvoker, Map<String, MethodInvoker> toKeyMappers)
    {

        if ((methodInvoker.getParameterCount() != 0) || (methodInvoker.getParameterTypes()[0] != String.class) ||
            (methodInvoker.getReturnType() == void.class))
        {
            throw new IllegalStateException("This isn't valid to key mapper function, valid function must have signature matching: 'KeyType " +
                                            "anyName(String)'");
        }
        String property = methodInvoker.getAnnotation(ToKeyMapperFunction.class).property();
        if (toKeyMappers.put(property, methodInvoker) != null)
        {
            throw new IllegalStateException("Duplicated " + ToKeyMapperFunction.class.getSimpleName() + " for '" + property + "' in: " + this.type);
        }
    }

    private String extractName(MethodInvoker methodInvoker)
    {
        String name;
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
            throw new RuntimeException("Duplicated property found in " + this.type + ": " + name + ", " + methodInvoker.getMethod());
        }
        return name;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void scanMethods(LinkedList<MethodInvoker> methods, Map<String, MethodInvoker> toKeyMappers,
                             Map<String, MethodInvoker> toStringMappers, Set<String> knownProperties)
    {
        int sizeBefore = methods.size();
        for (Iterator<MethodInvoker> iterator = methods.iterator(); iterator.hasNext(); )
        {
            MethodInvoker methodInvoker = iterator.next();
            ConfigPropertyTemplateImpl template;
            Class<?> returnType = methodInvoker.getReturnType();
            Type genericReturnType = methodInvoker.getGenericReturnType();
            String name;
            Method method = methodInvoker.getMethod();
            if (methodInvoker.isPrivate())
            {
                if (methodInvoker.isAnnotationPresent(ToKeyMapperFunction.class))
                {
                    this.scanKeyMapper(methodInvoker, toKeyMappers);
                    iterator.remove();
                    continue;
                }
                if (methodInvoker.isAnnotationPresent(ToStringMapperFunction.class))
                {
                    this.scanStringMapper(methodInvoker, toStringMappers);
                    iterator.remove();
                    continue;
                }
                name = this.extractName(methodInvoker);

                methodInvoker.ensureAccessible();
                if (! knownProperties.add(name))
                {
                    throw new IllegalStateException("Duplicated property: " + name);
                }
                template = new ConfigPropertyTemplateImpl(this, returnType, genericReturnType, name, cfg -> methodInvoker.invoke(cfg), method);
            }
            else
            {
                if (methodInvoker.isDefault() && methodInvoker.isAnnotationPresent(HelperMethod.class))
                {
                    iterator.remove();
                    continue;
                }
                Pair<ConfigPropertyAction, ActionMatcherResult> resultPair = ActionsRegistry.findMethod(method, knownProperties::contains);
                if (resultPair == null)
                {
                    iterator.remove();
                    continue;
                }
                ConfigPropertyAction propertyAction = resultPair.getLeft();
                if (! propertyAction.getActionName().equals("get") && methodInvoker.isDefault())
                {
                    throw new RuntimeException("Unexpected default implementation of: " + method);
                }
                ActionMatcherResult matcherResult = resultPair.getRight();
                if (! matcherResult.isValidatedName())
                {
                    continue; // wait for validation.
                }
                name = matcherResult.getPropertyName();

                ConfigPropertyTemplateImpl<?> oldTemplate = this.mutableProperties.get(name);
                if (oldTemplate != null)
                {
                    template = oldTemplate;
                }
                else
                {
                    if (! propertyAction.declaresProperty())
                    {
                        continue;
                    }
                    if (! knownProperties.add(name))
                    {
                        throw new IllegalStateException("Duplicated property: " + name);
                    }
                    template = new ConfigPropertyTemplateImpl(this, returnType, genericReturnType, name, cfg -> null, method);
                }

                if (propertyAction.getActionName().equals("get") && methodInvoker.isDefault())
                {
                    this.defaultValueFromDefaultMethod(methodInvoker, template);
                }

                MethodSignature methodSignature = new MethodSignature(method);
                PropertyActionKey propertyActionKey = new PropertyActionKey(propertyAction, methodSignature);
                this.mutableActions.put(propertyActionKey, template);
                this.actionsDispatcher.put(methodSignature, propertyActionKey);
            }
            this.order.add(name);
            this.mutableProperties.put(name, template);
            iterator.remove();
        }
        if (methods.isEmpty())
        {
            return;
        }
        if (sizeBefore == methods.size())
        {
            throw new IllegalStateException("Can't create config template, can't find how to implement: " + methods);
        }
        this.scanMethods(methods, toKeyMappers, toStringMappers, knownProperties);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void defaultValueFromDefaultMethod(MethodInvoker methodInvoker, ConfigPropertyTemplateImpl template)
    {
        Method method = methodInvoker.getMethod();
        methodInvoker.ensureAccessible();
        try
        {
            MethodHandle methodHandle =
                    DioriteReflectionUtils.createLookup(method.getDeclaringClass(), - 1).unreflectSpecial(method, method.getDeclaringClass());
            template.setDefaultValueSupplier(cfg ->
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

    @SuppressWarnings({"rawtypes"})
    private synchronized void setupActions()
    {
        LinkedList<MethodInvoker> methods = new LinkedList<>();
        this.scanInterface(this.type, methods);

        Map<String, MethodInvoker> toKeyMappers = new HashMap<>(methods.size());
        Map<String, MethodInvoker> toStringMappers = new HashMap<>(methods.size());
        this.scanMethods(new LinkedList<>(methods), toKeyMappers, toStringMappers, new HashSet<>(10));

        for (Entry<String, MethodInvoker> entry : toKeyMappers.entrySet())
        {
            ConfigPropertyTemplateImpl<?> propertyTemplate = this.mutableProperties.get(entry.getKey());
            if (propertyTemplate == null)
            {
                throw new IllegalStateException("Unknown property: " + entry.getKey());
            }
            propertyTemplate.setToKeyMapper(entry.getValue());
        }
        for (Entry<String, MethodInvoker> entry : toStringMappers.entrySet())
        {
            ConfigPropertyTemplateImpl<?> propertyTemplate = this.mutableProperties.get(entry.getKey());
            if (propertyTemplate == null)
            {
                throw new IllegalStateException("Unknown property: " + entry.getKey());
            }
            propertyTemplate.setToStringMapper(entry.getValue());
        }

        List<ConfigPropertyTemplateImpl<?>> templates = new ArrayList<>(this.mutableProperties.size());
        templates.addAll(this.mutableProperties.values());
        this.mutableProperties.clear();
        this.order.clear();
        for (ConfigPropertyTemplateImpl<?> template : templates)
        {
            template.init();
            this.mutableProperties.put(template.getName(), template);
            this.order.add(template.getName());
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
    public Map<? extends ConfigPropertyActionInstance, ? extends ConfigPropertyTemplate<?>> getActionsMap()
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
    public ConfigPropertyTemplate<?> getTemplateFor(ConfigPropertyActionInstance action)
    {
        return this.actions.get(action);
    }

    @Override
    public ConfigPropertyActionInstance getActionFor(MethodSignature method)
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