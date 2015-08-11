package org.diorite.command.sender;

public interface ConsoleCommandSender extends CommandSender
{
    @Override
    default String getName()
    {
        return "-Diorite-";
    }

    @Override
    default boolean isConsole()
    {
        return true;
    }

    @Override
    default boolean isPlayer()
    {
        return false;
    }

    @Override
    default boolean isCommandBlock()
    {
        return false;
    }

    @Override
    default void sendRawMessage(final String str)
    {
        this.sendMessage(str);
    }
}
