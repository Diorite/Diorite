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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.gameprofile.GameProfile;

/**
 * Represent ping player sample.
 */
public class ServerPingPlayerSample
{
    private int               maxPlayers;
    private int               onlinePlayers;
    private List<GameProfile> profiles;

    /**
     * Construct new sample.
     *
     * @param maxPlayers
     *         max players on server.
     * @param onlinePlayers
     *         online players.
     * @param profiles
     *         collection of players, it will be copied to this object.
     */
    public ServerPingPlayerSample(int maxPlayers, int onlinePlayers, Collection<GameProfile> profiles)
    {
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.profiles = new ArrayList<>(profiles);
    }

    /**
     * Returns max amount of players.
     *
     * @return max amount of players.
     */
    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    /**
     * Sets new max players amount.
     *
     * @param maxPlayers
     *         new max amount of players.
     */
    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    /**
     * Returns count of online players.
     *
     * @return count of online players.
     */
    public int getOnlinePlayers()
    {
        return this.onlinePlayers;
    }

    /**
     * Set count of online players.
     *
     * @param onlinePlayers
     *         new count of online players.
     */
    public void setOnlinePlayers(int onlinePlayers)
    {
        this.onlinePlayers = onlinePlayers;
    }

    /**
     * Returns unmodifiable collection of sample profiles.
     *
     * @return unmodifiable collection of sample profiles.
     */
    public List<? extends GameProfile> getProfiles()
    {
        return Collections.unmodifiableList(this.profiles);
    }

    /**
     * Set new profile sample, note that collection will be copied to new one.
     *
     * @param profiles
     *         new profiles to use.
     */
    public void setProfiles(Collection<GameProfile> profiles)
    {
        this.profiles = new ArrayList<>(profiles);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("maxPlayers", this.maxPlayers)
                                                                          .append("onlinePlayers", this.onlinePlayers).append("profiles", this.profiles)
                                                                          .toString();
    }
}