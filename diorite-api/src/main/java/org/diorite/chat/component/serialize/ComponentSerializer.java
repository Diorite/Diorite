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
import com.google.gson.JsonSyntaxException;

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
        final char first = json.charAt(0);
        final char last = json.charAt(json.length() - 1);
        if ((first == '[') && (last == ']'))
        {
            return gson.fromJson(json, BaseComponent[].class);
        }
        if ((first == '{') && (last == '}'))
        {
            return new BaseComponent[]{gson.fromJson(json, BaseComponent.class)};
        }
        throw new JsonSyntaxException("Can't parse given string, it isn't valid json string: " + json);
    }

    private static BaseComponent parseArrayOne(final String json)
    {
        final BaseComponent[] parts = gson.fromJson(json, BaseComponent[].class);
        if (parts.length > 0)
        {
            return parts[0];
        }
        return new TextComponent();
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
        final char first = json.charAt(0);
        final char last = json.charAt(json.length() - 1);
        if ((first == '[') && (last == ']'))
        {
            return parseArrayOne(json);
        }
        if ((first == '{') && (last == '}'))
        {
            return gson.fromJson(json, BaseComponent.class);
        }
        throw new JsonSyntaxException("Can't parse given string, it isn't valid json string: " + json);
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
            final char first = json.charAt(0);
            final char last = json.charAt(json.length() - 1);
            if ((first == '[') && (last == ']'))
            {
                result[i] = parseArrayOne(json);
                continue;
            }
            if ((first == '{') && (last == '}'))
            {
                result[i] = gson.fromJson(json, BaseComponent.class);
            }
            throw new JsonSyntaxException("Can't parse given string, it isn't valid json string: " + json);
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
            final char first = json.charAt(0);
            final char last = json.charAt(json.length() - 1);
            if ((first == '[') && (last == ']'))
            {
                return parseArrayOne(json);
            }
            if ((first == '{') && (last == '}'))
            {
                return gson.fromJson(json, BaseComponent.class);
            }
        } catch (final Exception ignored)
        {
        }
        if (colorChar == null)
        {
            return TextComponent.fromLegacyText(json);
        }
        return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodesInString(colorChar, json));
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
        final BaseComponent[] result = new BaseComponent[jsons.length];
        for (int i = 0, jsonsLength = jsons.length; i < jsonsLength; i++)
        {
            result[i] = safeParse(jsons[i], colorChar);
        }
        return result;
    }

    /**
     * Parse given json to {@link BaseComponent}.(if json is a list all elements are joined using {@link TextComponent#join(BaseComponent...)}) <br>
     * If json isn't valid json, {@link TextComponent} with given string will be returned. {@link TextComponent#fromLegacyText(String)}
     *
     * @param json      json to parse.
     * @param colorChar {@link org.diorite.chat.ChatColor#translateAlternateColorCodes(char, String)}, use null to skip.
     *
     * @return parsed base component.
     */
    public static BaseComponent safeParse(final String json, final Character colorChar)
    {
        try
        {
            final char first = json.charAt(0);
            final char last = json.charAt(json.length() - 1);
            if ((first == '[') && (last == ']'))
            {
                return TextComponent.join(gson.fromJson(json, BaseComponent[].class));
            }
            if ((first == '{') && (last == '}'))
            {
                return gson.fromJson(json, BaseComponent.class);
            }
        } catch (final Exception ignored)
        {
        }
        if (colorChar == null)
        {
            return TextComponent.fromLegacyText(json);
        }
        return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodesInString(colorChar, json));
    }

    /**
     * Parse given array of jsons to array {@link BaseComponent}.(if any json is a list all elements are joined using {@link TextComponent#join(BaseComponent...)})
     * If json isn't valid json, {@link TextComponent} with given string will be returned. {@link TextComponent#fromLegacyText(String)}
     *
     * @param jsons     json arrays to parse.
     * @param colorChar {@link org.diorite.chat.ChatColor#translateAlternateColorCodes(char, String)}, use null to skip.
     *
     * @return parsed base component.
     */
    public static BaseComponent[] safeParse(final String[] jsons, final Character colorChar)
    {
        final BaseComponent[] result = new BaseComponent[jsons.length];
        for (int i = 0, jsonsLength = jsons.length; i < jsonsLength; i++)
        {
            result[i] = safeParse(jsons[i], colorChar);
        }
        return result;
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
