package org.diorite.command;

public interface MainCommand extends Command
{

    byte getPriority();

    void setPriority(byte priority);
}
