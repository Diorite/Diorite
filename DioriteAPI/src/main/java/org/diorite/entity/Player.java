package org.diorite.entity;

import org.diorite.command.PlayerCommandSender;

public interface Player extends AttributableEntity, PlayerCommandSender
{
    float WALK_SPEED         = 0.1f;
    float FLY_SPEED          = 0.05f;
    float SPRINT_SPEED_BOOST = 0.30000001192092896f;

    @Override
    default Player getPlayer()
    {
        return this;
    }

    boolean isCrouching();

    void setCrouching(final boolean isCrouching);

    boolean isSprinting();

    void setSprinting(final boolean isSprinting);

    byte getViewDistance();

    byte getRenderDistance();

    void setRenderDistance(byte viewDistance);
}
