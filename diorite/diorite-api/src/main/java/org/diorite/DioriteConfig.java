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

package org.diorite;

import javax.annotation.Nullable;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.diorite.config.Config;
import org.diorite.config.ConfigManager;
import org.diorite.config.Property;
import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.Footer;
import org.diorite.config.annotations.Header;
import org.diorite.config.annotations.Unmodifiable;

/**
 * Configuration file of diorite, remember that config object will not reflect changes at runtime, like changing MOTD using diorite API, will not change MOTD
 * returned by instance of this class.
 */
@Header({"Welcome to the diorite configuration file!"})
@Footer("End of file ;)")
@SuppressWarnings("MagicNumber")
public interface DioriteConfig extends Config
{
    @Comment("List of IPs to be bound to.")
    @Unmodifiable
    default Set<InetSocketAddress> getHosts()
    {
        return Set.of(new InetSocketAddress("0.0.0.0", Diorite.DEFAULT_PORT));
    }

    @Comment("Default configuration of each host.")
    default HostConfiguration getDefaultHostConfiguration()
    {
        return ConfigManager.createInstance(HostConfiguration.class);
    }

    @Comment("You can use separate configuration for each host.")
    @Property(name = "hostConfiguration")
    private Map<InetSocketAddress, HostConfiguration> getHostConfiguration() {return Map.of();}

    @Nullable
    HostConfiguration getHostConfigurationBy(InetSocketAddress socketAddress);

    default HostConfiguration getHostConfigurationOrDefault(InetSocketAddress socketAddress)
    {
        HostConfiguration configuration = this.getHostConfigurationBy(socketAddress);
        if (configuration != null)
        {
            return configuration;
        }
        return this.getDefaultHostConfiguration();
    }

    @Comment("List of enabled protocols.")
    default Set<String> getProtocols()
    {
        return Set.of(Diorite.getMinecraftVersion());
    }

    @Property(name = "protocolSettings")
    private Map<String, ProtocolSettings> getProtocolSettings() {return Map.of();}

    default ProtocolSettings getDefaultProtocolSettings()
    {
        return ConfigManager.createInstance(ProtocolSettings.class);
    }

    @Nullable
    ProtocolSettings getFromProtocolSettings(String name);

    @Nullable
    default ProtocolSettings findProtocol(Collection<String> aliases)
    {
        boolean any = false;
        for (String alias : aliases)
        {
            if (! this.getProtocols().contains(alias))
            {
                continue;
            }
            any = true;
            ProtocolSettings protocols = this.getFromProtocolSettings(alias);
            if (protocols != null)
            {
                return protocols;
            }
        }
        if (any)
        {
            return this.getDefaultProtocolSettings();
        }
        return null;
    }

    @Comment("Server name, may be used by other plugins.")
    default String getServerName() {return "diorite";}

    @Comment("Players are kicked if they are idle for too long. (In seconds)")
    default int getPlayerTimeout() {return 600;}

    @Comment("Optional URI to a resource pack. Set to null to disable.")
    URI getResourcePack();

    @Comment(
            "Optional SHA-1 digest of the resource pack, in lowercase hexadecimal. It's recommended to specify this. This is not yet used to verify the " +
            "integrity of the resource pack, but improves the effectiveness and reliability of caching.")
    default String getResourcePackHash() {return "";}

    @Comment("It determines the server-side viewing distance, measured in chunks in each direction of the player. (radius)")
    default int getViewDistance() {return 8;}

    @Comment("Path to plugins directory.")
    default File getPluginsDirectories() {return new File("plugins");}

    @Comment("If true, only players from special file can join to server.")
    default boolean getWhiteListEnabled() {return false;}

    @Comment("Path to file with UUID/nicknames of players added to whitelist.")
    default File getWhiteListFile() {return new File("whitelist.yml");}

    @Comment("Languages to load, by enabling more languages you are allowing players to select one.")
    default Locale[] getLanguages() {return new Locale[]{Locale.forLanguageTag("en-US")};}

    @Comment("Metrics UUID.")
    default UUID getMetricsUuid() {return UUID.randomUUID();}

    @Comment(
            "Linux server performance improvements: optimized packet sending/receiving on Linux. Windows or other systems that don't support epoll will " +
            "just ignore this setting.")
    default boolean getUseNativeTransport() {return true;}

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

    interface HostConfiguration extends Config
    {
        @Comment("Amount of netty event loop threads.")
        default int getNettyThreads() {return 4;}

        @Comment("Connection throttling time in ms.")
        default int getConnectionThrottle() {return 1000;}

        @Comment(
                "Can be true, false or auto. If true all players will be authorized with Mojang, if false everyone can join with any nickname (no connections" +
                " to " +
                "mojang). With \"auto\", players that seems to be premium (they have premium nickname) will be authorized with Mojang, other players can join" +
                " " +
                "without it. (You should use online mode true, or at least find some authorization plugin for players.)")
        default OnlineMode getOnlineMode() {return OnlineMode.TRUE;}

        @Comment("The maximum number of players on server.")
        default int getMaxPlayers() {return 10;}

        @Comment("Default server motd, it can be changed at runtime.")
        default String getMotd() {return "  &fThis server is using &9diorite&f!\n    &7<&8==== &a#&9OnlyDiorite &8====&7>";}

        @Nullable
        @Comment("Enables GameSpy4 protocol server listener. Used to get information about server by other services.")
        Integer getQueryPort();

        @Nullable
        @Comment("Port for rcon (remote access to the server console).")
        Integer getRconPort();

        @Comment("Password to rcon.")
        default String getRconPassword() {return "";}

        @Comment("If host will be used only for query/rcon then you can disable accepting players on it.")
        default boolean isAcceptingPlayers() {return true;}
    }

    interface ProtocolSettings extends Config
    {
        @Comment({"By default it allows packets that are n-1 bytes big to go normally, but a packet that n bytes or more will be compressed down. 0 -> " +
                  "compress " +
                  "everything, -1 -> disabled",
                  "Many packets below 128 bytes may be even larger after compression!"})
        default int getNetworkCompressionThreshold() {return 128;}

        @Comment("Max size of compressed incoming packets, set to -1 to disable limits, set to 0 to let diorite calculate best size.")
        default int getMaxServerboundCompressedPacketSize() {return 0;}

        void setMaxServerboundCompressedPacketSize(int size);

        @Comment({"Max size of uncompressed incoming packets, set to -1 to disable limits, set to 0 to let diorite calculate best size.",
                  "If size of packet is constant, it will be used as limit ignoring this setting."})
        default int getMaxServerboundPacketSize() {return 0;}

        void setMaxServerboundPacketSize(int size);
    }

//    // TODO Separate settings per each gamemode, move that to separate file, special settings for special packets, like creative inventory, chat,
//    interface AntiCheatSettings
//    {
//        @Comment({"Maximum amount of processed packets per tick for each player, next packets will be added to queue and wait until next tick",
//                  "Players can send multiple packets per tick when performing some inventory actions."})
//        default int getMaxProcessedPacketsPerTickPerPlayer() {return 20;}
//
//        @Comment({"Maximum amount of processed packets per second for each player, next packets will be added to queue and wait until next tick",
//                  "Each player can do many operations in each tick, like when accessing inventory, but they should not do that all the time."})
//        default int getMaxProcessedPacketsPerSecondPerPlayer() {return 100;}
//
//        @Comment({"Maximum size of packets waiting for processing, if player will extend this limit, player will be kicked from server."})
//        default int getMaxPacketQueueSizePerPlayer() {return 100;}
//
//        @Comment({"Maximum byte size of packets waiting for processing, if player will extend this limit, player will be kicked from server."})
//        default int getMaxPacketQueueByteSizePerPlayer() {return 1024 * 10;}
//
//        default int getMaxIncomingBytesPerSecond(){return 1024;}
//    }
}
