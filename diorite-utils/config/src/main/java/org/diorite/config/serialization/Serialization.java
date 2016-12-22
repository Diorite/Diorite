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

package org.diorite.config.serialization;

import javax.annotation.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.diorite.commons.reflections.ConstructorInvoker;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.commons.reflections.ReflectMethod;

/**
 * Serialization manager, it allows to serialize and deserialize all registered type, own manager can be created but ech one must have some parent instance.
 * <br/>
 * If there is no parent instance, global instance will be used.
 */
public class Serialization
{
    private static final Serialization GLOBAL = new Serialization((Void) null);

    private final GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting().serializeNulls().serializeSpecialFloatingPointValues();
    private       Gson        cachedGson  = this.gsonBuilder.create();

    private final Map<Class<?>, StringSerializer<?>> stringSerializerMap = new ConcurrentHashMap<>(10);
    private final Map<Class<?>, StringSerializer<?>> serializerMap       = new ConcurrentHashMap<>(10);

    private final Set<String> trueValues  = new HashSet<>(Set.of("true", "enabled", "enable", "yes", "y", "e", "t", "on"));
    private final Set<String> falseValues = new HashSet<>(Set.of("false", "disabled", "disable", "no", "n", "d", "f", "off"));

    /**
     * Add string values that can be interpreted as {@link Boolean#TRUE} value when reading from config.
     *
     * @param strings
     *         string representations of {@link Boolean#TRUE} value.
     */
    public void addTrueValues(String... strings)
    {
        Collections.addAll(this.trueValues, strings);
    }

    /**
     * Add string values that can be interpreted as {@link Boolean#FALSE} value when reading from config.
     *
     * @param strings
     *         string representations of {@link Boolean#FALSE} value.
     */
    public void addFalseValues(String... strings)
    {
        Collections.addAll(this.falseValues, strings);
    }

    @Nullable
    Boolean toBool(String str)
    {
        if (this.trueValues.contains(str))
        {
            return true;
        }
        if (this.falseValues.contains(str))
        {
            return false;
        }
        return null;
    }

    public static Serialization getGlobal()
    {
        return GLOBAL;
    }

    private void refreshCache()
    {
        this.cachedGson = this.gsonBuilder.create();
    }

    @Nullable
    private final Serialization parent;

    private Serialization(@Nullable Void v)
    {
        this.parent = null;
    }

    public Serialization(Serialization parent)
    {
        this.parent = parent;
    }

    public Serialization()
    {
        this(GLOBAL);
    }

    /**
     * Register given string class to this serialization manager, it must contains two valid methods annotated with {@link StringSerializableMethod}.
     *
     * @param clazz
     *         serializable clazz to register.
     * @param <T>
     *         type of serializable class, must contains two valid methods annotated with {@link StringSerializableMethod}.
     *
     * @return old serializer if exists.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends StringSerializable> StringSerializer<T> registerStringSerializerByAnnotations(Class<T> clazz)
    {
        Function<T, String> serialization = null;
        Function<String, T> deserialization = null;
        for (Method method : clazz.getDeclaredMethods())
        {
            if (method.isAnnotationPresent(StringSerializableMethod.class))
            {
                if (Modifier.isStatic(method.getModifiers()))
                {
                    if (method.getReturnType().equals(String.class) && (method.getParameterCount() == 1) &&
                        (method.getParameterTypes()[0].equals(clazz) || method.getParameterTypes()[0].equals(Object.class)))
                    {
                        MethodInvoker methodInvoker = new MethodInvoker(method);
                        methodInvoker.ensureAccessible();
                        serialization = obj -> (String) methodInvoker.invoke(null, obj);
                        continue;
                    }
                    if (method.getReturnType().equals(clazz) && (method.getParameterCount() == 1) && method.getParameterTypes()[0].equals(String.class))
                    {
                        MethodInvoker methodInvoker = new MethodInvoker(method);
                        methodInvoker.ensureAccessible();
                        deserialization = str -> (T) methodInvoker.invoke(null, str);
                        continue;
                    }
                    throw annotationError(clazz);
                }
                if (method.getReturnType().equals(String.class) && (method.getParameterCount() == 0))
                {
                    MethodInvoker methodInvoker = new MethodInvoker(method);
                    methodInvoker.ensureAccessible();
                    serialization = obj -> (String) methodInvoker.invoke(obj);
                    continue;
                }
                throw annotationError(clazz);
            }
        }
        if (serialization == null)
        {
            throw annotationError(clazz);
        }
        if (deserialization == null)
        {
            for (Constructor<?> constructor : clazz.getDeclaredConstructors())
            {
                if (constructor.isAnnotationPresent(StringSerializableMethod.class))
                {
                    if ((constructor.getParameterCount() == 1) && constructor.getParameterTypes()[0].equals(String.class))
                    {
                        ConstructorInvoker<T> constructorInvoker = new ConstructorInvoker<>(constructor);
                        deserialization = constructorInvoker::invoke;
                    }
                    else
                    {
                        throw annotationError(clazz);
                    }
                }
            }
            if (deserialization == null)
            {
                throw annotationError(clazz);
            }
        }
        return this.registerStringSerializer(StringSerializer.of(clazz, serialization, deserialization));
    }

    private static IllegalArgumentException annotationError(Class<?> clazz)
    {
        return new IllegalArgumentException("Given class (" + clazz.getName() +
                                            ") does not contain needed methods (or contains annotations on invalid methods)! Make sure to create valid " +
                                            "serialization and deserialization methods, with " +
                                            "@StringSerializableMethod annotation over them!\n  Serialization methods:\n    static String nameOfMethod(T)\n  " +
                                            "  static String nameOfMethod(Object)\n    String nameOfMethod()\n  Deserialization methods:\n    static T " +
                                            "nameOfMethod(String)\n    constructor(String)");
    }

    /**
     * Register given string serializable class to this serialization manager.
     *
     * @param clazz
     *         serializable clazz to register.
     * @param <T>
     *         type of serializable class, must implement {@link StringSerializable}
     *
     * @return old serializer if exists.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends StringSerializable> StringSerializer<T> registerStringSerializer(Class<T> clazz)
    {
        ReflectMethod deserialize = DioriteReflectionUtils.getTypedMethod(clazz, "deserializeFromString", clazz, false, String.class);
        if ((deserialize == null) || deserialize.isStatic())
        {
            deserialize = DioriteReflectionUtils.getTypedMethod(clazz, "valueOf", clazz, false, String.class);
            if ((deserialize == null) || deserialize.isStatic())
            {
                deserialize = DioriteReflectionUtils.getConstructor(clazz, false, String.class);
                if (deserialize == null)
                {
                    throw new IllegalArgumentException("Given class (" + clazz.getName() +
                                                       ") does not contains deserialization method! Make sure to create one of this methods:\n    static T " +
                                                       "deserializeFromString(String)\n    static T valueOf(String)\n   constructor(String)");
                }
            }
        }
        MethodHandle handle = deserialize.getHandle();
        StringSerializer<T> serializer = StringSerializer.of(clazz, StringSerializable::serializeToString, str ->
        {
            try
            {
                return (T) handle.invokeExact(str);
            }
            catch (Throwable throwable)
            {
                throw new DeserializationException(clazz, str, throwable);
            }
        });
        return this.registerStringSerializer(serializer);
    }

    /**
     * Register given string serializer to this serialization manager.
     *
     * @param stringSerializer
     *         string serializer to register.
     * @param <T>
     *         serializer value type.
     *
     * @return old serializer if exists.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> StringSerializer<T> registerStringSerializer(StringSerializer<T> stringSerializer)
    {
//        gsonBuilder.registerTypeAdapter(stringSerializer.getType(), new TypeAdapter<T>()
//        {
//            @Override
//            public void write(JsonWriter jsonWriter, T t) throws IOException
//            {
//                jsonWriter.value(stringSerializer.serialize(t));
//            }
//
//            @Override
//            public T read(JsonReader jsonReader) throws IOException
//            {
//                return stringSerializer.deserialize(jsonReader.nextString());
//            }
//        });
        return (StringSerializer<T>) this.stringSerializerMap.put(stringSerializer.getType(), stringSerializer);
    }

    /**
     * Returns true if given object type is serializable to simple string.
     *
     * @param object
     *         object type to check.
     *
     * @return true if given object type is serializable to simple string.
     */
    public boolean isStringSerializable(Object object)
    {
        return this.isStringSerializable(object.getClass());
    }

    /**
     * Returns true if given type is serializable to simple string.
     *
     * @param clazz
     *         type to check.
     *
     * @return true if given type is serializable to simple string.
     */
    public boolean isStringSerializable(Class<?> clazz)
    {
        if ((this.parent != null) && this.parent.isStringSerializable(clazz))
        {
            return true;
        }
        return this.stringSerializerMap.containsKey(clazz);
    }

    /**
     * Returns true if given object type is serializable.
     *
     * @param object
     *         object type to check.
     *
     * @return true if given object type is serializable.
     */
    public boolean isSerializable(Object object)
    {
        return this.isSerializable(object.getClass());
    }

    /**
     * Returns true if given type is serializable.
     *
     * @param clazz
     *         type to check.
     *
     * @return true if given type is serializable.
     */
    public boolean isSerializable(Class<?> clazz)
    {
        if ((this.parent != null) && this.parent.isSerializable(clazz))
        {
            return true;
        }
        return this.serializerMap.containsKey(clazz);
    }
}
