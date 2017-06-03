/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.protocol.p16w50a.clientbound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import org.diorite.core.protocol.AbstractPacketDataSerializer;
import org.diorite.chat.ChatMessage;
import org.diorite.chat.Parser;
import org.diorite.core.protocol.InvalidPacketException;
import org.diorite.core.protocol.PacketClass;
import org.diorite.core.protocol.connection.ProtocolDirection;
import org.diorite.core.protocol.connection.internal.ProtocolState;
import org.diorite.gameprofile.GameProfile;

@PacketClass(id = 0x00, direction = ProtocolDirection.CLIENTBOUND, state = ProtocolState.STATUS, minSize = 0, maxSize = 0, preferredSize = 0)
public class CS00Response extends ClientboundPacket
{
    private String                            versionString;
    private int                               versionNumber;
    private int                               maxPlayers;
    private int                               onlinePlayers;
    private Collection<? extends GameProfile> sample;
    private ChatMessage                       description;
    private String                            encodedFavicon;

    @Override
    protected void write(AbstractPacketDataSerializer serializer) throws InvalidPacketException
    {
        JsonObject response = new JsonObject();
        {
            JsonObject version = new JsonObject();
            version.addProperty("name", this.versionString);
            version.addProperty("protocol", this.versionNumber);
            response.add("version", version);
        }
        {
            JsonObject players = new JsonObject();
            players.addProperty("max", this.maxPlayers);
            players.addProperty("online", this.onlinePlayers);
            JsonArray sample = new JsonArray();
            if (this.sample != null)
            {
                for (GameProfile gameProfile : this.sample)
                {
                    JsonObject profile = new JsonObject();
                    if (gameProfile.getId() == null)
                    {
                        profile.addProperty("id", UUID.randomUUID().toString());
                    }
                    else
                    {
                        profile.addProperty("id", gameProfile.getId().toString());
                    }
                    profile.addProperty("name", gameProfile.getName());
                    sample.add(profile);
                }
            }
            players.add("sample", sample);
            response.add("players", players);
        }
        if (this.description != null)
        {
            JsonParser parser = new JsonParser();
//            String parse = new Parser("s&2*/hi/*", null).parse();
            String parse = new Parser("*Test* _T&ao ~je&bst~ /*sformatowane* &dd MOTD/!!_ _I_ po /formatowaniu/. &5%nah%  [&4Even [Mo&3re](links.com)](diorite.org) dfd", null).parse();
//            String parse = new Parser("hi &6diorite.org lel &8https://diorite.org&r lel", null).parse()

            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(System.out));
            jsonWriter.setIndent("  ");
            JsonElement jsonElement = parser.parse(parse);
            try
            {
                Streams.write(jsonElement, jsonWriter);
                jsonWriter.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            response.add("description", jsonElement);
        }
        if (this.encodedFavicon != null)
        {
            response.addProperty("favicon", this.encodedFavicon);
        }
        serializer.writeText(response.toString());
    }

    @Nullable
    public String getVersionString()
    {
        return this.versionString;
    }

    public void setVersionString(@Nonnull String versionString)
    {
        this.versionString = versionString;
        this.setDirty();
    }

    public int getVersionNumber()
    {
        return this.versionNumber;
    }

    public void setVersionNumber(int versionNumber)
    {
        this.versionNumber = versionNumber;
        this.setDirty();
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
        this.setDirty();
    }

    public int getOnlinePlayers()
    {
        return this.onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers)
    {
        this.onlinePlayers = onlinePlayers;
        this.setDirty();
    }

    @Nullable
    public Collection<GameProfile> getSample()
    {
        return (this.sample == null) ? null : Collections.unmodifiableCollection(this.sample);
    }

    public void setSample(@Nullable Collection<? extends GameProfile> sample)
    {
        this.sample = sample;
        this.setDirty();
    }

    @Nullable
    public ChatMessage getDescription()
    {
        return this.description;
    }

    public void setDescription(@Nullable ChatMessage description)
    {
        this.description = description;
        this.setDirty();
    }

    @Nullable
    public String getEncodedFavicon()
    {
        return this.encodedFavicon;
    }

    public void setEncodedFavicon(@Nullable String encodedFavicon)
    {
        this.encodedFavicon = encodedFavicon;
        this.setDirty();
    }
}
