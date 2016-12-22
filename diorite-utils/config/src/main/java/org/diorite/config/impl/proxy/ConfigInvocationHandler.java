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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigTemplate;

public final class ConfigInvocationHandler implements InvocationHandler
{
    private final ConfigTemplate<?> template;

    private final Map<Method, BiFunction<Config, Object[], Object>> basicMethods = new ConcurrentHashMap<>(20);

    public ConfigInvocationHandler(ConfigTemplate<?> template)
    {
        this.template = template;
    }

    @Nullable
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        return this.invoke((Config) proxy, method, args);
    }

    @Nullable
    private Object invoke(Config proxy, Method method, Object[] args) throws Throwable
    {
        BiFunction<Config, Object[], Object> function = this.basicMethods.get(method);
        if (function != null)
        {
            return function.apply(proxy, args);
        }
        // check for default implementation
        try
        {
            MethodHandle methodHandle =
                    DioriteReflectionUtils.createLookup(method.getDeclaringClass(), - 1).unreflectSpecial(method, method.getDeclaringClass()).bindTo(proxy);
            Object r = methodHandle.invokeWithArguments(args);
            this.registerMethod(method, (cfg, arg) ->
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

    private void registerMethod(Method method, BiFunction<Config, Object[], Object> func)
    {
        this.basicMethods.put(method, func);
    }

    private void registerVoidMethod(Method method, BiConsumer<Config, Object[]> func)
    {
        this.basicMethods.put(method, (c, a) ->
        {
            func.accept(c, a);
            return null;
        });
    }

    private void registerMethod(Class<? extends Config> clazz, Method method, BiFunction<Config, Object[], Object> func)
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

    private void registerVoidMethod(Class<? extends Config> clazz, Method method, BiConsumer<Config, Object[]> func)
    {
        this.registerMethod(clazz, method, (c, a) ->
        {
            func.accept(c, a);
            return null;
        });
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void prepare(Config config)
    {
        try
        {
            Class<? extends Config> clazz = config.getClass();
            // add default method handlers
//            for (Class<?> iClass : clazz.getInterfaces())
//            {
//                for (Method method : iClass.getMethods())
//                {
//                    if (! method.isDefault())
//                    {
//                        continue;
//                    }
//                    MethodHandle result = DioriteReflectionUtils.createPrivateLookup(clazz).unreflectSpecial(method, clazz).bindTo(config);
//                    this.registerMethod(method, (cfg, args) ->
//                    {
//                        try
//                        {
//                            return result.invokeWithArguments(args);
//                        }
//                        catch (Throwable e)
//                        {
//                            throw new RuntimeException(e);
//                        }
//                    });
//                }
//            }
            {
                Method m = clazz.getMethod("template");
                this.registerMethod(clazz, m, (cfg, args) -> this.template(cfg));
            }
            {
                Method m = clazz.getMethod("fillWithDefaults");
                this.registerVoidMethod(clazz, m, (cfg, args) -> this.fillWithDefaults(cfg));
            }
            {
                Method m = clazz.getMethod("get", String.class);
                this.registerMethod(clazz, m, (cfg, args) -> this.get(cfg, (String) args[0]));
            }
            {
                Method m = clazz.getMethod("get", String.class, Object.class);
                this.registerMethod(clazz, m, (cfg, args) -> this.get(cfg, (String) args[0], args[1]));
            }
            {
                Method m = clazz.getMethod("get", String.class, Object.class, Class.class);
                this.registerMethod(clazz, m, (cfg, args) -> this.get(cfg, (String) args[0], args[1], (Class) args[2]));
            }
            {
                Method m = clazz.getMethod("get", String.class, Class.class);
                this.registerMethod(clazz, m, (cfg, args) -> this.get(cfg, (String) args[0], (Class) args[1]));
            }
            {
                Method m = clazz.getMethod("set", String.class, Object.class);
                this.registerMethod(clazz, m, (cfg, args) -> this.set(cfg, (String) args[0], args[1]));
            }
            {
                Method m = clazz.getMethod("remove", String.class);
                this.registerMethod(clazz, m, (cfg, args) -> this.remove(cfg, (String) args[0]));
            }
            {
                Method m = clazz.getMethod("encoder");
                this.registerMethod(clazz, m, (cfg, args) -> this.encoder(cfg));
            }
            {
                Method m = clazz.getMethod("encoder", CharsetEncoder.class);
                this.registerVoidMethod(clazz, m, (cfg, args) -> this.encoder(cfg, (CharsetEncoder) args[0]));
            }
            {
                Method m = clazz.getMethod("decoder");
                this.registerMethod(clazz, m, (cfg, args) -> this.decoder(cfg));
            }
            {
                Method m = clazz.getMethod("decoder", CharsetDecoder.class);
                this.registerVoidMethod(clazz, m, (cfg, args) -> this.decoder(cfg, (CharsetDecoder) args[0]));
            }
            {
                Method m = clazz.getMethod("bindFile");
                this.registerMethod(clazz, m, (cfg, args) -> this.bindFile(cfg));
            }
            {
                Method m = clazz.getMethod("bindFile", File.class);
                this.registerVoidMethod(clazz, m, (cfg, args) -> this.bindFile(cfg, (File) args[0]));
            }
            {
                Method m = clazz.getMethod("save");
                this.registerVoidMethod(clazz, m, (cfg, args) -> this.save(cfg));
            }
            {
                Method m = clazz.getMethod("save", Writer.class);
                this.registerVoidMethod(clazz, m, (cfg, args) -> this.save(cfg, (Writer) args[0]));
            }
            {
                Method m = clazz.getMethod("load");
                this.registerVoidMethod(clazz, m, (cfg, args) -> this.load(cfg));
            }
            {
                Method m = clazz.getMethod("load", Reader.class);
                this.registerVoidMethod(clazz, m, (cfg, args) -> this.load(cfg, (Reader) args[0]));
            }
            {
                Method m = clazz.getMethod("clone");
                this.registerMethod(clazz, m, (cfg, args) -> this.clone(cfg));
            }
            {
                Method m = clazz.getMethod("size");
                this.registerMethod(clazz, m, (cfg, args) -> this.size(cfg));
            }
            {
                Method m = clazz.getMethod("isEmpty");
                this.registerMethod(clazz, m, (cfg, args) -> this.isEmpty(cfg));
            }
            {
                Method m = clazz.getMethod("containsKey", String.class);
                this.registerMethod(clazz, m, (cfg, args) -> this.containsKey(cfg, (String) args[0]));
            }
            {
                Method m = clazz.getMethod("containsValue", Object.class);
                this.registerMethod(clazz, m, (cfg, args) -> this.containsValue(cfg, args[0]));
            }
            {
                Method m = clazz.getMethod("clear");
                this.registerVoidMethod(clazz, m, (cfg, args) -> this.clear(cfg));
            }
            {
                Method m = clazz.getMethod("keySet");
                this.registerMethod(clazz, m, (cfg, args) -> this.keySet(cfg));
            }
            {
                Method m = clazz.getMethod("values");
                this.registerMethod(clazz, m, (cfg, args) -> this.values(cfg));
            }
            {
                Method m = clazz.getMethod("entrySet");
                this.registerMethod(clazz, m, (cfg, args) -> this.entrySet(cfg));
            }

            {
                Method m = Object.class.getMethod("toString");
                this.registerMethod(m, (cfg, args) -> this.toString(cfg));
            }
            {
                Method m = Object.class.getMethod("hashCode");
                this.registerMethod(m, (cfg, args) -> this.hashCode(cfg));
            }
            {
                Method m = Object.class.getMethod("equals", Object.class);
                this.registerMethod(m, (cfg, args) -> this.equals(cfg, args[0]));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private ConfigTemplate<?> template(Config config)
    {
        return this.template;
    }

    private void fillWithDefaults(Config config)
    {

    }

    @Nullable
    private Object get(Config config, String key)
    {
        return null;
    }

    private Object get(Config config, String key, Object def)
    {
        return null;
    }

    private <T> T get(Config config, String key, T def, Class<T> type)
    {
        return null;
    }

    @Nullable
    private <T> T get(Config config, String key, Class<T> type)
    {
        return null;
    }

    @Nullable
    private Object set(Config config, String key, @Nullable Object value)
    {
        return null;
    }

    private Object remove(Config config, String key)
    {
        return null;
    }

    private CharsetEncoder encoder(Config config)
    {
        return null;
    }

    private void encoder(Config config, CharsetEncoder encoder)
    {

    }

    private CharsetDecoder decoder(Config config)
    {
        return null;
    }

    private void decoder(Config config, CharsetDecoder decoder)
    {

    }

    @Nullable
    private File bindFile(Config config)
    {
        return null;
    }

    private void bindFile(Config config, @Nullable File file)
    {

    }

    private void save(Config config)
    {

    }

    private void save(Config config, @WillNotClose Writer writer)
    {

    }

    private void load(Config config)
    {

    }

    private void load(Config config, @WillNotClose Reader reader)
    {

    }

    private Config clone(Config config)
    {
        return null;
    }

    private int size(Config config)
    {
        return 0;
    }

    private boolean isEmpty(Config config)
    {
        return false;
    }

    private boolean containsKey(Config config, String key)
    {
        return false;
    }

    private boolean containsValue(Config config, Object value)
    {
        return false;
    }

    private void clear(Config config)
    {

    }

    private Set<String> keySet(Config config)
    {
        return null;
    }

    private Collection<Object> values(Config config)
    {
        return null;
    }

    private Set<Entry<String, Object>> entrySet(Config config)
    {
        return null;
    }

    private String toString(Config config)
    {
        return "toString!";
    }

    private int hashCode(Config config)
    {
        return 0;
    }

    private boolean equals(Config config, Object object)
    {
        return false;
    }
}
