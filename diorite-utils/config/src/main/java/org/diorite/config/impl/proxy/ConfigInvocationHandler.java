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
import java.io.Writer;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.exceptions.InvalidConfigValueTypeException;

public final class ConfigInvocationHandler implements InvocationHandler
{
    private final ConfigTemplate<?> template;

    private final Map<Method, Function<Object[], Object>> basicMethods = new ConcurrentHashMap<>(20);

    private Config config;

    public ConfigInvocationHandler(ConfigTemplate<?> template)
    {
        this.template = template;
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
                    return methodHandle.invokeWithArguments(args);
                }
                catch (Throwable throwable)
                {
                    throw new RuntimeException(throwable);
                }
            });
            return r;
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
                Method m = clazz.getMethod("get", String.class, Object.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String) args[0], args[1]));
            }
            {
                Method m = clazz.getMethod("get", String.class, Object.class, Class.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String) args[0], args[1], (Class) args[2]));
            }
            {
                Method m = clazz.getMethod("get", String.class, Class.class);
                this.registerMethod(clazz, m, (args) -> this.getImpl((String) args[0], (Class) args[1]));
            }
            {
                Method m = clazz.getMethod("set", String.class, Object.class);
                this.registerMethod(clazz, m, (args) -> this.setImpl((String) args[0], args[1]));
            }
            {
                Method m = clazz.getMethod("remove", String.class);
                this.registerMethod(clazz, m, (args) -> this.removeImpl((String) args[0]));
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
                this.registerMethod(clazz, m, (args) -> this.valuesImpl());
            }
            {
                Method m = clazz.getMethod("entrySet");
                this.registerMethod(clazz, m, (args) -> this.entrySetImpl());
            }

            {
                Method m = Object.class.getMethod("toString");
                this.registerMethod(m, (args) -> this.toStringImpl());
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
    }

    private final Map<String, ConfigNode> data = new LinkedHashMap<>(20);

    private ConfigTemplate<?> templateImpl()
    {
        return this.template;
    }

    private void fillWithDefaultsImpl()
    {

    }

    @Nullable
    private Object getImpl(String[] keys, @Nullable Object def)
    {
        if (keys.length == 0)
        {
            return def;
        }
        ConfigNode configNode = this.data.get(keys[0]);
        if (configNode == null)
        {
            return def;
        }
        Object value = configNode.getValue();
//        if (keys.length == 1)
//        {
//            return value;
//        }
//        for (int i = 1; i < keys.length; i++)
//        {
//            String key = keys[i];
//            if ()
//
//        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private <T> T getImpl(String[] key, @Nullable T def, Class<T> type)
    {
        Object value = this.getImpl(key, def);
        if (value == null)
        {
            return def;
        }
        Class<?> wrapperClass = DioriteReflectionUtils.getWrapperClass(type);
        if (wrapperClass.isInstance(value))
        {
            return (T) value;
        }
        throw new InvalidConfigValueTypeException("Expected value of " + type.getName() + " type, but got " + value + " instead.");
    }

    @Nullable
    private Object setImpl(String[] key, @Nullable Object value)
    {
        return null;
    }

    private Object removeImpl(String[] key)
    {
        return null;
    }

    private CharsetEncoder encoderImpl()
    {
        return null;
    }

    private void encoderImpl(CharsetEncoder encoder)
    {

    }

    private CharsetDecoder decoderImpl()
    {
        return null;
    }

    private void decoderImpl(CharsetDecoder decoder)
    {

    }

    @Nullable
    private File bindFileImpl()
    {
        return null;
    }

    private void bindFileImpl(@Nullable File file)
    {

    }

    private void saveImpl()
    {

    }

    private void saveImpl(@WillNotClose Writer writer)
    {

    }

    private void loadImpl()
    {

    }

    private void loadImpl(@WillNotClose Reader reader)
    {

    }

    private Config cloneImpl()
    {
        return null;
    }

    private int sizeImpl()
    {
        return 0;
    }

    private boolean isEmptyImpl()
    {
        return false;
    }

    private boolean containsKeyImpl(String[] key)
    {
        return false;
    }

    private boolean containsValueImpl(Object value)
    {
        return false;
    }

    private void clearImpl()
    {

    }

    private Set<String> keySetImpl()
    {
        return null;
    }

    private Collection<Object> valuesImpl()
    {
        return null;
    }

    private Set<Entry<String, Object>> entrySetImpl()
    {
        return null;
    }

    private String toStringImpl()
    {
        return "toString!";
    }

    private int hashCodeImpl()
    {
        return 0;
    }

    private boolean equalsImpl(Object object)
    {
        return false;
    }

    @Nullable
    private Object getImpl(String key)
    {
        return this.getImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), null, Object.class);
    }

    @Nullable
    private Object getImpl(String key, @Nullable Object def)
    {
        return this.getImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), def, Object.class);
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

    @Nullable
    private Object setImpl(String key, @Nullable Object value)
    {
        return this.setImpl(StringUtils.splitPreserveAllTokens(key), value);
    }

    private Object removeImpl(String key)
    {
        return this.removeImpl(StringUtils.splitPreserveAllTokens(key));
    }

    private boolean containsKeyImpl(String key)
    {
        return this.containsKeyImpl(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR));
    }

}
