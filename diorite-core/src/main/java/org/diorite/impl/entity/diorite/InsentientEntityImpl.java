package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntityLiving;
import org.diorite.impl.entity.IInsentientEntity;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.ImmutableLocation;

abstract class InsentientEntityImpl extends LivingEntityImpl implements IInsentientEntity
{
    InsentientEntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IInsentientEntity.META_KEYS);
    }

    @Override
    public PacketPlayClientbound getSpawnPacket()
    {
        return new PacketPlayClientboundSpawnEntityLiving(this);
    }
}
