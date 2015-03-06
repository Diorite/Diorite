package diorite.entity;

import java.util.UUID;

import diorite.ImmutableLocation;
import diorite.Server;

public interface Entity
{
    int getId();

    UUID getUniqueID();

    double getX();

    double getZ();

    double getY();

    ImmutableLocation getLocation();

    Server getServer();
}
