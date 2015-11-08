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

package org.diorite.chat.component.serialize;

import java.lang.reflect.Type;
import java.util.HashSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.diorite.chat.ChatColor;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.chat.component.TranslatableComponent;

/**
 * Basic class for chat component (de)/serializer.
 *
 * @see com.google.gson.JsonDeserializer
 */
public class ComponentSerializer implements JsonDeserializer<BaseComponent>
{
    static final         ThreadLocal<HashSet<BaseComponent>> serializedComponents = new ThreadLocal<>();
    private static final Gson                                gson                 = new GsonBuilder().registerTypeAdapter(BaseComponent.class, new ComponentSerializer()).registerTypeAdapter(TextComponent.class, new TextComponentSerializer()).registerTypeAdapter(TranslatableComponent.class, new TranslatableComponentSerializer()).create();

    @Override
    public BaseComponent deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive())
        {
            return new TextComponent(json.getAsString());
        }
        final JsonObject object = json.getAsJsonObject();
        if (object.has("translate"))
        {
            return (BaseComponent) context.deserialize(json, TranslatableComponent.class);
        }
        return (BaseComponent) context.deserialize(json, TextComponent.class);
    }

    /**
     * Parse given json to one or more {@link BaseComponent} (if json is a list, then it may be more than one element)
     *
     * @param json json to parse.
     *
     * @return parsed base components.
     */
    public static BaseComponent[] parse(final String json)
    {
        if (json.startsWith("["))
        {
            return gson.fromJson(json, BaseComponent[].class);
        }
        return new BaseComponent[]{gson.fromJson(json, BaseComponent.class)};
    }

    /**
     * Parse given json to {@link BaseComponent}.(if json is a list, then it may be more than one element, and then only first element will be used)
     *
     * @param json json to parse.
     *
     * @return parsed base component.
     */
    public static BaseComponent parseOne(final String json)
    {
        if (json.startsWith("["))
        {
            final BaseComponent[] parts = gson.fromJson(json, BaseComponent[].class);
            if (parts.length > 0)
            {
                return parts[0];
            }
            return new TextComponent();
        }
        return gson.fromJson(json, BaseComponent.class);
    }

    /**
     * Parse given array of jsons to array {@link BaseComponent}.(if any json is a list, then it may be more than one element, and then only first element will be used)
     *
     * @param jsons json arrays to parse.
     *
     * @return parsed base components.
     */
    public static BaseComponent[] parseOne(final String[] jsons)
    {
        final BaseComponent[] result = new BaseComponent[jsons.length];
        for (int i = 0, jsonsLength = jsons.length; i < jsonsLength; i++)
        {
            final String json = jsons[i];
            if (json.startsWith("["))
            {
                final BaseComponent[] parts = gson.fromJson(json, BaseComponent[].class);
                if (parts.length > 0)
                {
                    result[i] = parts[0];
                    continue;
                }
                result[i] = new TextComponent();
                continue;
            }
            result[i] = gson.fromJson(json, BaseComponent.class);
        }
        return result;
    }

    /**
     * Parse given json to {@link BaseComponent}.(if json is a list, then it may be more than one element, and then only first element will be used) <br>
     * If json isn't valid json, {@link TextComponent} with given string will be returned. {@link TextComponent#fromLegacyText(String)}
     *
     * @param json      json to parse.
     * @param colorChar {@link org.diorite.chat.ChatColor#translateAlternateColorCodes(char, String)}, use null to skip.
     *
     * @return parsed base component.
     */
    public static BaseComponent safeParseOne(final String json, final Character colorChar)
    {
        try
        {
            if (json.startsWith("["))
            {
                final BaseComponent[] parts = gson.fromJson(json, BaseComponent[].class);
                if (parts.length > 0)
                {
                    return parts[0];
                }
                return new TextComponent();
            }
            return gson.fromJson(json, BaseComponent.class);
        } catch (final Exception e)
        {
            if (colorChar == null)
            {
                return TextComponent.fromLegacyText(json);
            }
            return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodesInString(colorChar, json));
        }
    }

    /**
     * Parse given array of jsons to array {@link BaseComponent}.(if any json is a list, then it may be more than one element, and then only first element will be used)
     * If json isn't valid json, {@link TextComponent} with given string will be returned. {@link TextComponent#fromLegacyText(String)}
     *
     * @param jsons     json arrays to parse.
     * @param colorChar {@link org.diorite.chat.ChatColor#translateAlternateColorCodes(char, String)}, use null to skip.
     *
     * @return parsed base component.
     */
    public static BaseComponent[] safeParseOne(final String[] jsons, final Character colorChar)
    {
        try
        {
            final BaseComponent[] result = new BaseComponent[jsons.length];
            for (int i = 0, jsonsLength = jsons.length; i < jsonsLength; i++)
            {
                final String json = jsons[i];
                if (json.startsWith("["))
                {
                    final BaseComponent[] parts = gson.fromJson(json, BaseComponent[].class);
                    if (parts.length > 0)
                    {
                        result[i] = parts[0];
                        continue;
                    }
                    result[i] = new TextComponent();
                    continue;
                }
                result[i] = gson.fromJson(json, BaseComponent.class);
            }
            return result;
        } catch (final Exception e)
        {
            final BaseComponent[] result = new BaseComponent[jsons.length];
            if (colorChar == null)
            {
                for (int i = 0; i < result.length; i++)
                {
                    result[i] = TextComponent.fromLegacyText(jsons[i]);
                }
                return result;
            }
            for (int i = 0; i < result.length; i++)
            {
                result[i] = TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodesInString(colorChar, jsons[i]));
            }
            return result;
        }
    }

    /**
     * Returns json representation of given {@link BaseComponent}
     *
     * @param component component to deserialize.
     *
     * @return json representation of given {@link BaseComponent}
     */
    public static String toString(final BaseComponent component)
    {
        return gson.toJson(component);
    }

    /**
     * Returns json representation of given array of {@link BaseComponent}
     *
     * @param components components to deserialize.
     *
     * @return json representation of given array of {@link BaseComponent}
     */
    public static String toString(final BaseComponent... components)
    {
        return gson.toJson(new TextComponent(components));
    }
}
