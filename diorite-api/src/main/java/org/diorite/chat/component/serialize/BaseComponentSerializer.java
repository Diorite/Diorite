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

import java.util.Arrays;
import java.util.HashSet;

import com.google.common.base.Preconditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import org.diorite.chat.ChatColor;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.ClickEvent;
import org.diorite.chat.component.HoverEvent;

/**
 * Class used by other chat component serializers to serialize {@link BaseComponent} to json or deserialize from json to base component.
 */
public interface BaseComponentSerializer
{
    /**
     * Deserialize basic attributes of {@link BaseComponent} from json object.
     *
     * @param object    json contains all data.
     * @param component base component to use.
     * @param context   json context object, used to deserialize other objects.
     */
    default void deserialize(final JsonObject object, final BaseComponent component, final JsonDeserializationContext context)
    {
        if (object.has("color"))
        {
            component.setColor(ChatColor.valueOf(object.get("color").getAsString().toUpperCase()));
        }
        if (object.has("bold"))
        {
            component.setBold(object.get("bold").getAsBoolean());
        }
        if (object.has("italic"))
        {
            component.setItalic(object.get("italic").getAsBoolean());
        }
        if (object.has("underlined"))
        {
            component.setUnderlined(object.get("underlined").getAsBoolean());
        }
        if (object.has("strikethrough"))
        {
            component.setUnderlined(object.get("strikethrough").getAsBoolean());
        }
        if (object.has("obfuscated"))
        {
            component.setUnderlined(object.get("obfuscated").getAsBoolean());
        }
        if (object.has("extra"))
        {
            component.setExtra(Arrays.asList(context.<BaseComponent[]>deserialize(object.get("extra"), BaseComponent[].class)));
        }

        if (object.has("clickEvent"))
        {
            final JsonObject event = object.getAsJsonObject("clickEvent");
            component.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(event.get("action").getAsString().toUpperCase()), event.get("value").getAsString()));
        }
        if (object.has("hoverEvent"))
        {
            final JsonObject event = object.getAsJsonObject("hoverEvent");
            final BaseComponent[] res;
            if (event.get("value").isJsonArray())
            {
                res = context.deserialize(event.get("value"), BaseComponent[].class);
            }
            else
            {
                res = new BaseComponent[]{context.<BaseComponent>deserialize(event.get("value"), BaseComponent.class)};
            }
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(event.get("action").getAsString().toUpperCase()), res));
        }
    }

    /**
     * Serialize basic attributes of {@link BaseComponent} to given json object.
     *
     * @param object    json to use.
     * @param component base component to use.
     * @param context   json context object, used to serialize other objects.
     */
    default void serialize(final JsonObject object, final BaseComponent component, final JsonSerializationContext context)
    {
        boolean first = false;
        if (ComponentSerializer.serializedComponents.get() == null)
        {
            first = true;
            ComponentSerializer.serializedComponents.set(new HashSet<>(5));
        }
        try
        {
            Preconditions.checkArgument(! ComponentSerializer.serializedComponents.get().contains(component), "Component loop");
            ComponentSerializer.serializedComponents.get().add(component);
            if (component.getColorRaw() != null)
            {
                object.addProperty("color", component.getColorRaw().getName());
            }
            if (component.isBoldRaw() != null)
            {
                object.addProperty("bold", component.isBoldRaw());
            }
            if (component.isItalicRaw() != null)
            {
                object.addProperty("italic", component.isItalicRaw());
            }
            if (component.isUnderlinedRaw() != null)
            {
                object.addProperty("underlined", component.isUnderlinedRaw());
            }
            if (component.isStrikethroughRaw() != null)
            {
                object.addProperty("strikethrough", component.isStrikethroughRaw());
            }
            if (component.isObfuscatedRaw() != null)
            {
                object.addProperty("obfuscated", component.isObfuscatedRaw());
            }

            if (component.getExtra() != null)
            {
                object.add("extra", context.serialize(component.getExtra()));
            }

            if (component.getClickEvent() != null)
            {
                final JsonObject clickEvent = new JsonObject();
                clickEvent.addProperty("action", component.getClickEvent().getAction().toString().toLowerCase());
                clickEvent.addProperty("value", component.getClickEvent().getValue());
                object.add("clickEvent", clickEvent);
            }
            if (component.getHoverEvent() != null)
            {
                final JsonObject hoverEvent = new JsonObject();
                hoverEvent.addProperty("action", component.getHoverEvent().getAction().toString().toLowerCase());
                hoverEvent.add("value", context.serialize(component.getHoverEvent().getValue()));
                object.add("hoverEvent", hoverEvent);
            }
        } finally
        {
            ComponentSerializer.serializedComponents.get().remove(component);
            if (first)
            {
                ComponentSerializer.serializedComponents.set(null);
            }
        }
    }
}
