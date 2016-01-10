package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntity;
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
    public PacketPlayClientbound getSpawnPacket()
    {
        return new PacketPlayClientboundSpawnEntity(this);
    }
}

