package org.diorite.utils.json.adapters;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JsonUUIDAdapter extends TypeAdapter<UUID>
{
    public static final  Pattern UUID_PAT_NO_DASHES   = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
    public static final  Pattern UUID_PAT_WITH_DASHES = Pattern.compile("(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12})");
    private static final Pattern DASH_PAT             = Pattern.compile("-", Pattern.LITERAL);

    private final boolean withDashes;

    public JsonUUIDAdapter(final boolean withDashes)
    {
        this.withDashes = withDashes;
    }

    public JsonUUIDAdapter()
    {
        this(true);
    }

    @Override
    public void write(final JsonWriter out, final UUID value) throws IOException
    {
        out.value(fromUUID(value, this.withDashes));
    }

    @Override
    public UUID read(final JsonReader in) throws IOException
    {
        return fromString(in.nextString(), this.withDashes);
    }

    public boolean isWithDashes()
    {
        return this.withDashes;
    }

    public static String fromUUID(final UUID value)
    {
        return DASH_PAT.matcher(value.toString()).replaceAll("");
    }

    public static String fromUUID(final UUID value, final boolean withDashes)
    {
        return withDashes ? value.toString() : DASH_PAT.matcher(value.toString()).replaceAll("");
    }

    public static UUID fromString(final String input)
    {
        return input.contains("-") ? UUID.fromString(input) : UUID.fromString(UUID_PAT_NO_DASHES.matcher(input).replaceFirst("$1-$2-$3-$4-$5"));
    }

    public static UUID fromString(final String input, final boolean withDashes)
    {
        return withDashes ? UUID.fromString(input) : UUID.fromString(UUID_PAT_NO_DASHES.matcher(input).replaceFirst("$1-$2-$3-$4-$5"));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("withDashes", this.withDashes).toString();
    }
}
