package org.diorite.entity;

import org.diorite.GameObject;
import org.diorite.ImmutableLocation;
import org.diorite.Server;

public interface Entity extends GameObject
{
    int getId();

    double getX();

    double getZ();

    double getY();

    ImmutableLocation getLocation();

    Server getServer();
}
