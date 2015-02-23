package diorite.impl.connection.ping;


import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public final class SmallGsonUtils // TODO: pozbyć się tego, kompletnie bezsensowne
{
    private SmallGsonUtils()
    {
    }

    public static boolean isArray(final JsonObject jsonObject, final String key)
    {
        return hasKey(jsonObject, key) && jsonObject.get(key).isJsonArray();
    }

    public static boolean hasKey(final JsonObject jsonObject, final String key)
    {
        return (jsonObject != null) && (jsonObject.get(key) != null);
    }

    public static String getAsString(final JsonElement jsonElement, final String str)
    {
        if (jsonElement.isJsonPrimitive())
        {
            return jsonElement.getAsString();
        }
        throw new JsonSyntaxException("Expected " + str + " to be a string, was " + getTypeName(jsonElement));
    }

    public static String getAsString(final JsonObject jsonObject, final String key)
    {
        if (jsonObject.has(key))
        {
            return getAsString(jsonObject.get(key), key);
        }
        throw new JsonSyntaxException("Missing " + key + ", expected to find a string");
    }

    public static boolean getAsBool(final JsonElement jsonElement, final String str)
    {
        if (jsonElement.isJsonPrimitive())
        {
            return jsonElement.getAsBoolean();
        }
        throw new JsonSyntaxException("Expected " + str + " to be a Boolean, was " + getTypeName(jsonElement));
    }

    public static boolean getAsBool(final JsonObject jsonObject, final String key, final boolean def)
    {
        if (jsonObject.has(key))
        {
            return getAsBool(jsonObject.get(key), key);
        }
        return def;
    }

    public static float getAsFloat(final JsonElement jsonElement, final String str)
    {
        if ((jsonElement.isJsonPrimitive()) && (jsonElement.getAsJsonPrimitive().isNumber()))
        {
            return jsonElement.getAsFloat();
        }
        throw new JsonSyntaxException("Expected " + str + " to be a Float, was " + getTypeName(jsonElement));
    }

    public static float getAsFloat(final JsonObject jsonObject, final String key, final float def)
    {
        if (jsonObject.has(key))
        {
            return getAsFloat(jsonObject.get(key), key);
        }
        return def;
    }

    public static int getInt(final JsonElement jsonElement, final String str)
    {
        if ((jsonElement.isJsonPrimitive()) && (jsonElement.getAsJsonPrimitive().isNumber()))
        {
            return jsonElement.getAsInt();
        }
        throw new JsonSyntaxException("Expected " + str + " to be a Int, was " + getTypeName(jsonElement));
    }

    public static int getAsInt(final JsonObject jsonObject, final String key)
    {
        if (jsonObject.has(key))
        {
            return getInt(jsonObject.get(key), key);
        }
        throw new JsonSyntaxException("Missing " + key + ", expected to find a Int");
    }

    public static int getInt(final JsonObject jsonObject, final String key, final int def)
    {
        if (jsonObject.has(key))
        {
            return getInt(jsonObject.get(key), key);
        }
        return def;
    }

    public static JsonObject getAsObject(final JsonElement jsonElement, final String str)
    {
        if (jsonElement.isJsonObject())
        {
            return jsonElement.getAsJsonObject();
        }
        throw new JsonSyntaxException("Expected " + str + " to be a JsonObject, was " + getTypeName(jsonElement));
    }

    public static JsonArray getAsArray(final JsonElement jsonElement, final String str)
    {
        if (jsonElement.isJsonArray())
        {
            return jsonElement.getAsJsonArray();
        }
        throw new JsonSyntaxException("Expected " + str + " to be a JsonArray, was " + getTypeName(jsonElement));
    }

    public static JsonArray getAsArray(final JsonObject jsonObject, final String key)
    {
        if (jsonObject.has(key))
        {
            return getAsArray(jsonObject.get(key), key);
        }
        throw new JsonSyntaxException("Missing " + key + ", expected to find a JsonArray");
    }

    public static String getTypeName(final JsonElement jsonElement)
    {
        final String str = StringUtils.abbreviateMiddle(String.valueOf(jsonElement), "...", 10);
        if (jsonElement == null)
        {
            return "null (missing)";
        }
        if (jsonElement.isJsonNull())
        {
            return "null (json)";
        }
        if (jsonElement.isJsonArray())
        {
            return "an array (" + str + ")";
        }
        if (jsonElement.isJsonObject())
        {
            return "an object (" + str + ")";
        }
        if (jsonElement.isJsonPrimitive())
        {
            final JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber())
            {
                return "a number (" + str + ")";
            }
            if (jsonPrimitive.isBoolean())
            {
                return "a boolean (" + str + ")";
            }
        }
        return str;
    }
}