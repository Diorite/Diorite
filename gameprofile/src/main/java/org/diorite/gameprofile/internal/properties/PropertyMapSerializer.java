/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.gameprofile.internal.properties;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.diorite.gameprofile.Property;
import org.diorite.gameprofile.PropertyMap;

public class PropertyMapSerializer implements JsonSerializer<PropertyMap>, JsonDeserializer<PropertyMap>
{
    @SuppressWarnings("unchecked")
    @Override
    public PropertyMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        PropertyMap result = new PropertyMap();

        if ((json instanceof JsonObject))
        {
            JsonObject object = (JsonObject) json;
            object.entrySet().stream().filter(entry -> (entry.getValue() instanceof JsonArray)).forEach(entry -> {
                for (JsonElement element : (Iterable<JsonArray>) entry.getValue())
                {
                    result.put(entry.getKey(), new PropertyImpl(entry.getKey(), element.getAsString()));
                }
            });
        }
        else if ((json instanceof JsonArray))
        {
            for (JsonElement element : (Iterable<JsonArray>) json)
            {
                if ((element instanceof JsonObject))
                {
                    JsonObject object = (JsonObject) element;
                    String name = object.getAsJsonPrimitive("name").getAsString();
                    String value = object.getAsJsonPrimitive("value").getAsString();
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
    public JsonElement serialize(PropertyMap src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonArray result = new JsonArray();
        for (Property property : src.values())
        {
            JsonObject object = new JsonObject();

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
