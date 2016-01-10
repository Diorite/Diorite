package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.entity.ICreatureEntity;
import org.diorite.ImmutableLocation;

abstract class CreatureEntityImpl extends InsentientEntityImpl implements ICreatureEntity
{
    CreatureEntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }

    @Override
    public PacketPlayClientbound[] getSpawnPackets()
    {
        // TODO: add other stuff, like potions
        return new PacketPlayClientbound[]{this.getSpawnPacket()};
    }
}
