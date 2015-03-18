package com.mojang.authlib.properties;

import java.lang.reflect.Type;

import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PropertyMap extends ForwardingMultimap<String, Property>
{
    private final Multimap<String, Property> properties;

    public PropertyMap()
    {
        this.properties = LinkedHashMultimap.create();
    }

    @Override
    protected Multimap<String, Property> delegate()
    {
        return this.properties;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("properties", this.properties).toString();
    }

    public static class Serializer implements JsonSerializer<PropertyMap>, JsonDeserializer<PropertyMap>
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
                        result.put(entry.getKey(), new Property(entry.getKey(), element.getAsString()));
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
                            result.put(name, new Property(name, value, object.getAsJsonPrimitive("signature").getAsString()));
                        }
                        else
                        {
                            result.put(name, new Property(name, value));
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
}
