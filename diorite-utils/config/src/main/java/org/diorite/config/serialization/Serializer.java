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
    Class<T> getType();

    /**
     * Returns {@link #deserialize(DeserializationData, Serialization)} as {@link Function}.
     *
     * @return {@link #deserialize(DeserializationData, Serialization)} as {@link Function}.
     */
    default BiFunction<DeserializationData, Serialization, T> getDeserializerFunction()
    {
        return this::deserialize;
    }

    /**
     * Returns {@link #serialize(SerializationData, Serialization)} as {@link Function}.
     *
     * @return {@link #serialize(SerializationData, Serialization)} as {@link Function}.
     */
    default BiConsumer<SerializationData, Serialization> getSerializerFunction()
    {
        return this::serialize;
    }

    /**
     * Serialize this object into given data object.
     *
     * @param data
     *         target data object to use.
     * @param serialization
     *         serialization instance.
     */
    void serialize(SerializationData data, Serialization serialization);

    /**
     * Deserialize object from given data object.
     *
     * @param data
     *         target data object to use.
     * @param serialization
     *         serialization instance.
     */
    T deserialize(DeserializationData data, Serialization serialization);
}
