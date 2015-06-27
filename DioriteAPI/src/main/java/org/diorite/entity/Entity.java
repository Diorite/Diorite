package org.diorite.entity;

import org.diorite.GameObject;
import org.diorite.ImmutableLocation;
import org.diorite.Server;
import org.diorite.scheduler.Synchronizable;

public interface Entity extends GameObject, Synchronizable
{
    EntityType getType();

    float getVelocityX();

    float getVelocityY();

    float getVelocityZ();

    boolean exist();

    int getId();

    double getX();

    double getZ();

    double getY();

    ImmutableLocation getLocation();

    Server getServer();

    default int getMcId()
    {
        return this.getType().getMcId();
    }
}
