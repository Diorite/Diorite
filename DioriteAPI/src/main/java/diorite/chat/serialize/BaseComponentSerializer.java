package diorite.chat.serialize;

import java.util.Arrays;
import java.util.HashSet;

import com.google.common.base.Preconditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import diorite.ChatColor;
import diorite.chat.BaseComponent;
import diorite.chat.ClickEvent;
import diorite.chat.HoverEvent;

public class BaseComponentSerializer
{
    public void deserialize(final JsonObject object, final BaseComponent component, final JsonDeserializationContext context)
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

    public void serialize(final JsonObject object, final BaseComponent component, final JsonSerializationContext context)
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
