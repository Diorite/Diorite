package com.mojang.util;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class UUIDTypeAdapter
        extends TypeAdapter<UUID>
{
    private static final Pattern UUID_PAT = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

    @Override
    public void write(final JsonWriter out, final UUID value)
            throws IOException
    {
        out.value(fromUUID(value));
    }

    @Override
    public UUID read(final JsonReader in)
            throws IOException
    {
        return fromString(in.nextString());
    }

    public static String fromUUID(final UUID value)
    {
        return value.toString().replace("-", "");
    }

    public static UUID fromString(final CharSequence input)
    {
        return UUID.fromString(UUID_PAT.matcher(input).replaceFirst("$1-$2-$3-$4-$5"));
    }
}
