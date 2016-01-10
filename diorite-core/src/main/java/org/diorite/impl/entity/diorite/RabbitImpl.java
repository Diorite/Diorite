package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntityLiving;
import org.diorite.impl.entity.IRabbit;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class RabbitImpl extends AnimalEntityImpl implements IRabbit
{
    RabbitImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IRabbit.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
    }

    @Override
    public PacketPlayClientbound getSpawnPacket()
    {
        return new PacketPlayClientboundSpawnEntityLiving(this);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.RABBIT;
    }
}
