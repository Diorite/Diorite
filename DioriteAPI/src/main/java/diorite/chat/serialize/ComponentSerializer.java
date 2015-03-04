package diorite.chat.serialize;

import java.lang.reflect.Type;
import java.util.HashSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import diorite.chat.BaseComponent;
import diorite.chat.TextComponent;
import diorite.chat.TranslatableComponent;

public class ComponentSerializer implements JsonDeserializer<BaseComponent>
{
    public static final  ThreadLocal<HashSet<BaseComponent>> serializedComponents = new ThreadLocal<>();
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

    public static BaseComponent[] parse(final String json)
    {
        if (json.startsWith("["))
        {
            return gson.fromJson(json, BaseComponent[].class);
        }
        return new BaseComponent[]{gson.fromJson(json, BaseComponent.class)};
    }

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

    public static String toString(final BaseComponent component)
    {
        return gson.toJson(component);
    }

    public static String toString(final BaseComponent... components)
    {
        return gson.toJson(new TextComponent(components));
    }
}
