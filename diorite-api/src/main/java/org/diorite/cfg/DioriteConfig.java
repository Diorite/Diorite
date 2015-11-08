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

package org.diorite.cfg;

import java.io.File;
import java.net.URI;
import java.util.Locale;

/**
 * Class representing diorite configuration, most of params are read-only and some of them may not be updated (no visible changes on server) if changed. (or saved)
 */
public interface DioriteConfig
{
    /**
     * Returns hostname used by server, like 0.0.0.0 or localhost.
     *
     * @return hostname used by server to listen on.
     */
    String getHostname();

    /**
     * Returns port of server.
     *
     * @return port of server.
     */
    int getPort();

    /**
     * Returns query port of server.
     *
     * @return query port of server.
     */
    int getQueryPort();

    /**
     * Returns rcon port of server.
     *
     * @return rcon port of server.
     */
    int getRconPort();

    /**
     * Returns network compression threshold, if packet is bigger than threshold it will be compressed. <br>
     * 0 - all packets will be compressed.<br>
     * -1 - no packets will be compressed.
     *
     * @return network compression threshold.
     */
    int getNetworkCompressionThreshold();

    /**
     * Returns how many threads netty should use.
     *
     * @return how many threads netty should use.
     */
    int getNettyThreads();

    /**
     * Returns {@link org.diorite.cfg.DioriteConfig.OnlineMode} of server.
     *
     * @return {@link org.diorite.cfg.DioriteConfig.OnlineMode} of server.
     */
    OnlineMode getOnlineMode();

    /**
     * Set {@link org.diorite.cfg.DioriteConfig.OnlineMode} of server.
     *
     * @param onlineMode new {@link org.diorite.cfg.DioriteConfig.OnlineMode} of server.
     */
    void setOnlineMode(OnlineMode onlineMode);

    /**
     * Returns max amount of players on server.
     *
     * @return max amount of players on server.
     */
    int getMaxPlayers();

    /**
     * Set max amount of players on server.
     *
     * @param maxPlayers new max amount of players on server.
     */
    void setMaxPlayers(int maxPlayers);

    /**
     * Returns time in seconds that player need idle to get kicked from server.
     *
     * @return time in seconds that player need idle to get kicked from server.
     */
    int getPlayerIdleTimeout();

    /**
     * Set time in seconds that player need idle to get kicked from server.
     *
     * @param playerIdleTimeout new time in seconds that player need idle to get kicked from server.
     */
    void setPlayerIdleTimeout(int playerIdleTimeout);

    /**
     * Returns {@link URI} to server resource pack.
     *
     * @return {@link URI} to server resource pack.
     */
    URI getResourcePack();

    /**
     * Set {@link URI} to server resource pack.
     *
     * @param resourcePack new {@link URI} to server resource pack.
     */
    void setResourcePack(URI resourcePack);

    /**
     * Returns hash of server resource pack.
     *
     * @return hash of server resource pack.
     */
    String getResourcePackHash();

    /**
     * Set hash of server resource pack.
     *
     * @param resourcePackHash new hash of server resource pack.
     */
    void setResourcePackHash(String resourcePackHash);

    /**
     * Returns if server optimized packet sending/receiving on Linux. (epoll)
     *
     * @return true if server use epoll if possible.
     */
    boolean isUseNativeTransport();

    /**
     * Returns server-side viewing distance, measured in chunks in each direction of the player. (radius)
     *
     * @return server-side viewing distance.
     */
    int getViewDistance();

    /**
     * Set server-side viewing distance, measured in chunks in each direction of the player. (radius)
     *
     * @param viewDistance new server-side viewing distance.
     */
    void setViewDistance(int viewDistance);

    /**
     * Returns amount of threads used to process basic input actions, like player messages, commands, tab-complete.
     *
     * @return amount of threads used to process basic input actions, like player messages, commands, tab-complete.
     */
    int getInputThreadPoolSize();

    /**
     * Returns name/location of file used to store administrators uuids/nicknames.
     *
     * @return name/location of file used to store administrators uuids/nicknames.
     */
    File getAdministratorsFile();

    /**
     * Returns name/location of directory used to store plugins.
     *
     * @return name/location of directory used to store plugins.
     */
    File getPluginsDirectory();

    /**
     * Returns if whitelist is enabled on server.
     *
     * @return if whitelist is enabled on server.
     */
    boolean isWhiteListEnabled();

    /**
     * Returns name/location of file used to store whitelisted uuids/nicknames.
     *
     * @return name/location of file used to store whitelisted uuids/nicknames.
     */
    File getWhiteListFile();

    /**
     * Returns server message of the day. (visible in client server list)
     *
     * @return server message of the day.
     */
    String getMotd();

    /**
     * Set server message of the day. (visible in client server list)
     *
     * @param motd new server message of the day.
     */
    void setMotd(String motd);

    /**
     * Returns enabled languages, diorite/plugin messages can be displayed for every player in thier own languages. (if they are supported)
     *
     * @return enabled languages.
     */
    Locale[] getLanguages();

    /**
     * Returns uuid used by metrics stats.
     *
     * @return uuid used by metrics stats.
     */
    String getMetricsUuid();

    /**
     * Returns worlds configuration instance.
     *
     * @return worlds configuration instance.
     */
    WorldsConfig getWorlds();

    /**
     * Enum for possible online-modes.
     */
    enum OnlineMode
    {
        /**
         * Only players who bought a game can join.
         */
        TRUE,
        /**
         * Any user can join as any player.
         */
        FALSE,
        /**
         * If player have nickname used by user who bought a game, he must be legal owner of this account,
         * but if nikcname isn't used, anyone can use it.
         */
        AUTO
    }
}
