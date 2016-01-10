package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntity;
import org.diorite.impl.entity.IMinecart;
import org.diorite.impl.entity.IMinecartTNT;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class MinecartTNTImpl extends AbstractMinecartImpl implements IMinecartTNT
{
    MinecartTNTImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(IMinecart.BASE_SIZE.create(this));
    }

    @Override
    public PacketPlayClientbound getSpawnPacket()
    {
        return new PacketPlayClientboundSpawnEntity(this);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
    }

    @Override
    public EntityType getType()
    {
        return EntityType.MINECART_TNT;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }
}

