package diorite.entity;

import java.util.UUID;

import diorite.Server;

public interface Entity
{
    int getId();

    UUID getUniqueID();

    double getX();

    double getZ();

    double getY();

    Server getServer();
}
