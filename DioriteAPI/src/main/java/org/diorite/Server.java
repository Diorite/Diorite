package org.diorite;

import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.command.CommandMap;
import org.diorite.command.sender.ConsoleCommandSender;
import org.diorite.command.PluginCommandBuilder;
import org.diorite.plugin.Plugin;

public interface Server
{
    String NANE    = "Diorite";
    String VERSION = "0.1-SNAPSHOT";

    int NANOS_IN_MILLI  = 1000000;
    int NANOS_IN_SECOND = NANOS_IN_MILLI * 1000;

    int    DEFAULT_PORT            = 25565;
    String DEFAULT_SERVER          = "main";
    int    DEFAULT_TPS             = 20;
    byte   DEFAULT_RENDER_DISTANCE = 10;
    int    DEFAULT_WAIT_TIME       = NANOS_IN_SECOND / DEFAULT_TPS;
    int    MAX_NICKNAME_SIZE       = 16;

    void broadcastMessage(ChatPosition position, String str);

    void broadcastMessage(ChatPosition position, String... strs);

    void broadcastSimpleColoredMessage(ChatPosition position, String str);

    void broadcastSimpleColoredMessage(ChatPosition position, String... strs);

    void broadcastDioriteMessage(ChatPosition position, String str);

    void broadcastDioriteMessage(ChatPosition position, String... strs);

    void broadcastMessage(ChatPosition position, BaseComponent component);

    void broadcastMessage(ChatPosition position, BaseComponent... components);

    void broadcastMessage(String str);

    void broadcastMessage(String... strs);

    void broadcastSimpleColoredMessage(String str);

    void broadcastSimpleColoredMessage(String... strs);

    void broadcastDioriteMessage(String str);

    void broadcastDioriteMessage(String... strs);

    void broadcastMessage(BaseComponent component);

    void broadcastMessage(BaseComponent... components);

    void sendConsoleMessage(String str);

    void sendConsoleMessage(String... strs);

    void sendConsoleMessage(BaseComponent component);

    void sendConsoleMessage(BaseComponent... components);

    void sendConsoleSimpleColoredMessage(String str);

    void sendConsoleSimpleColoredMessage(String... strs);

    void sendConsoleDioriteMessage(String str);

    void sendConsoleDioriteMessage(String... strs);

    byte getRenderDistance();

    void setRenderDistance(byte renderDistance);

    ConsoleCommandSender getConsoleSender();

    boolean isRunning();

    double getMutli();

    void setMutli(double mutli);

    int getTps();

    void setTps(int tps);

    void stop();

    CommandMap getCommandMap();

    PluginCommandBuilder createCommand(Plugin plugin, String name);

    String getServerName();
}
