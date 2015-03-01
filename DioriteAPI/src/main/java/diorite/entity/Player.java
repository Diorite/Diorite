package diorite.entity;

import diorite.command.PlayerCommandSender;

public interface Player extends Entity, PlayerCommandSender
{
    @Override
    default Player getPlayer()
    {
        return this;
    }

    boolean isCrouching();

    void setCrouching(final boolean isCrouching);

    boolean isSprinting();

    void setSprinting(final boolean isSprinting);
}
