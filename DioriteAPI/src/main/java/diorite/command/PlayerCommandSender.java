package diorite.command;

import diorite.entity.Player;

public interface PlayerCommandSender extends CommandSender
{
    @Override
    default boolean isConsole()
    {
        return false;
    }

    @Override
    default boolean isPlayer()
    {
        return true;
    }

    @Override
    default boolean isCommandBlock()
    {
        return false;
    }

    Player getPlayer();
}
