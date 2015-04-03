package org.diorite.entity;

import org.diorite.GameMode;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.command.sender.PlayerCommandSender;

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

    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    int getPing(); // may not be accurate.

    void kick(BaseComponent s);

    boolean isCrouching();

    void setCrouching(final boolean isCrouching);

    boolean isSprinting();

    void setSprinting(final boolean isSprinting);

    byte getViewDistance();

    byte getRenderDistance();

    void setRenderDistance(byte viewDistance);

    boolean canFly();

    void setCanFly(boolean value);

    void setCanFly(boolean value, double flySpeed);

    float getFlySpeed();

    void setFlySpeed(double flySpeed);

    float getWalkSpeed();

    void setWalkSpeed(double walkSpeed);


    default void kick(final String s)
    {
        this.kick(new TextComponent(s));
    }
}
