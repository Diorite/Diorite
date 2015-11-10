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
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.diorite.cfg.DioriteConfig;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.command.CommandMap;
import org.diorite.command.PluginCommandBuilder;
import org.diorite.command.sender.ConsoleCommandSender;
import org.diorite.entity.Player;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.PluginManager;
import org.diorite.scheduler.Scheduler;
import org.diorite.utils.timings.TimingsManager;
import org.diorite.world.WorldsManager;

public final class Diorite
{
    private static Core core;

    //TODO: make sure that it contains all metohds
    private Diorite()
    {
    }

    public static void setCore(final Core core)
    {
        if (Diorite.core != null)
        {
            throw new RuntimeException("Server instance can't be changed at runtime");
        }
        Diorite.core = core;
    }

    public static Core getCore()
    {
        return core;
    }

    public static String getVersion()
    {
        return core.getVersion();
    }

    public static void sendConsoleMessage(final BaseComponent component)
    {
        core.sendConsoleMessage(component);
    }

    public static void broadcastSimpleColoredMessage(final String str)
    {
        core.broadcastSimpleColoredMessage(str);
    }

    public static Collection<Player> matchPlayer(final Pattern pattern)
    {
        return core.matchPlayer(pattern);
    }

    public static void broadcastMessage(final ChatPosition position, final BaseComponent component)
    {
        core.broadcastMessage(position, component);
    }

    public static void broadcastTitle(final BaseComponent title, final BaseComponent subtitle, final int fadeIn, final int stay, final int fadeOut)
    {
        core.broadcastTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    public static WorldsManager getWorldsManager()
    {
        return core.getWorldsManager();
    }

    public static void broadcastMessage(final ChatPosition position, final String str)
    {
        core.broadcastMessage(position, str);
    }

    public static void setRenderDistance(final byte renderDistance)
    {
        core.setRenderDistance(renderDistance);
    }

    public static ConsoleCommandSender getConsoleSender()
    {
        return core.getConsoleSender();
    }

    public static Collection<Player> matchPlayer(final String str)
    {
        return core.matchPlayer(str);
    }

    public static List<String> getOnlinePlayersNames()
    {
        return core.getOnlinePlayersNames();
    }

    public static void broadcastSimpleColoredMessage(final ChatPosition position, final String... strs)
    {
        core.broadcastSimpleColoredMessage(position, strs);
    }

    public static double getMutli()
    {
        return core.getSpeedMutli();
    }

    public static void removeAllTitles()
    {
        core.removeAllTitles();
    }

    public static void updatePlayerListHeaderAndFooter(final BaseComponent header, final BaseComponent footer)
    {
        core.updatePlayerListHeaderAndFooter(header, footer);
    }

    public static void broadcastSimpleColoredMessage(final ChatPosition position, final String str)
    {
        core.broadcastSimpleColoredMessage(position, str);
    }

    public static Collection<Player> getOnlinePlayers()
    {
        return core.getOnlinePlayers();
    }

    public static void sendTitle(final BaseComponent title, final BaseComponent subtitle, final int fadeIn, final int stay, final int fadeOut, final Player player)
    {
        core.sendTitle(title, subtitle, fadeIn, stay, fadeOut, player);
    }

    public static void broadcastDioriteMessage(final ChatPosition position, final String... strs)
    {
        core.broadcastDioriteMessage(position, strs);
    }

    public static void sendConsoleDioriteMessage(final String str)
    {
        core.sendConsoleDioriteMessage(str);
    }

    public static <E extends Collection<Player>> E matchPlayer(final Pattern pattern, final E target)
    {
        return core.matchPlayer(pattern, target);
    }

    public static void broadcastMessage(final ChatPosition position, final BaseComponent... components)
    {
        core.broadcastMessage(position, components);
    }

    public static PluginCommandBuilder createCommand(final DioritePlugin dioritePlugin, final String name)
    {
        return core.createCommand(dioritePlugin, name);
    }

    public static void sendConsoleMessage(final String... strs)
    {
        core.sendConsoleMessage(strs);
    }

    public static void broadcastDioriteMessage(final String str)
    {
        core.broadcastDioriteMessage(str);
    }

    public static List<String> getOnlinePlayersNames(final String prefix)
    {
        return core.getOnlinePlayersNames(prefix);
    }

    public static void broadcastRawMessage(final ChatPosition position, final String str)
    {
        core.broadcastRawMessage(position, str);
    }

    public static void broadcastMessage(final String... strs)
    {
        core.broadcastMessage(strs);
    }

    public static <E extends Collection<Player>> E matchPlayer(final String str, final E target)
    {
        return core.matchPlayer(str, target);
    }

    public static void broadcastMessage(final BaseComponent component)
    {
        core.broadcastMessage(component);
    }

    public static void broadcastSimpleColoredMessage(final String... strs)
    {
        core.broadcastSimpleColoredMessage(strs);
    }

    public static void sendConsoleMessage(final BaseComponent... components)
    {
        core.sendConsoleMessage(components);
    }

    public static Player getPlayer(final UUID uuid)
    {
        return core.getPlayer(uuid);
    }

    public static void broadcastDioriteMessage(final ChatPosition position, final String str)
    {
        core.broadcastDioriteMessage(position, str);
    }

    public static DioriteConfig getConfig()
    {
        return core.getConfig();
    }

    public static PluginManager getPluginManager()
    {
        return core.getPluginManager();
    }

    public static ServerManager getServerManager()
    {
        return core.getServerManager();
    }

    public static int getTps()
    {
        return core.getTps();
    }

    public static <E extends Collection<Player>> E getRandomPlayers(final int num, final E target)
    {
        return core.getRandomPlayers(num, target);
    }

    public static Player getRandomPlayer()
    {
        return core.getRandomPlayer();
    }

    public static CommandMap getCommandMap()
    {
        return core.getCommandMap();
    }

    public static Player getPlayer(final String str)
    {
        return core.getPlayer(str);
    }

    public static Collection<Player> getRandomPlayers(final int num)
    {
        return core.getRandomPlayers(num);
    }

    public static double[] getRecentTps()
    {
        return core.getRecentTps();
    }

    public static boolean isRunning()
    {
        return core.isRunning();
    }

    public static void setTps(final int tps)
    {
        core.setTps(tps);
    }

    public static void removeTitle(final Player player)
    {
        core.removeTitle(player);
    }

    public static Collection<Player> getOnlinePlayers(final Predicate<Player> predicate)
    {
        return core.getOnlinePlayers(predicate);
    }

    public static void stop()
    {
        core.stop();
    }

    public static void sendConsoleSimpleColoredMessage(final String str)
    {
        core.sendConsoleSimpleColoredMessage(str);
    }

    public static Scheduler getScheduler()
    {
        return core.getScheduler();
    }

    public static TimingsManager getTimings()
    {
        return core.getTimings();
    }

    public static void sendConsoleDioriteMessage(final String... strs)
    {
        core.sendConsoleDioriteMessage(strs);
    }

    public static void sendConsoleSimpleColoredMessage(final String... strs)
    {
        core.sendConsoleSimpleColoredMessage(strs);
    }

    public static void broadcastMessage(final String str)
    {
        core.broadcastMessage(str);
    }

    public static void updatePlayerListHeaderAndFooter(final BaseComponent header, final BaseComponent footer, final Player player)
    {
        core.updatePlayerListHeaderAndFooter(header, footer, player);
    }

    public static void broadcastDioriteMessage(final String... strs)
    {
        core.broadcastDioriteMessage(strs);
    }

    public static void broadcastMessage(final BaseComponent... components)
    {
        core.broadcastMessage(components);
    }

    public static Player getPlayerExact(final String str)
    {
        return core.getPlayerExact(str);
    }

    public static void broadcastRawMessage(final ChatPosition position, final String... strs)
    {
        core.broadcastRawMessage(position, strs);
    }

    public static byte getRenderDistance()
    {
        return core.getRenderDistance();
    }

    public static void setMutli(final double mutli)
    {
        core.setSpeedMutli(mutli);
    }

    public static void sendConsoleMessage(final String str)
    {
        core.sendConsoleMessage(str);
    }

    public static void broadcastMessage(final ChatPosition position, final String... strs)
    {
        core.broadcastMessage(position, strs);
    }
}
