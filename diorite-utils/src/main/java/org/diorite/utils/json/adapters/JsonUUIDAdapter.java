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

package org.diorite.utils.json.adapters;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * {@link TypeAdapter} for {@link UUID}
 */
public class JsonUUIDAdapter extends TypeAdapter<UUID>
{
    public static final  Pattern UUID_PAT_NO_DASHES   = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
    public static final  Pattern UUID_PAT_WITH_DASHES = Pattern.compile("(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12})");
    private static final Pattern DASH_PAT             = Pattern.compile("-", Pattern.LITERAL);

    private final boolean withDashes;

    /**
     * Construct new UUID adapter.
     *
     * @param withDashes if uuid should use dashes.
     */
    public JsonUUIDAdapter(final boolean withDashes)
    {
        this.withDashes = withDashes;
    }

    /**
     * Construct new UUID adapter.
     */
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

    /**
     * Returns String UUID representation without dashes.
     *
     * @param value uuid to change to string.
     *
     * @return String UUID representation without dashes.
     */
    public static String fromUUID(final UUID value)
    {
        return DASH_PAT.matcher(value.toString()).replaceAll("");
    }

    /**
     * Returns String UUID representation.
     *
     * @param value      uuid to change to string.
     * @param withDashes if uuid string should use dashes.
     *
     * @return String UUID representation.
     */
    public static String fromUUID(final UUID value, final boolean withDashes)
    {
        return withDashes ? value.toString() : DASH_PAT.matcher(value.toString()).replaceAll("");
    }

    /**
     * Returns UUID converted from String. Method will detect if uuid use dashes or not.
     *
     * @param input String to parse.
     *
     * @return UUID converted from String.
     */
    public static UUID fromString(final String input)
    {
        return input.contains("-") ? UUID.fromString(input) : UUID.fromString(UUID_PAT_NO_DASHES.matcher(input).replaceFirst("$1-$2-$3-$4-$5"));
    }

    /**
     * Returns UUID converted from String.
     *
     * @param input      String to parse.
     * @param withDashes if uuid string use dashes.
     *
     * @return UUID converted from String.
     */
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
