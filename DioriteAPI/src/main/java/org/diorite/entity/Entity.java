package org.diorite.entity;

import java.util.List;

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

    List<Entity> getNearbyEntities(double x, double y, double z);

    default int getMcId()
    {
        return this.getType().getMcId();
    }
}
