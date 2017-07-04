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
import javax.script.ScriptException;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import org.diorite.commons.DioriteUtils;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ActionMatcherResult;
import org.diorite.config.Config;
import org.diorite.config.ConfigManager;
import org.diorite.config.ConfigPropertyAction;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.MethodSignature;
import org.diorite.config.Property;
import org.diorite.config.ValidatorFunction;
import org.diorite.config.annotations.GroovyValidator;
import org.diorite.config.annotations.HelperMethod;
import org.diorite.config.annotations.ToKeyMapperFunction;
import org.diorite.config.annotations.ToStringMapperFunction;
import org.diorite.config.annotations.Validator;
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
    private final Map<String, ConfigPropertyTemplateImpl<?>> mutableProperties = new ConcurrentHashMap<>(10);
    private final Map<String, ConfigPropertyTemplate<?>>     properties        = Collections.unmodifiableMap(this.mutableProperties);
    private @Nullable Map<String, ConfigPropertyTemplate<?>> orderedProperties;

    private final Map<ConfigPropertyActionInstance, ConfigPropertyTemplate<?>> mutableActions = new ConcurrentHashMap<>(15);
    private final Map<ConfigPropertyActionInstance, ConfigPropertyTemplate<?>> actions        = Collections.unmodifiableMap(this.mutableActions);
    private @Nullable Map<ConfigPropertyActionInstance, ConfigPropertyTemplate<?>> orderedActions;

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
            if ((methodInvoker.isPrivate() || methodInvoker.isDefault()) && (methodInvoker.getParameterCount() == 0) &&
                (methodInvoker.getReturnType() != void.class) && methodInvoker.isAnnotationPresent(Property.class))
            {
                methods.add(methodInvoker);
                continue;
            }
            if (methodInvoker.isAnnotationPresent(Validator.class))
            {
                if (methodInvoker.isDefault() || methodInvoker.isPrivate() || methodInvoker.isStatic())
                {
                    if (methodInvoker.getParameterCount() == 1)
                    {
                        methods.add(methodInvoker);
                        continue;
                    }
                    else if (methodInvoker.isStatic() && ((methodInvoker.getParameterCount() == 2) || (methodInvoker.getParameterCount() == 1)))
                    {
                        Parameter[] parameters = methodInvoker.getParameters();
                        if (parameters.length == 1)
                        {
                            methods.add(methodInvoker);
                            continue;
                        }
                        if (parameters[0].getType().isAssignableFrom(this.type) || parameters[1].getType().isAssignableFrom(this.type))
                        {
                            methods.add(methodInvoker);
                            continue;
                        }
                    }
                }
                throw this.throwInvalidValidatorMethodException(methodInvoker);
            }
            if (methodInvoker.isPublic())
            {
                methods.add(methodInvoker);
                continue;
            }
            if (methodInvoker.isAnnotationPresent(ToKeyMapperFunction.class) || methodInvoker.isAnnotationPresent(ToStringMapperFunction.class))
            {
                methods.add(methodInvoker);
                continue;
            }
        }
        for (Class<?> subType : type.getInterfaces())
        {
            this.scanInterface(subType, methods);
        }
    }

    private IllegalStateException throwInvalidValidatorMethodException(MethodInvoker methodInvoker)
    {
        throw new IllegalStateException("Invalid validator method! Validator method should be non static method with single argument or static method" +
                                        " with one or two arguments where second one is config instance.\n    Instead this method was found: " + methodInvoker +
                                        "\n    Example methods:\n" +
                                        "        private T validateName(T name){...}\n" +
                                        "        default void validateSomething(T something){...}\n" +
                                        "        static void validateAge(T age){...}\n" +
                                        "        static T validateNickname(ConfigType config, T nickname){...} (any order)");
    }

    private IllegalStateException throwInvalidStringMapperMethodException(MethodInvoker methodInvoker)
    {
        throw new IllegalStateException("This isn't valid to string mapper function (" + methodInvoker + "), valid function must have signature matching: \n" +
                                        "- 'String anyName(KeyType)'\n" +
                                        "- 'static String anyName(KeyType)'\n" +
                                        "- 'static String anyName(Config, KeyType)'\n" +
                                        "- 'static String anyName(KeyType, Config)`");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void scanStringMapper(MethodInvoker methodInvoker, Map<String, BiFunction<Config, Object, String>> toStringMappers)
    {
        if (methodInvoker.getReturnType() != String.class)
        {
            throw throwInvalidStringMapperMethodException(methodInvoker);
        }
        BiFunction func;
        if (methodInvoker.getParameterCount() == 1)
        {
            if (methodInvoker.isStatic())
            {
                func = (cfg, obj) -> methodInvoker.invokeWith(obj);
            }
            else
            {
                func = (cfg, obj) -> methodInvoker.invokeWith(cfg, obj);
            }
        }
        else
        {
            if ((methodInvoker.getParameterCount() != 2) || ! methodInvoker.isStatic())
            {
                throw throwInvalidStringMapperMethodException(methodInvoker);
            }
            Class<?>[] parameterTypes = methodInvoker.getParameterTypes();
            if (parameterTypes[0].isAssignableFrom(this.type))
            {
                func = (cfg, obj) -> methodInvoker.invokeWith(cfg, obj);
            }
            else if (parameterTypes[1].isAssignableFrom(this.type))
            {
                func = (cfg, obj) -> methodInvoker.invokeWith(obj, cfg);
            }
            else
            {
                throw throwInvalidStringMapperMethodException(methodInvoker);
            }
        }
        methodInvoker.ensureAccessible();
        ToStringMapperFunction annotation = methodInvoker.getAnnotation(ToStringMapperFunction.class);
        String property = annotation.property();
        if (property.isEmpty())
        {
            throw new IllegalStateException("Found " + ToStringMapperFunction.class.getSimpleName() + " as `" + methodInvoker +
                                            "` bur property name is missing!");
        }
        if (! annotation.value().isEmpty())
        {
            throw new IllegalStateException("Found " + ToStringMapperFunction.class.getSimpleName() + " as `" + methodInvoker + "` for '" + property +
                                            "' but annotation already contains mapping code: `" + annotation.value() + "`");
        }
        if (toStringMappers.put(property, func) != null)
        {
            throw new IllegalStateException("Duplicated " + ToStringMapperFunction.class.getSimpleName() + " for '" + property + "' in: " + this.type);
        }
    }

    private IllegalStateException throwInvalidKeyMapperMethodException(MethodInvoker methodInvoker)
    {
        throw new IllegalStateException("This isn't valid to key mapper function (" + methodInvoker + "), valid function must have signature matching: \n" +
                                        "- 'KeyType anyName(String)'\n" +
                                        "- 'static KeyType anyName(String)'\n" +
                                        "- 'static KeyType anyName(Config, String)'\n" +
                                        "- 'static KeyType anyName(String, Config)'");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void scanKeyMapper(MethodInvoker methodInvoker, Map<String, BiFunction<Config, String, Object>> toKeyMappers)
    {
        if (methodInvoker.getReturnType() == void.class)
        {
            throw throwInvalidKeyMapperMethodException(methodInvoker);
        }
        Class<?>[] parameterTypes = methodInvoker.getParameterTypes();
        BiFunction func;
        if (! methodInvoker.isStatic())
        {
            if (parameterTypes.length != 1)
            {
                throw throwInvalidKeyMapperMethodException(methodInvoker);
            }
            func = (cfg, str) -> methodInvoker.invokeWith(cfg, str);
        }
        else
        {
            if (parameterTypes.length == 1)
            {
                func = (cfg, str) -> methodInvoker.invokeWith(str);
            }
            else if (parameterTypes.length == 2)
            {
                if (parameterTypes[0].isAssignableFrom(this.type))
                {
                    func = (cfg, str) -> methodInvoker.invokeWith(cfg, str);
                }
                else if (parameterTypes[1].isAssignableFrom(this.type))
                {
                    func = (cfg, str) -> methodInvoker.invokeWith(str, cfg);
                }
                else
                {
                    throw throwInvalidKeyMapperMethodException(methodInvoker);
                }
            }
            else
            {
                throw throwInvalidKeyMapperMethodException(methodInvoker);
            }
        }
        ToKeyMapperFunction annotation = methodInvoker.getAnnotation(ToKeyMapperFunction.class);
        String property = methodInvoker.getAnnotation(ToKeyMapperFunction.class).property();
        if (property.isEmpty())
        {
            throw new IllegalStateException("Found " + ToKeyMapperFunction.class.getSimpleName() + " as `" + methodInvoker +
                                            "` bur property name is missing!");
        }
        if (! annotation.value().isEmpty())
        {
            throw new IllegalStateException("Found " + ToKeyMapperFunction.class.getSimpleName() + " as `" + methodInvoker + "` for '" + property +
                                            "' but annotation already contains mapping code: `" + annotation.value() + "`");
        }
        if (toKeyMappers.put(property, func) != null)
        {
            throw new IllegalStateException("Duplicated " + ToKeyMapperFunction.class.getSimpleName() + " for '" + property + "' in: " + this.type);
        }
    }

    @SuppressWarnings("unchecked")
    private <X> void processGroovyMappers(MethodInvoker methodInvoker, ConfigPropertyTemplateImpl<X> template)
    {
        if (! methodInvoker.isAnnotationPresent(ToKeyMapperFunction.class) && ! methodInvoker.isAnnotationPresent(ToStringMapperFunction.class))
        {
            return;
        }
        GroovyScriptEngineImpl groovy = (GroovyScriptEngineImpl) ConfigManager.get().getGroovy();
        if (methodInvoker.isAnnotationPresent(ToKeyMapperFunction.class))
        {
            ToKeyMapperFunction ann = methodInvoker.getAnnotation(ToKeyMapperFunction.class);
            String source = "java.util.function.BiFunction<Config, String, Object> func = { Config config, String x -> " + ann.value() + "}\nreturn func;";
            try
            {
                BiFunction<Config, String, X> validatorFunction = (BiFunction<Config, String, X>) groovy.eval(source);
                template.setToKeyMapper(validatorFunction);
            }
            catch (ScriptException e)
            {
                throw new RuntimeException("Can't compile toKeyMapperFunctionScript (" + methodInvoker + ") script for: " + template.getName() + " in " +
                                           this.type.getCanonicalName() + "\n\n" +
                                           source + "\n====================", e);
            }
        }
        if (methodInvoker.isAnnotationPresent(ToStringMapperFunction.class))
        {
            ToStringMapperFunction ann = methodInvoker.getAnnotation(ToStringMapperFunction.class);
            String source = "java.util.function.BiFunction<Config, Object, String> func = { Config config, Object x -> " + ann.value() + "}\nreturn func;";
            try
            {
                BiFunction<Config, X, String> validatorFunction = (BiFunction<Config, X, String>) groovy.eval(source);
                template.setToStringMapper(validatorFunction);
            }
            catch (ScriptException e)
            {
                throw new RuntimeException("Can't compile toStringMapperFunctionScript (" + methodInvoker + ") script for: " + template.getName() + " in " +
                                           this.type.getCanonicalName() + "\n\n" +
                                           source + "\n====================", e);
            }
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

    private static final String VALIDATOR_SUFFIX = "Validator";

    @SuppressWarnings("unchecked")
    private <X> void processGroovyValidators(@Nullable GroovyValidator[] groovyValidators, ConfigPropertyTemplateImpl<X> template)
    {
        if ((groovyValidators == null) || (groovyValidators.length == 0))
        {
            return;
        }
        StringBuilder groovyScript = new StringBuilder(groovyValidators.length * 100);
        groovyScript.append("ValidatorFunction func = { def x, Config cfg -> \n");
        for (GroovyValidator groovyValidator : groovyValidators)
        {
            groovyScript.append("    if (!(").append(groovyValidator.isTrue()).append(")) throw new RuntimeException(\"\"\"")
                        .append(groovyValidator.elseThrow())
                        .append("\"\"\")\n");
        }
        groovyScript.append("    return x;\n}\nreturn func");
        String script = groovyScript.toString();
        GroovyScriptEngineImpl groovy = (GroovyScriptEngineImpl) ConfigManager.get().getGroovy();
        try
        {
            ValidatorFunction<Config, X> validatorFunction = (ValidatorFunction<Config, X>) groovy.eval(groovyScript.toString());
            template.appendValidator(validatorFunction);
        }
        catch (ScriptException e)
        {
            throw new RuntimeException("Can't compile validator script for: " + template.getName() + " in " + this.type.getCanonicalName() + "\n\n" +
                                       groovyScript.toString() + "\n====================", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <X> ValidatorFunction<Config, X> createValidator(MethodInvoker methodInvoker, ConfigPropertyTemplateImpl<X> template)
    {
        methodInvoker.ensureAccessible();
        boolean isVoid = methodInvoker.getReturnType() == void.class;
        if (! isVoid)
        {
            if (! template.getRawType().isAssignableFrom(methodInvoker.getReturnType()))
            {
                throw this.throwInvalidValidatorMethodException(methodInvoker);
            }
        }
        Parameter[] parameters = methodInvoker.getParameters();
        if (methodInvoker.isStatic())
        {
            if (parameters.length == 1)
            {
                if (isVoid)
                {
                    return ValidatorFunction.ofSimple((data, cfg) -> methodInvoker.invoke(null, data));
                }
                return (data, cfg) -> (X) methodInvoker.invoke(null, data);
            }
            else if (parameters.length == 2)
            {
                if (parameters[1].getType().isAssignableFrom(this.type) && parameters[0].getType().isAssignableFrom(template.getRawType()))
                {
                    if (isVoid)
                    {
                        return ValidatorFunction.ofSimple((data, cfg) -> methodInvoker.invoke(null, data, cfg));
                    }
                    return (data, cfg) -> (X) methodInvoker.invoke(null, data, cfg);
                }
                else if (parameters[0].getType().isAssignableFrom(this.type) && parameters[1].getType().isAssignableFrom(template.getRawType()))
                {
                    if (isVoid)
                    {
                        return ValidatorFunction.ofSimple((data, cfg) -> methodInvoker.invoke(null, cfg, data));
                    }
                    return (data, cfg) -> (X) methodInvoker.invoke(null, cfg, data);
                }
                else
                {
                    throw this.throwInvalidValidatorMethodException(methodInvoker);
                }
            }
            else
            {
                throw this.throwInvalidValidatorMethodException(methodInvoker);
            }
        }
        if ((parameters.length != 1) || ! parameters[0].getType().isAssignableFrom(template.getRawType()))
        {
            throw this.throwInvalidValidatorMethodException(methodInvoker);
        }
        if (isVoid)
        {
            return ValidatorFunction.ofSimple((data, cfg) -> methodInvoker.invoke(cfg, data));
        }
        return (data, cfg) -> (X) methodInvoker.invoke(cfg, data);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void processValidators(MethodInvoker methodInvoker, Collection<String> properties, Set<Entry<String, MethodInvoker>> processedValidators)
    {
        if (properties.isEmpty())
        {
            String invokerName = methodInvoker.getName();
            if (invokerName.endsWith(VALIDATOR_SUFFIX))
            {
                invokerName = invokerName.substring(0, invokerName.length() - VALIDATOR_SUFFIX.length());
            }
            properties.add(invokerName);
        }
        for (Iterator<String> propIterator = properties.iterator(); propIterator.hasNext(); )
        {
            String property = propIterator.next();
            ConfigPropertyTemplateImpl<?> propertyTemplate = this.mutableProperties.get(property);
            if (propertyTemplate != null)
            {
                propIterator.remove();
                if (! processedValidators.add(Map.entry(property, methodInvoker)))
                {
                    continue;
                }
                ValidatorFunction<Config, ?> validator = this.createValidator(methodInvoker, propertyTemplate);
                propertyTemplate.appendValidator((ValidatorFunction) validator);
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void scanMethods(LinkedList<MethodInvoker> methods, Map<String, BiFunction<Config, String, Object>> toKeyMappers,
                             Map<String, BiFunction<Config, Object, String>> toStringMappers, Set<Entry<String, MethodInvoker>> processedValidators,
                             Set<String> knownProperties)
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
            if (methodInvoker.isAnnotationPresent(Validator.class))
            {
                Validator annotation = methodInvoker.getAnnotation(Validator.class);
                Collection<String> properties = new HashSet<>(Arrays.asList(annotation.value()));
                this.processValidators(methodInvoker, properties, processedValidators);
                if (properties.isEmpty())
                {
                    iterator.remove();
                }
                continue;
            }
            if (methodInvoker.isPrivate() || methodInvoker.isStatic())
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
            }
            if (methodInvoker.isAnnotationPresent(Property.class) && (methodInvoker.isPrivate() || methodInvoker.isDefault()))
            {
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
                if (propertyAction.getActionName().equals("set") || propertyAction.getActionName().equals("get"))
                {
                    this.processGroovyValidators(methodInvoker.getAnnotationsByType(GroovyValidator.class), template);
                    this.processGroovyMappers(methodInvoker, template);
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
        this.scanMethods(methods, toKeyMappers, toStringMappers, processedValidators, knownProperties);
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

        Map<String, BiFunction<Config, String, Object>> toKeyMappers = new HashMap<>(methods.size());
        Map<String, BiFunction<Config, Object, String>> toStringMappers = new HashMap<>(methods.size());
        this.scanMethods(new LinkedList<>(methods), toKeyMappers, toStringMappers, new HashSet<>(10), new HashSet<>(10));

        for (Entry<String, BiFunction<Config, String, Object>> entry : toKeyMappers.entrySet())
        {
            ConfigPropertyTemplateImpl<?> propertyTemplate = this.mutableProperties.get(entry.getKey());
            if (propertyTemplate == null)
            {
                throw new IllegalStateException("Unknown property: " + entry.getKey());
            }
            //noinspection unchecked
            propertyTemplate.setToKeyMapper((BiFunction) entry.getValue());
        }
        for (Entry<String, BiFunction<Config, Object, String>> entry : toStringMappers.entrySet())
        {
            ConfigPropertyTemplateImpl<?> propertyTemplate = this.mutableProperties.get(entry.getKey());
            if (propertyTemplate == null)
            {
                throw new IllegalStateException("Unknown property: " + entry.getKey());
            }
            //noinspection unchecked
            propertyTemplate.setToStringMapper((BiFunction) entry.getValue());
        }

        List<ConfigPropertyTemplateImpl<?>> templates = new ArrayList<>(this.mutableProperties.size());
        templates.addAll(this.mutableProperties.values());
        this.mutableProperties.clear();
        this.order.clear();
        Map<String, ConfigPropertyTemplate<?>> orderedProperties = new LinkedHashMap<>(templates.size());
        for (ConfigPropertyTemplateImpl<?> template : templates)
        {
            template.init();
            this.mutableProperties.put(template.getName(), template);
            this.order.add(template.getName());
            orderedProperties.put(template.getName(), template);
        }
        this.orderedProperties = Collections.unmodifiableMap(orderedProperties);

        Map<ConfigPropertyActionInstance, ConfigPropertyTemplate<?>> actions = this.actions;
        Multimap<ConfigPropertyTemplate<?>, ConfigPropertyActionInstance> actionsMultimap =
                Multimaps.newMultimap(new LinkedHashMap<>(this.order.size()), () -> new ArrayList<>(5));
        for (Entry<ConfigPropertyActionInstance, ConfigPropertyTemplate<?>> entry : actions.entrySet())
        {
            actionsMultimap.put(entry.getValue(), entry.getKey());
        }
        Map<ConfigPropertyActionInstance, ConfigPropertyTemplate<?>> orderedActions = new LinkedHashMap<>(actions.size());
        for (ConfigPropertyTemplate<?> configPropertyTemplate : this.orderedProperties.values())
        {
            Collection<ConfigPropertyActionInstance> actionInstances = actionsMultimap.get(configPropertyTemplate);
            for (ConfigPropertyActionInstance actionInstance : actionInstances)
            {
                orderedActions.put(actionInstance, configPropertyTemplate);
            }
        }
        this.orderedActions = Collections.unmodifiableMap(orderedActions);
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
    public Map<String, ? extends ConfigPropertyTemplate<?>> getOrderedProperties()
    {
        if (this.orderedProperties == null)
        {
            throw new IllegalStateException("Template not prepared yet!");
        }
        return this.orderedProperties;
    }

    @Override
    public Map<? extends ConfigPropertyActionInstance, ? extends ConfigPropertyTemplate<?>> getActionsMap()
    {
        return this.actions;
    }

    @Override
    public Map<? extends ConfigPropertyActionInstance, ? extends ConfigPropertyTemplate<?>> getOrderedActionsMap()
    {
        if (this.orderedActions == null)
        {
            throw new IllegalStateException("Template not prepared yet!");
        }
        return this.orderedActions;
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
        return this.implementationProvider.createImplementation(this);
    }
}