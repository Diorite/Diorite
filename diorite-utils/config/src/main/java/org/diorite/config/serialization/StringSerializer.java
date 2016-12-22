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

import java.util.function.Function;

/**
 * Represent serializer that can serialize objects to simple string, and deserialize strings to objects.
 *
 * @param <T>
 *         type of serializer object.
 */
public interface StringSerializer<T>
{
    /**
     * Returns type of values for this serializer.
     *
     * @return type of values for this serializer.
     */
    Class<T> getType();

    /**
     * Returns {@link #deserialize(String)} as {@link Function}.
     *
     * @return {@link #deserialize(String)} as {@link Function}.
     */
    default Function<String, T> deserializerFunction()
    {
        return this::deserialize;
    }

    /**
     * Returns {@link #serialize(Object)} as {@link Function}.
     *
     * @return {@link #serialize(Object)} as {@link Function}.
     */
    default Function<T, String> serializerFunction()
    {
        return this::serialize;
    }

    /**
     * Deserialize given string to valid object.
     *
     * @param data
     *         string data to deserialize.
     *
     * @return deserialized object.
     */
    T deserialize(String data);

    /**
     * Serialize given object to simple string value.
     *
     * @param data
     *         object data to serialize.
     *
     * @return serialized string value.
     */
    String serialize(T data);

    /**
     * Create instance of string serializer from type and serialize/deserializer functions.
     *
     * @param type
     *         type of implemented value type.
     * @param serializer
     *         function that change object into simple string.
     * @param deserializer
     *         function that change simple string into object.
     * @param <T>
     *         type of implemented value type.
     *
     * @return string serializer instance.
     */
    static <T> StringSerializer<T> of(Class<T> type, Function<T, String> serializer, Function<String, T> deserializer)
    {
        return new SimpleStringSerializer<>(type, deserializer, serializer);
    }
}
