package org.diorite.entity;

import java.util.Collection;

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

    Collection<? extends Entity> getNearbyEntities(double x, double y, double z);

    <T extends Entity> Collection<? extends T> getNearbyEntities(double x, double y, double z, Class<? extends T> type);

    <T extends Entity> Collection<? extends T> getNearbyEntities(double x, double y, double z, EntityType type);

    default int getMcId()
    {
        return this.getType().getMcId();
    }
}
