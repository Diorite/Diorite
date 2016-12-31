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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.serialization.comments.CommentsNode;
import org.diorite.config.serialization.comments.DocumentComments;

class SimpleSerializationData implements SerializationData
{
    private final SerializationType serializationType;

    private final Class<?>         type;
    private final Serialization    serialization;
    private final DocumentComments comments;

    private final Set<Class<?>> scannedCommentsClasses = new HashSet<>(20);

    private final List<Object>        dataList = new ArrayList<>(20);
    private final Map<Object, Object> dataMap  = new LinkedHashMap<>(20);

    private String trueValue  = "true";
    private String falseValue = "false";

    SimpleSerializationData(SerializationType serializationType, Serialization serialization, Class<?> type)
    {
        this.serializationType = serializationType;
        this.serialization = serialization;
        this.type = type;
        this.comments = DocumentComments.parseFromAnnotations(type);
    }

    @Override
    public SerializationType getSerializationType()
    {
        return this.serializationType;
    }

    private <T> Object serialize(T object)
    {
        return this.serialization.serialize(object, this.serializationType);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private <T> Object serialize(Class<T> type, @Nullable T object)
    {
        if (object == null)
        {
            return null;
        }
        if (type == Object.class)
        {
            type = (Class<T>) object.getClass();
        }
        return this.serialization.serialize(type, object, this.serializationType);
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
    @Nullable
    private <T> String toString(@Nullable T object, @Nullable Class<T> type)
    {
        if (object == null)
        {
            return null;
        }
        if ((type == null) || (type == Object.class))
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

    private void addComments(Class<?> type, String... key)
    {
        if (! this.scannedCommentsClasses.add(type))
        {
            return;
        }
        for (int i = 0; i < key.length; i++)
        {
            if (key[i].isEmpty())
            {
                key[i] = CommentsNode.ANY;
            }
        }
        DocumentComments comments = DocumentComments.parseFromAnnotations(type);
        this.comments.join(key, comments);
    }

    private void addComments(Class<?> type, String key)
    {
        if (! this.scannedCommentsClasses.add(type))
        {
            return;
        }
        DocumentComments comments = DocumentComments.parseFromAnnotations(type);
        if (key.isEmpty())
        {
            this.comments.join(CommentsNode.ANY, comments);
        }
        this.comments.join(key, comments);
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

        Class<?> valueClass = value.getClass();
        this.addComments(valueClass, key);

        if (! this.serialization.isSerializable(value))
        {
            if (this.serialization.isStringSerializable(value))
            {
                this.dataMap.put(key, this.serialization.serializeToString(value));
                return;
            }
            if (this.serialization.canBeSerialized(valueClass))
            {
                this.dataMap.put(key, this.serialize(value));
                return;
            }
            throw new SerializationException(this.type, "Given value must be serializable! key: " + key + ", value: " + value);
        }
        this.dataMap.put(key, this.serialize(value));
    }

    @Override
    public <T> void add(String key, @Nullable T value, Class<T> type)
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

        this.addComments(value.getClass(), key);

        if (! this.serialization.isSerializable(type))
        {
            if (this.serialization.isStringSerializable(type))
            {
                this.dataMap.put(key, this.serialization.serializeToString(type, value));
                return;
            }
            if (this.serialization.canBeSerialized(value.getClass()))
            {
                this.dataMap.put(key, this.serialize(type, value));
                return;
            }
            throw new SerializationException(this.type, "Given value must be serializable! key: " + key + ", value: (" + type.getName() + ") -> " + value);
        }
        this.dataMap.put(key, this.serialize(type, value));
    }

    @Override
    public <T> void addMappedList(String key, Class<T> type, Collection<? extends T> value, Function<T, String> mapper)
    {
        this.validateMap(key);

        this.addComments(type, key, CommentsNode.ANY);

        Map<String, Object> valueMap = new LinkedHashMap<>(value.size());
        for (T t : value)
        {
            if (t == null)
            {
                valueMap.put(key, null);
                continue;
            }

            this.addComments(t.getClass(), key, CommentsNode.ANY);

            if (Serialization.isSimple(t))
            {
                valueMap.put(key, this.toString(t, type));
                continue;
            }
            valueMap.put(mapper.apply(t), this.serialize(type, t));
        }
        this.dataMap.put(key, valueMap);
    }

    @Override
    public <T> void addCollection(String key, Collection<? extends T> value, Class<T> type)
    {
        this.validateList(key);

        this.addComments(type, key);

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
            if (t == null)
            {
                result.add(null);
                continue;
            }

            if (Serialization.isSimple(t))
            {
                result.add(this.toString(t, type));
                continue;
            }

            this.addComments(t.getClass(), key);

            result.add(this.serialize(type, t));
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

        this.addComments(type, key);

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
            T entryValue = entry.getValue();

            if (entryValue != null)
            {
                this.addComments(entryValue.getClass(), key);
            }

            if (Serialization.isSimple(entryValue))
            {
                result.add(this.toString(entryValue, type));
                continue;
            }
            result.add(this.serialize(type, entryValue));
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

        this.addComments(type, key);

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
            T entryValue = entry.getValue();

            if (entryValue != null)
            {
                this.addComments(entryValue.getClass(), key);
            }

            Object o = this.serialize(type, entryValue);
            if (o instanceof Map)
            {
                Object entryKey = entry.getKey();
                if (entryKey != null)
                {
                    this.addComments(entryKey.getClass(), key);
                }

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

        this.addComments(type, key, CommentsNode.ANY);
        this.addComments(keyType, key);

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
            T entryValue = entry.getValue();

            if (entryKey != null)
            {
                this.addComments(entryKey.getClass(), key);
            }
            if (entryValue != null)
            {
                this.addComments(entryValue.getClass(), key, CommentsNode.ANY);
            }

            if (Serialization.isSimple(entryKey))
            {
                if (Serialization.isSimple(entryValue))
                {
                    resultMap.put(entryKey, this.toString(entryValue, type));
                    continue;
                }
                resultMap.put(entryKey, this.serialize(type, entryValue));
                continue;
            }
            resultMap.put(this.serialization.serializeToString(keyType, entryKey), this.serialize(type, entryValue));
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

        this.addComments(type, key, CommentsNode.ANY);

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
            T entryValue = entry.getValue();

            if (entryKey != null)
            {
                this.addComments(entryKey.getClass(), key);
            }
            if (entryValue != null)
            {
                this.addComments(entryValue.getClass(), key, CommentsNode.ANY);
            }

            if (Serialization.isSimple(entryKey))
            {
                if (Serialization.isSimple(entryValue))
                {
                    resultMap.put(entryKey, this.toString(entryValue, type));
                    continue;
                }
                resultMap.put(entryKey, this.serialize(type, entryValue));
                continue;
            }
            resultMap.put(this.serialization.serializeToString(entryKey), this.serialize(type, entryValue));
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

        this.addComments(type, key, CommentsNode.ANY);

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
            T entryValue = entry.getValue();

            if (entryKey != null)
            {
                this.addComments(entryKey.getClass(), key);
            }
            if (entryValue != null)
            {
                this.addComments(entryValue.getClass(), key, CommentsNode.ANY);
            }

            if (Serialization.isSimple(entryKey))
            {
                if (Serialization.isSimple(entryValue))
                {
                    resultMap.put(entryKey, this.toString(entryValue, type));
                    continue;
                }
                resultMap.put(entryKey, this.serialize(type, entryValue));
                continue;
            }
            resultMap.put(keyMapper.apply(entryKey), this.serialize(type, entryValue));
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
