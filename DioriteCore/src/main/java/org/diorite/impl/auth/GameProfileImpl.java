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

package org.diorite.impl.auth;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.auth.GameProfile;
import org.diorite.auth.PropertyMap;
import org.diorite.nbt.NbtSerialization;
import org.diorite.nbt.NbtTagCompound;

public class GameProfileImpl implements GameProfile
{
    private UUID   id;
    private String name;
    private PropertyMap properties = new PropertyMap();
    private boolean legacy;

    public GameProfileImpl(final UUID id, final String name)
    {
        if ((id == null) && (StringUtils.isBlank(name)))
        {
            throw new IllegalArgumentException("Name and ID cannot both be blank");
        }
        this.id = id;
        this.name = name;
    }

    public GameProfileImpl(final NbtTagCompound tag)
    {
        this.id = tag.containsTag("Id") ? UUID.fromString(tag.getString("Id")) : null;
        this.name = tag.getString("Name");
        this.properties = NbtSerialization.deserialize(PropertyMap.class, tag.getCompound("Properties"));
    }

    @Override
    public UUID getId()
    {
        return this.id;
    }

    @Override
    public void setId(final UUID id)
    {
        this.id = id;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void setName(final String name)
    {
        this.name = name;
    }

    @Override
    public PropertyMap getProperties()
    {
        return this.properties;
    }

    @Override
    public void setProperties(final PropertyMap properties)
    {
        this.properties = properties;
    }

    public boolean isLegacy()
    {
        return this.legacy;
    }

    public void setLegacy(final boolean legacy)
    {
        this.legacy = legacy;
    }

    @Override
    public boolean isComplete()
    {
        return (this.id != null) && (StringUtils.isNotBlank(this.name));
    }

    public int hashCode()
    {
        int result = (this.id != null) ? this.id.hashCode() : 0;
        result = (31 * result) + ((this.name != null) ? this.name.hashCode() : 0);
        return result;
    }

    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass()))
        {
            return false;
        }
        final GameProfileImpl that = (GameProfileImpl) o;
        return ! ((this.id != null) ? ! this.id.equals(that.id) : (that.id != null)) && ! ((this.name != null) ? ! this.name.equals(that.name) : (that.name != null));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("name", this.name).append("properties", this.properties).append("legacy", this.legacy).toString();
    }

    @Override
    public NbtTagCompound serializeToNBT()
    {
        final NbtTagCompound tag = new NbtTagCompound();
        tag.setString("Id", this.id.toString());
        tag.setString("Name", this.name);
        tag.put("Properties", this.properties.serializeToNBT());
        return tag;
    }

    public static class Serializer implements JsonSerializer<GameProfileImpl>, JsonDeserializer<GameProfileImpl>
    {
        @Override
        public GameProfileImpl deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
        {
            final JsonObject object = (JsonObject) json;
            final UUID id = object.has("id") ? (UUID) context.deserialize(object.get("id"), UUID.class) : null;
            final String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
            return new GameProfileImpl(id, name);
        }

        @Override
        public JsonElement serialize(final GameProfileImpl src, final Type typeOfSrc, final JsonSerializationContext context)
        {
            final JsonObject result = new JsonObject();
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
