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

package org.diorite.impl.connection.ping;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ServerPingServerData
{
    private String name;
    private int    protocol;

    public ServerPingServerData(final String paramString, final int paramInt)
    {
        this.name = paramString;
        this.protocol = paramInt;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public int getProtocol()
    {
        return this.protocol;
    }

    public void setProtocol(final int protocol)
    {
        this.protocol = protocol;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("protocol", this.protocol).toString();
    }

    public static class Serializer implements JsonDeserializer<ServerPingServerData>, JsonSerializer<ServerPingServerData>
    {
        @Override
        public ServerPingServerData deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
        {
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new ServerPingServerData(jsonObject.get("name").getAsString(), jsonObject.get("protocol").getAsInt());
        }

        @Override
        public JsonElement serialize(final ServerPingServerData serverPingServerData, final Type type, final JsonSerializationContext context)
        {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", serverPingServerData.getName());
            jsonObject.addProperty("protocol", serverPingServerData.getProtocol());
            return jsonObject;
        }
    }
}