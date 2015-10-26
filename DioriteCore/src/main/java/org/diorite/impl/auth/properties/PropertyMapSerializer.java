/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.auth.properties;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.diorite.auth.Property;
import org.diorite.auth.PropertyMap;

public class PropertyMapSerializer implements JsonSerializer<PropertyMap>, JsonDeserializer<PropertyMap>
{
    @SuppressWarnings("unchecked")
    @Override
    public PropertyMap deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
    {
        final PropertyMap result = new PropertyMap();

        if ((json instanceof JsonObject))
        {
            final JsonObject object = (JsonObject) json;
            object.entrySet().stream().filter(entry -> (entry.getValue() instanceof JsonArray)).forEach(entry -> {
                for (final JsonElement element : (Iterable<JsonArray>) entry.getValue())
                {
                    result.put(entry.getKey(), new PropertyImpl(entry.getKey(), element.getAsString()));
                }
            });
        }
        else if ((json instanceof JsonArray))
        {
            for (final JsonElement element : (Iterable<JsonArray>) json)
            {
                if ((element instanceof JsonObject))
                {
                    final JsonObject object = (JsonObject) element;
                    final String name = object.getAsJsonPrimitive("name").getAsString();
                    final String value = object.getAsJsonPrimitive("value").getAsString();
                    if (object.has("signature"))
                    {
                        result.put(name, new PropertyImpl(name, value, object.getAsJsonPrimitive("signature").getAsString()));
                    }
                    else
                    {
                        result.put(name, new PropertyImpl(name, value));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public JsonElement serialize(final PropertyMap src, final Type typeOfSrc, final JsonSerializationContext context)
    {
        final JsonArray result = new JsonArray();
        for (final Property property : src.values())
        {
            final JsonObject object = new JsonObject();

            object.addProperty("name", property.getName());
            object.addProperty("value", property.getValue());
            if (property.hasSignature())
            {
                object.addProperty("signature", property.getSignature());
            }
            result.add(object);
        }
        return result;
    }
}
