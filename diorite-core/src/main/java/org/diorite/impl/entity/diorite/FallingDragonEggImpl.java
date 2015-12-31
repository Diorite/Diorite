package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntity;
import org.diorite.impl.entity.IFallingDragonEgg;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class FallingDragonEggImpl extends FallingBlockImpl implements IFallingDragonEgg
{
    FallingDragonEggImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(IFallingDragonEgg.BASE_SIZE.create(this));
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
        return EntityType.FALLING_DRAGON_EGG;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }
}

