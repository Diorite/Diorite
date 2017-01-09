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
import javax.annotation.WillNotClose;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.yaml.snakeyaml.nodes.Node;

import org.diorite.commons.io.StringBuilderWriter;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.Config;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.MethodSignature;
import org.diorite.config.SimpleConfig;
import org.diorite.config.exceptions.ConfigLoadException;
import org.diorite.config.exceptions.ConfigSaveException;
import org.diorite.config.impl.ConfigPropertyValueImpl;
import org.diorite.config.impl.NestedNodesHelper;
import org.diorite.config.serialization.Serialization;

public final class ConfigInvocationHandler implements InvocationHandler
{
    final ConfigTemplate<?> template;

    final Map<Method, Function<Object[], Object>>      basicMethods        = new ConcurrentHashMap<>(20);
    final Map<String, ConfigPropertyValueImpl<Object>> predefinedValues    = new LinkedHashMap<>(20);
    final Map<String, SimpleConfig>                    dynamicValues       = Collections.synchronizedMap(new LinkedHashMap<>(10));
    final Map<String, Node>                            simpleDynamicValues = Collections.synchronizedMap(new LinkedHashMap<>(10));

    @SuppressWarnings("NullableProblems")
    private volatile CharsetEncoder charsetEncoder;
    @SuppressWarnings("NullableProblems")
    private volatile CharsetDecoder charsetDecoder;
    @Nullable
    private volatile File           bindFile;

    @SuppressWarnings("NullableProblems")
    private Config config;

    public ConfigInvocationHandler(ConfigTemplate<?> template)
    {
        this.template = template;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private ConfigPropertyValueImpl<Object> getOrCreatePredefinedValue(ConfigPropertyTemplate template)
    {
        ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(template.getName());
        if (propertyValue != null)
        {
            return propertyValue;
        }
        propertyValue = new ConfigPropertyValueImpl<>(this.config, template, template.getDefault(this.config));
        this.predefinedValues.put(template.getName(), propertyValue);
        return propertyValue;
    }

    @Nullable
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if (this.config != proxy)
        {
            throw new IllegalStateException("This isn't handler of thia config instance!");
        }
        return this.invoke((Config) proxy, method, args);
    }

    @Nullable
    private Object invoke(Config proxy, Method method, Object[] args) throws Throwable
    {
        Function<Object[], Object> function = this.basicMethods.get(method);
        if (function != null)
        {
            return function.apply(args);
        }
        // check for dispatcher:
        ConfigPropertyActionInstance actionFor = this.template.getActionFor(new MethodSignature(method));
        if (actionFor != null)
        {
            MethodInvoker methodInvoker = new MethodInvoker(method);
            ConfigPropertyTemplate<?> templateFor = this.template.getTemplateFor(actionFor);
            if (templateFor != null)
            {
                ConfigPropertyValueImpl<Object> propertyValue = this.getOrCreatePredefinedValue(templateFor);
                if (methodInvoker.getReturnType() == void.class)
                {
                    this.registerVoidMethod(method, arg -> actionFor.perform(methodInvoker, propertyValue, arg));
                    actionFor.perform(methodInvoker, propertyValue, args);
                    return null;
                }
                else
                {
                    this.registerMethod(method, arg -> actionFor.perform(methodInvoker, propertyValue, arg));
                    return actionFor.perform(methodInvoker, propertyValue, args);
                }
            }
        }

        // check for default implementation
        try
        {
            MethodHandle methodHandle =
                    DioriteReflectionUtils.createLookup(method.getDeclaringClass(), - 1).unreflectSpecial(method, method.getDeclaringClass()).bindTo(proxy);
            Object r = methodHandle.invokeWithArguments(args);
            this.registerMethod(method, (arg) ->
            {
                try
                {
                    return methodHandle.invokeWithArguments(arg);
                }
                catch (Throwable throwable)
                {
                    throw new RuntimeException(throwable);
                }
            });
            return r;
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Config class " + this.template.getConfigType() + " executed unknown method: " + method, e);
        }
    }

    private void registerMethod(Method method, Function<Object[], Object> func)
    {
        this.basicMethods.put(method, func);
    }

    private void registerVoidMethod(Method method, Consumer<Object[]> func)
    {
        this.basicMethods.put(method, (a) ->
        {
            func.accept(a);
            return null;
        });
    }

    private void registerMethod(Class<? extends Config> clazz, Method method, Function<Object[], Object> func)
    {
        this.registerMethod(method, func);
        for (Class<?> aClass : clazz.getInterfaces())
        {
            Method m;
            try
            {
                m = aClass.getMethod(method.getName(), method.getParameterTypes());
            }
            catch (Exception e)
            {
                continue;
            }
            this.registerMethod(m, func);
        }
    }

    private void registerVoidMethod(Class<? extends Config> clazz, Method method, Consumer<Object[]> func)
    {
        this.registerMethod(clazz, method, (a) ->
        {
            func.accept(a);
            return null;
        });
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void prepare(Config config)
    {
        this.config = config;

        for (ConfigPropertyTemplate<?> propertyTemplate : this.template.getProperties().values())
        {
            this.getOrCreatePredefinedValue(propertyTemplate);
        }

        // load defaults
        this.fillWithDefaultsImpl();

        try
        {
            Class<? extends Config> clazz = config.getClass();
            {
                Method m = clazz.getMethod("template");
                this.registerMethod(clazz, m, (args) -> this.templateImpl());
            }
            {
                Method m = clazz.getMethod("fillWithDefaults");
                this.registerVoidMethod(clazz, m, (args) -> this.fillWithDefaultsImpl());
            }
            {
                Method m = clazz.getMethod("get", String.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String) args[0]));
            }
            {
                Method m = clazz.getMethod("get", String[].class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String[]) args[0], null, null));
            }
            {
                Method m = clazz.getMethod("get", String.class, Object.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String) args[0], args[1]));
            }
            {
                Method m = clazz.getMethod("get", String[].class, Object.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String[]) args[0], args[1], null));
            }
            {
                Method m = clazz.getMethod("get", String.class, Object.class, Class.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String) args[0], args[1], (Class) args[2]));
            }
            {
                Method m = clazz.getMethod("get", String[].class, Object.class, Class.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String[]) args[0], args[1], (Class) args[2]));
            }
            {
                Method m = clazz.getMethod("get", String.class, Class.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String) args[0], (Class) args[1]));
            }
            {
                Method m = clazz.getMethod("get", String[].class, Class.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String[]) args[0], (Class) args[1], null));
            }
            {
                Method m = clazz.getMethod("set", String.class, Object.class);
                this.registerVoidMethod(clazz, m, (args) -> this.setImpl((String) args[0], args[1]));
            }
            {
                Method m = clazz.getMethod("set", String[].class, Object.class);
                this.registerVoidMethod(clazz, m, (args) -> this.setImpl((String[]) args[0], args[1]));
            }
            {
                Method m = clazz.getMethod("remove", String.class);
                this.registerMethod(clazz, m, (args) -> this.removeImpl((String) args[0]));
            }
            {
                Method m = clazz.getMethod("remove", String[].class);
                this.registerMethod(clazz, m, (args) -> this.removeImpl((String[]) args[0]));
            }
            {
                Method m = clazz.getMethod("encoder");
                this.registerMethod(clazz, m, (args) -> this.encoderImpl());
            }
            {
                Method m = clazz.getMethod("encoder", CharsetEncoder.class);
                this.registerVoidMethod(clazz, m, (args) -> this.encoderImpl((CharsetEncoder) args[0]));
            }
            {
                Method m = clazz.getMethod("decoder");
                this.registerMethod(clazz, m, (args) -> this.decoderImpl());
            }
            {
                Method m = clazz.getMethod("decoder", CharsetDecoder.class);
                this.registerVoidMethod(clazz, m, (args) -> this.decoderImpl((CharsetDecoder) args[0]));
            }
            {
                Method m = clazz.getMethod("bindFile");
                this.registerMethod(clazz, m, (args) -> this.bindFileImpl());
            }
            {
                Method m = clazz.getMethod("bindFile", File.class);
                this.registerVoidMethod(clazz, m, (args) -> this.bindFileImpl((File) args[0]));
            }
            {
                Method m = clazz.getMethod("save");
                this.registerVoidMethod(clazz, m, (args) -> this.saveImpl());
            }
            {
                Method m = clazz.getMethod("save", Writer.class);
                this.registerVoidMethod(clazz, m, (args) -> this.saveImpl((Writer) args[0]));
            }
            {
                Method m = clazz.getMethod("load");
                this.registerVoidMethod(clazz, m, (args) -> this.loadImpl());
            }
            {
                Method m = clazz.getMethod("load", Reader.class);
                this.registerVoidMethod(clazz, m, (args) -> this.loadImpl((Reader) args[0]));
            }
            {
                Method m = clazz.getMethod("clone");
                this.registerMethod(clazz, m, (args) -> this.cloneImpl());
            }
            {
                Method m = clazz.getMethod("size");
                this.registerMethod(clazz, m, (args) -> this.sizeImpl());
            }
            {
                Method m = clazz.getMethod("isEmpty");
                this.registerMethod(clazz, m, (args) -> this.isEmptyImpl());
            }
            {
                Method m = clazz.getMethod("containsKey", String.class);
                this.registerMethod(clazz, m, (args) -> this.containsKeyImpl((String) args[0]));
            }
            {
                Method m = clazz.getMethod("containsKey", String[].class);
                this.registerMethod(clazz, m, (args) -> this.containsKeyImpl((String[]) args[0]));
            }
            {
                Method m = clazz.getMethod("containsValue", Object.class);
                this.registerMethod(clazz, m, (args) -> this.containsValueImpl(args[0]));
            }
            {
                Method m = clazz.getMethod("clear");
                this.registerVoidMethod(clazz, m, (args) -> this.clearImpl());
            }
            {
                Method m = clazz.getMethod("keySet");
                this.registerMethod(clazz, m, (args) -> this.keySetImpl());
            }
            {
                Method m = clazz.getMethod("values");
                this.registerMethod(clazz, m, (args) -> this.valuesImpl(false));
            }
            {
                Method m = clazz.getMethod("entrySet");
                this.registerMethod(clazz, m, (args) -> this.entrySetImpl(false));
            }

            {
                Method m = Object.class.getMethod("toString");
                this.registerMethod(m, (args) -> this.toStringImpl(true));
            }
            {
                Method m = Object.class.getMethod("hashCode");
                this.registerMethod(m, (args) -> this.hashCodeImpl());
            }
            {
                Method m = Object.class.getMethod("equals", Object.class);
                this.registerMethod(m, (args) -> this.equalsImpl(args[0]));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        config.encoding(StandardCharsets.UTF_8);
    }

    private ConfigTemplate<?> templateImpl()
    {
        return this.template;
    }

    private void fillWithDefaultsImpl()
    {
        for (ConfigPropertyValueImpl<Object> propertyValue : this.predefinedValues.values())
        {
            propertyValue.setPropertyValue(propertyValue.getDefault());
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private <T> T getImpl(String[] keys, @Nullable T def, @Nullable Class<T> type)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given");
        }
        Serialization serialization = Serialization.getGlobal();

        String key = keys[0];
        if (keys.length == 1)
        {
            ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(key);
            if (propertyValue != null)
            {
                Object rawValue = propertyValue.getPropertyValue();

                Class<?> ensureNonPrimitive;
                if (type != null)
                {
                    ensureNonPrimitive = DioriteReflectionUtils.getWrapperClass(type);
                }
                else
                {
                    ensureNonPrimitive = null;
                }
                if ((ensureNonPrimitive == null) || (rawValue == null) || ensureNonPrimitive.isInstance(rawValue))
                {
                    return (T) rawValue;
                }

                ConfigPropertyTemplate<Object> prop = propertyValue.getProperty();
                throw new IllegalArgumentException("Can't change type of predefined value: '" + prop.getName() + "' (" + prop.getGenericType() + ") to: " +
                                                   type);
            }
            Node node = this.simpleDynamicValues.get(key);
            if (node == null)
            {
                SimpleConfig configNode = this.dynamicValues.get(key);
                if (configNode != null)
                {
                    return (T) configNode;
                }
                return def;
            }
            if (type == null)
            {
                return serialization.fromYamlNode(node);
            }
            else
            {
                return serialization.fromYamlNode(node, type);
            }
        }
        String[] newPath = new String[keys.length - 1];
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1);

        ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(key);
        if (propertyValue != null)
        {
            return (T) propertyValue.get(newPath);
        }
        SimpleConfig configNode = this.dynamicValues.get(key);
        if (configNode != null)
        {
            if (type == null)
            {
                return (T) configNode.get(newPath, def);
            }
            return configNode.get(newPath, def, type);
        }
        Node node = this.simpleDynamicValues.get(key);
        if (node != null)
        {
            Object o;
            if (type == null)
            {
                o = serialization.fromYamlNode(node);
            }
            else
            {
                o = serialization.fromYamlNode(node, type);
            }
            if (o == null)
            {
                return def;
            }
            return (T) NestedNodesHelper.get(o, newPath);
        }
        return def;
    }

    private void setImpl(String[] keys, @Nullable Object value)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given");
        }
        Serialization serialization = Serialization.getGlobal();

        String key = keys[0];
        if (keys.length == 1)
        {
            ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(key);
            if (propertyValue != null)
            {
                propertyValue.setPropertyValue(value);
                return;
            }
            this.simpleDynamicValues.put(key, serialization.toYamlNode(value));
            return;
        }
        String[] newPath = new String[keys.length - 1];
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1);

        ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(key);
        if (propertyValue != null)
        {
            propertyValue.set(newPath, value);
            return;
        }
        Node node = this.simpleDynamicValues.get(key);
        if (node != null)
        {
            Object o = serialization.fromYamlNode(node);
            Validate.notNull(o);
            NestedNodesHelper.set(o, newPath, value);
            this.simpleDynamicValues.put(key, serialization.toYamlNode(o));
            return;
        }
        SimpleConfig configNode = this.dynamicValues.computeIfAbsent(key, k -> ProxyImplementationProvider.createNodeInstance());
        configNode.set(newPath, value);
    }

    @Nullable
    private Object removeImpl(String[] keys)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given");
        }
        Serialization serialization = Serialization.getGlobal();

        String key = keys[0];
        if (keys.length == 1)
        {
            ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(key);
            if (propertyValue != null)
            {
                Object rawValue = propertyValue.getPropertyValue();
                propertyValue.setPropertyValue(propertyValue.getDefault());
                return rawValue;
            }
            Node node = this.simpleDynamicValues.remove(key);
            if (node == null)
            {
                return this.dynamicValues.remove(key);
            }
            return serialization.fromYamlNode(node);
        }
        String[] newPath = new String[keys.length - 1];
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1);

        ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(key);
        if (propertyValue != null)
        {
            return propertyValue.remove(newPath);
        }
        SimpleConfig configNode = this.dynamicValues.get(key);
        if (configNode != null)
        {
            return configNode.remove(newPath);
        }
        Node node = this.simpleDynamicValues.get(key);
        if (node != null)
        {
            Object o = serialization.fromYamlNode(node);
            if (o != null)
            {
                Object removed = NestedNodesHelper.remove(o, newPath);
                this.simpleDynamicValues.put(key, serialization.toYamlNode(o));
                return removed;
            }
        }
        return null;
    }

    private CharsetEncoder encoderImpl()
    {
        return this.charsetEncoder;
    }

    private void encoderImpl(CharsetEncoder encoder)
    {
        this.charsetEncoder = encoder;
    }

    private CharsetDecoder decoderImpl()
    {
        return this.charsetDecoder;
    }

    private void decoderImpl(CharsetDecoder decoder)
    {
        this.charsetDecoder = decoder;
    }

    @Nullable
    private File bindFileImpl()
    {
        return this.bindFile;
    }

    private void bindFileImpl(@Nullable File file)
    {
        this.bindFile = file;
    }

    private void saveImpl()
    {
        File bindFile = this.bindFile;
        if (bindFile == null)
        {
            throw new ConfigSaveException(this.template, null, "Config isn't bound to file!");
        }
        this.config.save(bindFile);
    }

    private void loadImpl()
    {
        File bindFile = this.bindFile;
        if (bindFile == null)
        {
            throw new ConfigLoadException(this.template, null, "Config isn't bound to file!");
        }
        this.config.load(bindFile);
    }

    private void saveImpl(@WillNotClose Writer writer)
    {
        Serialization.getGlobal().toYamlWithComments(this.config, writer, this.template.getComments());
    }

    private void loadImpl(@WillNotClose Reader reader)
    {
        Config fromYaml = Serialization.getGlobal().fromYaml(reader, this.template.getConfigType());
        if (fromYaml == null)
        {
            return;
        }
        ConfigInvocationHandler invocationHandler = (ConfigInvocationHandler) Proxy.getInvocationHandler(fromYaml);

        this.predefinedValues.putAll(invocationHandler.predefinedValues);
        this.dynamicValues.putAll(invocationHandler.dynamicValues);
        this.simpleDynamicValues.putAll(invocationHandler.simpleDynamicValues);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Config cloneImpl()
    {
        Config copy = ProxyImplementationProvider.getInstance().createImplementation(this.template.getConfigType(), (ConfigTemplate) this.template);
        ConfigInvocationHandler invocationHandler = (ConfigInvocationHandler) Proxy.getInvocationHandler(copy);

        invocationHandler.charsetDecoder = this.charsetDecoder;
        invocationHandler.charsetEncoder = this.charsetEncoder;
        invocationHandler.bindFile = this.bindFile;

        StringBuilderWriter writer =
                new StringBuilderWriter((this.simpleDynamicValues.size() * 100) + ((this.dynamicValues.size() + this.predefinedValues.size()) * 1000));
        this.saveImpl(writer);
        invocationHandler.loadImpl(new StringReader(writer.toString()));

//        for (Entry<String, ConfigPropertyValueImpl<Object>> valueEntry : this.predefinedValues.entrySet())
//        {
//            String key = valueEntry.getKey();
//            ConfigPropertyValueImpl<Object> value = valueEntry.getValue();
//            ConfigPropertyValueImpl<Object> copyValue = invocationHandler.getOrCreatePredefinedValue(value.getProperty());
//            copyValue.setRawValue(value.getRawValue());
//        }
//
//        for (Entry<String, ConfigNode> entry : this.dynamicValues.entrySet())
//        {
//            invocationHandler.dynamicValues.put(entry.getKey(), (ConfigNode) entry.getValue().clone());
//        }
//
//        for (Entry<String, Node> entry : this.simpleDynamicValues.entrySet())
//        {
//            // should be safe to use this same node instance, as it will be deserialized and get/set anyway.
//            invocationHandler.simpleDynamicValues.put(entry.getKey(), entry.getValue());
//        }

        return copy;
    }

    private boolean isEmptyImpl()
    {
        return this.predefinedValues.isEmpty() && this.dynamicValues.isEmpty() && this.simpleDynamicValues.isEmpty();
    }

    private boolean containsKeyImpl(String[] keys)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given");
        }
        Serialization serialization = Serialization.getGlobal();

        String key = keys[0];
        if (keys.length == 1)
        {
            ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(key);
            if (propertyValue != null)
            {
                return true;
            }
            Node node = this.simpleDynamicValues.get(key);
            if (node == null)
            {
                return this.dynamicValues.containsKey(key);
            }
            return true;
        }
        String[] newPath = new String[keys.length - 1];
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1);

        ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(key);
        if (propertyValue != null)
        {
            return true;
        }
        SimpleConfig configNode = this.dynamicValues.get(key);
        if (configNode != null)
        {
            return configNode.containsKey(newPath);
        }
        Node node = this.simpleDynamicValues.get(key);
        if (node != null)
        {
            Object o = serialization.fromYamlNode(node);
            if (o == null)
            {
                return false;
            }
            try
            {
                Object v = NestedNodesHelper.get(o, newPath);
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return false;
    }

    private boolean containsValueImpl(Object toCheck)
    {
        for (ConfigPropertyValueImpl<Object> value : this.predefinedValues.values())
        {
            if (Objects.equals(toCheck, value.getPropertyValue()))
            {
                return true;
            }
        }
        for (SimpleConfig configNode : this.dynamicValues.values())
        {
            if (Objects.equals(toCheck, configNode))
            {
                return true;
            }
        }
        for (Node node : this.simpleDynamicValues.values())
        {
            if (Objects.equals(toCheck, Serialization.getGlobal().fromYamlNode(node)))
            {
                return true;
            }
        }
        return false;
    }

    private int sizeImpl()
    {
        return this.predefinedValues.size() + this.dynamicValues.size() + this.simpleDynamicValues.size();
    }

    private void clearImpl()
    {
        for (ConfigPropertyValueImpl<Object> propertyValue : this.predefinedValues.values())
        {
            propertyValue.setPropertyValue(null);
        }
        this.dynamicValues.clear();
        this.simpleDynamicValues.clear();
    }

    private Set<String> keySetImpl()
    {
        Set<String> keys = new HashSet<>(20);
        keys.addAll(this.predefinedValues.keySet());
        keys.addAll(this.dynamicValues.keySet());
        keys.addAll(this.simpleDynamicValues.keySet());
        return keys;
    }

    private Collection<Object> valuesImpl(boolean raw)
    {
        Collection<Object> values = new ArrayList<>(20);
        for (ConfigPropertyValueImpl<Object> value : this.predefinedValues.values())
        {
            values.add(raw ? value.getRawValue() : value.getPropertyValue());
        }
        for (Node node : this.simpleDynamicValues.values())
        {
            values.add(Serialization.getGlobal().fromYamlNode(node));
        }
        values.addAll(this.dynamicValues.values());
        return values;
    }

    private Set<Entry<String, Object>> entrySetImpl(boolean raw)
    {
        Set<Entry<String, Object>> result = new HashSet<>(20);

        for (Entry<String, ConfigPropertyValueImpl<Object>> entry : this.predefinedValues.entrySet())
        {
            result.add(new SimpleEntry<>(entry.getKey(), raw ? entry.getValue().getRawValue() : entry.getValue().getPropertyValue()));
        }
        for (Entry<String, Node> entry : this.simpleDynamicValues.entrySet())
        {
            result.add(new SimpleEntry<>(entry.getKey(), Serialization.getGlobal().fromYamlNode(entry.getValue())));
        }
        for (Entry<String, SimpleConfig> entry : this.dynamicValues.entrySet())
        {
            result.add(new SimpleEntry<>(entry.getKey(), entry.getValue()));
        }

        return result;
    }

    private Map<String, Object> toMap(boolean raw)
    {
        Map<String, Object> result = new LinkedHashMap<>(20);

        for (Entry<String, ConfigPropertyValueImpl<Object>> entry : this.predefinedValues.entrySet())
        {
            result.put(entry.getKey(), raw ? entry.getValue().getRawValue() : entry.getValue().getPropertyValue());
        }
        for (Entry<String, Node> entry : this.simpleDynamicValues.entrySet())
        {
            result.put(entry.getKey(), Serialization.getGlobal().fromYamlNode(entry.getValue()));
        }
        for (Entry<String, SimpleConfig> entry : this.dynamicValues.entrySet())
        {
            result.put(entry.getKey(), ((ConfigInvocationHandler) Proxy.getInvocationHandler(entry.getValue())).toMap(raw));
        }

        return result;
    }

    private String toStringImpl(boolean raw)
    {
        ToStringBuilder builder = new ToStringBuilder(this.config);
        builder.append(this.template.getConfigType().getName());
        builder.append(this.bindFile);
        for (Entry<String, ConfigPropertyValueImpl<Object>> entry : this.predefinedValues.entrySet())
        {
            builder.append(entry.getKey(), raw ? entry.getValue().getRawValue() : entry.getValue().getPropertyValue());
        }
        for (Entry<String, Node> entry : this.simpleDynamicValues.entrySet())
        {
            builder.append(entry.getKey(), (Object) Serialization.getGlobal().fromYamlNode(entry.getValue()));
        }
        for (Entry<String, SimpleConfig> entry : this.dynamicValues.entrySet())
        {
            builder.append(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    private int hashCodeImpl()
    {
        return this.entrySetImpl(true).hashCode();
    }

    private boolean equalsImpl(Object object)
    {
        if (object == this)
        {
            return true;
        }
        if (! (object instanceof Config))
        {
            return false;
        }
        Config config = (Config) object;
        if (this.template.getConfigType().isInstance(config) && Proxy.isProxyClass(config.getClass()))
        {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(config);
            if (invocationHandler instanceof ConfigInvocationHandler)
            {
                return this.entrySetImpl(true).equals(((ConfigInvocationHandler) invocationHandler).entrySetImpl(true));
            }
        }
        return this.entrySetImpl(true).equals(config.entrySet());
    }

    @Nullable
    private Object getImpl(String key)
    {
        return this.getImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), null, null);
    }

    @Nullable
    private Object getImpl(String key, @Nullable Object def)
    {
        return this.getImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), def, null);
    }

    @Nullable
    private <T> T getImpl(String key, @Nullable T def, Class<T> type)
    {
        return this.getImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), def, type);
    }

    @Nullable
    private <T> T getImpl(String key, Class<T> type)
    {
        return this.getImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), null, type);
    }

    private void setImpl(String key, @Nullable Object value)
    {
        this.setImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), value);
    }

    @Nullable
    private Object removeImpl(String key)
    {
        return this.removeImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR));
    }

    private boolean containsKeyImpl(String key)
    {
        return this.containsKeyImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR));
    }

}
