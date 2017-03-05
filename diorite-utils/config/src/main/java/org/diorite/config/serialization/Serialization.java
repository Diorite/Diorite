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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;

import org.diorite.commons.enums.DynamicEnum;
import org.diorite.commons.function.function.ExceptionalFunction;
import org.diorite.commons.reflections.ConstructorInvoker;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.commons.reflections.ReflectMethod;
import org.diorite.commons.threads.DioriteThreadUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.annotations.DelegateSerializable;
import org.diorite.config.annotations.SerializableAs;
import org.diorite.config.annotations.StringSerializable;
import org.diorite.config.serialization.comments.CommentsManager;
import org.diorite.config.serialization.comments.DocumentComments;
import org.diorite.config.serialization.serializers.InetAddressSerializer;
import org.diorite.config.serialization.serializers.SocketAddressSerializer;
import org.diorite.config.serialization.snakeyaml.DumperOptions;
import org.diorite.config.serialization.snakeyaml.Representer;
import org.diorite.config.serialization.snakeyaml.Yaml;
import org.diorite.config.serialization.snakeyaml.YamlConstructor;

/**
 * Serialization manager, it allows to serialize and deserialize all registered types. <br>
 * It is used to serialize and deserialize both json and yaml.
 */
public final class Serialization
{
    private static final Serialization GLOBAL     = new Serialization((Void) null);
    private static final int           BEST_WIDTH = 180;

    // gson section
    private final GsonBuilder       gsonBuilder =
            new GsonBuilder().setPrettyPrinting().serializeNulls().serializeSpecialFloatingPointValues().enableComplexMapKeySerialization();
    private       ThreadLocal<Gson> cachedGson  = ThreadLocal.withInitial(this.gsonBuilder::create);

    // yaml section
    private final Collection<Class<?>>                                                              yamlIgnoredClasses = new ConcurrentLinkedQueue<>();
    private final Collection<BiFunction<YamlConstructor, Representer, YamlStringSerializerImpl<?>>> stringRepresenters = new ConcurrentLinkedQueue<>();
    private final Collection<BiFunction<YamlConstructor, Representer, YamlSerializerImpl<?>>>       objectRepresenters = new ConcurrentLinkedQueue<>();
    private       ThreadLocal<Yaml>                                                                 cachedYaml         = ThreadLocal.withInitial(this::createYaml);
    private final ThreadLocal<AtomicInteger>                                                        localCounter       = ThreadLocal.withInitial(AtomicInteger::new);

    private final CommentsManager commentsManager = new CommentsManager();

    /**
     * Returns instance of comments manager.
     *
     * @return instance of comments manager.
     */
    public CommentsManager getCommentsManager()
    {
        return this.commentsManager;
    }

    private Yaml createYaml()
    {
        Representer representer = new Representer();
        YamlConstructor constructor = new YamlConstructor();

        // register types
        for (Class<?> ignoredClass : this.yamlIgnoredClasses)
        {
            representer.addClassTag(ignoredClass, Tag.MAP);
        }
        for (BiFunction<YamlConstructor, Representer, YamlStringSerializerImpl<?>> serializerCreator : this.stringRepresenters)
        {
            YamlStringSerializerImpl<?> yamlSerializer = serializerCreator.apply(constructor, representer);
            Class<?> type = yamlSerializer.getType();
            representer.addClassTag(type, Tag.MAP);
            representer.addRepresenter(type, yamlSerializer);
            constructor.addConstruct(type, yamlSerializer);
        }
        for (BiFunction<YamlConstructor, Representer, YamlSerializerImpl<?>> serializerCreator : this.objectRepresenters)
        {
            YamlSerializerImpl<?> yamlSerializer = serializerCreator.apply(constructor, representer);
            Class<?> type = yamlSerializer.getType();
            representer.addClassTag(type, Tag.MAP);
            representer.addRepresenter(type, yamlSerializer);
            constructor.addConstruct(type, yamlSerializer);
        }

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setAllowReadOnlyProperties(true);
        dumperOptions.setAllowUnicode(true);
        dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
        dumperOptions.setIndent(2);
        dumperOptions.setPrettyFlow(false);
        dumperOptions.setWidth(BEST_WIDTH);

        Resolver resolver = new Resolver();

        Yaml yaml = new Yaml(this, constructor, representer, dumperOptions, resolver);

        yaml.setName("DioriteYaml[" + this.localCounter.get().getAndIncrement() + "]:" + DioriteThreadUtils.getFullThreadName(Thread.currentThread(), true));
        return yaml;
    }

    private Gson gson()
    {
        return this.cachedGson.get();
    }

    private Yaml yaml()
    {
        return this.cachedYaml.get();
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

    public static Serialization getInstance()
    {
        return GLOBAL;
    }

    private void refreshCache()
    {
        this.cachedGson = ThreadLocal.withInitial(this.gsonBuilder::create);
        this.cachedYaml = ThreadLocal.withInitial(this::createYaml);
    }

    /**
     * Remove all cached values.
     */
    public void cleanup()
    {
        this.refreshCache();
    }

    /**
     * Remove cached values for current thread.
     */
    public void cleanupThread()
    {
        this.cachedGson.remove();
        this.cachedYaml.remove();
    }

    private Serialization(@Nullable Void v)
    {
        this.registerStringSerializer(StringSerializer.of(UUID.class, UUID::toString, UUID::fromString));
        this.registerStringSerializer(StringSerializer.of(File.class, File::getPath, File::new));
        this.registerStringSerializer(StringSerializer.of(Locale.class, Locale::toLanguageTag, Locale::forLanguageTag));
        this.registerStringSerializer(StringSerializer.of(URL.class, ExceptionalFunction.of(URL::getPath), ExceptionalFunction.of(URL::new)));
        this.registerStringSerializer(StringSerializer.of(URI.class, ExceptionalFunction.of(URI::getPath), ExceptionalFunction.of(URI::new)));
        this.registerStringSerializer(new InetAddressSerializer());
        this.registerStringSerializer(new SocketAddressSerializer());
    }

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
        Class target = getDelegatedLookupClass(clazz);
        if (clazz.isAnnotationPresent(StringSerializable.class))
        {
            return this.registerStringSerializableByAnnotations(target);
        }
        return this.registerStringSerializableByType(target);
    }

    /**
     * Register given string serializer to this serialization manager.
     *
     * @param stringSerializer
     *         string serializer to register.
     * @param <T>
     *         serializer value type.
     */
    public <T> void registerStringSerializer(StringSerializer<T> stringSerializer)
    {
        this.gsonBuilder.registerTypeAdapter(stringSerializer.getType(), new JsonStringSerializerImpl<>(stringSerializer));
        this.stringRepresenters.add((c, r) -> new YamlStringSerializerImpl<>(r, stringSerializer));
        this.refreshCache();
        this.stringSerializerMap.put(stringSerializer.getType(), stringSerializer);
    }

    /**
     * Register given serializable class to this serialization manager.
     *
     * @param clazz
     *         serializable clazz to register.
     * @param <T>
     *         type of serializable class, must implement {@link Serializable} or contain {@link org.diorite.config.annotations.Serializable} annotations.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> void registerSerializable(Class<T> clazz)
    {
        Class target = getDelegatedLookupClass(clazz);
        Serializer serializer;
        if (clazz.isAnnotationPresent(org.diorite.config.annotations.Serializable.class))
        {
            serializer = this.registerSerializableByAnnotations(target);
        }
        else
        {
            serializer = this.registerSerializableByType(target);
        }
        if (clazz != target)
        {
            this.registerSerializer(Serializer.of(clazz, serializer.getSerializerFunction(), serializer.getDeserializerFunction()));
        }
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
        this.gsonBuilder.registerTypeAdapter(serializer.getType(), new JsonSerializerImpl<>(serializer, this));
        this.objectRepresenters.add((c, r) -> new YamlSerializerImpl<>(r, c, serializer, this));
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
        return this.stringSerializerMap.containsKey(clazz) || (this.getAsStringSerializable(clazz) != null);
    }

    @Nullable
    private Class<?> getAsStringSerializable(Class<?> clazz)
    {
        boolean containsKey = this.stringSerializerMap.containsKey(clazz);
        if (! containsKey)
        {
            Class<?> superclass = clazz.getSuperclass();
            if ((superclass != null) && (superclass != Object.class))
            {
                Class<?> superSerializable = this.getAsStringSerializable(superclass);
                if (superSerializable != null)
                {
                    return superSerializable;
                }
            }
            for (Class<?> interfaceClass : clazz.getInterfaces())
            {
                Class<?> interfaceSerializable = this.getAsStringSerializable(interfaceClass);
                if (interfaceSerializable != null)
                {
                    return interfaceSerializable;
                }
            }
            return null;
        }
        return clazz;
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
        return this.serializerMap.containsKey(clazz);
    }

    private final Set<Class<?>> canBeSerializedChecked = new ConcurrentSkipListSet<>();

    boolean canBeSerialized(Class<?> clazz)
    {
        try
        {
            TypeAdapter<?> adapter = this.gson().getAdapter(clazz);
//            this.yamlIgnoredClasses.add(clazz);
            return adapter != null;
        }
        catch (Exception e)
        {
            // print error but still return false to caller.
            e.printStackTrace();
            return false;
        }
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
        Class<?> asStringSerializable = this.getAsStringSerializable(type);
        if (asStringSerializable == null)
        {
            throw new IllegalArgumentException("Given object isn't string serializable: (" + type.getName() + ") -> " + object);
        }
        return ((StringSerializer<T>) this.stringSerializerMap.get(asStringSerializable)).serialize(object);
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
        Class<?> stringSerializable = this.getAsStringSerializable(type);
        if (stringSerializable == null)
        {
            throw new IllegalArgumentException("Given type isn't string serializable: (" + type.getName() + ") -> " + str);
        }
        return ((StringSerializer<T>) this.stringSerializerMap.get(stringSerializable)).deserialize(str);
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
        Class<?> stringSerializable = this.getAsStringSerializable(object.getClass());
        if (stringSerializable == null)
        {
            throw new IllegalArgumentException("Given object isn't string serializable: (" + object.getClass() + ") " + object);
        }
        return ((StringSerializer<Object>) this.stringSerializerMap.get(stringSerializable)).serialize(object);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Nullable
    Object serialize(Object object, SerializationType serializationType, @Nullable DocumentComments comments)
    {
        if (isSimple(object))
        {
            return object;
        }
        return this.serialize((Class) object.getClass(), object, serializationType, comments);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    <T> Object serialize(Class<T> type, T object, SerializationType serializationType, @Nullable DocumentComments comments)
    {
        if (isSimple(object))
        {
            return object;
        }
        if (! this.isSerializable(type))
        {
            if (this.canBeSerialized(type))
            {
                if (serializationType == SerializationType.YAML)
                {
                    return this.fromYaml(this.toYaml(object), Map.class);
                }
                else
                {
                    TypeAdapter<T> typeAdapter = this.gson().getAdapter(type);
                    return this.fromJson(typeAdapter.toJsonTree(object), Map.class);
                }
            }
            throw new IllegalArgumentException("Given object isn't serializable: (" + type.getName() + ") -> " + object);
        }
        SimpleSerializationData serializationData = (SimpleSerializationData) SerializationData.create(serializationType, this, type);
        if (comments != null)
        {
            serializationData.setComments(comments);
        }

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
                        deserialization = constructorInvoker::invokeWith;
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
        StringSerializer<T> stringSerializer = StringSerializer.of(getRedirectionClass(clazz), serialization, deserialization);
        this.registerStringSerializer(stringSerializer);
        return stringSerializer;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private <T extends org.diorite.config.serialization.StringSerializable> StringSerializer<T> registerStringSerializableByType(Class<T> clazz)
    {

        ReflectMethod deserialize = DioriteReflectionUtils.getTypedMethod(clazz, "deserializeFromString", clazz, false, String.class);
        if ((deserialize == null) || ! deserialize.isStatic())
        {
            deserialize = DioriteReflectionUtils.getTypedMethod(clazz, "valueOf", clazz, false, String.class);
            if ((deserialize == null) || ! deserialize.isStatic())
            {
                deserialize = DioriteReflectionUtils.getConstructor(clazz, false, String.class);
                if (deserialize == null)
                {
                    String simpleName = clazz.getSimpleName();
                    throw new IllegalArgumentException
                                  ("Given class (" + clazz.getName() +
                                   ") does not contains deserialization method! Make sure to create one of this methods:\n" +
                                   "    static " + simpleName + " deserializeFromString(String)\n" +
                                   "    static " + simpleName + " valueOf(String)\n" +
                                   "   constructor(String)");
                }
            }
        }
        MethodHandle handle = deserialize.getHandle();
        StringSerializer<T> serializer =
                StringSerializer.of(getRedirectionClass(clazz), org.diorite.config.serialization.StringSerializable::serializeToString, str ->
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
        this.registerStringSerializer(serializer);
        return serializer;
    }

    @SuppressWarnings("unchecked")
    private <T extends Serializable> Serializer<T> registerSerializableByType(Class<T> clazz)
    {
        ReflectMethod deserialize = DioriteReflectionUtils.getTypedMethod(clazz, "deserialize", clazz, false, DeserializationData.class);
        if ((deserialize == null) || ! deserialize.isStatic() || ! deserialize.isPublic())
        {
            deserialize = DioriteReflectionUtils.getTypedMethod(clazz, "valueOf", clazz, false, DeserializationData.class);
            if ((deserialize == null) || ! deserialize.isStatic() || ! deserialize.isPublic())
            {
                deserialize = DioriteReflectionUtils.getConstructor(clazz, false, DeserializationData.class);
                if ((deserialize == null) || ! deserialize.isPublic())
                {
                    String simpleName = clazz.getSimpleName();
                    throw new IllegalArgumentException
                                  ("Given class (" + clazz.getName() +
                                   ") does not contains deserialization method! Make sure to create one of this methods:\n" +
                                   "    static " + simpleName + " deserialize(DeserializationData)\n" +
                                   "    static " + simpleName + " valueOf(DeserializationData)\n" +
                                   "    constructor(DeserializationData)");
                }
            }
        }
        MethodHandle handle = deserialize.getHandle();
        Serializer<T> serializer = Serializer.of(getRedirectionClass(clazz), Serializable::serialize, data ->
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
        this.registerSerializer(Serializer.of(clazz, serializer.getSerializerFunction(), serializer.getDeserializerFunction()));
        this.registerSerializer(serializer);
        return serializer;
    }

    @SuppressWarnings("unchecked")
    private <T> Serializer<T> registerSerializableByAnnotations(Class<T> clazz)
    {
        BiConsumer<T, SerializationData> serialization = null;
        Function<DeserializationData, T> deserialization = null;
        for (Method method : clazz.getDeclaredMethods())
        {
            Class<?> returnType = method.getReturnType();
            Class<?>[] params = method.getParameterTypes();
            if (method.isAnnotationPresent(org.diorite.config.annotations.Serializable.class))
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
                if (constructor.isAnnotationPresent(org.diorite.config.annotations.Serializable.class))
                {
                    Class<?>[] params = constructor.getParameterTypes();
                    if ((params.length == 1) && params[0].equals(DeserializationData.class))
                    {
                        ConstructorInvoker<T> constructorInvoker = new ConstructorInvoker<>(constructor);
                        deserialization = constructorInvoker::invokeWith;
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
        Serializer<T> serializer = Serializer.of(getRedirectionClass(clazz), serialization, deserialization);
        this.registerSerializer(Serializer.of(clazz, serializer.getSerializerFunction(), serializer.getDeserializerFunction()));
        this.registerSerializer(serializer);
        return serializer;
    }

    private static IllegalArgumentException stringAnnotationError(Class<?> clazz)
    {
        String simpleName = clazz.getSimpleName();
        return new IllegalArgumentException
                       ("Given class (" + clazz.getName() + ") does not contain needed methods (or contains annotations on invalid methods)! " +
                        "Make sure to create valid serialization and deserialization methods, with @StringSerializable annotation over them!\n" +
                        "  Serialization methods:\n" +
                        "    static String nameOfMethod(" + simpleName + ")\n" +
                        "    static String nameOfMethod(Object)\n" +
                        "    String nameOfMethod()\n" +
                        "  Deserialization methods:\n" +
                        "    static " + simpleName + " nameOfMethod(String)\n" +
                        "    constructor(String)");
    }

    private static IllegalArgumentException annotationError(Class<?> clazz)
    {
        String simpleName = clazz.getSimpleName();
        return new IllegalArgumentException
                       ("Given class (" + clazz.getName() + ") does not contain needed methods (or contains annotations on invalid methods)! " +
                        "Make sure to create valid serialization and deserialization methods, with @Serializable annotation over them!\n" +
                        "  Serialization methods:\n" +
                        "    static void nameOfMethod(" + simpleName + ", SerializationData)\n" +
                        "    static void nameOfMethod(SerializationData, " + simpleName + ")\n" +
                        "    static void nameOfMethod(Object, SerializationData)\n" +
                        "    static void nameOfMethod(SerializationData, Object)\n" +
                        "    void nameOfMethod(SerializationData)\n" +
                        "  Deserialization methods:\n" +
                        "    static " + simpleName + " nameOfMethod(DeserializationData)\n" +
                        "    constructor(DeserializationData)");
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<? super T> getRedirectionClass(Class<T> type)
    {
        if (type.isAnnotationPresent(SerializableAs.class))
        {
            SerializableAs serializableAs = type.getAnnotation(SerializableAs.class);
            return (Class<? super T>) getRedirectionClass(serializableAs.value());
        }
        return type;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<? extends T> getDelegatedLookupClass(Class<T> type)
    {
        if (type.isAnnotationPresent(DelegateSerializable.class))
        {
            DelegateSerializable serializableAs = type.getAnnotation(DelegateSerializable.class);
            return (Class<? extends T>) getDelegatedLookupClass(serializableAs.value());
        }
        return type;
    }

    static boolean isSimpleType(Class<?> type)
    {
        while (true)
        {
            if (DioriteReflectionUtils.getPrimitive(type).isPrimitive())
            {
                return true;
            }
            if (DynamicEnum.class.isAssignableFrom(type))
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
            if (type.isArray())
            {
                type = type.getComponentType();
            }
            else
            {
                return false;
            }
        }
    }

    static boolean isSimple(@Nullable Object object)
    {
        if (object == null)
        {
            return true;
        }
        return isSimpleType(object.getClass());
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
     * @param src
     *         the object for which Json representation is to be created setting for Gson
     *
     * @return Json representation of {@code src}.
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
     * @param src
     *         the object for which JSON representation is to be created
     * @param typeOfSrc
     *         The specific genericized type of src. You can obtain this type by using the {@link TypeToken} class. For example, to get the type for {@code
     *         Collection<Foo>}, you should use:
     *         <pre> Type typeOfSrc = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType(); </pre>
     *
     * @return Json representation of {@code src}
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
     * @param src
     *         the object for which Json representation is to be created setting for Gson
     *
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
     * @param src
     *         the object for which JSON representation is to be created
     * @param typeOfSrc
     *         The specific genericized type of src. You can obtain this type by using the {@link TypeToken} class. For example, to get the type for {@code
     *         Collection<Foo>}, you should use:
     *         <pre> new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){} </pre>
     *
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
     * @param src
     *         the object for which Json representation is to be created setting for Gson
     * @param writer
     *         Writer to which the Json representation needs to be written
     *
     * @throws JsonIOException
     *         if there was a problem writing to the writer
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
     * @param src
     *         the object for which JSON representation is to be created
     * @param typeOfSrc
     *         The specific genericized type of src. You can obtain this type by using the {@link TypeToken} class. For example, to get the type for {@code
     *         Collection<Foo>}, you should use:
     *         <pre> new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){} </pre>
     * @param writer
     *         Writer to which the Json representation of src needs to be written.
     *
     * @throws JsonIOException
     *         if there was a problem writing to the writer
     */
    public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException
    {
        this.gson().toJson(src, typeOfSrc, writer);
    }

    /**
     * Writes the JSON representation of {@code src} of type {@code typeOfSrc} to
     * {@code writer}.
     *
     * @param src
     *         the object for which JSON representation is to be created
     * @param typeOfSrc
     *         The specific genericized type of src. You can obtain this type by using the {@link TypeToken} class. For example, to get the type for {@code
     *         Collection<Foo>}, you should use:
     *         <pre> new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){} </pre>
     * @param writer
     *         Writer to which the Json representation of src needs to be written.
     *
     * @throws JsonIOException
     *         if there was a problem writing to the writer
     */
    public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException
    {
        this.gson().toJson(src, typeOfSrc, writer);
    }

    /**
     * Converts a tree of {@link JsonElement}s into its equivalent JSON representation.
     *
     * @param jsonElement
     *         root of a tree of {@link JsonElement}s
     *
     * @return JSON String representation of the tree
     */
    public String toJson(JsonElement jsonElement)
    {
        return this.gson().toJson(jsonElement);
    }

    /**
     * Writes out the equivalent JSON for a tree of {@link JsonElement}s.
     *
     * @param jsonElement
     *         root of a tree of {@link JsonElement}s
     * @param writer
     *         Writer to which the Json representation needs to be written
     *
     * @throws JsonIOException
     *         if there was a problem writing to the writer
     */
    public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException
    {
        this.gson().toJson(jsonElement, writer);
    }

    /**
     * Returns a new JSON writer configured for the settings on this Gson instance.
     *
     * @param writer
     *         writer to wrap.
     *
     * @return created json writer.
     *
     * @throws IOException
     *         if write to writer fails.
     */
    public JsonWriter newJsonWriter(Writer writer) throws IOException
    {
        return this.gson().newJsonWriter(writer);
    }

    /**
     * Returns a new JSON reader configured for the settings on this Gson instance.
     *
     * @param reader
     *         reader to wrap.
     *
     * @return created json reader.
     */
    public JsonReader newJsonReader(Reader reader)
    {
        return this.gson().newJsonReader(reader);
    }

    /**
     * Writes the JSON for {@code jsonElement} to {@code writer}.
     *
     * @param writer
     *         Writer to which the Json representation needs to be written
     * @param jsonElement
     *         root of a tree of {@link JsonElement}s
     *
     * @throws JsonIOException
     *         if there was a problem writing to the writer
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
     * @param json
     *         the string from which the object is to be deserialized
     * @param classOfT
     *         the class of T
     * @param <T>
     *         type of class.
     *
     * @return an object of type T from the string. Returns {@code null} if {@code json} is {@code null}.
     *
     * @throws JsonSyntaxException
     *         if json is not a valid representation for an object of type classOfT
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
     * @param json
     *         the string from which the object is to be deserialized
     * @param typeOfT
     *         The specific genericized type of src. You can obtain this type by using the {@link TypeToken} class. For example, to get the type for {@code
     *         Collection<Foo>}, you should use:
     *         <pre> new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType(); </pre>
     * @param <T>
     *         type of class.
     *
     * @return an object of type T from the string. Returns {@code null} if {@code json} is {@code null}.
     *
     * @throws com.google.gson.JsonParseException
     *         if json is not a valid representation for an object of type typeOfT
     * @throws JsonSyntaxException
     *         if json is not a valid representation for an object of type
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
     * @param json
     *         the reader producing the Json from which the object is to be deserialized.
     * @param classOfT
     *         the class of T
     * @param <T>
     *         type of class.
     *
     * @return an object of type T from the string. Returns {@code null} if {@code json} is at EOF.
     *
     * @throws JsonIOException
     *         if there was a problem reading from the Reader
     * @throws JsonSyntaxException
     *         if json is not a valid representation for an object of type
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
     * @param json
     *         the reader producing Json from which the object is to be deserialized
     * @param typeOfT
     *         The specific genericized type of src. You can obtain this type by using the {@link TypeToken} class. For example, to get the type for {@code
     *         Collection<Foo>}, you should use:
     *         <pre> new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){} </pre>
     * @param <T>
     *         type of class.
     *
     * @return an object of type T from the json. Returns {@code null} if {@code json} is at EOF.
     *
     * @throws JsonIOException
     *         if there was a problem reading from the Reader
     * @throws JsonSyntaxException
     *         if json is not a valid representation for an object of type
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
     * @param reader
     *         the reader producing Json from which the object is to be deserialized
     * @param typeOfT
     *         The specific genericized type of src. You can obtain this type by using the {@link TypeToken} class. For example, to get the type for {@code
     *         Collection<Foo>}, you should use:
     *         <pre> new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){} </pre>
     * @param <T>
     *         type of class.
     *
     * @return an object of type T from the json. Returns {@code null} if {@code json} is at EOF.
     *
     * @throws JsonIOException
     *         if there was a problem writing to the Reader
     * @throws JsonSyntaxException
     *         if json is not a valid representation for an object of type
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
     *
     * @param json
     *         the root of the parse tree of {@link JsonElement}s from which the object is to be deserialized
     * @param classOfT
     *         The class of T
     * @param <T>
     *         type of class.
     *
     * @return an object of type T from the json. Returns {@code null} if {@code json} is {@code null}.
     *
     * @throws JsonSyntaxException
     *         if json is not a valid representation for an object of type typeOfT
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
     * @param json
     *         the root of the parse tree of {@link JsonElement}s from which the object is to be deserialized
     * @param typeOfT
     *         The specific genericized type of src. You can obtain this type by using the {@link TypeToken} class. For example, to get the type for {@code
     *         Collection<Foo>}, you should use:
     *         <pre> new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType(); </pre>
     * @param <T>
     *         type of class.
     *
     * @return an object of type T from the json. Returns {@code null} if {@code json} is {@code null}.
     *
     * @throws JsonSyntaxException
     *         if json is not a valid representation for an object of type typeOfT
     */
    public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException
    {
        return this.gson().fromJson(json, typeOfT);
    }

    // yaml delegate:

    /**
     * Serialize a Java object into a YAML String.
     *
     * @param data
     *         Java object to be Serialized to YAML
     *
     * @return YAML String
     */
    public String toYamlWithComments(Object data)
    {
        return this.yaml().toYamlWithComments(data, this.commentsManager.getComments(data.getClass()));
    }

    /**
     * Serialize a Java object into a YAML String.
     *
     * @param data
     *         Java object to be Serialized to YAML
     * @param comments
     *         comments node.
     *
     * @return YAML String
     */
    public String toYamlWithComments(Object data, DocumentComments comments)
    {
        return this.yaml().toYamlWithComments(data, comments);
    }

    /**
     * Serialize a Java object into a YAML Node.
     *
     * @param data
     *         Java object to be Serialized to YAML
     *
     * @return YAML Node
     */
    public Node toYamlNode(@Nullable Object data)
    {
        Node represent = this.yaml().represent(data);
        if (data != null)
        {
            if (! (data instanceof Map) && ! (data instanceof Collection))
            {
                represent.setType(data.getClass());
                represent.setTag(new Tag(data.getClass()));
            }
        }
        return represent;
    }

    /**
     * Serialize a Java object into a YAML String.
     *
     * @param data
     *         Java object to be Serialized to YAML
     *
     * @return YAML String
     */
    public String toYaml(Object data)
    {
        return this.yaml().toYaml(data);
    }

    /**
     * Serialize a sequence of Java objects into a YAML String.
     *
     * @param data
     *         Iterator with Objects
     *
     * @return YAML String with all the objects in proper sequence
     */
    public String toYaml(Iterator<?> data)
    {
        return this.yaml().toYaml(data);
    }

    /**
     * Serialize a Java object into a YAML stream.
     *
     * @param data
     *         Java object to be serialized to YAML
     * @param output
     *         output for yaml.
     */
    public void toYaml(Object data, Writer output)
    {
        this.yaml().toYaml(data, output);
    }

    /**
     * Serialize a Java object into a YAML stream.
     *
     * @param data
     *         Java object to be serialized to YAML
     * @param output
     *         output for yaml.
     */
    public void toYamlWithComments(Object data, Writer output)
    {
        this.yaml().toYamlWithComments(data, output, this.commentsManager.getComments(data.getClass()));
    }

    /**
     * Serialize a Java object into a YAML stream.
     *
     * @param data
     *         Java object to be serialized to YAML
     * @param output
     *         output for yaml.
     * @param comments
     *         comments node.
     */
    public void toYamlWithComments(Object data, Writer output, DocumentComments comments)
    {
        this.yaml().toYamlWithComments(data, output, comments);
    }

    /**
     * Serialize a sequence of Java objects into a YAML stream.
     *
     * @param data
     *         Iterator with Objects
     * @param output
     *         output for yaml.
     */
    public void toYaml(Iterator<?> data, Writer output)
    {
        this.yaml().toYaml(data, output);
    }

    /**
     * <p>
     * Serialize a Java object into a YAML string. Override the default root tag
     * with <code>rootTag</code>.
     * </p>
     *
     * <p>
     * This method is similar to <code>Yaml.dump(data)</code> except that the
     * root tag for the whole document is replaced with the given tag. This has
     * two main uses.
     * </p>
     *
     * <p>
     * First, if the root tag is replaced with a standard YAML tag, such as
     * <code>Tag.MAP</code>, then the object will be dumped as a map. The root
     * tag will appear as <code>!!map</code>, or blank (implicit !!map).
     * </p>
     *
     * <p>
     * Second, if the root tag is replaced by a different custom tag, then the
     * document appears to be a different type when loaded. For example, if an
     * instance of MyClass is dumped with the tag !!YourClass, then it will be
     * handled as an instance of YourClass when loaded.
     * </p>
     *
     * @param data
     *         Java object to be serialized to YAML
     * @param rootTag
     *         the tag for the whole YAML document. The tag should be Tag.MAP for a JavaBean to make the tag disappear (to use implicit tag !!map). If
     *         <code>null</code> is provided then the standard tag with the full class name is used.
     * @param flowStyle
     *         flow style for the whole document. See Chapter 10. Collection Styles http://yaml.org/spec/1.1/#id930798. If <code>null</code> is provided then
     *         the flow style from DumperOptions is used.
     *
     * @return YAML String
     */
    public String toYaml(Object data, Tag rootTag, @Nullable FlowStyle flowStyle)
    {
        return this.yaml().toYaml(data, rootTag, flowStyle);
    }

    /**
     * <p>
     * Serialize a Java object into a YAML string. Override the default root tag
     * with <code>Tag.MAP</code>.
     * </p>
     * <p>
     * This method is similar to <code>Yaml.dump(data)</code> except that the
     * root tag for the whole document is replaced with <code>Tag.MAP</code> tag
     * (implicit !!map).
     * </p>
     * <p>
     * Block Mapping is used as the collection style. See 10.2.2. Block Mappings
     * (http://yaml.org/spec/1.1/#id934537)
     * </p>
     *
     * @param data
     *         Java object to be serialized to YAML
     *
     * @return YAML String
     */
    public String toYamlAsMap(Object data)
    {
        return this.yaml().toYamlAsMap(data);
    }

    /**
     * Construct object from given node, node must have set type tags.
     *
     * @param node
     *         node to load.
     * @param <T>
     *         type of deserialized object.
     *
     * @return loaded object.
     */
    @Nullable
    public <T> T fromYamlNode(Node node)
    {
        return this.yaml().fromYamlNode(node);
    }

    /**
     * Construct object from given node.
     *
     * @param node
     *         node to load.
     * @param type
     *         type of node.
     * @param <T>
     *         type of deserialized object.
     *
     * @return loaded object.
     */
    @Nullable
    public <T> T fromYamlNode(Node node, Class<T> type)
    {
        return this.yaml().fromYamlNode(node, type);
    }

    /**
     * Parse the only YAML document in a String and produce the corresponding
     * Java object. (Because the encoding in known BOM is not respected.)
     *
     * @param yaml
     *         YAML data to load from (BOM must not be present)
     *
     * @return parsed object
     */
    @Nullable
    public Object fromYaml(String yaml)
    {
        return this.yaml().fromYaml(yaml);
    }

    /**
     * Parse the only YAML document in a stream and produce the corresponding
     * Java object.
     *
     * @param io
     *         data to load from (BOM is respected and removed)
     *
     * @return parsed object
     */
    @Nullable
    public Object fromYaml(InputStream io)
    {
        return this.yaml().fromYaml(io);
    }

    /**
     * Parse the only YAML document in a stream and produce the corresponding
     * Java object.
     *
     * @param io
     *         data to load from (BOM must not be present)
     *
     * @return parsed object
     */
    @Nullable
    public Object fromYaml(Reader io)
    {
        return this.yaml().fromYaml(io);
    }

    /**
     * Parse the only YAML document in a stream as configuration object.
     *
     * @param template
     *         template of config object.
     * @param io
     *         data to load from (BOM must not be present)
     * @param <T>
     *         type of deserialized object.
     *
     * @return parsed object
     */
    @Nullable
    public <T extends Config> T fromYaml(ConfigTemplate<T> template, Reader io)
    {
        return this.yaml().fromYaml(template, io);
    }

    /**
     * Parse the only YAML document in a stream and produce the corresponding
     * Java object.
     *
     * @param io
     *         data to load from (BOM must not be present)
     * @param type
     *         Class of the object to be created
     * @param <T>
     *         type of deserialized object.
     *
     * @return parsed object
     */
    @Nullable
    public <T> T fromYaml(Reader io, Class<T> type)
    {
        return this.yaml().fromYaml(io, type);
    }

    /**
     * Parse the only YAML document in a String and produce the corresponding
     * Java object. (Because the encoding in known BOM is not respected.)
     *
     * @param yaml
     *         YAML data to load from (BOM must not be present)
     * @param type
     *         Class of the object to be created
     * @param <T>
     *         type of deserialized object.
     *
     * @return parsed object
     */
    @Nullable
    public <T> T fromYaml(String yaml, Class<T> type)
    {
        return this.yaml().fromYaml(yaml, type);
    }

    /**
     * Parse the only YAML document in a stream and produce the corresponding
     * Java object.
     *
     * @param input
     *         data to load from (BOM is respected and removed)
     * @param type
     *         Class of the object to be created
     * @param <T>
     *         type of deserialized object.
     *
     * @return parsed object
     */
    @Nullable
    public <T> T fromYaml(InputStream input, Class<T> type)
    {
        return this.yaml().fromYaml(input, type);
    }

    /**
     * Parse all YAML documents in a String and produce corresponding Java
     * objects. The documents are parsed only when the iterator is invoked.
     *
     * @param yaml
     *         YAML data to load from (BOM must not be present)
     *
     * @return an iterator over the parsed Java objects in this String in proper sequence
     */
    public Iterable<Object> fromAllYaml(Reader yaml)
    {
        return this.yaml().fromAllYaml(yaml);
    }

    /**
     * Parse all YAML documents in a String and produce corresponding Java
     * objects. (Because the encoding in known BOM is not respected.) The
     * documents are parsed only when the iterator is invoked.
     *
     * @param yaml
     *         YAML data to load from (BOM must not be present)
     *
     * @return an iterator over the parsed Java objects in this String in proper sequence
     */
    public Iterable<Object> fromAllYaml(String yaml)
    {
        return this.yaml().fromAllYaml(yaml);
    }

    /**
     * Parse all YAML documents in a stream and produce corresponding Java
     * objects. The documents are parsed only when the iterator is invoked.
     *
     * @param yaml
     *         YAML data to load from (BOM is respected and ignored)
     *
     * @return an iterator over the parsed Java objects in this stream in proper sequence
     */
    public Iterable<Object> fromAllYaml(InputStream yaml)
    {
        return this.yaml().fromAllYaml(yaml);
    }
}
