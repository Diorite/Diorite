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

package org.diorite;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import org.diorite.cfg.DioriteConfig;
import org.diorite.chat.ChatColor;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.DioriteMarkdownParser;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.command.CommandMap;
import org.diorite.command.PluginCommandBuilder;
import org.diorite.command.sender.ConsoleCommandSender;
import org.diorite.entity.Player;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.PluginManager;
import org.diorite.scheduler.Scheduler;
import org.diorite.scheduler.Synchronizable;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.utils.timings.TimingsManager;
import org.diorite.world.WorldsManager;

public interface Core extends Synchronizable
{
    String PREFIX     = ChatColor.GRAY.toString() + "[" + ChatColor.DARK_AQUA.toString() + "Diorite" + ChatColor.GRAY.toString() + "]";
    String PREFIX_RAW = "[Diorite]";
    String PREFIX_MSG = PREFIX + " " + ChatColor.RESET.toString();
    String NANE       = "Diorite";

    int NANOS_IN_MILLI  = 1000000;
    int NANOS_IN_SECOND = NANOS_IN_MILLI * 1000;

    int  DEFAULT_PORT                         = 25565;
    int  DEFAULT_PACKET_COMPRESSION_THRESHOLD = 256;
    int  DEFAULT_TPS                          = 20;
    byte DEFAULT_RENDER_DISTANCE              = 10;
    int  DEFAULT_WAIT_TIME                    = NANOS_IN_SECOND / DEFAULT_TPS;
    int  MAX_NICKNAME_SIZE                    = 16;

    ServerManager getServerManager();

    static String getMinecraftVersion()
    {
        return "1.8.7";
    }

    Logger getLogger();

    String getVersion();

    double[] getRecentTps();

    ItemFactory getItemFactory();

    List<String> getOnlinePlayersNames(String prefix);

    List<String> getOnlinePlayersNames();

    DioriteConfig getConfig();

    WorldsManager getWorldsManager();

    Scheduler getScheduler();

    TimingsManager getTimings();

    void broadcastMessage(ChatPosition position, BaseComponent component);

    void sendConsoleMessage(String str);

    void sendConsoleMessage(BaseComponent component);

    byte getRenderDistance();

    void setRenderDistance(byte renderDistance);

    ConsoleCommandSender getConsoleSender();

    boolean isRunning();

    double getSpeedMutli();

    void setSpeedMutli(double mutli);

    int getTps();

    void resetRecentTps();

    void setTps(int tps);

    void stop();

    void updatePlayerListHeaderAndFooter(BaseComponent header, BaseComponent footer);

    void updatePlayerListHeaderAndFooter(BaseComponent header, BaseComponent footer, Player player);

    void broadcastTitle(BaseComponent title, BaseComponent subtitle, int fadeIn, int stay, int fadeOut);

    void sendTitle(BaseComponent title, BaseComponent subtitle, int fadeIn, int stay, int fadeOut, Player player);

    void removeTitle(Player player);

    void removeAllTitles();

    PluginManager getPluginManager();

    CommandMap getCommandMap();

    PluginCommandBuilder createCommand(DioritePlugin dioritePlugin, String name);

    Collection<Player> getOnlinePlayers();

    Collection<Player> getOnlinePlayers(Predicate<Player> predicate);

    Player getPlayer(UUID uuid);

    default Player getPlayer(final String str)
    {
        for (final Player player : this.getOnlinePlayers())
        {
            if (player.getName().equalsIgnoreCase(str))
            {
                return player;
            }
        }

        for (final Player player : this.getOnlinePlayers())
        {
            if (player.getName().startsWith(str))
            {
                return player;
            }
        }
        return null;
    }

    default Player getPlayerExact(final String str)
    {
        for (final Player player : this.getOnlinePlayers())
        {
            if (player.getName().equalsIgnoreCase(str))
            {
                return player;
            }
        }
        return null;
    }

    default Collection<Player> matchPlayer(final String str)
    {
        return this.getOnlinePlayers().parallelStream().filter(player -> player.getName().startsWith(str)).collect(Collectors.toSet());
    }

    default Collection<Player> matchPlayer(final Pattern pattern)
    {
        return this.getOnlinePlayers().parallelStream().filter(player -> pattern.matcher(player.getName()).matches()).collect(Collectors.toSet());
    }

    default <E extends Collection<Player>> E matchPlayer(final String str, final E target)
    {
        this.getOnlinePlayers().parallelStream().filter(player -> player.getName().startsWith(str)).forEach(target::add);
        return target;
    }

    default <E extends Collection<Player>> E matchPlayer(final Pattern pattern, final E target)
    {
        this.getOnlinePlayers().parallelStream().filter(player -> pattern.matcher(player.getName()).matches()).forEach(target::add);
        return target;
    }

    default Collection<Player> getRandomPlayers(final int num)
    {
        return this.getRandomPlayers(num, new HashSet<>(num));
    }

    default <E extends Collection<Player>> E getRandomPlayers(final int num, final E target)
    {
        return DioriteRandomUtils.getRand(this.getOnlinePlayers(), target, num);
    }

    default Player getRandomPlayer()
    {
        return DioriteRandomUtils.getRand(this.getOnlinePlayers());
    }

    default void broadcastMessage(final ChatPosition position, final String str)
    {
        if (position.equals(ChatPosition.ACTION)) // json don't work for action
        {
            this.broadcastRawMessage(ChatPosition.ACTION, str);
        }
        else
        {
            this.broadcastMessage(position, TextComponent.fromLegacyText(str));
        }
    }

    default void broadcastMessage(final ChatPosition position, final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                if (position.equals(ChatPosition.ACTION)) // json don't work for action
                {
                    this.broadcastRawMessage(ChatPosition.ACTION, str);
                }
                else
                {
                    this.broadcastMessage(position, TextComponent.fromLegacyText(str));
                }
            }
        }
    }

    default void broadcastRawMessage(final ChatPosition position, final String str)
    {
        this.broadcastMessage(position, new TextComponent(str));
    }

    default void broadcastRawMessage(final ChatPosition position, final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.broadcastMessage(position, new TextComponent(str));
            }
        }
    }

    default void broadcastSimpleColoredMessage(final ChatPosition position, final String str)
    {
        if (position.equals(ChatPosition.ACTION)) // json don't work for action
        {
            this.broadcastRawMessage(ChatPosition.ACTION, ChatColor.translateAlternateColorCodesInString(str));
        }
        else
        {
            this.broadcastMessage(position, ChatColor.translateAlternateColorCodes(str));
        }
    }

    default void broadcastSimpleColoredMessage(final ChatPosition position, final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.broadcastSimpleColoredMessage(position, str);
            }
        }
    }

    default void broadcastDioriteMessage(final ChatPosition position, final String str)
    {
        this.broadcastMessage(position, DioriteMarkdownParser.parse(str, false));
    }

    default void broadcastDioriteMessage(final ChatPosition position, final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.broadcastDioriteMessage(position, str);
            }
        }
    }

    default void broadcastMessage(final ChatPosition position, final BaseComponent... components)
    {
        if (components != null)
        {
            for (final BaseComponent component : components)
            {
                this.broadcastMessage(position, component);
            }
        }
    }

    default void broadcastMessage(final String str)
    {
        this.broadcastMessage(TextComponent.fromLegacyText(str));
    }

    default void broadcastMessage(final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.broadcastMessage(TextComponent.fromLegacyText(str));
            }
        }
    }

    default void broadcastSimpleColoredMessage(final String str)
    {
        this.broadcastMessage(ChatColor.translateAlternateColorCodes(str));
    }

    default void broadcastSimpleColoredMessage(final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.broadcastSimpleColoredMessage(str);
            }
        }
    }

    default void broadcastDioriteMessage(final String str)
    {
        this.broadcastMessage(DioriteMarkdownParser.parse(str, false));
    }

    default void broadcastDioriteMessage(final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.broadcastDioriteMessage(str);
            }
        }
    }

    default void broadcastMessage(final BaseComponent component)
    {
        this.broadcastMessage(ChatPosition.SYSTEM, component);
    }

    default void broadcastMessage(final BaseComponent... components)
    {
        if (components != null)
        {
            for (final BaseComponent component : components)
            {
                this.broadcastMessage(component);
            }
        }
    }

    default void sendConsoleMessage(final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendConsoleMessage(str);
            }
        }
    }

    default void sendConsoleMessage(final BaseComponent... components)
    {
        if (components != null)
        {
            for (final BaseComponent component : components)
            {
                this.sendConsoleMessage(component);
            }
        }
    }

    default void sendConsoleSimpleColoredMessage(final String str)
    {
        this.sendConsoleMessage(ChatColor.translateAlternateColorCodesInString(str));
    }

    default void sendConsoleSimpleColoredMessage(final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendConsoleSimpleColoredMessage(str);
            }
        }
    }

    default void sendConsoleDioriteMessage(final String str)
    {
        this.sendConsoleMessage(DioriteMarkdownParser.parse(str, false));
    }

    default void sendConsoleDioriteMessage(final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendConsoleDioriteMessage(str);
            }
        }
    }
}
