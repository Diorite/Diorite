package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntityLiving;
import org.diorite.impl.entity.IInsentientEntity;
import org.diorite.ImmutableLocation;

abstract class InsentientEntityImpl extends LivingEntityImpl implements IInsentientEntity
{
    InsentientEntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }

    @Override
    public PacketPlayServer getSpawnPacket()
    {
        return new PacketPlayServerSpawnEntityLiving(this);
    }
}
