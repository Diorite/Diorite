package org.diorite.command.sender;

public interface ConsoleCommandSender extends CommandSender
{
    @Override
    default String getName()
    {
        return "[DioriteConsole]";
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

    @Override
    default boolean setOp(final boolean op)
    {
        return false; // can't change op state of console
    }

    @Override
    default boolean isOp()
    {
        return true;
    }
}
