package diorite.chat.serialize;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import diorite.chat.TextComponent;
import diorite.chat.BaseComponent;

public class TextComponentSerializer extends BaseComponentSerializer implements JsonSerializer<TextComponent>, JsonDeserializer<TextComponent>
{
    @Override
    public TextComponent deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
    {
        final TextComponent component = new TextComponent();
        final JsonObject object = json.getAsJsonObject();
        this.deserialize(object, component, context);
        component.setText(object.get("text").getAsString());
        return component;
    }

    @Override
    public JsonElement serialize(final TextComponent src, final Type typeOfSrc, final JsonSerializationContext context)
    {
        final List<BaseComponent> extra = src.getExtra();
        if ((! src.hasFormatting()) && ((extra == null) || (extra.isEmpty())))
        {
            return new JsonPrimitive(src.getText());
        }
        final JsonObject object = new JsonObject();
        this.serialize(object, src, context);
        object.addProperty("text", src.getText());
        return object;
    }
}
