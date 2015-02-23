package diorite.chat.serialize;

import java.lang.reflect.Type;
import java.util.Arrays;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import diorite.chat.BaseComponent;
import diorite.chat.TranslatableComponent;

public class TranslatableComponentSerializer extends BaseComponentSerializer implements JsonSerializer<TranslatableComponent>, JsonDeserializer<TranslatableComponent>
{
    @Override
    public TranslatableComponent deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException
    {
        final TranslatableComponent component = new TranslatableComponent();
        final JsonObject object = json.getAsJsonObject();
        this.deserialize(object, component, context);
        component.setTranslate(object.get("translate").getAsString());
        if (object.has("with"))
        {
            component.setWith(Arrays.asList((BaseComponent[]) context.deserialize(object.get("with"), BaseComponent[].class)));
        }
        return component;
    }

    @Override
    public JsonElement serialize(final TranslatableComponent src, final Type typeOfSrc, final JsonSerializationContext context)
    {
        final JsonObject object = new JsonObject();
        this.serialize(object, src, context);
        object.addProperty("translate", src.getTranslate());
        if (src.getWith() != null)
        {
            object.add("with", context.serialize(src.getWith()));
        }
        return object;
    }
}
