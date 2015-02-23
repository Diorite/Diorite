package diorite.command;

import diorite.Server;

public interface CommandSender
{
    String getName();

    boolean isConsole();

    boolean isPlayer();

    boolean isCommandBlock();

    void sendMessage(String str);

    Server getServer();
}
