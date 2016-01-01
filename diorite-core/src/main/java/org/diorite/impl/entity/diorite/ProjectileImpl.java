package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntity;
import org.diorite.impl.entity.EntityObject;
import org.diorite.impl.entity.IProjectile;
import org.diorite.ImmutableLocation;

abstract class ProjectileImpl extends EntityImpl implements IProjectile, EntityObject
{
    ProjectileImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
    }

    @Override
    public PacketPlayServer getSpawnPacket()
    {
        return new PacketPlayServerSpawnEntity(this);
    }
}

