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

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Represent serializer that can serialize objects into {@link SerializationData}, and deserialize {@link DeserializationData} into object.
 *
 * @param <T>
 *         type of serializer object.
 */
public interface Serializer<T>
{
    /**
     * Returns type of values for this serializer.
     *
     * @return type of values for this serializer.
     */
    Class<? super T> getType();

    /**
     * Returns {@link #deserialize(DeserializationData, Class)} as {@link Function}.
     *
     * @return {@link #deserialize(DeserializationData, Class)} as {@link Function}.
     */
    default BiFunction<DeserializationData, Class<?>, T> getDeserializerFunction()
    {
        return this::deserialize;
    }

    /**
     * Returns {@link #serialize(Object, SerializationData)} as {@link BiConsumer}.
     *
     * @return {@link #serialize(Object, SerializationData)} as {@link BiConsumer}.
     */
    default BiConsumer<T, SerializationData> getSerializerFunction()
    {
        return this::serialize;
    }

    /**
     * Serialize this object into given data object.
     *
     * @param object
     *         object to serialize.
     * @param data
     *         target data object to use.
     */
    void serialize(T object, SerializationData data);

    /**
     * Deserialize object from given data object.
     *
     * @param data
     *         target data object to use.
     *
     * @return deserialized object.
     */
    T deserialize(DeserializationData data);

    /**
     * Deserialize object from given data object.
     *
     * @param data
     *         target data object to use.
     * @param type
     *         expected type, might be null or object, some deserializer might then need to throw error.
     *
     * @return deserialized object.
     */
    default T deserialize(DeserializationData data, @Nullable Class<?> type)
    {
        return this.deserialize(data);
    }

    /**
     * Create instance of serializer from type and serialize/deserializer functions.
     *
     * @param type
     *         type of implemented value type.
     * @param serializer
     *         function that serialize object into given data object.
     * @param deserializer
     *         function that change given data object into deserialized object.
     * @param <T>
     *         type of implemented value type.
     *
     * @return serializer instance.
     */
    static <T> Serializer<T> of(Class<? super T> type, BiConsumer<T, SerializationData> serializer, Function<DeserializationData, T> deserializer)
    {
        return new SimpleSerializer<>(type, (data, x) -> deserializer.apply(data), serializer);
    }

    /**
     * Create instance of serializer from type and serialize/deserializer functions.
     *
     * @param type
     *         type of implemented value type.
     * @param serializer
     *         function that serialize object into given data object.
     * @param deserializer
     *         function that change given data object into deserialized object.
     * @param <T>
     *         type of implemented value type.
     *
     * @return serializer instance.
     */
    static <T> Serializer<T> ofDynamic(Class<? super T> type, BiConsumer<T, SerializationData> serializer, BiFunction<DeserializationData, Class<?>, T> deserializer)
    {
        return new SimpleSerializer<>(type, deserializer, serializer);
    }
}
