package diorite;

import diorite.command.CommandMap;
import diorite.command.ConsoleCommandSender;
import diorite.command.PluginCommandBuilder;
import diorite.plugin.Plugin;

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

    double[] getRecentTps();

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
