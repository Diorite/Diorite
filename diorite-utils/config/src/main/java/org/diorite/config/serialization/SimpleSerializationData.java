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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.Validate;

import org.diorite.commons.enums.DynamicEnum;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.serialization.comments.CommentsNode;
import org.diorite.config.serialization.comments.DocumentComments;
import org.diorite.config.serialization.snakeyaml.YamlCollectionCreator;

class SimpleSerializationData implements SerializationData
{
    private final SerializationType serializationType;

    protected final Class<?>         type;
    protected final Serialization    serialization;
    private         DocumentComments comments;

    private final Map<Class<?>, DocumentComments> scannedCommentsClasses = new HashMap<>(20);
    private final Set<String>                     joinedKeys             = new HashSet<>(20);

    private final   List<Object>        dataList = new ArrayList<>(20);
    protected final Map<Object, Object> dataMap  = new LinkedHashMap<>(20);

    private String trueValue  = "true";
    private String falseValue = "false";

    protected SimpleSerializationData(SerializationType serializationType, Serialization serialization, Class<?> type)
    {
        this.serializationType = serializationType;
        this.serialization = serialization;
        this.type = type;
        this.comments = Serialization.getInstance().getCommentsManager().getComments(type);
    }

    @Override
    public SerializationType getSerializationType()
    {
        return this.serializationType;
    }

    @Override
    public DocumentComments getComments()
    {
        return this.comments;
    }

    @Override
    public void setComments(DocumentComments comments)
    {
        this.comments = comments;
    }

    @Nullable
    protected <T> Object serialize(T object, @Nullable DocumentComments comments)
    {
        return this.serialization.serialize(object, this.serializationType, comments);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    protected <T> Object serialize(Class<T> type, @Nullable T object, @Nullable DocumentComments comments)
    {
        if (object == null)
        {
            return null;
        }
        if (type == Object.class)
        {
            type = (Class<T>) object.getClass();
        }
        if (this.serialization.isStringSerializable(type))
        {
            return this.serialization.serializeToString(type, object);
        }
        return this.serialization.serialize(type, object, this.serializationType, comments);
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

    protected void validateMap(String key)
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
    protected <T> String toString(@Nullable T object, @Nullable Class<T> type)
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
        if (object instanceof DynamicEnum)
        {
            return ((DynamicEnum<?>) object).prettyName();
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

    protected DocumentComments addComments(Class<?> type, String... key)
    {
        DocumentComments cached = this.scannedCommentsClasses.get(type);
        if (cached != null)
        {
            return cached;
        }
        for (int i = 0; i < key.length; i++)
        {
            if (key[i].isEmpty())
            {
                key[i] = CommentsNode.ANY;
            }
        }
        DocumentComments comments = Serialization.getInstance().getCommentsManager().getComments(type);
        this.comments.join(key, comments);
        this.scannedCommentsClasses.put(type, comments);
        return comments;
    }

    protected void joinComments(String key, @Nullable DocumentComments comments)
    {
        if ((comments == null) || ! this.joinedKeys.add(key))
        {
            return;
        }
        if (key.isEmpty())
        {
            this.comments.join(CommentsNode.ANY, comments);
        }
        else
        {
            this.comments.join(key, comments);
        }
    }

    private DocumentComments addComments(Class<?> type, String key)
    {
        DocumentComments cached = this.scannedCommentsClasses.get(type);
        if (cached != null)
        {
            return cached;
        }
        DocumentComments comments = Serialization.getInstance().getCommentsManager().getComments(type);
        if (key.isEmpty())
        {
            this.comments.join(CommentsNode.ANY, comments);
        }
        else
        {
            this.comments.join(key, comments);
        }
        this.scannedCommentsClasses.put(type, comments);
        return comments;
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
        DocumentComments comments = this.addComments(valueClass, key);

        if (! this.serialization.isSerializable(value))
        {
            if (this.serialization.isStringSerializable(value))
            {
                this.dataMap.put(key, this.serialization.serializeToString(value));
                return;
            }
            if (this.serialization.canBeSerialized(valueClass))
            {
                this.dataMap.put(key, this.serialize(value, comments));
                this.joinComments(key, comments);
                return;
            }
            throw new SerializationException(this.type, "Given value must be serializable! key: " + key + ", value: " + value);
        }
        this.dataMap.put(key, this.serialize(value, comments));
        this.joinComments(key, comments);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
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
            if (value.getClass().isArray())
            {
                this.dataMap.put(key, value);
            }
            else
            {
                if (Object.class.equals(type))
                {
                    this.dataMap.put(key, value);
                }
                else
                {
                    this.dataMap.put(key, this.toString(value, type));
                }
            }
            return;
        }
        if (value.getClass().isArray())
        {
            List<Object> objects = Arrays.asList((Object[]) value);
            this.addCollection(key, objects, (Class) value.getClass().getComponentType());
            return;
        }
        DocumentComments comments = this.addComments(value.getClass(), key);

        if (! this.serialization.isSerializable(type))
        {
            if (this.serialization.isStringSerializable(type))
            {
                this.dataMap.put(key, this.serialization.serializeToString(type, value));
                return;
            }
            if (this.serialization.canBeSerialized(value.getClass()))
            {
                this.dataMap.put(key, this.serialize(type, value, comments));
                this.joinComments(key, comments);
                return;
            }
            throw new SerializationException(this.type, "Given value must be serializable! key: " + key + ", value: (" + type.getName() + ") -> " + value);
        }
        this.dataMap.put(key, this.serialize(type, value, comments));
        this.joinComments(key, comments);
    }

    @Override
    public <T> void addMappedList(String key, Class<T> type, @Nullable Collection<? extends T> value, Function<T, String> mapper)
    {
        this.validateMap(key);

        this.addComments(type, key, CommentsNode.ANY);

        if (value == null)
        {
            this.add(key, null);
            return;
        }

        Map<String, Object> valueMap = new LinkedHashMap<>(value.size());
        for (T t : value)
        {
            if (t == null)
            {
                valueMap.put(key, null);
                continue;
            }

            DocumentComments comments = this.addComments(t.getClass(), key, CommentsNode.ANY);

            if (Serialization.isSimple(t))
            {
                if (Object.class.equals(type))
                {
                    valueMap.put(key, t);
                }
                else
                {
                    valueMap.put(key, this.toString(t, type));
                }
                continue;
            }
            valueMap.put(mapper.apply(t), this.serialize(type, t, comments));
            this.joinComments(key, comments);
        }
        this.dataMap.put(key, valueMap);
    }

    @Override
    public <T> void addCollection(String key, @Nullable Collection<? extends T> value, Class<T> type)
    {
        this.validateList(key);

        this.addComments(type, key);

        List<Object> result;
        if (key.isEmpty())
        {
            Validate.notNull(value);
            result = this.dataList;
        }
        else
        {
            if (value == null)
            {
                this.add(key, null);
                return;
            }
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
                if (Object.class.equals(type))
                {
                    result.add(t);
                }
                else
                {
                    result.add(this.toString(t, type));
                }
                continue;
            }

            DocumentComments comments = this.addComments(t.getClass(), key);

            result.add(this.serialize(type, t, comments));
            this.joinComments(key, comments);
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, result);
        }
    }

    @Override
    public <T> void addMapAsList(String key, @Nullable Map<?, ? extends T> value, Class<T> type)
    {
        this.validateList(key);

        this.addComments(type, key);

        List<Object> result;
        if (key.isEmpty())
        {
            Validate.notNull(value);
            result = this.dataList;
        }
        else
        {
            if (value == null)
            {
                this.add(key, null);
                return;
            }
            result = new ArrayList<>(value.size());
        }
        for (Entry<?, ? extends T> entry : value.entrySet())
        {
            T entryValue = entry.getValue();

            DocumentComments comments = null;
            if (entryValue != null)
            {
                comments = this.addComments(entryValue.getClass(), key);
            }

            if (Serialization.isSimple(entryValue))
            {
                if (Object.class.equals(type))
                {
                    result.add(entryValue);
                }
                else
                {
                    result.add(this.toString(entryValue, type));
                }
                continue;
            }
            result.add(this.serialize(type, entryValue, comments));
            this.joinComments(key, comments);
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, result);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> void addMapAsListWithKeys(String key, @Nullable Map<?, ? extends T> value, Class<T> type, String keyPropertyName)
    {
        this.validateList(key);

        this.addComments(type, key);

        List<Object> result;
        if (key.isEmpty())
        {
            Validate.notNull(value);
            result = this.dataList;
        }
        else
        {
            if (value == null)
            {
                this.add(key, null);
                return;
            }
            result = new ArrayList<>(value.size());
        }
        for (Entry<?, ? extends T> entry : value.entrySet())
        {
            T entryValue = entry.getValue();

            DocumentComments comments = null;
            if (entryValue != null)
            {
                comments = this.addComments(entryValue.getClass(), key);
            }

            Object o = this.serialize(type, entryValue, comments);
            this.joinComments(key, comments);
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
                try
                {
                    Map collection = YamlCollectionCreator.createCollection(o.getClass(), ((Map) o).size());
                    collection.put(keyPropertyName, keyStr);
                    collection.putAll(((Map) o));
                    o = collection;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    ((Map) o).put(keyPropertyName, keyStr);
                }
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
    public <K, T> void addMap(String key, @Nullable Map<? extends K, ? extends T> value, Class<K> keyType, Class<T> type)
    {
        this.validateMap(key);

        this.addComments(type, key, CommentsNode.ANY);
        this.addComments(keyType, key);

        Map<Object, Object> resultMap;
        if (key.isEmpty())
        {
            Validate.notNull(value);
            resultMap = this.dataMap;
        }
        else
        {
            if (value == null)
            {
                this.add(key, null);
                return;
            }
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
            DocumentComments comments = null;
            if (entryValue != null)
            {
                comments = this.addComments(entryValue.getClass(), key, CommentsNode.ANY);
            }

            if (Serialization.isSimple(entryKey))
            {
                if (Serialization.isSimple(entryValue))
                {
                    if (Object.class.equals(type))
                    {
                        resultMap.put(entryKey, entryValue);
                    }
                    else
                    {
                        resultMap.put(entryKey, this.toString(entryValue, type));
                    }
                    continue;
                }
                resultMap.put(entryKey, this.serialize(type, entryValue, comments));
                this.joinComments(key, comments);
                continue;
            }
            resultMap.put(this.serialization.serializeToString(keyType, entryKey), this.serialize(type, entryValue, comments));
            this.joinComments(key, comments);
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, resultMap);
        }
    }

    @Override
    public <K, T> void addMap(String key, @Nullable Map<? extends K, ? extends T> value, Class<T> type)
    {
        this.validateMap(key);

        this.addComments(type, key, CommentsNode.ANY);

        Map<Object, Object> resultMap;
        if (key.isEmpty())
        {
            Validate.notNull(value);
            resultMap = this.dataMap;
        }
        else
        {
            if (value == null)
            {
                this.add(key, null);
                return;
            }
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

            DocumentComments comments = null;
            if (entryValue != null)
            {
                comments = this.addComments(entryValue.getClass(), key, CommentsNode.ANY);
            }

            if (Serialization.isSimple(entryKey))
            {
                if (Serialization.isSimple(entryValue))
                {
                    if (Object.class.equals(type))
                    {
                        resultMap.put(entryKey, entryValue);
                    }
                    else
                    {
                        resultMap.put(entryKey, this.toString(entryValue, type));
                    }
                    continue;
                }
                resultMap.put(entryKey, this.serialize(type, entryValue, comments));
                this.joinComments(key, comments);
                continue;
            }
            resultMap.put(this.serialization.serializeToString(entryKey), this.serialize(type, entryValue, comments));
            this.joinComments(key, comments);
        }
        if (! key.isEmpty())
        {
            this.dataMap.put(key, resultMap);
        }
    }

    @Override
    public <K, T> void addMap(String key, @Nullable Map<? extends K, ? extends T> value, Class<T> type, Function<K, String> keyMapper)
    {
        this.validateMap(key);

        this.addComments(type, key, CommentsNode.ANY);

        Map<Object, Object> resultMap;
        if (key.isEmpty())
        {
            Validate.notNull(value);
            resultMap = this.dataMap;
        }
        else
        {
            if (value == null)
            {
                this.add(key, null);
                return;
            }
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
            DocumentComments comments = null;
            if (entryValue != null)
            {
                comments = this.addComments(entryValue.getClass(), key, CommentsNode.ANY);
            }

            if (Serialization.isSimple(entryKey))
            {
                if (Serialization.isSimple(entryValue))
                {
                    if (Object.class.equals(type))
                    {
                        resultMap.put(entryKey, entryValue);
                    }
                    else
                    {
                        resultMap.put(entryKey, this.toString(entryValue, type));
                    }
                    continue;
                }
                resultMap.put(entryKey, this.serialize(type, entryValue, comments));
                this.joinComments(key, comments);
                continue;
            }
            resultMap.put(keyMapper.apply(entryKey), this.serialize(type, entryValue, comments));
            this.joinComments(key, comments);
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

    static SimpleSerializationData createSerializationData(SerializationType serializationType, Serialization serialization, Class<?> type)
    {
        if (serializationType == SerializationType.YAML)
        {
            return new YamlSerializationData(serialization, type);
        }
        return new SimpleSerializationData(serializationType, serialization, type);
    }
}
