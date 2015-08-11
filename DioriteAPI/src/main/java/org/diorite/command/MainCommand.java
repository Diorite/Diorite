package org.diorite.command;

public interface MainCommand extends Command
{
    String getFullName();

    byte getPriority();

    void setPriority(byte priority);
}
