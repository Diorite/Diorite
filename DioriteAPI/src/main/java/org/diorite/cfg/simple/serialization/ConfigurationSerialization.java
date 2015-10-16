/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.cfg.simple.serialization;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ConfigurationSerialization
{
    public static final  String                                                  SERIALIZED_TYPE_KEY = "=[Type]=>";
    private static final Map<String, Class<? extends ConfigurationSerializable>> aliases             = new HashMap<>(10);
    private final Class<? extends ConfigurationSerializable> clazz;

    protected ConfigurationSerialization(final Class<? extends ConfigurationSerializable> clazz)
    {
        this.clazz = clazz;
    }

    protected Method getMethod(final String name, final boolean isStatic)
    {
        try
        {
            final Method method = this.clazz.getDeclaredMethod(name, Map.class);

            if (! ConfigurationSerializable.class.isAssignableFrom(method.getReturnType()))
            {
                return null;
            }
            if (Modifier.isStatic(method.getModifiers()) != isStatic)
            {
                return null;
            }

            return method;
        } catch (final NoSuchMethodException ex)
        {
            return null;
        } catch (final SecurityException ex)
        {
            return null;
        }
    }

    protected Constructor<? extends ConfigurationSerializable> getConstructor()
    {
        try
        {
            return this.clazz.getConstructor(Map.class);
        } catch (final NoSuchMethodException ex)
        {
            return null;
        } catch (final SecurityException ex)
        {
            return null;
        }
    }

    protected ConfigurationSerializable deserializeViaMethod(final Method method, final Map<String, ?> args)
    {
        try
        {
            final ConfigurationSerializable result = (ConfigurationSerializable) method.invoke(null, args);

            if (result == null)
            {
                Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization: method returned null");
            }
            else
            {
                return result;
            }
        } catch (final Throwable ex)
        {
            Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization", (ex instanceof InvocationTargetException) ? ex.getCause() : ex);
        }

        return null;
    }

    protected ConfigurationSerializable deserializeViaCtor(final Constructor<? extends ConfigurationSerializable> ctor, final Map<String, ?> args)
    {
        try
        {
            return ctor.newInstance(args);
        } catch (final Throwable ex)
        {
            Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call constructor '" + ctor.toString() + "' of " + this.clazz + " for deserialization", (ex instanceof InvocationTargetException) ? ex.getCause() : ex);
        }

        return null;
    }

    public ConfigurationSerializable deserialize(final Map<String, ?> args)
    {
        Validate.notNull(args, "Args must not be null");

        ConfigurationSerializable result = null;
        Method method = this.getMethod("deserialize", true);

        if (method != null)
        {
            result = this.deserializeViaMethod(method, args);
        }

        if (result == null)
        {
            method = this.getMethod("valueOf", true);

            if (method != null)
            {
                result = this.deserializeViaMethod(method, args);
            }
        }

        if (result == null)
        {
            final Constructor<? extends ConfigurationSerializable> constructor = this.getConstructor();

            if (constructor != null)
            {
                result = this.deserializeViaCtor(constructor, args);
            }
        }

        return result;
    }

    public static ConfigurationSerializable deserializeObject(final Map<String, ?> args, final Class<? extends ConfigurationSerializable> clazz)
    {
        return new ConfigurationSerialization(clazz).deserialize(args);
    }

    public static ConfigurationSerializable deserializeObject(final Map<String, ?> args)
    {
        final Class<? extends ConfigurationSerializable> clazz;

        if (args.containsKey(SERIALIZED_TYPE_KEY))
        {
            try
            {
                final String alias = (String) args.get(SERIALIZED_TYPE_KEY);

                if (alias == null)
                {
                    throw new IllegalArgumentException("Cannot have null alias");
                }
                clazz = getClassByAlias(alias);
                if (clazz == null)
                {
                    throw new IllegalArgumentException("Specified class does not exist ('" + alias + "')");
                }
            } catch (final ClassCastException ex)
            {
                ex.fillInStackTrace();
                throw ex;
            }
        }
        else
        {
            throw new IllegalArgumentException("Args doesn't contain type key ('" + SERIALIZED_TYPE_KEY + "')");
        }

        return new ConfigurationSerialization(clazz).deserialize(args);
    }

    public static void registerClass(final Class<? extends ConfigurationSerializable> clazz)
    {
        final DelegateDeserialization delegate = clazz.getAnnotation(DelegateDeserialization.class);

        if (delegate == null)
        {
            registerClass(clazz, getAlias(clazz));
            registerClass(clazz, clazz.getName());
        }
    }

    public static void registerClass(final Class<? extends ConfigurationSerializable> clazz, final String alias)
    {
        aliases.put(alias, clazz);
    }

    public static void unregisterClass(final String alias)
    {
        aliases.remove(alias);
    }

    public static void unregisterClass(final Class<? extends ConfigurationSerializable> clazz)
    {
        while (aliases.containsValue(clazz))
        {
            aliases.values().remove(clazz);
        }
    }

    public static Class<? extends ConfigurationSerializable> getClassByAlias(final String alias)
    {
        return aliases.get(alias);
    }

    public static String getAlias(final Class<? extends ConfigurationSerializable> clazz)
    {
        final DelegateDeserialization delegate = clazz.getAnnotation(DelegateDeserialization.class);
        if ((delegate != null) && (delegate.value() != null) && (! delegate.value().equals(clazz)))
        {
            return getAlias(delegate.value());
        }
        final SerializableAs alias = clazz.getAnnotation(SerializableAs.class);

        if ((alias != null) && (alias.value() != null))
        {
            return alias.value();
        }
        return clazz.getName();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("clazz", this.clazz).toString();
    }
}
