package diorite.command;

public interface ConsoleCommandSender extends CommandSender
{
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
    default String getName()
    {
        return "-Diorite-";
    }
}
