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

import static org.diorite.config.serialization.SerializationData.DEFAULT_KEY_PROPERTY;


import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Interface used in serialization methods to help with serializaing different types of data.
 */
@SuppressWarnings("MagicNumber")
public interface DeserializationData
{
    /**
     * Returns type of serializer data.
     *
     * @return type of serializer data.
     */
    SerializationType getSerializationType();

    /**
     * Returns serialization object that started serialization.
     *
     * @return serialization object that started serialization.
     */
    Serialization getSerializationInstance();

    /**
     * Add string values that can be interpreted as {@link Boolean#TRUE} value when reading from config. <br>
     * Note that this value must be supported by deserializer too.
     *
     * @param strings
     *         string representations of {@link Boolean#TRUE} value.
     */
    void addTrueValues(String... strings);

    /**
     * Add string values that can be interpreted as {@link Boolean#FALSE} value when reading from config. <br>
     * Note that this value must be supported by deserializer too.
     *
     * @param strings
     *         string representations of {@link Boolean#FALSE} value.
     */
    void addFalseValues(String... strings);

    /**
     * Returns set of root level keys.
     *
     * @return set of root level keys.
     */
    Set<String> getKeys();

    /**
     * Check if this data instance contains given key, note that value on that key might be still a null value.
     *
     * @param key
     *         key to check.
     *
     * @return true if key exists.
     */
    boolean containsKey(String key);

    /**
     * Get and deserialize given object from this data instance. <br>
     * If there is no value, or it is null, throw error.
     *
     * @param key
     *         value to deserialize.
     * @param type
     *         type of value.
     * @param <T>
     *         type of value.
     *
     * @return deserialized value.
     */
    default <T> T getOrThrow(String key, Class<T> type)
    {
        T t = this.get(key, type, null);
        if (t == null)
        {
            throw new IllegalStateException("Missing value: " + key);
        }
        return t;
    }

    /**
     * Get and deserialize given object from this data instance.
     *
     * @param key
     *         value to deserialize.
     * @param type
     *         type of value.
     * @param <T>
     *         type of value.
     *
     * @return deserialized value.
     */
    @Nullable
    default <T> T get(String key, Class<T> type)
    {
        return this.get(key, type, null);
    }

    /**
     * Get and deserialize given object from this data instance, or default value if there is no value on given key.
     *
     * @param key
     *         value to deserialize.
     * @param type
     *         type of value.
     * @param def
     *         default value.
     * @param <T>
     *         type of value.
     *
     * @return deserialized value.
     */
    @Nullable
    <T> T get(String key, Class<T> type, @Nullable T def);

    /**
     * Get and deserialize given boolean value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default boolean getAsBoolean(String key)
    {
        Boolean val = this.get(key, Boolean.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get boolean value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        return val;
    }

    /**
     * Get and deserialize given byte value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default byte getAsByte(String key)
    {
        Byte val = this.get(key, Byte.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get byte value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        return val;
    }

    /**
     * Get and deserialize given char value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default char getAsChar(String key)
    {
        Character val = this.get(key, Character.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get char value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        return val;
    }

    /**
     * Get and deserialize given short value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default short getAsShort(String key)
    {
        Short val = this.get(key, Short.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get short value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        return val;
    }

    /**
     * Get and deserialize given int value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default int getAsInt(String key)
    {
        Integer val = this.get(key, Integer.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get int value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        return val;
    }

    /**
     * Get and deserialize given long value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default long getAsLong(String key)
    {
        Long val = this.get(key, Long.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get long value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        return val;
    }

    /**
     * Get and deserialize given float value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default float getAsFloat(String key)
    {
        Float val = this.get(key, Float.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get float value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        return val;
    }

    /**
     * Get and deserialize given double value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default double getAsDouble(String key)
    {
        Double val = this.get(key, Double.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get double value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        return val;
    }

    /**
     * Get and deserialize given boolean value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default boolean getAsBoolean(String key, boolean def)
    {
        Boolean v = this.get(key, Boolean.class, def);
        if (v == null)
        {
            return def;
        }
        return v;
    }

    /**
     * Get and deserialize given byte value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default byte getAsByte(String key, byte def)
    {
        Byte v = this.get(key, Byte.class, def);
        if (v == null)
        {
            return def;
        }
        return v;
    }

    /**
     * Get and deserialize given char value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default char getAsChar(String key, char def)
    {
        Character v = this.get(key, Character.class, def);
        if (v == null)
        {
            return def;
        }
        return v;
    }

    /**
     * Get and deserialize given short value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default short getAsShort(String key, short def)
    {
        Short v = this.get(key, Short.class, def);
        if (v == null)
        {
            return def;
        }
        return v;
    }

    /**
     * Get and deserialize given int value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default int getAsInt(String key, int def)
    {
        Integer v = this.get(key, Integer.class, def);
        if (v == null)
        {
            return def;
        }
        return v;
    }

    /**
     * Get and deserialize given long value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default long getAsLong(String key, long def)
    {
        Long v = this.get(key, Long.class, def);
        if (v == null)
        {
            return def;
        }
        return v;
    }

    /**
     * Get and deserialize given float value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default float getAsFloat(String key, float def)
    {
        Float v = this.get(key, Float.class, def);
        if (v == null)
        {
            return def;
        }
        return v;
    }

    /**
     * Get and deserialize given double value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default double getAsDouble(String key, double def)
    {
        Double v = this.get(key, Double.class, def);
        if (v == null)
        {
            return def;
        }
        return v;
    }

    /**
     * Get and deserialize given number value from this data instance, method will throw {@link NullPointerException} if type is primitive and value don't
     * exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param type
     *         type of number.
     * @param def
     *         default value.
     * @param <T>
     *         type of number.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when type is primitive and value don't exists or it's null.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    default <T extends Number> T getAsHexNumber(String key, Class<T> type, @Nullable T def)
    {
        if (type == byte.class)
        {
            return (T) (Byte) this.getAsHexByte(key);
        }
        if (type == short.class)
        {
            return (T) (Short) this.getAsHexShort(key);
        }
        if (type == int.class)
        {
            return (T) (Integer) this.getAsHexInt(key);
        }
        if (type == long.class)
        {
            return (T) (Long) this.getAsHexLong(key);
        }

        String val = this.get(key, String.class, null);
        if (val == null)
        {
            return def;
        }
        val = val.substring(2);
        try
        {
            Object r;
            if (type == Byte.class)
            {
                r = Byte.parseByte(val, 16);
            }
            else if (type == Short.class)
            {
                r = Short.parseShort(val, 16);
            }
            else if (type == Integer.class)
            {
                r = Integer.parseInt(val, 16);
            }
            else if (type == Long.class)
            {
                r = Long.parseLong(val, 16);
            }
            else
            {
                throw new RuntimeException("Can't deserialize " + type + " as hex number.");
            }
            return (T) r;
        }
        catch (NumberFormatException e)
        {
            return def;
        }
    }

    /**
     * Get and deserialize given byte value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default byte getAsHexByte(String key)
    {
        String val = this.get(key, String.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get byte value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        val = val.substring(2);
        return Byte.parseByte(val, 16);
    }

    /**
     * Get and deserialize given short value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default short getAsHexShort(String key)
    {
        String val = this.get(key, String.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get short value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        val = val.substring(2);
        return Short.parseShort(val, 16);
    }

    /**
     * Get and deserialize given int value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default int getAsHexInt(String key)
    {
        String val = this.get(key, String.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get int value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        val = val.substring(2);
        return Integer.parseInt(val, 16);
    }

    /**
     * Get and deserialize given long value from this data instance, method will throw {@link NullPointerException} if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     *
     * @return deserialized value.
     *
     * @throws NullPointerException
     *         when value don't exists or it's null.
     */
    default long getAsHexLong(String key)
    {
        String val = this.get(key, String.class, null);
        if (val == null)
        {
            throw new NullPointerException("Can't get long value on: " + key + ", as value is null, but primitive can't be a null.");
        }
        val = val.substring(2);
        return Long.parseLong(val, 16);
    }

    /**
     * Get and deserialize given byte value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default byte getAsHexByte(String key, byte def)
    {
        String v = this.get(key, String.class, null);
        if (v == null)
        {
            return def;
        }
        v = v.substring(2);
        return Byte.parseByte(v, 16);
    }

    /**
     * Get and deserialize given short value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default short getAsHexShort(String key, short def)
    {
        String v = this.get(key, String.class, null);
        if (v == null)
        {
            return def;
        }
        v = v.substring(2);
        return Short.parseShort(v, 16);
    }

    /**
     * Get and deserialize given int value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default int getAsHexInt(String key, int def)
    {
        String v = this.get(key, String.class, null);
        if (v == null)
        {
            return def;
        }
        v = v.substring(2);
        return Integer.parseInt(v, 16);
    }

    /**
     * Get and deserialize given long value from this data instance, or return default value if value don't exists or it's null.
     *
     * @param key
     *         value to deserialize.
     * @param def
     *         default value.
     *
     * @return deserialized value.
     */
    default long getAsHexLong(String key, long def)
    {
        String v = this.get(key, String.class, null);
        if (v == null)
        {
            return def;
        }
        v = v.substring(2);
        return Long.parseLong(v, 16);
    }

    /**
     * Deserialize list on given key, if key contains Map instead of List, it will be deserialized and returned as list of values.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param type
     *         type of elements.
     * @param <T>
     *         type of elements.
     *
     * @return deserialized value.
     */
    default <T> List<T> getAsList(String key, Class<T> type)
    {
        ArrayList<T> objects = new ArrayList<>(20);
        this.getAsCollection(key, type, objects);
        objects.trimToSize();
        return objects;
    }

    /**
     * Deserialize list on given key, if key contains Map instead of List, it will be deserialized as list of values. <br>
     * All deserialized values are added to given collection.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param type
     *         type of elements.
     * @param collection
     *         target collection for deserialized elements.
     * @param <T>
     *         type of elements.
     * @param <C>
     *         type of collection.
     */
    <T, C extends Collection<T>> void getAsCollection(String key, Class<T> type, C collection);

    /**
     * Deserialize map on given key, if key contains Collection instead of Map, all elements are added to map using given key mapper. <br>
     * If key contains Map value, keyMapper is still used, and all keys are updated.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param type
     *         type of elements.
     * @param keyMapper
     *         function that returns key name for given element.
     * @param <T>
     *         type of elements.
     *
     * @return deserialized value.
     */
    default <T> Map<String, T> getAsMap(String key, Class<T> type, Function<T, String> keyMapper)
    {
        LinkedHashMap<String, T> map = new LinkedHashMap<>(20);
        this.getAsMap(key, type, keyMapper, map);
        return map;
    }

    /**
     * Deserialize map on given key, if key contains Collection instead of Map, all elements are added to map using {@link
     * SerializationData#DEFAULT_KEY_PROPERTY} value as map key. <br>
     * If key contains Map value, key will be still updated if key property exists.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param keyType
     *         type of keys.
     * @param type
     *         type of elements.
     * @param <T>
     *         type of elements.
     * @param <K>
     *         type of keys.
     *
     * @return deserialized value.
     */
    default <K, T> Map<K, T> getAsMapWithKeys(String key, Class<K> keyType, Class<T> type)
    {
        LinkedHashMap<K, T> map = new LinkedHashMap<>(20);
        this.getAsMapWithKeys(key, keyType, type, DEFAULT_KEY_PROPERTY, map);
        return map;
    }

    /**
     * Deserialize map on given key, if key contains Collection instead of Map, all elements are added to map using given property value as map key. <br>
     * If key contains Map value, key will be still updated if key property exists.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param keyType
     *         type of keys.
     * @param type
     *         type of elements.
     * @param keyPropertyName
     *         name of key property for map.
     * @param <T>
     *         type of elements.
     * @param <K>
     *         type of keys.
     *
     * @return deserialized value.
     */
    default <K, T> Map<K, T> getAsMapWithKeys(String key, Class<K> keyType, Class<T> type, String keyPropertyName)
    {
        LinkedHashMap<K, T> map = new LinkedHashMap<>(20);
        this.getAsMapWithKeys(key, keyType, type, keyPropertyName, map);
        return map;
    }

    /**
     * Deserialize map on given key, key type must be serializable to string.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param keyType
     *         type of keys, must be string serializable.
     * @param type
     *         type of elements.
     * @param <K>
     *         type of keys, must be string serializable.
     * @param <T>
     *         type of elements.
     *
     * @return deserialized value.
     */
    default <K, T> Map<K, T> getMap(String key, Class<K> keyType, Class<T> type)
    {
        LinkedHashMap<K, T> map = new LinkedHashMap<>(20);
        this.getMap(key, keyType, type, map);
        return map;
    }

    /**
     * Deserialize map on given key using keyMapper to change string keys to key objects. <br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param keyMapper
     *         function that change string key to key object.
     * @param type
     *         type of elements.
     * @param <K>
     *         type of keys.
     * @param <T>
     *         type of elements.
     *
     * @return deserialized value.
     */
    default <K, T> Map<K, T> getMap(String key, Function<String, K> keyMapper, Class<T> type)
    {
        LinkedHashMap<K, T> map = new LinkedHashMap<>(20);
        this.getMap(key, keyMapper, type, map);
        return map;
    }

    /**
     * Deserialize map on given key, if key contains Collection instead of Map, all elements are added to map using given key mapper. <br>
     * If key contains Map value, keyMapper is still used, and all keys are updated.<br>
     * All deserialized values are added to given map.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param type
     *         type of elements.
     * @param keyMapper
     *         function that returns key name for given element.
     * @param map
     *         target map for deserialized elements.
     * @param <T>
     *         type of elements.
     * @param <M>
     *         type of map.
     */
    <T, M extends Map<String, T>> void getAsMap(String key, Class<T> type, Function<T, String> keyMapper, M map);

    /**
     * Deserialize map on given key, if key contains Collection instead of Map, all elements are added to map using {@link
     * SerializationData#DEFAULT_KEY_PROPERTY} value as map key. <br>
     * If key contains Map value, key will be still updated if key property exists.<br>
     * All deserialized values are added to given map.
     *
     * @param key
     *         value to deserialize.
     * @param keyType
     *         type of keys.
     * @param type
     *         type of elements
     * @param map
     *         target map for deserialized elements.
     * @param <K>
     *         type of keys
     * @param <T>
     *         type of elements
     * @param <M>
     *         type of map.
     */
    default <K, T, M extends Map<K, T>> void getAsMapWithKeys(String key, Class<K> keyType, Class<T> type, M map)
    {
        this.getAsMapWithKeys(key, keyType, type, DEFAULT_KEY_PROPERTY, map);
    }

    /**
     * Deserialize map on given key, if key contains Collection instead of Map, all elements are added to map using given property value as map key. <br>
     * If key contains Map value, key will be still updated if key property exists.<br>
     * All deserialized values are added to given map.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param keyType
     *         type of keys.
     * @param type
     *         type of elements.
     * @param map
     *         target map for deserialized elements.
     * @param keyPropertyName
     *         name of key property for map.
     * @param <K>
     *         type of keys
     * @param <T>
     *         type of elements
     * @param <M>
     *         type of map.
     */
    <K, T, M extends Map<K, T>> void getAsMapWithKeys(String key, Class<K> keyType, Class<T> type, String keyPropertyName, M map);

    /**
     * Deserialize map on given key, key type must be serializable to string.<br>
     * All deserialized values are added to given map.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param keyType
     *         type of keys, must be string serializable.
     * @param type
     *         type of elements
     * @param map
     *         target map for deserialized elements.
     * @param <K>
     *         type of keys, must be string serializable.
     * @param <T>
     *         type of elements
     * @param <M>
     *         type of map.
     */
    <K, T, M extends Map<K, T>> void getMap(String key, Class<K> keyType, Class<T> type, M map);

    /**
     * Deserialize map on given key using keyMapper to change string keys to key objects.<br>
     * All deserialized values are added to given map.<br>
     * Use empty key to deserialize map from root element.
     *
     * @param key
     *         value to deserialize.
     * @param keyMapper
     *         function that change string key to key object.
     * @param type
     *         type of elements
     * @param map
     *         target map for deserialized elements.
     * @param <K>
     *         type of keys.
     * @param <T>
     *         type of elements
     * @param <M>
     *         type of map.\
     */
    <K, T, M extends Map<K, T>> void getMap(String key, Function<String, K> keyMapper, Class<T> type, M map);

//    /**
//     * Return copy of raw data as map instance.
//     *
//     * @return simple map of values.
//     */
//    Map<String, Object> asMap();
//
//    /**
//     * Create deserialization data instance for given manager.
//     *
//     * @param serialization
//     *         serialization manager to use.
//     * @param dataMap
//     *         map with configuration values.
//     *
//     * @return created deserialization data instance.
//     */
//    static DeserializationData create(Serialization serialization, Map<String, Object> dataMap)
//    {
//        return new SimpleDeserializationData(serialization, dataMap);
//    }
}
