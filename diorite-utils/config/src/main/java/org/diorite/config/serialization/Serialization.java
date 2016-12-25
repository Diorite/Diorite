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

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.diorite.commons.reflections.ConstructorInvoker;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.commons.reflections.ReflectMethod;
import org.diorite.config.serialization.annotations.StringSerializable;

/**
 * Serialization manager, it allows to serialize and deserialize all registered types. <br/>
 * It is used to serialize and deserialize both json and yaml.
 */
public final class Serialization
{
    private static final Serialization GLOBAL = new Serialization((Void) null);

    final   GsonBuilder       gsonBuilder = new GsonBuilder().setPrettyPrinting().serializeNulls().serializeSpecialFloatingPointValues();
    private ThreadLocal<Gson> cachedGson  = ThreadLocal.withInitial(this.gsonBuilder::create);

    private Gson gson()
    {
        return this.cachedGson.get();
    }

    private final Map<Class<?>, StringSerializer<?>> stringSerializerMap = new ConcurrentHashMap<>(10);
    private final Map<Class<?>, Serializer<?>>       serializerMap       = new ConcurrentHashMap<>(10);

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
        this.cachedGson = ThreadLocal.withInitial(this.gsonBuilder::create);
    }

    @Nullable
    private final Serialization parent;

    private Serialization(@Nullable Void v)
    {
        this.parent = null;
        this.registerStringSerializer(StringSerializer.of(UUID.class, UUID::toString, UUID::fromString));
    }

//    public Serialization(Serialization parent)
//    {
//        this.parent = parent;
//    }
//
//    public Serialization()
//    {
//        this(GLOBAL);
//    }

    /**
     * Register given string serializable class to this serialization manager.
     *
     * @param clazz
     *         serializable clazz to register.
     * @param <T>
     *         type of serializable class, must implement {@link org.diorite.config.serialization.StringSerializable} or contain {@link StringSerializable}
     *         annotations.
     *
     * @return old serializer if exists.
     */
    @Nullable
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> StringSerializer<T> registerStringSerializable(Class<T> clazz)
    {
        if (clazz.isAnnotationPresent(StringSerializable.class))
        {
            return this.registerStringSerializableByAnnotations(clazz);
        }
        return this.registerStringSerializableByType((Class) clazz);
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
        this.gsonBuilder.registerTypeAdapter(stringSerializer.getType(), new StringSerializerTypeAdapter<>(stringSerializer));
        this.refreshCache();
        return (StringSerializer<T>) this.stringSerializerMap.put(stringSerializer.getType(), stringSerializer);
    }

    /**
     * Register given serializable class to this serialization manager.
     *
     * @param clazz
     *         serializable clazz to register.
     * @param <T>
     *         type of serializable class, must implement {@link Serializable} or contain {@link org.diorite.config.serialization.annotations.Serializable}
     *         annotations.
     *
     * @return old serializer if exists.
     */
    @Nullable
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> Serializer<T> registerSerializable(Class<T> clazz)
    {
        if (clazz.isAnnotationPresent(org.diorite.config.serialization.annotations.Serializable.class))
        {
            return this.registerSerializableByAnnotations(clazz);
        }
        return this.registerSerializableByType((Class) clazz);
    }

    /**
     * Register given serializer to this serialization manager.
     *
     * @param serializer
     *         serializer to register.
     * @param <T>
     *         serializer value type.
     *
     * @return old serializer if exists.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> Serializer<T> registerSerializer(Serializer<T> serializer)
    {
        this.gsonBuilder.registerTypeAdapter(serializer.getType(), new SerializerTypeAdapter<>(serializer, this));
        this.refreshCache();
        return (Serializer<T>) this.serializerMap.put(serializer.getType(), serializer);
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

    /**
     * Serialize given object to simple string.
     *
     * @param type
     *         type of object to serialize.
     * @param object
     *         object to serialize.
     * @param <T>
     *         type of object to serialize.
     *
     * @return serialized object as string.
     *
     * @throws IllegalArgumentException
     *         if given object isn't string serializable.
     */
    @SuppressWarnings("unchecked")
    public <T> String serializeToString(Class<T> type, T object)
    {
        if (! this.isStringSerializable(type))
        {
            throw new IllegalArgumentException("Given object isn't string serializable: (" + type.getName() + ") -> " + object);
        }
        return ((StringSerializer<T>) this.stringSerializerMap.get(type)).serialize(object);
    }

    /**
     * Deserialize given string to object.
     *
     * @param type
     *         type of object to deserialize.
     * @param str
     *         string to deserialize.
     * @param <T>
     *         type of object to deserialize.
     *
     * @return deserialized object from string.
     *
     * @throws IllegalArgumentException
     *         if given type isn't string serializable.
     */
    @SuppressWarnings("unchecked")
    public <T> T deserializeFromString(Class<T> type, String str)
    {
        if (! this.isStringSerializable(type))
        {
            throw new IllegalArgumentException("Given type isn't string serializable: (" + type.getName() + ") -> " + str);
        }
        return ((StringSerializer<T>) this.stringSerializerMap.get(type)).deserialize(str);
    }

    /**
     * Serialize given object to simple string.
     *
     * @param object
     *         object to serialize.
     *
     * @return serialized object as string.
     *
     * @throws IllegalArgumentException
     *         if given object isn't string serializable.
     */
    @SuppressWarnings("unchecked")
    public String serializeToString(Object object)
    {
        if (! this.isStringSerializable(object))
        {
            throw new IllegalArgumentException("Given object isn't string serializable: " + object);
        }
        return ((StringSerializer<Object>) this.stringSerializerMap.get(object.getClass())).serialize(object);
    }

    @SuppressWarnings("unchecked")
    Object serialize(Object object)
    {
        if (isSimple(object))
        {
            return object;
        }
        if (! this.isSerializable(object))
        {
            throw new IllegalArgumentException("Given object isn't serializable: " + object);
        }
        SimpleSerializationData serializationData = (SimpleSerializationData) SerializationData.create(this, object.getClass());
        ((Serializer<Object>) this.serializerMap.get(object.getClass())).serialize(object, serializationData);
        return serializationData.rawValue();
    }

    @SuppressWarnings("unchecked")
    <T> Object serialize(Class<T> type, T object)
    {
        if (isSimple(object))
        {
            return object;
        }
        if (! this.isSerializable(type))
        {
            throw new IllegalArgumentException("Given object isn't serializable: (" + type.getName() + ") -> " + object);
        }
        SimpleSerializationData serializationData = (SimpleSerializationData) SerializationData.create(this, type);
        ((Serializer<T>) this.serializerMap.get(type)).serialize(object, serializationData);
        return serializationData.rawValue();
    }

    // private

    @Nullable
    @SuppressWarnings("unchecked")
    private <T> StringSerializer<T> registerStringSerializableByAnnotations(Class<T> clazz)
    {
        Function<T, String> serialization = null;
        Function<String, T> deserialization = null;
        for (Method method : clazz.getDeclaredMethods())
        {
            if (method.isAnnotationPresent(StringSerializable.class))
            {
                Class<?> returnType = method.getReturnType();
                Class<?>[] params = method.getParameterTypes();
                if (Modifier.isStatic(method.getModifiers()))
                {
                    if (returnType.equals(String.class) && (params.length == 1) && (params[0].equals(clazz) || params[0].equals(Object.class)))
                    {
                        MethodInvoker methodInvoker = new MethodInvoker(method);
                        methodInvoker.ensureAccessible();
                        serialization = obj -> (String) methodInvoker.invoke(null, obj);
                        continue;
                    }
                    if (returnType.equals(clazz) && (params.length == 1) && params[0].equals(String.class))
                    {
                        MethodInvoker methodInvoker = new MethodInvoker(method);
                        methodInvoker.ensureAccessible();
                        deserialization = str -> (T) methodInvoker.invoke(null, str);
                        continue;
                    }
                    throw stringAnnotationError(clazz);
                }
                if (returnType.equals(String.class) && (params.length == 0))
                {
                    MethodInvoker methodInvoker = new MethodInvoker(method);
                    methodInvoker.ensureAccessible();
                    serialization = obj -> (String) methodInvoker.invoke(obj);
                    continue;
                }
                throw stringAnnotationError(clazz);
            }
        }
        if (serialization == null)
        {
            throw stringAnnotationError(clazz);
        }
        if (deserialization == null)
        {
            for (Constructor<?> constructor : clazz.getDeclaredConstructors())
            {
                if (constructor.isAnnotationPresent(StringSerializable.class))
                {
                    Class<?>[] params = constructor.getParameterTypes();
                    if ((params.length == 1) && params[0].equals(String.class))
                    {
                        ConstructorInvoker<T> constructorInvoker = new ConstructorInvoker<>(constructor);
                        //noinspection Convert2MethodRef Java 9 conpiler bug: 9046392 // FIXME
                        deserialization = (arguments) -> constructorInvoker.invoke(arguments);
                    }
                    else
                    {
                        throw stringAnnotationError(clazz);
                    }
                }
            }
            if (deserialization == null)
            {
                throw stringAnnotationError(clazz);
            }
        }
        return this.registerStringSerializer(StringSerializer.of(clazz, serialization, deserialization));
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private <T extends org.diorite.config.serialization.StringSerializable> StringSerializer<T> registerStringSerializableByType(Class<T> clazz)
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
        StringSerializer<T> serializer = StringSerializer.of(clazz, org.diorite.config.serialization.StringSerializable::serializeToString, str ->
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

    @Nullable
    @SuppressWarnings("unchecked")
    private <T extends Serializable> Serializer<T> registerSerializableByType(Class<T> clazz)
    {
        ReflectMethod deserialize = DioriteReflectionUtils.getTypedMethod(clazz, "deserialize", clazz, false, DeserializationData.class);
        if ((deserialize == null) || deserialize.isStatic())
        {
            deserialize = DioriteReflectionUtils.getTypedMethod(clazz, "valueOf", clazz, false, DeserializationData.class);
            if ((deserialize == null) || deserialize.isStatic())
            {
                deserialize = DioriteReflectionUtils.getConstructor(clazz, false, DeserializationData.class);
                if (deserialize == null)
                {
                    throw new IllegalArgumentException("Given class (" + clazz.getName() +
                                                       ") does not contains deserialization method! Make sure to create one of this methods:\n    static T " +
                                                       "deserialize(DeserializationData)\n    static T valueOf(DeserializationData)\n   constructor" +
                                                       "(DeserializationData)");
                }
            }
        }
        MethodHandle handle = deserialize.getHandle();
        Serializer<T> serializer = Serializer.of(clazz, Serializable::serialize, data ->
        {
            try
            {
                return (T) handle.invokeWithArguments(data);
            }
            catch (DeserializationException e)
            {
                throw e;
            }
            catch (Throwable throwable)
            {
                throw new DeserializationException(clazz, data, throwable);
            }
        });
        return this.registerSerializer(serializer);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private <T> Serializer<T> registerSerializableByAnnotations(Class<T> clazz)
    {
        BiConsumer<T, SerializationData> serialization = null;
        Function<DeserializationData, T> deserialization = null;
        for (Method method : clazz.getDeclaredMethods())
        {
            Class<?> returnType = method.getReturnType();
            Class<?>[] params = method.getParameterTypes();
            if (method.isAnnotationPresent(org.diorite.config.serialization.annotations.Serializable.class))
            {
                if (Modifier.isStatic(method.getModifiers()))
                {
                    if (returnType.equals(void.class) && (params.length == 2))
                    {
                        if ((params[0].equals(clazz) || params[0].equals(Object.class)) && params[1].equals(SerializationData.class))
                        {
                            MethodInvoker methodInvoker = new MethodInvoker(method);
                            methodInvoker.ensureAccessible();
                            serialization = (t, data) -> methodInvoker.invoke(null, t, data);
                        }
                        else if ((params[1].equals(clazz) || params[1].equals(Object.class)) && params[0].equals(SerializationData.class))
                        {
                            MethodInvoker methodInvoker = new MethodInvoker(method);
                            methodInvoker.ensureAccessible();
                            serialization = (t, data) -> methodInvoker.invoke(null, data, t);
                        }
                        else
                        {
                            throw annotationError(clazz);
                        }
                        continue;
                    }
                    if (returnType.equals(clazz) && (params.length == 1) && params[0].equals(DeserializationData.class))
                    {
                        MethodInvoker methodInvoker = new MethodInvoker(method);
                        methodInvoker.ensureAccessible();
                        deserialization = data -> (T) methodInvoker.invoke(null, data);
                        continue;
                    }
                    throw annotationError(clazz);
                }
                if (returnType.equals(void.class) && (params.length == 1) && params[0].equals(SerializationData.class))
                {
                    MethodInvoker methodInvoker = new MethodInvoker(method);
                    methodInvoker.ensureAccessible();
                    serialization = methodInvoker::invoke;
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
                if (constructor.isAnnotationPresent(org.diorite.config.serialization.annotations.Serializable.class))
                {
                    Class<?>[] params = constructor.getParameterTypes();
                    if ((params.length == 1) && params[0].equals(DeserializationData.class))
                    {
                        ConstructorInvoker<T> constructorInvoker = new ConstructorInvoker<>(constructor);
                        //noinspection Convert2MethodRef Java 9 conpiler bug: 9046392 // FIXME
                        deserialization = (arguments) -> constructorInvoker.invoke(arguments);
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
        return this.registerSerializer(Serializer.of(clazz, serialization, deserialization));
    }

    private static IllegalArgumentException stringAnnotationError(Class<?> clazz)
    {
        return new IllegalArgumentException("Given class (" + clazz.getName() +
                                            ") does not contain needed methods (or contains annotations on invalid methods)! Make sure to create valid " +
                                            "serialization and deserialization methods, with " +
                                            "@StringSerializable annotation over them!\n  Serialization methods:\n    static String nameOfMethod(T)\n  " +
                                            "  static String nameOfMethod(Object)\n    String nameOfMethod()\n  Deserialization methods:\n    static T " +
                                            "nameOfMethod(String)\n    constructor(String)");
    }

    private static IllegalArgumentException annotationError(Class<?> clazz)
    {
        return new IllegalArgumentException("Given class (" + clazz.getName() +
                                            ") does not contain needed methods (or contains annotations on invalid methods)! Make sure to create valid " +
                                            "serialization and deserialization methods, with @Serializable annotation over them!\n  Serialization methods:\n " +
                                            "   static void nameOfMethod(T, SerializationData)\n    static void nameOfMethod(SerializationData, T)\n    " +
                                            "static void nameOfMethod(Object, SerializationData)\n    static void nameOfMethod(SerializationData, Object)\n  " +
                                            "  void nameOfMethod(SerializationData)\n  Deserialization methods:\n    static T nameOfMethod" +
                                            "(DeserializationData)\n    constructor(DeserializationData)");
    }

    static boolean isSimpleType(Class<?> type)
    {
        if (type.isPrimitive())
        {
            return true;
        }
        if (type.isEnum())
        {
            return true;
        }
        if (type.equals(String.class))
        {
            return true;
        }
        if (type.equals(Boolean.class))
        {
            return true;
        }
        if (type.equals(Byte.class))
        {
            return true;
        }
        if (type.equals(Character.class))
        {
            return true;
        }
        if (type.equals(Short.class))
        {
            return true;
        }
        if (type.equals(Integer.class))
        {
            return true;
        }
        if (type.equals(Long.class))
        {
            return true;
        }
        if (type.equals(Float.class))
        {
            return true;
        }
        return type.equals(Double.class);
    }

    static boolean isSimple(@Nullable Object object)
    {
        if (object == null)
        {
            return true;
        }
        if (object instanceof Enum)
        {
            return true;
        }
        if (object instanceof String)
        {
            return true;
        }
        if (object instanceof Boolean)
        {
            return true;
        }
        if (object instanceof Byte)
        {
            return true;
        }
        if (object instanceof Character)
        {
            return true;
        }
        if (object instanceof Short)
        {
            return true;
        }
        if (object instanceof Integer)
        {
            return true;
        }
        if (object instanceof Long)
        {
            return true;
        }
        if (object instanceof Float)
        {
            return true;
        }
        return object instanceof Double;
    }

    // json delegates.

    /**
     * This method serializes the specified object into its equivalent representation as a tree of
     * {@link JsonElement}s. This method should be used when the specified object is not a generic
     * type. This method uses {@link Class#getClass()} to get the type for the specified object, but
     * the {@code getClass()} loses the generic type information because of the Type Erasure feature
     * of Java. Note that this method works fine if the any of the object fields are of generic type,
     * just the object itself should not be of a generic type. If the object is of generic type, use
     * {@link #toJsonTree(Object, Type)} instead.
     *
     * @param src the object for which Json representation is to be created setting for Gson
     * @return Json representation of {@code src}.
     * @since 1.4
     */
    public JsonElement toJsonTree(Object src)
    {
        return this.gson().toJsonTree(src);
    }

    /**
     * This method serializes the specified object, including those of generic types, into its
     * equivalent representation as a tree of {@link JsonElement}s. This method must be used if the
     * specified object is a generic type. For non-generic objects, use {@link #toJsonTree(Object)}
     * instead.
     *
     * @param src the object for which JSON representation is to be created
     * @param typeOfSrc The specific genericized type of src. You can obtain
     * this type by using the {@link TypeToken} class. For example,
     * to get the type for {@code Collection<Foo>}, you should use:
     * <pre>
     * Type typeOfSrc = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
     * </pre>
     * @return Json representation of {@code src}
     * @since 1.4
     */
    public JsonElement toJsonTree(Object src, Type typeOfSrc)
    {
        return this.gson().toJsonTree(src, typeOfSrc);
    }

    /**
     * This method serializes the specified object into its equivalent Json representation.
     * This method should be used when the specified object is not a generic type. This method uses
     * {@link Class#getClass()} to get the type for the specified object, but the
     * {@code getClass()} loses the generic type information because of the Type Erasure feature
     * of Java. Note that this method works fine if the any of the object fields are of generic type,
     * just the object itself should not be of a generic type. If the object is of generic type, use
     * {@link #toJson(Object, Type)} instead. If you want to write out the object to a
     * {@link Writer}, use {@link #toJson(Object, Appendable)} instead.
     *
     * @param src the object for which Json representation is to be created setting for Gson
     * @return Json representation of {@code src}.
     */
    public String toJson(Object src)
    {
        return this.gson().toJson(src);
    }

    /**
     * This method serializes the specified object, including those of generic types, into its
     * equivalent Json representation. This method must be used if the specified object is a generic
     * type. For non-generic objects, use {@link #toJson(Object)} instead. If you want to write out
     * the object to a {@link Appendable}, use {@link #toJson(Object, Type, Appendable)} instead.
     *
     * @param src the object for which JSON representation is to be created
     * @param typeOfSrc The specific genericized type of src. You can obtain
     * this type by using the {@link TypeToken} class. For example,
     * to get the type for {@code Collection<Foo>}, you should use:
     * <pre>
     * Type typeOfSrc = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
     * </pre>
     * @return Json representation of {@code src}
     */
    public String toJson(Object src, Type typeOfSrc)
    {
        return this.gson().toJson(src, typeOfSrc);
    }

    /**
     * This method serializes the specified object into its equivalent Json representation.
     * This method should be used when the specified object is not a generic type. This method uses
     * {@link Class#getClass()} to get the type for the specified object, but the
     * {@code getClass()} loses the generic type information because of the Type Erasure feature
     * of Java. Note that this method works fine if the any of the object fields are of generic type,
     * just the object itself should not be of a generic type. If the object is of generic type, use
     * {@link #toJson(Object, Type, Appendable)} instead.
     *
     * @param src the object for which Json representation is to be created setting for Gson
     * @param writer Writer to which the Json representation needs to be written
     * @throws JsonIOException if there was a problem writing to the writer
     * @since 1.2
     */
    public void toJson(Object src, Appendable writer) throws JsonIOException
    {
        this.gson().toJson(src, writer);
    }

    /**
     * This method serializes the specified object, including those of generic types, into its
     * equivalent Json representation. This method must be used if the specified object is a generic
     * type. For non-generic objects, use {@link #toJson(Object, Appendable)} instead.
     *
     * @param src the object for which JSON representation is to be created
     * @param typeOfSrc The specific genericized type of src. You can obtain
     * this type by using the {@link TypeToken} class. For example,
     * to get the type for {@code Collection<Foo>}, you should use:
     * <pre>
     * Type typeOfSrc = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
     * </pre>
     * @param writer Writer to which the Json representation of src needs to be written.
     * @throws JsonIOException if there was a problem writing to the writer
     * @since 1.2
     */
    public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException
    {
        this.gson().toJson(src, typeOfSrc, writer);
    }

    /**
     * Writes the JSON representation of {@code src} of type {@code typeOfSrc} to
     * {@code writer}.
     * @throws JsonIOException if there was a problem writing to the writer
     * @param src
     * @param typeOfSrc
     * @param writer
     */
    public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException
    {
        this.gson().toJson(src, typeOfSrc, writer);
    }

    /**
     * Converts a tree of {@link JsonElement}s into its equivalent JSON representation.
     *
     * @param jsonElement root of a tree of {@link JsonElement}s
     * @return JSON String representation of the tree
     * @since 1.4
     */
    public String toJson(JsonElement jsonElement)
    {
        return this.gson().toJson(jsonElement);
    }

    /**
     * Writes out the equivalent JSON for a tree of {@link JsonElement}s.
     *
     * @param jsonElement root of a tree of {@link JsonElement}s
     * @param writer Writer to which the Json representation needs to be written
     * @throws JsonIOException if there was a problem writing to the writer
     * @since 1.4
     */
    public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException
    {
        this.gson().toJson(jsonElement, writer);
    }

    /**
     * Returns a new JSON writer configured for the settings on this Gson instance.
     * @param writer
     */
    public JsonWriter newJsonWriter(Writer writer) throws IOException
    {
        return this.gson().newJsonWriter(writer);
    }

    /**
     * Returns a new JSON reader configured for the settings on this Gson instance.
     * @param reader
     */
    public JsonReader newJsonReader(Reader reader)
    {
        return this.gson().newJsonReader(reader);
    }

    /**
     * Writes the JSON for {@code jsonElement} to {@code writer}.
     * @throws JsonIOException if there was a problem writing to the writer
     * @param jsonElement
     * @param writer
     */
    public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException
    {
        this.gson().toJson(jsonElement, writer);
    }

    /**
     * This method deserializes the specified Json into an object of the specified class. It is not
     * suitable to use if the specified class is a generic type since it will not have the generic
     * type information because of the Type Erasure feature of Java. Therefore, this method should not
     * be used if the desired type is a generic type. Note that this method works fine if the any of
     * the fields of the specified object are generics, just the object itself should not be a
     * generic type. For the cases when the object is of generic type, invoke
     * {@link #fromJson(String, Type)}. If you have the Json in a {@link Reader} instead of
     * a String, use {@link #fromJson(Reader, Class)} instead.
     *
     * @param json the string from which the object is to be deserialized
     * @param classOfT the class of T
     * @return an object of type T from the string. Returns {@code null} if {@code json} is {@code null}.
     * @throws JsonSyntaxException if json is not a valid representation for an object of type
     * classOfT
     */
    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException
    {
        return this.gson().fromJson(json, classOfT);
    }

    /**
     * This method deserializes the specified Json into an object of the specified type. This method
     * is useful if the specified object is a generic type. For non-generic objects, use
     * {@link #fromJson(String, Class)} instead. If you have the Json in a {@link Reader} instead of
     * a String, use {@link #fromJson(Reader, Type)} instead.
     *
     * @param json the string from which the object is to be deserialized
     * @param typeOfT The specific genericized type of src. You can obtain this type by using the
     * {@link TypeToken} class. For example, to get the type for
     * {@code Collection<Foo>}, you should use:
     * <pre>
     * Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
     * </pre>
     * @return an object of type T from the string. Returns {@code null} if {@code json} is {@code null}.
     * @throws com.google.gson.JsonParseException if json is not a valid representation for an object of type typeOfT
     * @throws JsonSyntaxException if json is not a valid representation for an object of type
     */
    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException
    {
        return this.gson().fromJson(json, typeOfT);
    }

    /**
     * This method deserializes the Json read from the specified reader into an object of the
     * specified class. It is not suitable to use if the specified class is a generic type since it
     * will not have the generic type information because of the Type Erasure feature of Java.
     * Therefore, this method should not be used if the desired type is a generic type. Note that
     * this method works fine if the any of the fields of the specified object are generics, just the
     * object itself should not be a generic type. For the cases when the object is of generic type,
     * invoke {@link #fromJson(Reader, Type)}. If you have the Json in a String form instead of a
     * {@link Reader}, use {@link #fromJson(String, Class)} instead.
     *
     * @param json the reader producing the Json from which the object is to be deserialized.
     * @param classOfT the class of T
     * @return an object of type T from the string. Returns {@code null} if {@code json} is at EOF.
     * @throws JsonIOException if there was a problem reading from the Reader
     * @throws JsonSyntaxException if json is not a valid representation for an object of type
     * @since 1.2
     */
    public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException
    {
        return this.gson().fromJson(json, classOfT);
    }

    /**
     * This method deserializes the Json read from the specified reader into an object of the
     * specified type. This method is useful if the specified object is a generic type. For
     * non-generic objects, use {@link #fromJson(Reader, Class)} instead. If you have the Json in a
     * String form instead of a {@link Reader}, use {@link #fromJson(String, Type)} instead.
     *
     * @param json the reader producing Json from which the object is to be deserialized
     * @param typeOfT The specific genericized type of src. You can obtain this type by using the
     * {@link TypeToken} class. For example, to get the type for
     * {@code Collection<Foo>}, you should use:
     * <pre>
     * Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
     * </pre>
     * @return an object of type T from the json. Returns {@code null} if {@code json} is at EOF.
     * @throws JsonIOException if there was a problem reading from the Reader
     * @throws JsonSyntaxException if json is not a valid representation for an object of type
     * @since 1.2
     */
    public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException
    {
        return this.gson().fromJson(json, typeOfT);
    }

    /**
     * Reads the next JSON value from {@code reader} and convert it to an object
     * of type {@code typeOfT}. Returns {@code null}, if the {@code reader} is at EOF.
     * Since Type is not parameterized by T, this method is type unsafe and should be used carefully
     *
     * @throws JsonIOException if there was a problem writing to the Reader
     * @throws JsonSyntaxException if json is not a valid representation for an object of type
     * @param reader
     * @param typeOfT
     */
    public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException
    {
        return this.gson().fromJson(reader, typeOfT);
    }

    /**
     * This method deserializes the Json read from the specified parse tree into an object of the
     * specified type. It is not suitable to use if the specified class is a generic type since it
     * will not have the generic type information because of the Type Erasure feature of Java.
     * Therefore, this method should not be used if the desired type is a generic type. Note that
     * this method works fine if the any of the fields of the specified object are generics, just the
     * object itself should not be a generic type. For the cases when the object is of generic type,
     * invoke {@link #fromJson(JsonElement, Type)}.
     * @param json the root of the parse tree of {@link JsonElement}s from which the object is to
     * be deserialized
     * @param classOfT The class of T
     * @return an object of type T from the json. Returns {@code null} if {@code json} is {@code null}.
     * @throws JsonSyntaxException if json is not a valid representation for an object of type typeOfT
     * @since 1.3
     */
    public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException
    {
        return this.gson().fromJson(json, classOfT);
    }

    /**
     * This method deserializes the Json read from the specified parse tree into an object of the
     * specified type. This method is useful if the specified object is a generic type. For
     * non-generic objects, use {@link #fromJson(JsonElement, Class)} instead.
     *
     * @param json the root of the parse tree of {@link JsonElement}s from which the object is to
     * be deserialized
     * @param typeOfT The specific genericized type of src. You can obtain this type by using the
     * {@link TypeToken} class. For example, to get the type for
     * {@code Collection<Foo>}, you should use:
     * <pre>
     * Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
     * </pre>
     * @return an object of type T from the json. Returns {@code null} if {@code json} is {@code null}.
     * @throws JsonSyntaxException if json is not a valid representation for an object of type typeOfT
     * @since 1.3
     */
    public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException
    {
        return this.gson().fromJson(json, typeOfT);
    }
}
