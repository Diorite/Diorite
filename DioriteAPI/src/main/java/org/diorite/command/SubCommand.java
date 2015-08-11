package org.diorite.command;

public interface SubCommand extends Command
{
    Command getParent();
}
