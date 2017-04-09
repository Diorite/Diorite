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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.commons.enums.DynamicEnum;
import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.commons.reflections.DioriteReflectionUtils;

public class JsonDeserializationData extends AbstractDeserializationData
{
    private final JsonElement                element;
    private final JsonDeserializationContext context;

    JsonDeserializationData(Serialization serialization, JsonElement element, JsonDeserializationContext context, Class<?> type)
    {
        super(type, serialization);
        this.element = element;
        this.context = context;
    }

    @Override
    public SerializationType getSerializationType()
    {
        return SerializationType.JSON;
    }

    @Override
    public Set<String> getKeys()
    {
        if (this.element.isJsonObject())
        {
            Set<Entry<String, JsonElement>> entries = this.element.getAsJsonObject().entrySet();
            Set<String> result = new LinkedHashSet<>(entries.size());
            for (Entry<String, JsonElement> entry : entries)
            {
                result.add(entry.getKey());
            }
            return result;
        }
        return Collections.emptySet();
    }

    @Override
    public boolean containsKey(String key)
    {
        if (this.element.isJsonObject())
        {
            return this.element.getAsJsonObject().has(key);
        }
        if (this.element.isJsonArray())
        {
            int i = DioriteMathUtils.asInt(key, - 1);
            if (i == - 1)
            {
                return false;
            }
            return i < this.element.getAsJsonArray().size();
        }
        return false;
    }

    @Nullable
    private JsonElement getElement(JsonElement element, String key)
    {
        if (key.isEmpty())
        {
            return element;
        }
        if (element.isJsonArray())
        {
            JsonArray jsonArray = element.getAsJsonArray();
            int i = DioriteMathUtils.asInt(key, - 1);
            if ((i == - 1) || (i < jsonArray.size()))
            {
                return null;
            }
            return jsonArray.get(i);
        }
        if (element.isJsonObject())
        {
            return element.getAsJsonObject().get(key);
        }
        return null;
    }

    @Nullable
    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> T deserializeSpecial(Class<T> type, JsonElement element, @Nullable T def)
    {
        if (DioriteReflectionUtils.getWrapperClass(type).equals(Boolean.class))
        {
            T t = (T) this.toBool(this.context.deserialize(element, String.class));
            if (t == null)
            {
                return def;
            }
            return t;
        }
        if (Enum.class.isAssignableFrom(type))
        {
            Enum valueSafe = DioriteReflectionUtils.getEnumValueSafe(this.context.<String>deserialize(element, String.class), - 1, (Class) type);
            if (valueSafe == null)
            {
                return def;
            }
            return (T) valueSafe;
        }
        if (DynamicEnum.class.isAssignableFrom(type))
        {
            String name = this.context.deserialize(element, String.class);
            DynamicEnum[] values = DynamicEnum.values((Class<DynamicEnum>) type);
            for (DynamicEnum value : values)
            {
                if (value.prettyName().equalsIgnoreCase(name) || value.name().equalsIgnoreCase(name) || String.valueOf(value.ordinal()).equalsIgnoreCase(name))
                {
                    return (T) value;
                }
            }
            return def;
        }
        return this.context.deserialize(element, type);
    }

    @SuppressWarnings("unchecked")
    private <T> T deserializeSpecialOrThrow(Class<T> type, JsonElement element)
    {
        try
        {
            if (DioriteReflectionUtils.getWrapperClass(type).equals(Boolean.class))
            {
                T t = (T) this.toBool(this.context.deserialize(element, String.class));
                if (t == null)
                {
                    throw new DeserializationException(this.type, this, "Can't deserialize boolean value: (" + type.getName() + ") -> " + element);
                }
                return t;
            }
            return this.context.deserialize(element, type);
        }
        catch (DeserializationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new DeserializationException(this.type, this, "Can't deserialize boolean value: (" + type.getName() + ") -> " + element, e);
        }
    }

    @Nullable
    @Override
    public <T> T get(String key, Class<T> type, @Nullable T def)
    {
        JsonElement element = this.getElement(this.element, key);
        if (element == null)
        {
            return def;
        }
        return this.deserializeSpecial(type, element, def);
    }

    @Override
    public <T, C extends Collection<T>> void getAsCollection(String key, Class<T> type, C collection)
    {
        JsonElement element = this.getElement(this.element, key);
        if (element == null)
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
        if (element.isJsonArray())
        {
            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray)
            {
                collection.add(this.deserializeSpecial(type, jsonElement, null));
            }
        }
        else if (element.isJsonObject())
        {
            JsonObject jsonObject = element.getAsJsonObject();
            for (Entry<String, JsonElement> entry : jsonObject.entrySet())
            {
                collection.add(this.deserializeSpecial(type, entry.getValue(), null));
            }
        }
        else
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
    }

    @Override
    public <T, M extends Map<String, T>> void getAsMap(String key, Class<T> type, Function<T, String> keyMapper, M map)
    {
        JsonElement element = this.getElement(this.element, key);
        if (element == null)
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
        if (element.isJsonArray())
        {
            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray)
            {
                T deserialize = this.context.deserialize(jsonElement, type);
                map.put(keyMapper.apply(deserialize), deserialize);
            }
        }
        else if (element.isJsonObject())
        {
            JsonObject jsonObject = element.getAsJsonObject();
            for (Entry<String, JsonElement> entry : jsonObject.entrySet())
            {
                T deserialize = this.context.deserialize(entry.getValue(), type);
                map.put(keyMapper.apply(deserialize), deserialize);
            }
        }
        else
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
    }

    @Override
    public <K, T, M extends Map<K, T>> void getAsMapWithKeys(String key, Class<K> keyType, Class<T> type, String keyPropertyName, M map)
    {
        JsonElement element = this.getElement(this.element, key);
        if (element == null)
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
        if (element.isJsonArray())
        {
            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray)
            {
                if (! jsonElement.isJsonObject())
                {
                    throw new DeserializationException(type, this, "Expected json element in array list on '" + key + "' but found: " + jsonElement);
                }
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                JsonElement propKeyElement = jsonObject.get(keyPropertyName);
                if (propKeyElement == null)
                {
                    throw new DeserializationException(type, this, "Missing property '" + keyPropertyName + "' in " + jsonObject + ". Key: " + key);
                }
                jsonObject.remove(keyPropertyName);

                map.put(this.context.deserialize(propKeyElement, keyType), this.context.deserialize(jsonObject, type));
            }
        }
        else if (element.isJsonObject())
        {
            JsonObject jsonObject = element.getAsJsonObject();
            for (Entry<String, JsonElement> entry : jsonObject.entrySet())
            {
                JsonElement value = entry.getValue();
                if (! value.isJsonObject())
                {
                    throw new DeserializationException(type, this, "Expected json element as values of '" + key + "' but found: " + value);
                }
                JsonObject jsonObj = value.getAsJsonObject();

                K mapKey;
                if (jsonObj.has(keyPropertyName) && jsonObj.get(keyPropertyName).isJsonPrimitive())
                {
                    mapKey = this.context.deserialize(jsonObj.getAsJsonPrimitive(keyPropertyName), keyType);
                    jsonObj.remove(keyPropertyName);
                }
                else
                {
                    mapKey = this.context.deserialize(new JsonPrimitive(entry.getKey()), keyType);
                }

                map.put(mapKey, this.context.deserialize(value, type));
            }
        }
        else
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
    }

    @Override
    public <K, T, M extends Map<K, T>> void getMap(String key, Class<K> keyType, Class<T> type, M map)
    {
        JsonElement element = this.getElement(this.element, key);
        if ((element == null) || ! element.isJsonObject())
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }

        JsonObject jsonObject = element.getAsJsonObject();
        for (Entry<String, JsonElement> entry : jsonObject.entrySet())
        {
            K keyObj;
            if (Serialization.isSimpleType(keyType))
            {
                keyObj = this.context.deserialize(new JsonPrimitive(entry.getKey()), keyType);
            }
            else if (this.serialization.isStringSerializable(keyType))
            {
                keyObj = this.serialization.deserializeFromString(keyType, entry.getKey());
            }
            else
            {
                throw new DeserializationException(type, this, "Can't deserialize given string to key: (" + type.getName() + ") -> " + entry.getKey());
            }

            map.put(keyObj, this.context.deserialize(entry.getValue(), type));
        }
    }

    @Override
    public <K, T, M extends Map<K, T>> void getMap(String key, Function<String, K> keyMapper, Class<T> type, M map)
    {
        JsonElement element = this.getElement(this.element, key);
        if ((element == null) || ! element.isJsonObject())
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }

        JsonObject jsonObject = element.getAsJsonObject();
        for (Entry<String, JsonElement> entry : jsonObject.entrySet())
        {
            K keyObj = keyMapper.apply(entry.getKey());
            map.put(keyObj, this.context.deserialize(entry.getValue(), type));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("element", this.element).toString();
    }
}
