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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.diorite.commons.reflections.DioriteReflectionUtils;

class SimpleSerializationData implements SerializationData
{
    private final Class<?>      type;
    private final Serialization serialization;

    private final List<Object>        dataList = new ArrayList<>(20);
    private final Map<Object, Object> dataMap  = new LinkedHashMap<>(20);

    private String trueValue  = "true";
    private String falseValue = "false";

    SimpleSerializationData(Serialization serialization, Class<?> type)
    {
        this.serialization = serialization;
        this.type = type;
    }

    private void validateList(String key)
    {
        if (! key.isEmpty())
        {
            return;
        }
        if (! this.dataMap.isEmpty())
        {
            throw new SerializationException(this.type, "Can't serialize directly as list when other values are used.");
        }
    }

    private void validateMap(String key)
    {
        if (! key.isEmpty())
        {
            return;
        }
        if (! this.dataList.isEmpty())
        {
            throw new SerializationException(this.type, "Can't serialize map value when other value is directly serialized as list.");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> String toString(T object, @Nullable Class<T> type)
    {
        if (type == null)
        {
            type = (Class<T>) object.getClass();
        }
        if (DioriteReflectionUtils.getWrapperClass(type).equals(Boolean.class))
        {
            if ((Boolean) object)
            {
                return this.trueValue;
            }
            else
            {
                return this.falseValue;
            }
        }
        return object.toString();
    }

    @Override
    public void setTrueValue(String str)
    {
        this.trueValue = str;
    }

    @Override
    public void setFalseValue(String str)
    {
        this.falseValue = str;
    }

    @Override
    public Serialization getSerializationInstance()
    {
        return this.serialization;
    }

    @Override
    public <T> void add(String key, @Nullable T value)
    {
        this.validateMap(key);
        if (value == null)
        {
            this.dataMap.put(key, null);
            return;
        }
        if (Serialization.isSimple(value))
        {
            this.dataMap.put(key, this.toString(value, null));
            return;
        }
        if (! this.serialization.isSerializable(value))
        {
            if (! this.serialization.isStringSerializable(value))
            {
                throw new SerializationException(this.type, "Given value must be serializable! key: " + key + ", value: " + value);
            }
            this.dataMap.put(key, this.serialization.serializeToString(value));
            return;
        }
        this.dataMap.put(key, this.serialization.serialize(value));
    }

    @Override
    public <T> void add(String key, Class<T> type, @Nullable T value)
    {
        this.validateMap(key);
        if (value == null)
        {
            this.dataMap.put(key, null);
            return;
        }
        if (Serialization.isSimple(value))
        {
            this.dataMap.put(key, this.toString(value, type));
            return;
        }
        if (! this.serialization.isSerializable(type))
        {
            if (! this.serialization.isStringSerializable(type))
            {
                throw new SerializationException(this.type, "Given value must be serializable! key: " + key + ", value: (" + type.getName() + ") -> " + value);
            }
            this.dataMap.put(key, this.serialization.serializeToString(type, value));
            return;
        }
        this.dataMap.put(key, this.serialization.serialize(type, value));
    }

    @Override
    public <T> void addMappedList(String key, Class<T> type, Collection<? extends T> value, Function<T, String> mapper)
    {
        this.validateMap(key);
        Map<String, Object> valueMap = new LinkedHashMap<>(value.size());
        for (T t : value)
        {
            if (Serialization.isSimple(t))
            {
                valueMap.put(key, this.toString(t, type));
                continue;
            }
            valueMap.put(mapper.apply(t), this.serialization.serialize(type, t));
        }
        this.dataMap.put(key, valueMap);
    }

    @Override
    public <T> void addCollection(String key, Collection<? extends T> value, Class<T> type)
    {
        this.validateList(key);
        List<Object> result;
        if (key.isEmpty())
        {
            result = this.dataList;
        }
        else
        {
            result = new ArrayList<>(value.size());
        }
        for (T t : value)
        {
            if (Serialization.isSimple(t))
            {
                result.add(this.toString(t, type));
                continue;
            }
            result.add(this.serialization.serialize(type, t));
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, result);
        }
    }

    @Override
    public <T> void addMapAsList(String key, Map<?, ? extends T> value, Class<T> type)
    {
        this.validateList(key);
        List<Object> result;
        if (key.isEmpty())
        {
            result = this.dataList;
        }
        else
        {
            result = new ArrayList<>(value.size());
        }
        for (Entry<?, ? extends T> entry : value.entrySet())
        {
            if (Serialization.isSimple(entry.getValue()))
            {
                result.add(this.toString(entry.getValue(), type));
                continue;
            }
            result.add(this.serialization.serialize(type, entry.getValue()));
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, result);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> void addMapAsListWithKeys(String key, Map<?, ? extends T> value, Class<T> type, String keyPropertyName)
    {
        this.validateList(key);
        List<Object> result;
        if (key.isEmpty())
        {
            result = this.dataList;
        }
        else
        {
            result = new ArrayList<>(value.size());
        }
        for (Entry<?, ? extends T> entry : value.entrySet())
        {
            Object o = this.serialization.serialize(type, entry.getValue());
            if (o instanceof Map)
            {
                Object entryKey = entry.getKey();

                String keyStr;
                if (Serialization.isSimple(entryKey))
                {
                    keyStr = this.toString(entryKey, null);
                }
                else if (this.serialization.isStringSerializable(entryKey))
                {
                    keyStr = this.serialization.serializeToString(entryKey);
                }
                else
                {
                    throw new SerializationException(this.type, "(key: '" + key + "') Invalid, not serializable, key found" + entryKey);
                }
                ((Map) o).put(keyPropertyName, keyStr);
            }
            else
            {
                throw new SerializationException(this.type, "(key: '" + key + "') Expected map but found: " + o);
            }
            result.add(o);
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, result);
        }
    }

    @Override
    public <K, T> void addMap(String key, Map<? extends K, ? extends T> value, Class<K> keyType, Class<T> type)
    {
        this.validateMap(key);
        Map<Object, Object> resultMap;
        if (key.isEmpty())
        {
            resultMap = this.dataMap;
        }
        else
        {
            resultMap = new LinkedHashMap<>(value.size());
        }
        for (Entry<? extends K, ? extends T> entry : value.entrySet())
        {
            K entryKey = entry.getKey();
            if (Serialization.isSimple(entryKey))
            {
                if (Serialization.isSimple(entry.getValue()))
                {
                    resultMap.put(entryKey, this.toString(entry.getValue(), type));
                    continue;
                }
                resultMap.put(entryKey, this.serialization.serialize(type, entry.getValue()));
                continue;
            }
            resultMap.put(this.serialization.serializeToString(keyType, entryKey), this.serialization.serialize(type, entry.getValue()));
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, resultMap);
        }
    }

    @Override
    public <K, T> void addMap(String key, Map<? extends K, ? extends T> value, Class<T> type)
    {
        this.validateMap(key);
        Map<Object, Object> resultMap;
        if (key.isEmpty())
        {
            resultMap = this.dataMap;
        }
        else
        {
            resultMap = new LinkedHashMap<>(value.size());
        }
        for (Entry<? extends K, ? extends T> entry : value.entrySet())
        {
            K entryKey = entry.getKey();
            if (Serialization.isSimple(entryKey))
            {
                if (Serialization.isSimple(entry.getValue()))
                {
                    resultMap.put(entryKey, this.toString(entry.getValue(), type));
                    continue;
                }
                resultMap.put(entryKey, this.serialization.serialize(type, entry.getValue()));
                continue;
            }
            resultMap.put(this.serialization.serializeToString(entryKey), this.serialization.serialize(type, entry.getValue()));
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, resultMap);
        }
    }

    @Override
    public <K, T> void addMap(String key, Map<? extends K, ? extends T> value, Class<T> type, Function<K, String> keyMapper)
    {
        this.validateMap(key);
        Map<Object, Object> resultMap;
        if (key.isEmpty())
        {
            resultMap = this.dataMap;
        }
        else
        {
            resultMap = new LinkedHashMap<>(value.size());
        }
        for (Entry<? extends K, ? extends T> entry : value.entrySet())
        {
            K entryKey = entry.getKey();
            if (Serialization.isSimple(entryKey))
            {
                if (Serialization.isSimple(entry.getValue()))
                {
                    resultMap.put(entryKey, this.toString(entry.getValue(), type));
                    continue;
                }
                resultMap.put(entryKey, this.serialization.serialize(type, entry.getValue()));
                continue;
            }
            resultMap.put(keyMapper.apply(entryKey), this.serialization.serialize(type, entry.getValue()));
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, resultMap);
        }
    }

    Object rawValue()
    {
        if (! this.dataList.isEmpty())
        {
            return this.dataList;
        }
        return this.dataMap;
    }
}
