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

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

class JsonSerializerImpl<T> implements JsonSerializer<T>, JsonDeserializer<T>
{
    private final Serializer<T> serializer;
    private final Serialization serialization;

    JsonSerializerImpl(Serializer<T> serializer, Serialization serialization)
    {
        this.serializer = serializer;
        this.serialization = serialization;
    }

    @Override
    public T deserialize(JsonElement jsonData, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        DeserializationData data = new JsonDeserializationData(this.serialization, jsonData, jsonDeserializationContext, this.serializer.getType());
        try
        {
            if (type instanceof Class)
            {
                return this.serializer.deserialize(data, (Class<?>) type);
            }
            else
            {
                return this.serializer.deserialize(data);
            }
        }
        catch (DeserializationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new DeserializationException(this.serializer.getType(), data, e.getMessage(), e);
        }
    }

    @Override
    public JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext)
    {
        try
        {
            SimpleSerializationData data =
                    (SimpleSerializationData) SerializationData.create(SerializationType.JSON, this.serialization, this.serializer.getType());
            this.serializer.serialize(t, data);
            return jsonSerializationContext.serialize(data.rawValue());
        }
        catch (SerializationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new SerializationException(this.serializer.getType(), e.getMessage(), e);
        }
    }
}
