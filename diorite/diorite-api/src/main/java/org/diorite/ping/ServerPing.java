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

package org.diorite.ping;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatMessage;

/**
 * Represent server ping data.
 */
public class ServerPing
{
    private           ChatMessage            motd;
    private           ServerPingPlayerSample playerData;
    private           ServerPingServerData   serverData;
    private @Nullable Favicon                favicon;

    /**
     * Create new ping instance.
     *
     * @param motd
     *         message of the day to show.
     * @param favicon
     *         favicon object.
     * @param serverData
     *         version server data.
     * @param playerData
     *         player sample data.
     */
    public ServerPing(ChatMessage motd, @Nullable Favicon favicon, ServerPingServerData serverData, ServerPingPlayerSample playerData)
    {
        this.motd = motd;
        this.favicon = favicon;
        this.serverData = serverData;
        this.playerData = playerData;
    }

    /**
     * Returns message of the day to display.
     *
     * @return message of the day to display.
     */
    public ChatMessage getMotd()
    {
        return this.motd;
    }

    /**
     * Set message of the day to display.
     *
     * @param motd
     *         new message of the day.
     */
    public void setMotd(ChatMessage motd)
    {
        this.motd = motd;
    }

    /**
     * Returns favicon object.
     *
     * @return favicon object.
     */
    @Nullable
    public Favicon getFavicon()
    {
        return this.favicon;
    }

    /**
     * Sets new favicon.
     *
     * @param favicon
     *         new favicon.
     */
    public void setFavicon(@Nullable Favicon favicon)
    {
        this.favicon = favicon;
    }

    /**
     * Returns server version ping data.
     *
     * @return server version ping data.
     */
    public ServerPingServerData getServerData()
    {
        return this.serverData;
    }

    /**
     * Set new ping version data.
     *
     * @param serverData
     *         new ping version data.
     */
    public void setServerData(ServerPingServerData serverData)
    {
        this.serverData = serverData;
    }

    /**
     * Returns player sample data.
     *
     * @return player sample data.
     */
    public ServerPingPlayerSample getPlayerData()
    {
        return this.playerData;
    }

    /**
     * Set new player sample data.
     *
     * @param playerData
     *         new player sample data.
     */
    public void setPlayerData(ServerPingPlayerSample playerData)
    {
        this.playerData = playerData;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("motd", this.motd)
                                                                          .append("playerData", this.playerData).append("serverData", this.serverData)
                                                                          .append("favicon", this.favicon != null).toString();
    }
}