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
import org.diorite.world.WorldsManager;

public final class Diorite
{
    private static Server server;

    //TODO: make sure that it contains all metohds
    private Diorite()
    {
    }

    public static void setServer(final Server server)
    {
        if (Diorite.server != null)
        {
            throw new RuntimeException("Server instance can't be changed at runtime");
        }
        Diorite.server = server;
    }

    public static Server getServer()
    {
        return server;
    }

    public static String getVersion()
    {
        return server.getVersion();
    }

    public static void sendConsoleMessage(final BaseComponent component)
    {
        server.sendConsoleMessage(component);
    }

    public static void broadcastSimpleColoredMessage(final String str)
    {
        server.broadcastSimpleColoredMessage(str);
    }

    public static Collection<Player> matchPlayer(final Pattern pattern)
    {
        return server.matchPlayer(pattern);
    }

    public static void broadcastMessage(final ChatPosition position, final BaseComponent component)
    {
        server.broadcastMessage(position, component);
    }

    public static void broadcastTitle(final BaseComponent title, final BaseComponent subtitle, final int fadeIn, final int stay, final int fadeOut)
    {
        server.broadcastTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    public static WorldsManager getWorldsManager()
    {
        return server.getWorldsManager();
    }

    public static void broadcastMessage(final ChatPosition position, final String str)
    {
        server.broadcastMessage(position, str);
    }

    public static void setRenderDistance(final byte renderDistance)
    {
        server.setRenderDistance(renderDistance);
    }

    public static ConsoleCommandSender getConsoleSender()
    {
        return server.getConsoleSender();
    }

    public static Collection<Player> matchPlayer(final String str)
    {
        return server.matchPlayer(str);
    }

    public static List<String> getOnlinePlayersNames()
    {
        return server.getOnlinePlayersNames();
    }

    public static void broadcastSimpleColoredMessage(final ChatPosition position, final String... strs)
    {
        server.broadcastSimpleColoredMessage(position, strs);
    }

    public static double getMutli()
    {
        return server.getSpeedMutli();
    }

    public static void removeAllTitles()
    {
        server.removeAllTitles();
    }

    public static void updatePlayerListHeaderAndFooter(final BaseComponent header, final BaseComponent footer)
    {
        server.updatePlayerListHeaderAndFooter(header, footer);
    }

    public static void broadcastSimpleColoredMessage(final ChatPosition position, final String str)
    {
        server.broadcastSimpleColoredMessage(position, str);
    }

    public static Collection<Player> getOnlinePlayers()
    {
        return server.getOnlinePlayers();
    }

    public static void sendTitle(final BaseComponent title, final BaseComponent subtitle, final int fadeIn, final int stay, final int fadeOut, final Player player)
    {
        server.sendTitle(title, subtitle, fadeIn, stay, fadeOut, player);
    }

    public static void broadcastDioriteMessage(final ChatPosition position, final String... strs)
    {
        server.broadcastDioriteMessage(position, strs);
    }

    public static void sendConsoleDioriteMessage(final String str)
    {
        server.sendConsoleDioriteMessage(str);
    }

    public static <E extends Collection<Player>> E matchPlayer(final Pattern pattern, final E target)
    {
        return server.matchPlayer(pattern, target);
    }

    public static void broadcastMessage(final ChatPosition position, final BaseComponent... components)
    {
        server.broadcastMessage(position, components);
    }

    public static PluginCommandBuilder createCommand(final DioritePlugin dioritePlugin, final String name)
    {
        return server.createCommand(dioritePlugin, name);
    }

    public static void sendConsoleMessage(final String... strs)
    {
        server.sendConsoleMessage(strs);
    }

    public static void broadcastDioriteMessage(final String str)
    {
        server.broadcastDioriteMessage(str);
    }

    public static List<String> getOnlinePlayersNames(final String prefix)
    {
        return server.getOnlinePlayersNames(prefix);
    }

    public static void broadcastRawMessage(final ChatPosition position, final String str)
    {
        server.broadcastRawMessage(position, str);
    }

    public static void broadcastMessage(final String... strs)
    {
        server.broadcastMessage(strs);
    }

    public static <E extends Collection<Player>> E matchPlayer(final String str, final E target)
    {
        return server.matchPlayer(str, target);
    }

    public static void broadcastMessage(final BaseComponent component)
    {
        server.broadcastMessage(component);
    }

    public static void broadcastSimpleColoredMessage(final String... strs)
    {
        server.broadcastSimpleColoredMessage(strs);
    }

    public static void sendConsoleMessage(final BaseComponent... components)
    {
        server.sendConsoleMessage(components);
    }

    public static Player getPlayer(final UUID uuid)
    {
        return server.getPlayer(uuid);
    }

    public static void broadcastDioriteMessage(final ChatPosition position, final String str)
    {
        server.broadcastDioriteMessage(position, str);
    }

    public static DioriteConfig getConfig()
    {
        return server.getConfig();
    }

    public static PluginManager getPluginManager()
    {
        return server.getPluginManager();
    }

    public static int getTps()
    {
        return server.getTps();
    }

    public static <E extends Collection<Player>> E getRandomPlayers(final int num, final E target)
    {
        return server.getRandomPlayers(num, target);
    }

    public static Player getRandomPlayer()
    {
        return server.getRandomPlayer();
    }

    public static CommandMap getCommandMap()
    {
        return server.getCommandMap();
    }

    public static Player getPlayer(final String str)
    {
        return server.getPlayer(str);
    }

    public static Collection<Player> getRandomPlayers(final int num)
    {
        return server.getRandomPlayers(num);
    }

    public static double[] getRecentTps()
    {
        return server.getRecentTps();
    }

    public static boolean isRunning()
    {
        return server.isRunning();
    }

    public static void setTps(final int tps)
    {
        server.setTps(tps);
    }

    public static void removeTitle(final Player player)
    {
        server.removeTitle(player);
    }

    public static Collection<Player> getOnlinePlayers(final Predicate<Player> predicate)
    {
        return server.getOnlinePlayers(predicate);
    }

    public static void stop()
    {
        server.stop();
    }

    public static void sendConsoleSimpleColoredMessage(final String str)
    {
        server.sendConsoleSimpleColoredMessage(str);
    }

    public static Scheduler getScheduler()
    {
        return server.getScheduler();
    }

    public static void sendConsoleDioriteMessage(final String... strs)
    {
        server.sendConsoleDioriteMessage(strs);
    }

    public static void sendConsoleSimpleColoredMessage(final String... strs)
    {
        server.sendConsoleSimpleColoredMessage(strs);
    }

    public static void broadcastMessage(final String str)
    {
        server.broadcastMessage(str);
    }

    public static void updatePlayerListHeaderAndFooter(final BaseComponent header, final BaseComponent footer, final Player player)
    {
        server.updatePlayerListHeaderAndFooter(header, footer, player);
    }

    public static void broadcastDioriteMessage(final String... strs)
    {
        server.broadcastDioriteMessage(strs);
    }

    public static void broadcastMessage(final BaseComponent... components)
    {
        server.broadcastMessage(components);
    }

    public static Player getPlayerExact(final String str)
    {
        return server.getPlayerExact(str);
    }

    public static void broadcastRawMessage(final ChatPosition position, final String... strs)
    {
        server.broadcastRawMessage(position, strs);
    }

    public static byte getRenderDistance()
    {
        return server.getRenderDistance();
    }

    public static void setMutli(final double mutli)
    {
        server.setSpeedMutli(mutli);
    }

    public static void sendConsoleMessage(final String str)
    {
        server.sendConsoleMessage(str);
    }

    public static void broadcastMessage(final ChatPosition position, final String... strs)
    {
        server.broadcastMessage(position, strs);
    }
}
