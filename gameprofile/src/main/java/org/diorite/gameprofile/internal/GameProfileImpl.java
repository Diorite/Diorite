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

package org.diorite.gameprofile.internal;

import javax.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.common.base.Strings;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.diorite.gameprofile.GameProfile;
import org.diorite.gameprofile.PropertyMap;

public class GameProfileImpl implements GameProfile
{
    @Nullable private UUID   id;
    @Nullable private String name;
    private PropertyMap properties = new PropertyMap();
    private boolean legacy;

    public GameProfileImpl(@Nullable UUID id, @Nullable String name)
    {
        if ((id == null) && (Strings.isNullOrEmpty(name)))
        {
            throw new IllegalArgumentException("Name and ID cannot both be blank");
        }
        this.id = id;
        this.name = name;
    }

    @Override
    @Nullable
    public UUID getId()
    {
        return this.id;
    }

    @Override
    public void setId(UUID id)
    {
        this.id = id;
    }

    @Override
    @Nullable
    public String getName()
    {
        return this.name;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public PropertyMap getProperties()
    {
        return this.properties;
    }

    @Override
    public void setProperties(PropertyMap properties)
    {
        this.properties = properties;
    }

    public boolean isLegacy()
    {
        return this.legacy;
    }

    public void setLegacy(boolean legacy)
    {
        this.legacy = legacy;
    }

    @Override
    public boolean isComplete()
    {
        return (this.id != null) && (! Strings.isNullOrEmpty(this.name));
    }

    public int hashCode()
    {
        int result = (this.id != null) ? this.id.hashCode() : 0;
        result = (31 * result) + ((this.name != null) ? this.name.hashCode() : 0);
        return result;
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass()))
        {
            return false;
        }
        GameProfileImpl that = (GameProfileImpl) o;
        return ! ((this.id != null) ? ! this.id.equals(that.id) : (that.id != null)) &&
               ! ((this.name != null) ? ! this.name.equals(that.name) : (that.name != null));
    }

    public static class Serializer implements JsonSerializer<GameProfileImpl>, JsonDeserializer<GameProfileImpl>
    {
        @Override
        public GameProfileImpl deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject object = (JsonObject) json;
            UUID id = object.has("id") ? (UUID) context.deserialize(object.get("id"), UUID.class) : null;
            String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
            return new GameProfileImpl(id, name);
        }

        @Override
        public JsonElement serialize(GameProfileImpl src, Type typeOfSrc, JsonSerializationContext context)
        {
            JsonObject result = new JsonObject();
            if (src.getId() != null)
            {
                result.add("id", context.serialize(src.getId()));
            }
            if (src.getName() != null)
            {
                result.addProperty("name", src.getName());
            }
            return result;
        }
    }
}
