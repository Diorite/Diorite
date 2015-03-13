package diorite.entity;

import diorite.GameObject;
import diorite.ImmutableLocation;
import diorite.Server;

public interface Entity extends GameObject
{
    int getId();

    double getX();

    double getZ();

    double getY();

    ImmutableLocation getLocation();

    Server getServer();
}
