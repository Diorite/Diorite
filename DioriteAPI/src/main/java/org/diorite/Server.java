package org.diorite;

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
