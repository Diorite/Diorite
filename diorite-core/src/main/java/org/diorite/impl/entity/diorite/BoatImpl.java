package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntity;
import org.diorite.impl.entity.IBoat;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class BoatImpl extends EntityImpl implements IBoat
{
    BoatImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IBoat.META_KEYS);
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
        final EntityMetadata metadata = this.getMetadata();
        metadata.add(new EntityMetadataIntEntry(META_KEY_BOAT_TIME_SINCE_LAST_HIT, 0));
        metadata.add(new EntityMetadataIntEntry(META_KEY_BOAT_FORWARD_DIRECTION, 0));
        metadata.add(new EntityMetadataFloatEntry(META_KEY_BOAT_DAMAGE_TAKEN, 0));
        metadata.add(new EntityMetadataIntEntry(META_KEY_BOAT_TYPE, 0));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.BOAT;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }
}

