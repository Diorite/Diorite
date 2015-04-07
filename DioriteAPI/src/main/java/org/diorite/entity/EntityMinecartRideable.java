package org.diorite.entity;

import java.util.UUID;

import org.diorite.Diorite;
import org.diorite.ImmutableLocation;
import org.diorite.Server;

public class EntityMinecartRideable implements Entity
{
    private Server            server;
    private int               id;
    private UUID              uniqueId;
    private ImmutableLocation location;

    public EntityMinecartRideable(final int x, final int y, final int z)
    {
        this.location = new ImmutableLocation(x, y, z);
        this.id = 666;
        this.uniqueId = UUID.randomUUID();
        server = Diorite.getServer();
    }

    @Override
    public EntityType getType()
    {
        return EntityType.MINECART_RIDEABLE;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public double getX()
    {
        return location.getX();
    }

    @Override
    public double getZ()
    {
        return location.getZ();
    }

    @Override
    public double getY()
    {
        return location.getY();
    }

    @Override
    public ImmutableLocation getLocation()
    {
        return location;
    }

    @Override
    public Server getServer()
    {
        return server;
    }

    @Override
    public UUID getUniqueID()
    {
        return uniqueId;
    }
}
