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

package org.diorite.impl.cfg;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Core;
import org.diorite.cfg.DioriteConfig;
import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.CfgComments;
import org.diorite.cfg.annotations.CfgFooterComment;
import org.diorite.cfg.annotations.defaults.CfgBooleanDefault;
import org.diorite.cfg.annotations.defaults.CfgByteDefault;
import org.diorite.cfg.annotations.defaults.CfgCustomDefault;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;
import org.diorite.cfg.annotations.defaults.CfgDelegateImport;
import org.diorite.cfg.annotations.defaults.CfgIntDefault;
import org.diorite.cfg.annotations.defaults.CfgStringArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgStringDefault;

@SuppressWarnings({"HardcodedFileSeparator", "SimplifiableIfStatement"})
@CfgClass(name = "DioriteConfig")
@CfgComments({"Welcome in Diorite configuration file, in this file you will", "find every option needed to configure your server."})
@CfgFooterComment("End of configuration!")
@CfgFooterComment("=====================")
@CfgDelegateDefault("new DioriteConfigImpl()")
public class DioriteConfigImpl implements DioriteConfig
{
    @CfgComment("Hostname to listen on.")
    @CfgStringDefault("localhost")
    private String hostname;

    @CfgComment("Port to listen on.")
    @CfgIntDefault(Core.DEFAULT_PORT)
    private int port;

    @CfgComment("Enables GameSpy4 protocol server listener. Used to get information about server. Set to -1 to disable.")
    @CfgIntDefault(- 1)
    private int queryPort;

    @CfgComment("Port for rcon (remote access to the server console), Set to -1 to disable.")
    @CfgIntDefault(- 1)
    private int rconPort;

    @CfgComment("Password to rcon.")
    @CfgStringDefault("")
    private String rconPassword;

    @CfgComment("By default it allows packets that are n-1 bytes big to go normally, but a packet that n bytes or more will be compressed down. 0 -> compress everything, -1 -> disabled")
    @CfgComment("Many packets below 128 bytes may be even larger after compression!")
    @CfgIntDefault(Core.DEFAULT_PACKET_COMPRESSION_THRESHOLD)
    private int networkCompressionThreshold;

    @CfgComment("Amount of netty event loop threads.")
    @CfgByteDefault(4)
    private int nettyThreads;

    @CfgComment("Can be true, false or auto. If true all players will be authorized with Mojang, if false everyone can join with any nickname (no connections to mojang). With \"auto\", players that seems to be premium (they have premium nickname) will be authorized with Mojang, other players can join without it. (You should use online mode true, or at least find some authorization plugin for players.)")
    @CfgOnlineModeDefault(OnlineMode.TRUE)
    private OnlineMode onlineMode;

    @CfgComment("The maximum number of players that can play on the server at the same time.")
    @CfgIntDefault(10)
    private int maxPlayers;

    @CfgComment("Players are kicked if they are idle for more than that many seconds.")
    @CfgIntDefault(600)
    private int playerIdleTimeout;

    @CfgComment("Optional URI to a resource pack. The player may choose to use it. Set to empty to disable.")
    @CfgStringDefault("")
    private URI resourcePack;

    @CfgComment("Optional SHA-1 digest of the resource pack, in lowercase hexadecimal. It's recommended to specify this. This is not yet used to verify the integrity of the resource pack, but improves the effectiveness and reliability of caching.")
    @CfgStringDefault("")
    private String resourcePackHash;

    @CfgComment("Linux server performance improvements: optimized packet sending/receiving on Linux. Windows or other systems that don't support epoll will just ignore this setting.")
    @CfgBooleanDefault(true)
    private boolean useNativeTransport;

    @CfgComment("It determines the server-side viewing distance, measured in chunks in each direction of the player. (radius)")
    @CfgIntDefault(8)
    private int viewDistance;

    @CfgComment("How many threads are used by diorite to handle command, chat and tab-complete input.")
    @CfgIntDefault(2)
    private int inputThreadPoolSize;

    @CfgComment("Path to file with administrators UUIDs/nicknames and settings. (Users that have most of permissions by default.)")
    @CfgStringDefault("adms.yml")
    private File administratorsFile;

    @CfgComment("Path to plugins directory. Plugins will be loaded from this directory.")
    @CfgStringDefault("plugins")
    private File pluginsDirectory;

    @CfgComment("If true, only players from special file can join to server.")
    @CfgBooleanDefault(false)
    private boolean whiteListEnabled;

    @CfgComment("Path to file with UUID/nicknames of players added to whitelist.")
    @CfgStringDefault("whitelist.yml")
    private File whiteListFile;

    @CfgComment("Message of the day, used on client server list. You may use JSON message here too.")
    @CfgStringDefault("&7Welcome on &3diorite &7server&3!\n&7Join and play today&3!")
    private String motd;

    @CfgComment("Languages to load, by enabling more languages you may allow players to select own one.")
    @CfgStringArrayDefault(value = "en-US")
    private Locale[] languages;

    @CfgComment("Metrics UUID")
    @CfgDelegateImport("java.util")
    @CfgDelegateDefault("UUID.randomUUID().toString()")
    private String metricsUuid;

    @CfgComment("Worlds configuration.")
    @CfgDelegateImport("org.diorite.cfg.system")
    @CfgDelegateDefault("adv|final Template template = TemplateCreator.getTemplate(WorldsConfigImpl.class, true);return template.fillDefaults(new WorldsConfigImpl());")
    private WorldsConfigImpl worlds;

    @Override
    public String getHostname()
    {
        return this.hostname;
    }

    public void setHostname(final String hostname)
    {
        this.hostname = hostname;
    }

    @Override
    public int getPort()
    {
        return this.port;
    }

    public void setPort(final int port)
    {
        this.port = port;
    }

    @Override
    public int getQueryPort()
    {
        return this.queryPort;
    }

    public void setQueryPort(final int queryPort)
    {
        this.queryPort = queryPort;
    }

    @Override
    public int getRconPort()
    {
        return this.rconPort;
    }

    public void setRconPort(final int rconPort)
    {
        this.rconPort = rconPort;
    }

    public String getRconPassword()
    {
        return this.rconPassword;
    }

    public void setRconPassword(final String rconPassword)
    {
        this.rconPassword = rconPassword;
    }

    @Override
    public int getNetworkCompressionThreshold()
    {
        return this.networkCompressionThreshold;
    }

    public void setNetworkCompressionThreshold(final int networkCompressionThreshold)
    {
        this.networkCompressionThreshold = networkCompressionThreshold;
    }

    @Override
    public int getNettyThreads()
    {
        return this.nettyThreads;
    }

    public void setNettyThreads(final int nettyThreads)
    {
        this.nettyThreads = nettyThreads;
    }

    @Override
    public OnlineMode getOnlineMode()
    {
        return this.onlineMode;
    }

    @Override
    public void setOnlineMode(final OnlineMode onlineMode)
    {
        this.onlineMode = onlineMode;
    }

    @Override
    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    @Override
    public void setMaxPlayers(final int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public int getPlayerIdleTimeout()
    {
        return this.playerIdleTimeout;
    }

    @Override
    public void setPlayerIdleTimeout(final int playerIdleTimeout)
    {
        this.playerIdleTimeout = playerIdleTimeout;
    }

    @Override
    public URI getResourcePack()
    {
        return this.resourcePack;
    }

    @Override
    public void setResourcePack(final URI resourcePack)
    {
        this.resourcePack = resourcePack;
    }

    @Override
    public String getResourcePackHash()
    {
        return this.resourcePackHash;
    }

    @Override
    public void setResourcePackHash(final String resourcePackHash)
    {
        this.resourcePackHash = resourcePackHash;
    }

    @Override
    public boolean isUseNativeTransport()
    {
        return this.useNativeTransport;
    }

    public void setUseNativeTransport(final boolean useNativeTransport)
    {
        this.useNativeTransport = useNativeTransport;
    }

    @Override
    public int getViewDistance()
    {
        return this.viewDistance;
    }

    @Override
    public void setViewDistance(final int viewDistance)
    {
        this.viewDistance = viewDistance;
    }

    @Override
    public int getInputThreadPoolSize()
    {
        return this.inputThreadPoolSize;
    }

    public void setInputThreadPoolSize(final int inputThreadPoolSize)
    {
        this.inputThreadPoolSize = inputThreadPoolSize;
    }

    @Override
    public File getAdministratorsFile()
    {
        return this.administratorsFile;
    }

    public void setAdministratorsFile(final File administratorsFile)
    {
        this.administratorsFile = administratorsFile;
    }

    @Override
    public File getPluginsDirectory()
    {
        return this.pluginsDirectory;
    }

    public void setPluginsDirectory(final File pluginsDirectory)
    {
        this.pluginsDirectory = pluginsDirectory;
    }

    @Override
    public boolean isWhiteListEnabled()
    {
        return this.whiteListEnabled;
    }

    public void setWhiteListEnabled(final boolean whiteListEnabled)
    {
        this.whiteListEnabled = whiteListEnabled;
    }

    @Override
    public File getWhiteListFile()
    {
        return this.whiteListFile;
    }

    public void setWhiteListFile(final File whiteListFile)
    {
        this.whiteListFile = whiteListFile;
    }

    @Override
    public String getMotd()
    {
        return this.motd;
    }

    @Override
    public void setMotd(final String motd)
    {
        this.motd = motd;
    }

    @Override
    public Locale[] getLanguages()
    {
        return this.languages;
    }

    public void setLanguages(final Locale[] languages)
    {
        this.languages = languages;
    }

    @Override
    public String getMetricsUuid()
    {
        return this.metricsUuid;
    }

    public void setMetricsUuid(final String metricsUuid)
    {
        this.metricsUuid = metricsUuid;
    }

    @Override
    public WorldsConfigImpl getWorlds()
    {
        return this.worlds;
    }

    public void setWorlds(final WorldsConfigImpl worlds)
    {
        this.worlds = worlds;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof DioriteConfigImpl))
        {
            return false;
        }

        final DioriteConfigImpl that = (DioriteConfigImpl) o;

        if (this.port != that.port)
        {
            return false;
        }
        if (this.queryPort != that.queryPort)
        {
            return false;
        }
        if (this.rconPort != that.rconPort)
        {
            return false;
        }
        if (this.networkCompressionThreshold != that.networkCompressionThreshold)
        {
            return false;
        }
        if (this.nettyThreads != that.nettyThreads)
        {
            return false;
        }
        if (this.maxPlayers != that.maxPlayers)
        {
            return false;
        }
        if (this.playerIdleTimeout != that.playerIdleTimeout)
        {
            return false;
        }
        if (this.useNativeTransport != that.useNativeTransport)
        {
            return false;
        }
        if (this.viewDistance != that.viewDistance)
        {
            return false;
        }
        if (this.inputThreadPoolSize != that.inputThreadPoolSize)
        {
            return false;
        }
        if (this.whiteListEnabled != that.whiteListEnabled)
        {
            return false;
        }
        if ((this.hostname != null) ? ! this.hostname.equals(that.hostname) : (that.hostname != null))
        {
            return false;
        }
        if ((this.rconPassword != null) ? ! this.rconPassword.equals(that.rconPassword) : (that.rconPassword != null))
        {
            return false;
        }
        if (this.onlineMode != that.onlineMode)
        {
            return false;
        }
        if ((this.resourcePack != null) ? ! this.resourcePack.equals(that.resourcePack) : (that.resourcePack != null))
        {
            return false;
        }
        if ((this.resourcePackHash != null) ? ! this.resourcePackHash.equals(that.resourcePackHash) : (that.resourcePackHash != null))
        {
            return false;
        }
        if ((this.administratorsFile != null) ? ! this.administratorsFile.equals(that.administratorsFile) : (that.administratorsFile != null))
        {
            return false;
        }
        if ((this.whiteListFile != null) ? ! this.whiteListFile.equals(that.whiteListFile) : (that.whiteListFile != null))
        {
            return false;
        }
        if ((this.motd != null) ? ! this.motd.equals(that.motd) : (that.motd != null))
        {
            return false;
        }
        return ! ((this.worlds != null) ? ! this.worlds.equals(that.worlds) : (that.worlds != null));

    }

    @Override
    public int hashCode()
    {
        int result = (this.hostname != null) ? this.hostname.hashCode() : 0;
        result = (31 * result) + this.port;
        result = (31 * result) + this.queryPort;
        result = (31 * result) + this.rconPort;
        result = (31 * result) + ((this.rconPassword != null) ? this.rconPassword.hashCode() : 0);
        result = (31 * result) + this.networkCompressionThreshold;
        result = (31 * result) + this.nettyThreads;
        result = (31 * result) + ((this.onlineMode != null) ? this.onlineMode.hashCode() : 0);
        result = (31 * result) + this.maxPlayers;
        result = (31 * result) + this.playerIdleTimeout;
        result = (31 * result) + ((this.resourcePack != null) ? this.resourcePack.hashCode() : 0);
        result = (31 * result) + ((this.resourcePackHash != null) ? this.resourcePackHash.hashCode() : 0);
        result = (31 * result) + (this.useNativeTransport ? 1 : 0);
        result = (31 * result) + this.viewDistance;
        result = (31 * result) + this.inputThreadPoolSize;
        result = (31 * result) + ((this.administratorsFile != null) ? this.administratorsFile.hashCode() : 0);
        result = (31 * result) + (this.whiteListEnabled ? 1 : 0);
        result = (31 * result) + ((this.whiteListFile != null) ? this.whiteListFile.hashCode() : 0);
        result = (31 * result) + ((this.motd != null) ? this.motd.hashCode() : 0);
        result = (31 * result) + ((this.worlds != null) ? this.worlds.hashCode() : 0);
        return result;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @CfgCustomDefault(OnlineMode.class)
    public @interface CfgOnlineModeDefault
    {
        OnlineMode value();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("hostname", this.hostname).append("port", this.port).append("queryPort", this.queryPort).append("rconPort", this.rconPort).append("rconPassword", this.rconPassword).append("networkCompressionThreshold", this.networkCompressionThreshold).append("onlineMode", this.onlineMode).append("maxPlayers", this.maxPlayers).append("playerIdleTimeout", this.playerIdleTimeout).append("resourcePack", this.resourcePack).append("resourcePackHash", this.resourcePackHash).append("useNativeTransport", this.useNativeTransport).append("viewDistance", this.viewDistance).append("inputThreadPoolSize", this.inputThreadPoolSize).append("administratorsFile", this.administratorsFile).append("whiteListEnabled", this.whiteListEnabled).append("whiteListFile", this.whiteListFile).append("motd", this.motd).append("worlds", this.worlds).toString();
    }
}
