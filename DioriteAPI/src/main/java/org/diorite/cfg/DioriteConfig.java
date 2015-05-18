package org.diorite.cfg;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Server;
import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.CfgComments;
import org.diorite.cfg.annotations.CfgFooterComment;
import org.diorite.cfg.annotations.defaults.CfgBooleanDefault;
import org.diorite.cfg.annotations.defaults.CfgCustomDefault;
import org.diorite.cfg.annotations.defaults.CfgIntDefault;
import org.diorite.cfg.annotations.defaults.CfgShortDefault;
import org.diorite.cfg.annotations.defaults.CfgStringDefault;

@SuppressWarnings("HardcodedFileSeparator")
@CfgClass(name = "DioriteConfig")
@CfgComments({"Welcome in Diorite configuration file, in this file you will", "find every option needed to configurate your server."})
@CfgFooterComment("End of configuration!")
@CfgFooterComment("=====================")
public class DioriteConfig
{
    @CfgComment("Used only in few places for debug information, should be short")
    @CfgStringDefault("Main")
    private String serverName = "Main";

    @CfgComment("Hostname to listen on.")
    @CfgStringDefault("localhost")
    private String hostname = "localhost";

    @CfgComment("Port to listen on.")
    @CfgShortDefault(Server.DEFAULT_PORT)
    private int port = Server.DEFAULT_PORT;

    @CfgComment("Enables GameSpy4 protocol server listener. Used to get information about server. Set to -1 to disable.")
    @CfgShortDefault(- 1)
    private int queryPort = - 1;

    @CfgComment("Port for rcon (remote access to the server console), Set to -1 to disable.")
    @CfgShortDefault(- 1)
    private int rconPort = - 1;

    @CfgComment("Password to rcon.")
    @CfgStringDefault("")
    private String rconPassword = "";

    @CfgComment("By default it allows packets that are n-1 bytes big to go normally, but a packet that n bytes or more will be compressed down. 0 -> compress everything, -1 -> disabled")
    @CfgIntDefault(Server.DEFAULT_PACKET_COMPRESSION_THRESHOLD)
    private int networkCompressionThreshold = Server.DEFAULT_PACKET_COMPRESSION_THRESHOLD;

    @CfgComment("Can be true, false or auto. If true all players will be authorized with Mojang, if false everyone can join with any nickname (no connections to mojang). With \"auto\", players that seems to be premium (they have premium nickname) will be authorized with Mojang, other players can join without it. (You should use online mode true, or at least find some authorization plugin for players.)")
    @CfgOnlineModeDefault(OnlineMode.TRUE)
    private OnlineMode onlineMode = OnlineMode.TRUE;

    @CfgComment("The maximum number of players that can play on the server at the same time.")
    @CfgIntDefault(10)
    private int maxPlayers = 10;

    @CfgComment("Players are kicked if they are idle for more than that many seconds.")
    @CfgIntDefault(600)
    private int playerIdleTimeout = 600;

    @CfgComment("Optional URI to a resource pack. The player may choose to use it. Set to empty to disable.")
    @CfgStringDefault("")
    private String resourcePack = "";

    @CfgComment("Optional SHA-1 digest of the resource pack, in lowercase hexadecimal. It's recommended to specify this. This is not yet used to verify the integrity of the resource pack, but improves the effectiveness and reliability of caching.")
    @CfgStringDefault("")
    private String resourcePackHash = "";

    @CfgComment("Linux server performance improvements: optimized packet sending/receiving on Linux. Windows or other systems that don't support epoll will just ignore this setting.")
    @CfgBooleanDefault(true)
    private boolean useNativeTransport = true;

    @CfgComment("It determines the server-side viewing distance, measured in chunks in each direction of the player. (radius)")
    @CfgIntDefault(8)
    private int viewDistance = 8;

    @CfgComment("Path to file with administrators UUIDs/nicknames and settings. (Users that have most of permissions by default.)")
    @CfgStringDefault("adms.yml")
    private File administratorsFile = new File("adms.yml");

    @CfgComment("If true, only players from special file can join to server.")
    @CfgBooleanDefault(false)
    private boolean whiteListEnabled = false;

    @CfgComment("Path to file with UUID/nicknames of players added to whitelist.")
    @CfgStringDefault("whitelist.yml")
    private File whiteListFile = new File("whitelist.yml");

    @CfgComment("Message of the day, used on client server list. You may use JSON message here too.")
    @CfgStringDefault("&7Welcome on &3diorite &7server&3!\n&7Join and play today&3!")
    private String motd = "&7Welcome on &3diorite &7server&3!\n&7Join and play today&3!";

    public String getServerName()
    {
        return this.serverName;
    }

    public void setServerName(final String serverName)
    {
        this.serverName = serverName;
    }

    public String getHostname()
    {
        return this.hostname;
    }

    public void setHostname(final String hostname)
    {
        this.hostname = hostname;
    }

    public int getPort()
    {
        return this.port;
    }

    public void setPort(final int port)
    {
        this.port = port;
    }

    public int getQueryPort()
    {
        return this.queryPort;
    }

    public void setQueryPort(final int queryPort)
    {
        this.queryPort = queryPort;
    }

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

    public int getNetworkCompressionThreshold()
    {
        return this.networkCompressionThreshold;
    }

    public void setNetworkCompressionThreshold(final int networkCompressionThreshold)
    {
        this.networkCompressionThreshold = networkCompressionThreshold;
    }

    public OnlineMode getOnlineMode()
    {
        return this.onlineMode;
    }

    public void setOnlineMode(final OnlineMode onlineMode)
    {
        this.onlineMode = onlineMode;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public void setMaxPlayers(final int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public int getPlayerIdleTimeout()
    {
        return this.playerIdleTimeout;
    }

    public void setPlayerIdleTimeout(final int playerIdleTimeout)
    {
        this.playerIdleTimeout = playerIdleTimeout;
    }

    public String getResourcePack()
    {
        return this.resourcePack;
    }

    public void setResourcePack(final String resourcePack)
    {
        this.resourcePack = resourcePack;
    }

    public String getResourcePackHash()
    {
        return this.resourcePackHash;
    }

    public void setResourcePackHash(final String resourcePackHash)
    {
        this.resourcePackHash = resourcePackHash;
    }

    public boolean isUseNativeTransport()
    {
        return this.useNativeTransport;
    }

    public void setUseNativeTransport(final boolean useNativeTransport)
    {
        this.useNativeTransport = useNativeTransport;
    }

    public int getViewDistance()
    {
        return this.viewDistance;
    }

    public void setViewDistance(final int viewDistance)
    {
        this.viewDistance = viewDistance;
    }

    public File getAdministratorsFile()
    {
        return this.administratorsFile;
    }

    public void setAdministratorsFile(final File administratorsFile)
    {
        this.administratorsFile = administratorsFile;
    }

    public boolean isWhiteListEnabled()
    {
        return this.whiteListEnabled;
    }

    public void setWhiteListEnabled(final boolean whiteListEnabled)
    {
        this.whiteListEnabled = whiteListEnabled;
    }

    public File getWhiteListFile()
    {
        return this.whiteListFile;
    }

    public void setWhiteListFile(final File whiteListFile)
    {
        this.whiteListFile = whiteListFile;
    }

    public String getMotd()
    {
        return this.motd;
    }

    public void setMotd(final String motd)
    {
        this.motd = motd;
    }

    public enum OnlineMode
    {
        TRUE,
        FALSE,
        AUTO
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("serverName", this.serverName).append("hostname", this.hostname).append("port", this.port).append("queryPort", this.queryPort).append("networkCompressionThreshold", this.networkCompressionThreshold).append("onlineMode", this.onlineMode).append("maxPlayers", this.maxPlayers).append("playerIdleTimeout", this.playerIdleTimeout).append("resourcePack", this.resourcePack).append("resourcePackHash", this.resourcePackHash).append("useNativeTransport", this.useNativeTransport).append("viewDistance", this.viewDistance).append("administratorsFile", this.administratorsFile).append("whiteListEnabled", this.whiteListEnabled).append("whiteListFile", this.whiteListFile).append("motd", this.motd).toString();
    }
}
