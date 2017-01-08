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
import javax.annotation.Signed;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.diorite.config.serialization.comments.DocumentComments;

/**
 * Interface used in serialization methods to help with serializaing different types of data.
 */
public interface SerializationData
{
    String DEFAULT_KEY_PROPERTY = "_key";

    /**
     * Returns type of serializer data.
     *
     * @return type of serializer data.
     */
    SerializationType getSerializationType();

    DocumentComments getComments();

    void setComments(DocumentComments comments);

    /**
     * Change how {@link Boolean#TRUE} value is saved to config. <br>
     * Note that this value must be supported by serializer.
     *
     * @param str
     *         string representation of {@link Boolean#TRUE} value.
     */
    void setTrueValue(String str);

    /**
     * Change how {@link Boolean#FALSE} value is saved to config. <br>
     * Note that this value must be supported by serializer.
     *
     * @param str
     *         string representation of {@link Boolean#FALSE} value.
     */
    void setFalseValue(String str);

    /**
     * Returns serialization object that started serialization.
     *
     * @return serialization object that started serialization.
     */
    Serialization getSerializationInstance();

    /**
     * Adds value to serializer data instance.
     *
     * @param key
     *         key of value.
     * @param value
     *         value to serialize.
     * @param <T>
     *         type of object.
     */
    <T> void add(String key, @Nullable T value);

    /**
     * Adds value to serializer data instance.
     *
     * @param key
     *         key of value.
     * @param type
     *         type of value.
     * @param value
     *         value to serialize.
     * @param <T>
     *         type of object.
     */
    <T> void add(String key, @Nullable T value, Class<T> type);

    /**
     * Adds value to serializer data instance with given formatting, note that you need to manually deserialize that value in deserializer. <br>
     * Null values are saved without formatting.
     *
     * @param key
     *         key of value.
     * @param format
     *         format of value.
     * @param value
     *         value to serialize.
     * @param <T>
     *         type of object.
     *
     * @see java.util.Formatter
     */
    default <T> void addFormatted(String key, String format, @Nullable T value)
    {
        if (value == null)
        {
            this.add(key, null);
        }
        this.add(key, String.format(format, value));
    }

    /**
     * Adds boolean value to serializer data instance using given words for true and false value.
     *
     * @param key
     *         key of value.
     * @param value
     *         value to serialize.
     * @param forTrue
     *         string representation of {@link Boolean#TRUE} value.
     * @param forFalse
     *         string representation of {@link Boolean#FALSE} value.
     */
    default void addBoolean(String key, @Nullable Boolean value, String forTrue, String forFalse)
    {
        if (value == null)
        {
            this.add(key, null);
            return;
        }
        if (value)
        {
            this.add(key, forTrue);
        }
        else
        {
            this.add(key, forFalse);
        }
    }

    /**
     * Adds numeric value to serializer with padding. <br>
     * Like setting padding to 3 and saving value of 35 will be saved as '035'
     *
     * @param key
     *         key of value.
     * @param value
     *         value to serialize.
     * @param padding
     *         padding to use.
     * @param <T>
     *         type of object.
     *
     * @throws IllegalArgumentException
     *         when given number is floating point number.
     */
    default <T extends Number> void addNumber(String key, @Nullable T value, @Signed int padding)
    {
        if (value == null)
        {
            this.add(key, null);
        }
        if (padding <= 0)
        {
            this.add(key, value);
            return;
        }
        if ((value instanceof Float) || (value instanceof Double))
        {
            throw new IllegalArgumentException("Can't use padding for float or double!");
        }
        if (this.getSerializationType() == SerializationType.YAML) // yaml reads padded value as octal, so we need escape it
        {
            this.add(key, String.format("%0" + padding + "d", value), String.class);
            return;
        }
        this.add(key, String.format("%0" + padding + "d", value));
    }

    /**
     * Adds numeric value to serializer as hex value (like 0xABCDEF), it must be later read back by proper function in {@link DeserializationData}.
     *
     * @param key
     *         key of value.
     * @param value
     *         value to serialize.
     * @param <T>
     *         type of object.
     *
     * @throws IllegalArgumentException
     *         when given number is floating point number.
     */
    default <T extends Number> void addHexNumber(String key, @Nullable T value)
    {
        this.addHexNumber(key, value, 1);
    }

    /**
     * Adds numeric value to serializer as hex value (like 0xABCDEF), it must be later read back by proper function in {@link DeserializationData}.
     * <br>
     * You can also set custom padding value, so values like 0xff, will be saved as 0x0000ff (padding = 6)
     *
     * @param key
     *         key of value.
     * @param value
     *         value to serialize.
     * @param padding
     *         padding to use.
     * @param <T>
     *         type of object.
     *
     * @throws IllegalArgumentException
     *         when given number is floating point number.
     */
    default <T extends Number> void addHexNumber(String key, @Nullable T value, @Signed int padding)
    {
        if (value == null)
        {
            this.add(key, null);
        }
        if ((value instanceof Float) || (value instanceof Double))
        {
            throw new IllegalArgumentException("Can't use hex values for float or double!");
        }
        this.add(key, String.format("0x%0" + padding + "x", value));
    }

    /**
     * Add collection value as map value, using given function to create keys for each value.
     *
     * @param key
     *         key of value, use empty string to add values directly to backing map.
     * @param type
     *         type of elements.
     * @param value
     *         value to serialize.
     * @param mapper
     *         function that returns key value for each element.
     * @param <T>
     *         type of elements.
     */
    <T> void addMappedList(String key, Class<T> type, @Nullable Collection<? extends T> value, Function<T, String> mapper);

    /**
     * Add list value.
     *
     * @param key
     *         key of value, use empty string to save value directly as list.
     * @param value
     *         value to serialize.
     * @param type
     *         type of elements.
     * @param <T>
     *         type of elements.
     */
    <T> void addCollection(String key, @Nullable Collection<? extends T> value, Class<T> type);

    /**
     * Add map values as list, keys are lost.
     *
     * @param key
     *         key of value, use empty string to save value directly as list.
     * @param value
     *         value to serialize.
     * @param type
     *         type of elements.
     * @param <T>
     *         type of elements.
     */
    <T> void addMapAsList(String key, @Nullable Map<?, ? extends T> value, Class<T> type);

    /**
     * Add map values as list, keys are save as one of values using {@link #DEFAULT_KEY_PROPERTY} as key.
     *
     * @param key
     *         key of value, use empty string to save value directly as list.
     * @param value
     *         value to serialize.
     * @param type
     *         type of elements.
     * @param <T>
     *         type of elements.
     */
    default <T> void addMapAsListWithKeys(String key, @Nullable Map<?, ? extends T> value, Class<T> type)
    {
        this.addMapAsListWithKeys(key, value, type, DEFAULT_KEY_PROPERTY);
    }

    /**
     * Add map values as list, keys are save as one of values using given string as key.
     *
     * @param key
     *         key of value, use empty string to save value directly as list.
     * @param value
     *         value to serialize.
     * @param type
     *         type of elements.
     * @param keyPropertyName
     *         name of config key used to store map key.
     * @param <T>
     *         type of elements.
     */
    <T> void addMapAsListWithKeys(String key, @Nullable Map<?, ? extends T> value, Class<T> type, String keyPropertyName);

    /**
     * Adds all map values to this data. <br>
     * This method works only if key type has registered String serializer or it is some primitive/simple type.
     *
     * @param key
     *         key of value, use empty string to add values directly to backing map.
     * @param value
     *         value to serialize.
     * @param keyType
     *         type of map keys.
     * @param type
     *         type of elements.
     * @param <K>
     *         type of map keys.
     * @param <T>
     *         type of elements.
     */
    <K, T> void addMap(String key, @Nullable Map<? extends K, ? extends T> value, Class<K> keyType, Class<T> type);

    /**
     * Adds all map values to this data. <br>
     * This method works only if key type has registered String serializer.
     *
     * @param key
     *         key of value, use empty string to add values directly to backing map.
     * @param value
     *         value to serialize.
     * @param type
     *         type of elements.
     * @param <K>
     *         type of map keys.
     * @param <T>
     *         type of elements.
     */
    <K, T> void addMap(String key, @Nullable Map<? extends K, ? extends T> value, Class<T> type);

    /**
     * Adds all map values to this data.
     *
     * @param key
     *         key of value, use empty string to add values directly to backing map.
     * @param value
     *         value to serialize.
     * @param type
     *         type of elements.
     * @param keyMapper
     *         function that change key object to string that can be used to save data.
     * @param <K>
     *         type of map keys.
     * @param <T>
     *         type of elements.
     */
    <K, T> void addMap(String key, @Nullable Map<? extends K, ? extends T> value, Class<T> type, Function<K, String> keyMapper);

    /**
     * Create serialization data instance for given manager.
     *
     * @param serializationType
     *         type of serialization.
     * @param serialization
     *         serialization manager to use.
     * @param type
     *         type of object to serialize.
     *
     * @return created serialization data instance.
     */
    static SerializationData create(SerializationType serializationType, Serialization serialization, Class<?> type)
    {
        return SimpleSerializationData.createSerializationData(serializationType, serialization, type);
    }
}
