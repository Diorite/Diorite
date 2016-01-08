package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntity;
import org.diorite.impl.entity.IMinecart;
import org.diorite.impl.entity.IMinecartChest;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class MinecartChestImpl extends AbstractMinecartImpl implements IMinecartChest
{
    MinecartChestImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(IMinecart.BASE_SIZE.create(this));
    }

    @Override
    public PacketPlayServer getSpawnPacket()
    {
        return new PacketPlayServerSpawnEntity(this);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
    }

    @Override
    public EntityType getType()
    {
        return EntityType.MINECART_CHEST;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }
}

